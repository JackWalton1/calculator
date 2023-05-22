package calculator.project2calculatorgui;
/**
 * A class representing a node in an arithmetic expression tree
 * that represents division between two sub-expressions.
 */
public class DivideNode extends BinaryExpression {
    /**
     * Constructs a new DivideNode with the specified left and right children.
     *
     * @param lChild the left child expression of this node
     * @param rChild the right child expression of this node
     */
    public DivideNode(Expression lChild, Expression rChild){
        super("/", lChild, rChild);
    }

    /**
     * Evaluates the result of dividing the left child expression by the right child expression of this node.
     * If the right child expression is a DigitNode with a digit value of zero, returns positive infinity.
     *
     * @return the result of dividing the left child expression by the right child expression of this node, or
     * positive infinity if the right child expression is a DigitNode with a digit value of zero
     */
    @Override
    public double evaluate() {
        if(rChild instanceof DigitNode dn){
            String digit = dn.stringify(ExpressionType.POSTFIX);
            if(digit.equals("0")){
                return Double.POSITIVE_INFINITY;
            }
        }
        return lChild.evaluate() / rChild.evaluate();
    }
}

