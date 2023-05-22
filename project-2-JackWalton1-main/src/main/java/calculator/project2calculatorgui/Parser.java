package calculator.project2calculatorgui;
/**
 * The Parser interface represents a parser for expressions in a specified format.
 * It contains methods for creating expression roots, determining if a string is
 * a valid expression type, and checking if a given token is an operand or an operator.
 */
public interface Parser {
    /**
     *
     * @return should build the Composite design tree
     */
    Expression buildExpressionTree();
    /**
     * Determines if the given expression is a valid expression type for the parser. (Prototype)
     *
     * @param expression: A String representing the expression to be evaluated.
     * @return true if the given expression is a valid expression type; otherwise, false.
     */
    boolean isThisTypeExpression(String expression);
    /**
     * Determines if the given token is a valid operand.
     *
     * @param token: String, but basically char because always length one.
     * @return true if the given token is a numeric operand
     *      (double, integer, negative or positive); otherwise, false.
     */
    default boolean isOperand(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Determines if the given token is a valid operator.
     *
     * @param token: String, again char - not trying to cast back and forth.
     * @return true if the given token is an operator (+, -, *, /, %, or ^);
     *          otherwise, false.
     */
    default boolean isOperator(String token) {
        return "+-*/%^".contains(token);
    }
}
