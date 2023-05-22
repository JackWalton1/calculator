package calculator.project2calculatorgui;
/**
 * The AddNode class extends the BinaryExpression class and represents
 * addition operation between two sub-expressions.
 */
public class AddNode extends BinaryExpression {
    /**
     * Constructs a new AddNode object with the given left and right child expressions.
     *
     * @param lChild the left child expression
     * @param rChild the right child expression
     */
    public AddNode(Expression lChild, Expression rChild){
        super("+", lChild, rChild);
    }

    /**
     * Returns the result of evaluating the sum of the left and right children of this node.
     *
     * @return the sum of the left and right children of this node
     */
    @Override
    public double evaluate() {
        return lChild.evaluate() + rChild.evaluate();
    }
}
