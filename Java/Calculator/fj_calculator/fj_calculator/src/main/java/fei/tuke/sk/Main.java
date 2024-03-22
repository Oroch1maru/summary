package fei.tuke.sk;

import java.io.StringReader;

public class Main {
    public static void main(String[] args) {
        //String input = "S";
        //String input = "2***4SUMYY(1YY{2POWER2^2})";
        //String input = "{-{2 ^ {3 ^ 2}} ^ {4 ^ 3}} ^ {{{5 ^ 4} ^ 3} ^ 2}";
        String input = "-2*3+{4-5}^2";
        try {
            Lexer lexer = new Lexer(new StringReader(input));
            int result = new Parser(lexer).statement();
            System.out.printf("\nResult: %d\n", result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}