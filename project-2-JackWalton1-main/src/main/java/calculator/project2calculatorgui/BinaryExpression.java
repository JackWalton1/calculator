package calculator.project2calculatorgui;
/**
 * An abstract class representing a binary expression with an operator and two child expressions.
 * The BinaryExpression class implements the Expression interface and represents a binary
 * expression with an operator and two child expressions. It provides default implementations
 * for the toString(), equals(), (and as SL requested hashCode()) methods, and requires that
 * subclasses implement the evaluate() method to calculate the value of the expression.
 */
public abstract class BinaryExpression implements Expression{
    private final String operator;
    protected Expression lChild;
    protected Expression rChild;

    /**
     * Constructs a new BinaryExpression object with the given operator and child expressions.
     *
     * @param operator the operator for the binary expression
     * @param lChild the left child expression
     * @param rChild the right child expression
     */
    protected BinaryExpression(String operator, Expression lChild, Expression rChild){
        this.operator = operator;
        this.lChild = lChild;
        this.rChild = rChild;
    }

    /**
     * Returns a string representation of this binary expression in infix notation.
     *
     * @return a string representation of this binary expression (infix)
     */
    public String stringify(ExpressionType type){
        if (type.equals(ExpressionType.PREFIX)){
            return operator + " " + lChild.stringify(ExpressionType.PREFIX) + " " +
                    rChild.stringify(ExpressionType.PREFIX);
        }else {
            return lChild.stringify(ExpressionType.POSTFIX) + " " +
                    rChild.stringify(ExpressionType.POSTFIX) + " " + operator;
        }
    }

    /**
     * Compares this binary expression to another object for equality.
     * This method returns true if the other object is a BinaryExpression with the same
     * operator and equal left and right child expressions, and false otherwise.
     *
     * @param o the object to compare to this binary expression
     * @return true if the other object is equal to this binary expression, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof BinaryExpression)) {
            return false;
        }
        return (this.lChild.equals(((BinaryExpression) o).lChild) &&
                this.rChild.equals(((BinaryExpression) o).rChild) &&
                this.operator.equals(((BinaryExpression) o).operator));
    }

    /**
     * Returns the hash code for this binary expression.
     * The hash code is computed using the operator, left child expression, and right child expression.
     * Not used anywhere else but tests -- SonarLint gave warning once overrode equals()
     *
     * @return the hash code for this binary expression
     */
    @Override
    public int hashCode() {
        int result = operator.hashCode();
        result = 31 * result + lChild.hashCode();
        result = 31 * result + rChild.hashCode();
        return result;
    }
}
