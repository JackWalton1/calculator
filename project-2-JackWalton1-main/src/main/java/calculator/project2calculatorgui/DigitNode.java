package calculator.project2calculatorgui;
/**
 * A class representing a node in an arithmetic expression tree that contains a single digit value.
 */
public record DigitNode(String digit) implements Expression {
    /**
     * Evaluates the digit value of this node as a double.
     *
     * @return the double value of this digit node
     */
    @Override
    public double evaluate() {
        return Double.parseDouble(this.digit);
    }

    /**
     * Returns the string representation of this digit node, which is simply its digit value.
     *
     * @return the string representation of this digit node
     */
    public String stringify(ExpressionType type) {
        return this.digit;
    }
    /**
     * Determines whether this digit node is equal to another object. Two digit nodes are considered equal if they have
     * the same digit value.
     *
     * @param o the object to compare this digit node to
     * @return true if the object is equal to this digit node, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof DigitNode)) {
            return false;
        }
        return this.digit.equals(((DigitNode) o).digit);
    }

}
