import fei.tuke.sk.CalculatorException;
import fei.tuke.sk.Lexer;
import fei.tuke.sk.Parser;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class test {
    private int calculator(String input) throws CalculatorException, IOException {
        Lexer lexer = new Lexer(new StringReader(input));
        return new Parser(lexer).statement();
    }
    @Test
    public void easyTest() throws CalculatorException, IOException {
        //1
        String input = "2+5";
        assertEquals(7,calculator(input));

        //2
        input = "5*3POWER2";
        assertEquals(225,calculator(input));

        //3
        input = "2SUM5***-3";
        assertEquals(-13,calculator(input));
    }

    @Test(expected = CalculatorException.class)
    public void testInvalidInput1() throws CalculatorException, IOException {
        //4
        String input = "2SUM5             **    *           -3";
        calculator(input);
    }

    @Test
    public void testInvalidInput2() throws CalculatorException, IOException {
        //5
        try {
            String input = "2SUM5             ***           -  x";
            calculator(input);
            fail("Expected CalculatorException was not thrown");
        } catch (CalculatorException | IOException e) {
            assertTrue(e instanceof CalculatorException);
        }
    }

    @Test
    public void testInvalidInput3() throws CalculatorException, IOException {
        //6
        try {
            String input = "2SUMSUM5";
            calculator(input);
            fail("Expected CalculatorException was not thrown");
        } catch (CalculatorException | IOException e) {
            assertTrue(e instanceof CalculatorException);
        }
    }

    @Test
    public void testWithUnarny() throws CalculatorException, IOException {
        //7
        String input = "2SUMYY5";
        assertEquals(-3,calculator(input));

        //8
        input = "-(2***YY5)";
        assertEquals(10,calculator(input));
    }

    @Test
    public void testWithBracket() throws CalculatorException, IOException {
        //9
        String input = "{(2+4)*5}***3";
        assertEquals(90,calculator(input));

        //10
        input = "{-(2+4)YY-5}***3";
        assertEquals(-3,calculator(input));
    }

    @Test
    public void testInvalidBracket() throws CalculatorException, IOException {
        //11
        try {
            String input = "(2+3}*4";
            calculator(input);
            fail("Expected CalculatorException was not thrown");
        } catch (CalculatorException | IOException e) {
            assertTrue(e instanceof CalculatorException);
        }
    }

    @Test
    public void testWithPower() throws CalculatorException, IOException {
        //12
        String input = "2+4^2";
        assertEquals(36,calculator(input));

        //13
        input = "2^4+2";
        assertEquals(64,calculator(input));
    }

    @Test
    public void testInvalidPower() throws CalculatorException, IOException {
        //14
        try {
            String input = "2POWER                  ^           3";
            calculator(input);
            fail("Expected CalculatorException was not thrown");
        } catch (CalculatorException | IOException e) {
            assertTrue(e instanceof CalculatorException);
        }
    }

    @Test
    public void hardTest() throws CalculatorException, IOException {
        //15
        String input = "(2+341151)+                YY         5 * (4-{3 POWER4})";
        assertEquals(341538,calculator(input));
    }

}

