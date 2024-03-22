package fei.tuke.sk;

import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private final Reader input;
    private int current;
    private int value;

    public Lexer(Reader reader) throws IOException, CalculatorException {
        this.input = reader;
        consume();
    }

    public Token nextToken() throws IOException, CalculatorException {
        while (Character.isWhitespace(current)) {
            consume();
        }
        switch ((char) current) {
            case 'S':
                consume();
                if (current == 'U') {
                    consume();
                    if (current == 'M') {
                        System.out.println("Token.PLUS");
                        consume();
                        return Token.PLUS;
                    }
                    throw new CalculatorException("Invalid character: " + (char) current);
                }
                throw new CalculatorException("Invalid character: " + (char) current);
            case '+':
                System.out.println("Token.PLUS");
                consume();
                return Token.PLUS;
            case 'Y':
                consume();
                if (current == 'Y') {
                    System.out.println("Token.MINUS");
                    consume();
                    return Token.MINUS;
                }
                throw new CalculatorException("Invalid character: " + (char) current);
            case '-':
                System.out.println("Token.MINUS");
                consume();
                return Token.MINUS;
            case '*':
                consume();
                if(current == '*'){
                    consume();
                    if(current == '*'){
                        consume();
                        System.out.println("Token.3*MUL");
                        return Token.MUL;
                    }
                    throw new CalculatorException("Invalid character: " + (char) current);
                }
                System.out.println("Token.MUL");
                return Token.MUL;
            case 'P':
                consume();
                if (current == 'O') {
                    consume();
                    if (current == 'W') {
                        consume();
                        if (current == 'E') {
                            consume();
                            if (current == 'R') {
                                System.out.println("Token.POWER");
                                consume();
                                return Token.POWER;
                            }
                        }
                    }
                }
                throw new CalculatorException("Invalid character: " + (char) current);
            case '^':
                System.out.println("Token.POWER");
                consume();
                return Token.POWER;
            case '{':
                System.out.println("Token.LBRA");
                consume();
                return Token.LBRA;
            case '}':
                System.out.println("Token.RBRA");
                consume();
                return Token.RBRA;
            case '(':
                System.out.println("Token.LPAR");
                consume();
                return Token.LPAR;
            case ')':
                System.out.println("Token.RPAR");
                consume();
                return Token.RPAR;
            default:
                if(current>=48 && current<=57){
                    StringBuilder number = new StringBuilder();
                    while (current>=48 && current<=57) {
                        number.append((char) current);
                        consume();
                    }
                    value = Integer.parseInt(number.toString());
                    System.out.println("NUMBER: " + value);
                    return Token.NUMBER;
                }
                else if (current == -1) {
                    return Token.EOF;
                }
                else {
                    throw new CalculatorException("Invalid character: " + (char) current);
                }
        }
    }

    private void consume() throws IOException {
        current = input.read();
    }

    public int getValue() {
        return value;
    }
}
