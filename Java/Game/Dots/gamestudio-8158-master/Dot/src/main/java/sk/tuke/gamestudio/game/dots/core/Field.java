package sk.tuke.gamestudio.game.dots.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {
    private Dot[][] dots;
    private final int rowCount;
    private final int columnCount;
    private int step;
    private int score;
    private int minScore;


    private List<Dot> listDot = new ArrayList<>();
    private FieldState state =FieldState.PLAYING;

    public Field(){
        rowCount=6;
        columnCount=6;
        step=15;
        minScore=150;
        dots=new Dot[rowCount][columnCount];
        generate();
    }


    private void generate(){
        Random random = new Random();
        Color[] colors = Color.values();
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (dots[row][column] == null || getDot(row, column).getState() == DotState.EMPTY) {
                    int randomIndex = random.nextInt(colors.length);
                    Color randomColor = colors[randomIndex];
                    dots[row][column] = new UnitDot(randomColor, row, column);
                }
            }
        }
        if (!checkDuplicates()) {
            clearField();
            generate();
        }
    }
    private void clearField() {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                dots[row][column] = null;
            }
        }
    }

    private boolean checkDuplicates() {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (row < getRowCount() - 1) {
                    if (pairCheckingDuplicates(row,column,row+1, column)) return true;
                }
                if (row > 0) {
                    if (pairCheckingDuplicates(row,column,row-1, column)) return true;
                }
                if (column < getColumnCount() - 1) {
                    if (pairCheckingDuplicates(row,column,row, column+1)) return true;
                }
                if (column > 0) {
                    if (pairCheckingDuplicates(row,column,row, column-1)) return true;
                }
            }
        }
        return false;
    }

    private boolean pairCheckingDuplicates(int row, int column, int nextRow, int nextColumn) {
        return getDot(row, column).getColor() == getDot(nextRow, nextColumn).getColor();
    }


    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public FieldState getState() {
        return state;
    }



    public int getScore(){
        return score;
    }

    public int getStep() {
        return step;
    }

    public Dot getDot(int row,int col){
        return dots[row][col];
    }



    public int getMinScore(){
        return minScore;
    }



    private void setStep(){
        step=step-1;
    }

    private void setScore(int newScore){
        if(newScore>=0){
            score=5*newScore+score;
        }
    }


    private boolean stepOut(){
        if(step<=0){
            return true;
        }
        return false;
    }

    private boolean checkScore(){
        if(score>=minScore){
            return true;
        }
        return false;
    }

    public void markFirstDot(int row, int col){
        if(state==FieldState.PLAYING) {
            listDot.clear();
            if (row >= rowCount || col >= columnCount)
                throw new RuntimeException("Invalid index for dot");
            listDot.add(getDot(row, col));
            if (getDot(row, col).getState() == DotState.NORMAL) {
                getDot(row, col).setState(DotState.MARKED);
            }
        }
    }

    public void markNextDot(Direction direction){
        if(state==FieldState.PLAYING) {
            if (!listDot.isEmpty()) {
                int previousDotRow = listDot.get(listDot.size() - 1).getRow();
                int previousDotColumn = listDot.get(listDot.size() - 1).getCol();
                if ((previousDotRow - direction.getDy() < 0 || previousDotColumn + direction.getDx() < 0) || (previousDotRow - direction.getDy() >= rowCount || previousDotColumn + direction.getDx() >= columnCount)) {
                    setStateAllMarkedDots(DotState.NORMAL);
                    return;
                }
                if (getDot(previousDotRow, previousDotColumn).getColor() == getDot(previousDotRow - direction.getDy(), previousDotColumn + direction.getDx()).getColor()) {
                    getDot(previousDotRow - direction.getDy(), previousDotColumn + direction.getDx()).setState(DotState.MARKED);
                    listDot.add(getDot(previousDotRow - direction.getDy(), previousDotColumn + direction.getDx()));
                } else {
                    setStateAllMarkedDots(DotState.NORMAL);
                }
            }
        }
    }

    public void markLastDot(){
        setScore(listDot.size()-1);
        setStateAllMarkedDots(DotState.EMPTY);
        deleteMarkedDots();
        generate();
        setStep();
        if(checkScore()){
            state=FieldState.SOLVED;
        }
        else if(stepOut()){
            state=FieldState.FAILED;
        }
    }

    private void setStateAllMarkedDots(DotState state) {
        for (int i = listDot.size() - 1; i >= 0; i--) {
            listDot.get(i).setState(state);
            listDot.remove(listDot.get(i));
        }
    }

    private void deleteMarkedDots() {
        int helpRow;
        for (int column=0;column<=columnCount-1;column++){
            for (int row=rowCount-1;row>=0;row--){
                if(getDot(row,column).getState()==DotState.EMPTY){
                    helpRow=row;
                    while (getDot(helpRow,column).getState()==DotState.EMPTY){
                        if(helpRow<=0){
                            break;
                        }
                        helpRow--;
                    }
                    if(getDot(helpRow, column).getState()!=DotState.EMPTY) {
                        dots[row][column]=new UnitDot(getDot(helpRow, column).getColor(),row,column);
                        getDot(row, column).setState(DotState.NORMAL);
                        getDot(helpRow, column).setState(DotState.EMPTY);
                    }
                }
            }
        }
    }
}
