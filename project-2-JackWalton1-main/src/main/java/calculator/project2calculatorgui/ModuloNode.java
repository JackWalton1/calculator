package calculator.project2calculatorgui;
/**
 * The AddNode class extends the BinaryExpression class and represents
 * a modulo operation between two sub-expressions.
 */
public class ModuloNode extends BinaryExpression {
    /**
     * Constructs a ModuloNode with a left and right child expression.
     *
     * @param lChild the left child expression
     * @param rChild the right child expression
     */
    public ModuloNode(Expression lChild, Expression rChild){
        super("%", lChild, rChild);
    }

    /**
     * Evaluates the modulo operation between the left and right child expressions.
     * If the right child expression is a DigitNode with a value of 0, returns positive infinity.
     *
     * @return the result of the modulo operation
     */
    public double evaluate() {
        if(rChild instanceof DigitNode dn){
            String digit = dn.stringify(ExpressionType.POSTFIX);
            if(digit.equals("0")){
                return Double.POSITIVE_INFINITY;
            }
        }
        return lChild.evaluate() % rChild.evaluate();
    }

}