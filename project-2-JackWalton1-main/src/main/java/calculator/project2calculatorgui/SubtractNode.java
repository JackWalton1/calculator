package calculator.project2calculatorgui;
/**
 * The SubtractNode class extends the BinaryExpression class and represents
 * subtraction operation between two sub-expressions.
 */
public class SubtractNode extends BinaryExpression {
    /**
     * Constructs a SubtractNode object with given left and right child expressions.
     *
     * @param lChild the left child expression
     * @param rChild the right child expression
     */
    public SubtractNode(Expression lChild, Expression rChild){
        super("-", lChild, rChild);
    }

    /**
     * Evaluates the subtraction expression represented by this node.
     *
     * @return the result of subtracting right child expression from left child expression
     */
    @Override
    public double evaluate() {
        return lChild.evaluate() - rChild.evaluate();
    }
}
