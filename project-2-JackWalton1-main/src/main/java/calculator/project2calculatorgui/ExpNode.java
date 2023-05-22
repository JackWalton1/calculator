package calculator.project2calculatorgui;
/**
 * A class representing a node in an arithmetic expression tree that represents exponential notation
 * where the left child expression is the base and the right child expression is the exponent.
 */
public class ExpNode extends BinaryExpression {
    /**
     * Constructs a new ExpNode with the specified left and right children.
     *
     * @param lChild the left child expression of this node, representing the base
     * @param rChild the right child expression of this node, representing the exponent
     */
    public ExpNode(Expression lChild, Expression rChild){
        super("^", lChild, rChild);
    }

    /**
     * Evaluates the result of raising the left child expression to the power of the right child expression.
     *
     * @return the result of raising the left child expression to the power of the right child expression
     */
    @Override
    public double evaluate() {
        return Math.pow(lChild.evaluate(), rChild.evaluate());
    }
}
