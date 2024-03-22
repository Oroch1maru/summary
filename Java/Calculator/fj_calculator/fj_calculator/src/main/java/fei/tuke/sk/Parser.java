package fei.tuke.sk;

import java.io.IOException;

public class Parser {
    private final Lexer lexer;

    private Token symbol;

    public Parser(Lexer lexer) throws CalculatorException, IOException {
        this.lexer = lexer;
        symbol=lexer.nextToken();
    }

    public int statement() throws IOException, CalculatorException {
        return A();
    }

    private int A() throws IOException, CalculatorException {
        int value=B();
        if (symbol.equals(Token.POWER)) {
            consume();
            int nextValue=A();
            value=(int) Math.pow(value, nextValue);
        }
        return value;
    }

    private int B() throws IOException, CalculatorException {
        int value = C();
        while (symbol.equals(Token.PLUS) || symbol.equals(Token.MINUS)) {
            Token previosSymbol = symbol;
            consume();
//            System.out.println("value: "+value);
            int nextValue = C();
//            System.out.println("nextValue: "+nextValue);
            if (previosSymbol.equals(Token.PLUS)) {
                value = value + nextValue;
            } else {
                value -= nextValue;
            }
        }
//        System.out.println("result: "+value);
        return value;
    }

    private int C() throws CalculatorException, IOException {
        int value=D();
        if(symbol.equals(Token.MUL)){
            consume();
            int nextValue=C();
            value=value*nextValue;
        }
        return value;
    }

    private int D() throws CalculatorException, IOException {
        if (symbol.equals(Token.MINUS)) {
            consume();
            return -E();
        }
        return E();
    }

    private int E() throws CalculatorException, IOException {
        if (symbol.equals(Token.NUMBER)) {
            int number = lexer.getValue();
            consume();
            return number;
        } else if (symbol.equals(Token.LPAR)) {
            consume();
            int result = A();
            if (symbol.equals(Token.RPAR)) {
                consume();
                return result;
            } else {
                throw new CalculatorException("must be )");
            }
        } else if (symbol.equals(Token.LBRA)) {
            consume();
            int result = A();
            if (symbol.equals(Token.RBRA)) {
                consume();
                return result;
            } else {
                throw new CalculatorException("must be }" + symbol);
            }
        }

        throw new CalculatorException("ERROR: " + symbol);
    }

    private void consume() throws CalculatorException, IOException {
        symbol=lexer.nextToken();
    }

}
