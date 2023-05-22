import calculator.project2calculatorgui.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import static org.junit.jupiter.api.Assertions.*;

class PrefixParserTest {
    @Test
    void isOperatorTest() {
        String minus = "-";
        String plus = "+";
        String times = "*";
        String divides = "/";
        String modulo = "%";
        String exponent = "^";
        PrefixParser prefixParser = new PrefixParser("", new NameBindings());
        assertTrue(prefixParser.isOperator((minus)));
        assertTrue(prefixParser.isOperator((plus)));
        assertTrue(prefixParser.isOperator((times)));
        assertTrue(prefixParser.isOperator((divides)));
        assertTrue(prefixParser.isOperator((modulo)));
        assertTrue(prefixParser.isOperator((exponent)));
    }
    @Test
    void isOperandTest() {
        String integer = "1";
        String dbl = "0.25";
        String negative = "-0.25";
        String bigDbl = "1234.5678901234";
        PrefixParser prefixParser = new PrefixParser("", new NameBindings());
        assertTrue(prefixParser.isOperand((integer)));
        assertTrue(prefixParser.isOperand((dbl)));
        assertTrue(prefixParser.isOperand((negative)));
        assertTrue(prefixParser.isOperand((bigDbl)));
    }
    @Test
    void isThisTypeExpressionFalse1() {
        String expression = "1 + 2";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse2() {
        String expression = "+ 2 x";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse3() {
        // this has 3 digits in a row after only two operators
        String expression = "+ + 2 2 3 + + 3 4";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse4() {
        // this has 3 digits in a row after only two operators
        String expression = "+ 2 3 4 - 2 5";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse5() {
        // this has 3 digits in a row after only two operators
        String expression = "xyz";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse6() {
        // this has 3 digits in a row after only two operators
        String expression = "+ 5 +";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionFalse7() {
        // this has 3 digits in a row after only two operators
        String expression = "+ 5 + 5";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionSpacing() {
        String expression = "+ * 12 5 2       ";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpressionEmpty() {
        String expression = "       ";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertFalse(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression0() {
        String expression = "* 3 - 5 3";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression1() {
        String expression = "+ * 12 5 2";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression2() {
        String expression = "- + 1 / * 2 3 ^ 4 3 1";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression3() {
        String expression = "+ 1 2";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression4() {
        String expression = "+ * 12 0.5 2";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpression5() {
        String expression = "+ 1 / 4 * 2 3";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }
    @Test
    void isThisTypeExpression6() {
        String expression = "* 3 - 5 + 4 3";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression7() {
        String expression = "+ 3 + 5 + 4 + 3 3";
        PrefixParser prefixParser = new PrefixParser(expression, new NameBindings());
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void isThisTypeExpression8() {
        NameBindings nameBindings = new NameBindings();
        nameBindings.addBinding(new Variable("a", "3"));
        String expression = "+ a + 5 + 4 + a a";
        PrefixParser prefixParser = new PrefixParser(expression, nameBindings);
        assertTrue(prefixParser.isThisTypeExpression((expression)));
    }

    @Test
    void createPrefixExpressionTreeTests() {
        String expression1 = "* 3 5";
        String expression2 = "- 3 5";
        String expression3 = "^ 3 5";
        String expression4 = "/ 3 5";
        String expression5 = "+ 3 5";
        String expression6 = "% 3 5";
        PrefixParser prefixParser1 = new PrefixParser(expression1, new NameBindings());
        PrefixParser prefixParser2 = new PrefixParser(expression2, new NameBindings());
        PrefixParser prefixParser3 = new PrefixParser(expression3, new NameBindings());
        PrefixParser prefixParser4 = new PrefixParser(expression4, new NameBindings());
        PrefixParser prefixParser5 = new PrefixParser(expression5, new NameBindings());
        PrefixParser prefixParser6 = new PrefixParser(expression6, new NameBindings());
        assertEquals(new MultiplyNode(new DigitNode("3"),new DigitNode("5")), prefixParser1.buildExpressionTree());
        assertEquals(new SubtractNode(new DigitNode("3"),new DigitNode("5")), prefixParser2.buildExpressionTree());
        assertEquals(new ExpNode(new DigitNode("3"), new DigitNode("5")), prefixParser3.buildExpressionTree());
        assertEquals(new DivideNode(new DigitNode("3"), new DigitNode("5")), prefixParser4.buildExpressionTree());
        assertEquals(new AddNode(new DigitNode("3"), new DigitNode("5")), prefixParser5.buildExpressionTree());
        assertEquals(new ModuloNode(new DigitNode("3"), new DigitNode("5")), prefixParser6.buildExpressionTree());

    }

    @Test
    void testCreateExpressionTreeIllegalArgument() {
        //test that the constructor throws an IllegalArgumentException when the start time is after the end time
        String illegal = "r 3 5";
        PrefixParser illegalParser = new PrefixParser(illegal, new NameBindings());
        Assertions.assertThrows(IllegalStateException.class, () ->
            illegalParser.createExpressionRoot(illegal, null, null)
        );
    }
}