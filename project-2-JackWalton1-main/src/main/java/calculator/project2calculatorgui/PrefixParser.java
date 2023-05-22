package calculator.project2calculatorgui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A class implementation of Parser interface for prefix expressions.
 */
public class PrefixParser implements Parser{
    private final Queue<String> prefixQueue;
    private final NameBindings nameBindings;

    /**
     * Constructs a new PrefixParser for the given prefix expression.
     *
     * @param prefixString the expression to parse into Expression tree
     */
    public PrefixParser(String prefixString, NameBindings nameBindings){
        prefixQueue = new LinkedList<>(Arrays.asList(prefixString.split(" ")));
        this.nameBindings = nameBindings;

    }

    /**
     * Creates a new expression node based on the given operator and operands.
     *
     * @param prefixString the prefix expression
     * @param left the left operand
     * @param right the right operand
     * @return the new expression node
     * @throws IllegalStateException if the operator is not recognized
     */
    public Expression createExpressionRoot(String prefixString, Expression left, Expression right) {
        char c = prefixString.charAt(0);
        if(c == '+'){
            return new AddNode(left, right);
        }else if(c == '-'){
            return new SubtractNode(left, right);
        }else if(c == '*'){
            return new MultiplyNode(left, right);
        }else if(c == '^'){
            return new ExpNode(left, right);
        }else if(c == '/'){
            return new DivideNode(left, right);
        }else if(c == '%'){
            return new ModuloNode(left, right);
        }else{
            throw new IllegalStateException("Unexpected value: " + c);
        }
    }

    /**
     * Builds the expression tree for the prefix expression.
     *
     * @return the root node of the expression tree
     */
    public Expression buildExpressionTree(){
        String s = prefixQueue.remove();
        if(isOperator(s)){
            Expression left = buildExpressionTree();
            Expression right = buildExpressionTree();
            return createExpressionRoot(s, left, right);
        }else if (nameBindings.isVariable(s)){
            return new Variable(s, nameBindings.getBinding(s));
        }else{
            return new DigitNode(s);
        }
    }

    /**
     * Determines whether the given string is a valid prefix expression.
     *
     * @param expression the string to check
     * @return true if the string is a valid prefix expression, false otherwise
     */
    @Override
    public boolean isThisTypeExpression(String expression) {
        String[] tokens = expression.split("\\s+"); // split expression into tokens
        // check that first comes an operator, and last comes two numbers
        if(tokens.length >= 3 && (isOperand(tokens[0]) || isOperator(tokens[tokens.length - 1]) ||
                                  (isOperator(tokens[tokens.length - 2])))) {
            return false;
        }
        int numOperands = 0;
        int numOperators = 0;
        for (String token : tokens) {
            if (isOperand(token) || nameBindings.isVariable(token)) {
                numOperands++;
            } else if (isOperator(token)) {
                numOperators++;
                if (numOperators <= numOperands) {
                    return false; // too many numbers, not enough operators
                }
            } else {
                return false; // invalid token
            }
        }
        return numOperands == numOperators + 1; // make sure there is one more digit that operators
    }

}
