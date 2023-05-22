package calculator.project2calculatorgui;
/**
 * The MultiplyNode class extends the BinaryExpression class and represents
 * multiplication operation between two sub-expressions.
 */
public class MultiplyNode extends BinaryExpression {
    /**
     * Constructs a MultiplyNode with the given left and right children expressions.
     *
     * @param lChild the left child expression
     * @param rChild the right child expression
     */
    public MultiplyNode(Expression lChild, Expression rChild){
        super("*", lChild, rChild);
    }

    /**
     * Evaluates the multiplication expression by computing the product of the left and right child expressions.
     *
     * @return the product of the left and right child expressions
     */
    @Override
    public double evaluate() {
        return lChild.evaluate() * rChild.evaluate();
    }
}
