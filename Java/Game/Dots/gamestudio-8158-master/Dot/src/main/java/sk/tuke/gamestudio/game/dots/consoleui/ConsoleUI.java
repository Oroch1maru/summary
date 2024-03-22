package sk.tuke.gamestudio.game.dots.consoleui;

import sk.tuke.gamestudio.game.dots.core.Direction;
import sk.tuke.gamestudio.game.dots.core.Field;
import sk.tuke.gamestudio.game.dots.core.FieldState;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.*;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private Field field;

    private String nickname;
    private String feadback;
    private String wrongIput = "\u001B[31mWrong input\u001B[0m";

    private static final Pattern inputFirstDotPattern = Pattern.compile("([A-F])\\s*([1-6])\\s*");
    private static final Pattern inputNextDotPattern = Pattern.compile("(S|W|N|E|\\s)+");

    private static final Pattern inputNickname = Pattern.compile("[A-Za-z0-9]+");
    private static final Pattern inputRating = Pattern.compile("[1-5]");
    private static final Pattern questionOfRepeat = Pattern.compile("[YN]|(YES|NO)+");
    private static final Pattern inputStart = Pattern.compile("\\s*[RHSE]\\s*");


    private Scanner scanner = new Scanner(System.in);

    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();
    private RatingService ratingService = new RatingServiceJDBC();


    public ConsoleUI(Field field){
        this.field=field;
    }



    public void play(){
//        scoreService.reset();
//        commentService.reset();
//        ratingService.reset();
        greeting();
        while(field.getState() == FieldState.PLAYING){
            show();
            processInput();
        }
        show();
        if(field.getState()==FieldState.SOLVED){
            System.out.println("\u001B[32mYou won\u001B[0m");
        }
        else{
            System.out.println("\u001B[31mYou failed\u001B[0m");
        }
        inputFeadback();
        save();
        repeatGame();
    }

    private void greeting() {
        headerGreeting();
        startQuestion();
    }


    private static void headerGreeting() {
        System.out.println("WELCOME TO A BETTER GAME OF");
        System.out.println("\u001B[31m______  _____  _____  _____ ");
        System.out.println("|  _  \\|  _  ||_   _|/  ___|");
        System.out.println("| | | || | | |  | |  \\ `--. ");
        System.out.println("| | | || | | |  | |   `--. \\");
        System.out.println("| |/ / \\ \\_/ /  | |  /\\__/ /");
        System.out.println("|___/   \\___/   \\_/  \\____/ \u001B[0m");
    }

    private void startQuestion() {
        System.out.println("\n\nIf you want to see the top-10 player rankings, enter \u001B[31mR\u001B[0m\nIf you want to see \"How to Play\" help enter \u001B[31mH\u001B[0m\nIf you want to start playing enter \u001B[31mS\u001B[0m\nIf you want to exit, enter \u001B[31mE\u001B[0m\n");
        System.out.print("Enter here: ");
        var answer=scanner.nextLine().toUpperCase();
        var matcher = inputStart.matcher(answer);
        if(matcher.matches()){
            if(answer.equals("R")){
                printTopScore();
                startQuestion();
            }
            else if(answer.equals("H")){
                printHelp();
                startQuestion();
            }
            else if(answer.equals("S")){
                writeNickname();
            }
            else{
                System.out.println("Bye!");
                System.exit(0);
            }
        }
        else{
            System.out.println(wrongIput);
            greeting();
        }
    }

    private void printHelp(){
        System.out.println("\n\n\u001B[33mThis section is to help new players\u001B[0m\n\n");
        System.out.println("\u001B[31mWhat's the point of the game?\u001B[0m");
        System.out.println("                             You have to \u001B[33mconnect the dots\u001B[0m of the same color and you get scores  for it (sounds easy).");
        System.out.println("\u001B[31mHow do I get the scores?\u001B[0m");
        System.out.println("                        Connect the dots.");
        System.out.println("\u001B[31mHow do I get more scores?\u001B[0m");
        System.out.println("                         The more dots you  \u001B[33mconnect at one time\u001B[0m, the more scores  you get.");
        System.out.println("\u001B[31mHow do I win?\u001B[0m");
        System.out.printf("             You have to get \u001B[33m%d scores\u001B[0m.\n",field.getMinScore());
        System.out.println("\u001B[31mHow to play?\u001B[0m");
        System.out.println("            First you enter the \u001B[33mcoordinates of the starting dot\u001B[0m (for example A 6) then you connect from it all dots that have the same color, the more the better.");
        System.out.println("            To set the direction you want to go, type \u001B[33mN, E, S, W\u001B[0m.");
        System.out.println("\u001B[31mWhat do these directions mean?\u001B[0m");
        System.out.println("                              N - \u001B[33mNORTH\u001B[0m (UP), S - \u001B[33mSOUTH\u001B[0m (DOWN), E - \u001B[33mEAST\u001B[0m (RIGHT), W - \u001B[33mWEST\u001B[0m (LEFT)");
        System.out.println("\nI think that's all you need to know, so good luck.\n\n");
    }

    private void printTopScore() {
        var list=scoreService.getTopScore("dots");
        if(!list.isEmpty()){
            System.out.printf("\n\n\u001B[33m========================= TOP-%d =========================\u001B[0m\n\n",list.size());
            for (int i=0;i<list.size();i++){
                System.out.printf("\u001B[33m%-32s\u001B[0m %-10d %-10tF\n",list.get(i).getPlayer(),list.get(i).getScore(),list.get(i).getPlayedData());
            }
        }
    }


    private void writeNickname() {
        System.out.print("\n\nEnter your \u001B[33mnickname\u001B[0m: ");
        nickname=scanner.nextLine();
        var matcher = inputNickname.matcher(nickname);
        if(matcher.matches()){
            if(nickname.length()>32){
                System.out.println("\u001B[31mVery long nickname(max. length 32)\u001B[0m");
                writeNickname();
            }
            System.out.println("\n\u001B[32mLet's start\u001B[0m");
        }
        else{
            System.out.println(wrongIput);
            writeNickname();
        }
    }


    private void processInput() {
        printFirstPositionDot();
        printDirectionNextDot();
    }

    private void printFirstPositionDot() {
        System.out.print("Enter start dot: ");
        var line=scanner.nextLine().toUpperCase();
        var matcher = inputFirstDotPattern.matcher(line);
        if(matcher.matches()){
            int row = matcher.group(1).charAt(0) - 'A';
            int column= Integer.parseInt(matcher.group(2)) - 1;
            field.markFirstDot(row,column);
            show();
        }
        else{
            System.out.println(wrongIput);
            printFirstPositionDot();
        }
    }

    private void printDirectionNextDot() {
        System.out.print("Enter direction the next dot( N(up), E(right), S(down), W(left) ): ");
        var line=scanner.nextLine().toUpperCase();
        var matcher = inputNextDotPattern.matcher(line);
        if(matcher.matches()){
            if(line.charAt(line.length()-1)==' '){
                System.out.println(wrongIput+"(There shouldn't be a \u001B[31mSPACE\u001B[0m at the end)");
                printDirectionNextDot();
            }
            for (int i = 0; i <= line.length()-1; i++) {
                if(line.charAt(i)==' '){
                    continue;
                }
                if(i==line.length()-1){
                    field.markNextDot(Direction.fromString(line.charAt(i)));
                    show();
                    field.markLastDot();
                }
                else {
                    field.markNextDot(Direction.fromString(line.charAt(i)));
                }
                if (i + 1 >= line.length()) {
                    break;
                }
            }
        }

        else{
            System.out.println(wrongIput);
            printDirectionNextDot();
        }
    }



    private void show() {
        printHeaderColumn();
        printBodyField();
        printfFooterColumns();
    }



    private void printHeaderColumn() {
        System.out.println();
        for (int column = 0; column < field.getColumnCount(); column++) {
            for (int i = 0; i < 6; i++) {
                System.out.print(" ");
            }
            System.out.print(column + 1);
        }
        System.out.println();
    }
    private void printBodyField() {
        for (int row = 0; row< field.getRowCount(); row++){
            System.out.print((char)(row + 'A') + "   ");
            for (int column = 0; column< field.getColumnCount(); column++){
                if(field.getDot(row,column)!=null) {
                    String colorText = String.format("\u001B[%dm", 31 + field.getDot(row, column).getColor().ordinal());
                    String colorBG = String.format("\u001B[%dm", 41 + field.getDot(row, column).getColor().ordinal());
                    switch (field.getDot(row, column).getState()) {
                        case NORMAL:
                            System.out.print(colorBG + " \u001B[30m( " + (field.getDot(row, column).getColor().ordinal() + 1) + " ) ");
                            break;
                        case MARKED:
                            System.out.print("\u001B[40m" + colorText + " ( " + (field.getDot(row, column).getColor().ordinal() + 1) + " ) ");
                            break;
                    }
                }
            }
            System.out.println("\u001B[0m");
        }
        System.out.println();
    }

    private void printfFooterColumns() {
        System.out.println("Score: \u001B[33m" + field.getScore() + "\u001B[0m");
        System.out.println("Steps: \u001B[31m" + field.getStep()+ "\u001B[0m");
        System.out.println();
        System.out.printf("You have to get \u001B[33m%d scores\u001B[0m.\n\n",field.getMinScore());
    }

    private void inputFeadback() {
        System.out.println("\nIf you want to write a review(comment), write it here, if you don't, hit \u001B[31mENTER\u001B[0m:");
        feadback = scanner.nextLine();
        String feadbackWithoutSpaces = feadback.replaceAll("\\s", "");
        if (!feadbackWithoutSpaces.isEmpty()) {
            if (feadback.length() >= 200) {
                System.out.println("\u001B[31mThank you, but this is a very long review\u001B[0m");
                inputFeadback();
            }
            commentService.addComment(new Comment(nickname, "dots", feadback, new Date()));
        }
        System.out.println("\nIf you want to write a rating(1-5), write it here, if you don't, hit \u001B[31mENTER\u001B[0m:");
        feadback=scanner.nextLine();
        var matcher = inputRating.matcher(feadback);
        if(matcher.matches()){
            ratingService.setRating(new Rating(nickname,"dots", Integer.parseInt(feadback), new Date()));
        }
    }

    private void save(){
        scoreService.addScore(new Score(nickname,"dots", field.getScore(), new Date()));
    }

    private void repeatGame(){
        System.out.println("Would you like to play again(Yes/No)?");
        var answer=scanner.nextLine().toUpperCase();
        var matcher = questionOfRepeat.matcher(answer);
        if(matcher.matches()){
            if(answer.equals("Y") || answer.equals("YES")){
                this.field=new Field();
                play();
            }
            else{
                System.out.println("Bye!");
            }
        }
        else{
            System.out.println(wrongIput);
            repeatGame();
        }

    }

}
