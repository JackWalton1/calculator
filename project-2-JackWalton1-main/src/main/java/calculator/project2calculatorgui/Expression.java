package calculator.project2calculatorgui;
/**
 * An interface representing an arithmetic expression that can be evaluated to a numerical value.
 */
public interface Expression {
    /**
     * Evaluates the value of this expression and returns the result. (Prototype)
     *
     * @return the numerical result of evaluating this expression
     */
    double evaluate();

    String stringify(ExpressionType type);


}
