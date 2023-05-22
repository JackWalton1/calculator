import calculator.project2calculatorgui.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BinaryExpressionsTest {
    @Test
    void evaluateTests() {
        ExpNode en = new ExpNode(new DigitNode("2"), new DigitNode("3"));
        assertEquals(8, en.evaluate());
        MultiplyNode mn = new MultiplyNode(new DigitNode("2.5"), new DigitNode("3"));
        assertEquals(7.5, mn.evaluate());
        SubtractNode sn = new SubtractNode(new DigitNode("10"), new DigitNode("100"));
        assertEquals(-90, sn.evaluate());
        AddNode an = new AddNode(new DigitNode("10"), new DigitNode("100"));
        assertEquals(110, an.evaluate());
        ModuloNode mod = new ModuloNode(new DigitNode("111"), new DigitNode("2"));
        assertEquals(1, mod.evaluate());
        ModuloNode mod2 = new ModuloNode(new DigitNode("111"), new DigitNode("1.5"));
        assertEquals(0, mod2.evaluate());
        DivideNode dn = new DivideNode(new DigitNode("111"), new DigitNode("1.5"));
        assertEquals(74, dn.evaluate());
    }
    @Test
    void divideByZeroTests() {
        DivideNode dn = new DivideNode(new DigitNode("111"), new DigitNode("0"));
        assertEquals(Double.POSITIVE_INFINITY, dn.evaluate());
        ModuloNode mod = new ModuloNode(new DigitNode("111"), new DigitNode("0"));
        assertEquals(Double.POSITIVE_INFINITY, mod.evaluate());

        DivideNode dn2 = new DivideNode(new DigitNode("111"), new DivideNode(new DigitNode("111"), new DigitNode("0")));
        assertEquals(0, dn2.evaluate());
        ModuloNode mod2 = new ModuloNode(new DigitNode("111"),
                new ModuloNode(new DigitNode("111"), new DigitNode("0")));
        assertEquals(111, mod2.evaluate());
    }

    @Test
    void testEquals() {
        //test that the constructor throws an IllegalArgumentException when the start time is after the end time
        String entered = "+ 3 / * 12 1 2";
        PrefixParser parser = new PrefixParser(entered, new NameBindings());
        Expression root = parser.buildExpressionTree();
        assertEquals(root, root);

        String entered2 = "+ 3 / * % 12 5 0.5 2";
        PrefixParser parser2 = new PrefixParser(entered2, new NameBindings());
        Expression root2 = parser2.buildExpressionTree();
        assertNotEquals(root, root2);

        String entered3 = "+ 3 / * % 12 5 0.5 2";
        PrefixParser parser3 = new PrefixParser(entered3, new NameBindings());
        Expression root3 = parser3.buildExpressionTree();
        assertEquals(root2, root3);

        String entered4 = "+ 3 4";
        PrefixParser parser4 = new PrefixParser(entered4, new NameBindings());
        Expression root4 = parser4.buildExpressionTree();
        assertNotEquals(root3, root4);

        String entered5 = "* 3 4";
        PrefixParser parser5 = new PrefixParser(entered5, new NameBindings());
        Expression root5 = parser5.buildExpressionTree();
        assertNotEquals(root4, root5);
    }

    @Test
    void testHashCode() {
        //test that the constructor throws an IllegalArgumentException when the start time is after the end time
        String entered = "+ 3 / * 12 5 2";
        PrefixParser parser = new PrefixParser(entered, new NameBindings());
        Expression root = parser.buildExpressionTree();
        assertEquals(root.hashCode(), root.hashCode());
    }
    @Test
    void testStringify() {
        Expression an = new AddNode(new DigitNode("10"), new DigitNode("100"));
        String expectedPrefix = "+ 10 100";
        assertEquals(expectedPrefix, an.stringify(ExpressionType.PREFIX));
        String expectedPostfix = "10 100 +";
        assertEquals(expectedPostfix, an.stringify(ExpressionType.POSTFIX));
    }

    @Test
    void stepByStepTest() {
        // Making sure parser works with both
        NameBindings nameBindings = new NameBindings();
        nameBindings.addBinding(new Variable("a", "3"));
        String input = "+ a + 5 + 4 + a a";
        PrefixParser prefixParser = new PrefixParser(input, nameBindings);
        assertTrue(prefixParser.isThisTypeExpression(input));
        // * - + a 4 5 a
        String input2 = "a 4 + 5 - a *";
        PostfixParser postfixParser = new PostfixParser(input2, nameBindings);
        assertTrue(postfixParser.isThisTypeExpression(input2));

        // make sure String -> Expression works correctly with both parsers.
        Expression root1 = prefixParser.buildExpressionTree();
        assertEquals(new AddNode(new Variable("a", "3"), new AddNode(
                new DigitNode("5"), new AddNode(
                        new DigitNode("4"), new AddNode(
                                new Variable("a", "3"),
                new Variable("a", "3"))))), root1);

        Expression root2 = postfixParser.buildExpressionTree();
        assertEquals(new MultiplyNode(new SubtractNode(
                new AddNode(
                        new Variable("a", "3"),
                        new DigitNode("4")), new DigitNode("5")),
                new Variable("a", "3")), root2);

        // make sure Expression -> double works correctly with both parsers.
        double expected = 18;
        double expected2 = 6;
        assertEquals(expected, root1.evaluate());
        assertEquals(expected2, root2.evaluate());

        // make sure double -> String works correctly.
        assertEquals("18.0", String.valueOf(root1.evaluate()));
        assertEquals("6.0", String.valueOf(root2.evaluate()));

    }
}
