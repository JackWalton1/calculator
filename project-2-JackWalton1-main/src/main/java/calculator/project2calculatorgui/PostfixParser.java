package calculator.project2calculatorgui;

import java.util.*;

public class PostfixParser implements Parser {
    private final String postfixString;
    private final NameBindings nameBindings;
    /**
     * Constructs a new PrefixParser for the given prefix expression.
     *
     * @param postfixString the expression to parse into Expression tree
     */
    public PostfixParser(String postfixString, NameBindings nameBindings) {
        this.postfixString = postfixString;
        this.nameBindings = nameBindings;
    }
    /**
     * Determines whether the given string is a valid postfix expression.
     *
     * @param expression the string to check
     * @return true if the string is a valid postfix expression, false otherwise
     */
    @Override
    public boolean isThisTypeExpression(String expression) {
        String[] tokens = expression.split("\\s+"); // split expression into tokens
        // check that first comes two numbers, and last comes an operator
        if(tokens.length >= 3 && (isOperator(tokens[0]) || isOperator(tokens[1]) ||
                (isOperand(tokens[tokens.length - 1])))) {
            return false;
        }
        int numOperands = 0;
        int numOperators = 0;
        for (String token : tokens) {
            if (isOperand(token) || nameBindings.isVariable(token)) {
                numOperands++;
            } else if (isOperator(token)) {
                numOperators++;
                if (numOperators >= numOperands) {
                    return false; // too many numbers, not enough operators
                }
            } else {
                return false; // invalid token
            }
        }
        return numOperands == numOperators + 1; // make sure there is one more digit that operators
    }

    /**
     * @return String: prefix notation version of the originally, postfix notation String
     */
    public String convertToPrefix() {
        Deque<String> deque = new LinkedList<>();
        String[] tokens = postfixString.split("\\s+");
        for (String token : tokens) {
            if (isOperator(token)) {
                String operand2 = deque.removeLast();
                String operand1 = deque.removeLast();
                String prefixExpression = token + " " + operand1 + " " + operand2;
                deque.addLast(prefixExpression);
            } else {
                deque.addLast(token);
            }
        }

        return deque.removeLast();
    }
    /**
     * Builds the expression tree for the postfix expression by converting it to prefix and running
     * prefix's build expression tree method.
     *
     * @return the root node of the expression tree
     */
    @Override
    public Expression buildExpressionTree() {
        // put string into prefix notation and then that into prefixParser.
        PrefixParser prefixParser = new PrefixParser(convertToPrefix(), nameBindings);
        return prefixParser.buildExpressionTree();
    }
}
