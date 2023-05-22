import calculator.project2calculatorgui.*;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import static org.junit.jupiter.api.Assertions.*;

class PostfixParserTest {
    @Test
    void isOperatorTest() {
        String minus = "-";
        String plus = "+";
        String times = "*";
        String divides = "/";
        String modulo = "%";
        String exponent = "^";
        PostfixParser parser = new PostfixParser("", new NameBindings());
        assertTrue(parser.isOperator((minus)));
        assertTrue(parser.isOperator((plus)));
        assertTrue(parser.isOperator((times)));
        assertTrue(parser.isOperator((divides)));
        assertTrue(parser.isOperator((modulo)));
        assertTrue(parser.isOperator((exponent)));
    }
    @Test
    void isOperandTest() {
        String integer = "1";
        String dbl = "0.25";
        String negative = "-0.25";
        String bigDbl = "1234.5678901234";
        PostfixParser parser = new PostfixParser("", new NameBindings());
        assertTrue(parser.isOperand((integer)));
        assertTrue(parser.isOperand((dbl)));
        assertTrue(parser.isOperand((negative)));
        assertTrue(parser.isOperand((bigDbl)));
    }

    @Test
    void isThisTypeExpressionFalse1() {
        String expression = "1 + 2";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse2() {
        String expression = "+ 2 x";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse3() {
        String expression = "2 2 3 + + 3 4 + + +";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse4() {
        String expression = "2 3 4 - 2 5 +";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse5() {
        String expression = "xyz";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse6() {
        String expression = "+ 5 +";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse7() {
        String expression = "+ 5 + 5";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse8() {
        String expression = "5 4 2 + * /";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse9() {
        String expression = "1 2 3";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpressionSpacing() {
        String expression = "12 5 2 + *      ";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionEmpty() {
        String expression = "       ";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertFalse(parser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression0() {
        String expression = "3 5 - 3 *";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression1() {
        String expression = "12 5 2 + *";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression2() {
        String expression = "1 3 4 ^ 2 3 * / 1 + -";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression3() {
        String expression = "1 2 +";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression4() {
        String expression = "12 0.5 2 * + ";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpression5() {
        String expression = "3 2 * 4 / 1 +";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        assertTrue(parser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpression6() {
        NameBindings nameBindings = new NameBindings();
        nameBindings.addBinding(new Variable("a", "3"));
        String expression = "a 4 + 5 - a *";
        PostfixParser parser = new PostfixParser(expression, nameBindings);
        assertTrue(parser.isThisTypeExpression((expression)));
    }

    @Test
    void postfixToPrefix0() {
        String expression = "3 4 /";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        parser.convertToPrefix();
        assertEquals("/ 3 4", parser.convertToPrefix());
    }
    @Test
    void postfixToPrefix1() {
        String expression = "3 4 / 4 -";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        parser.convertToPrefix();
        assertEquals("- / 3 4 4", parser.convertToPrefix());
    }

    @Test
    void postfixToPrefix2() {
        // (8%((28-(4^2))*(45/5)))
        String expression = "8 28 4 2 ^ - 45 5 / * %";
        PostfixParser parser = new PostfixParser(expression, new NameBindings());
        parser.convertToPrefix();
        assertEquals("% 8 * - 28 ^ 4 2 / 45 5", parser.convertToPrefix());
    }

    @Test
    void createPostfixExpressionTreeTests() {
        Variable v = new Variable("a", "5");
        NameBindings nameBindings = new NameBindings();
        nameBindings.addBinding(v);
        String expression1 = "3 a *";
        String expression2 = "3 a -";
        String expression3 = "3 a ^";
        String expression4 = "3 a /";
        String expression5 = "3 a +";
        String expression6 = "3 a %";

        PostfixParser parser1 = new PostfixParser(expression1,nameBindings);
        PostfixParser parser2 = new PostfixParser(expression2, nameBindings);
        PostfixParser parser3 = new PostfixParser(expression3, nameBindings);
        PostfixParser parser4 = new PostfixParser(expression4, nameBindings);
        PostfixParser parser5 = new PostfixParser(expression5, nameBindings);
        PostfixParser parser6 = new PostfixParser(expression6, nameBindings);
        assertEquals(new MultiplyNode(new DigitNode("3"), v), parser1.buildExpressionTree());
        assertEquals(new SubtractNode(new DigitNode("3"),v), parser2.buildExpressionTree());
        assertEquals(new ExpNode(new DigitNode("3"), v), parser3.buildExpressionTree());
        assertEquals(new DivideNode(new DigitNode("3"), v), parser4.buildExpressionTree());
        assertEquals(new AddNode(new DigitNode("3"), v), parser5.buildExpressionTree());
        assertEquals(new ModuloNode(new DigitNode("3"), v), parser6.buildExpressionTree());
        assertEquals(15, parser1.buildExpressionTree().evaluate());
    }
}
