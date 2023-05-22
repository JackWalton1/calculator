package calculator.project2calculatorgui;

import java.util.ArrayList;
import java.util.List;
/**
 * A class representing the history of mathematical expressions and their results.
 */
public class History {
    /**
     * The maximum number of entries that can be stored in the history.
     */
    private static final int SIZE = 26;
    private final List<Expression> pastExpressions;
    /**
     *Constructs a new History object with empty lists of past expressions.
     */
    public History(){
        pastExpressions = new ArrayList<>();
    }

    /**
     * @return List<Expression>: getter for past Expressions
     */
    public List<Expression> getHistory() {
        return this.pastExpressions;
    }

    /**
     * Adds the given expression and result to the history.
     * If the history is already full, removes the last entry before adding the new one.
     * @param e the expression to be added
     *
     */
    public void addHistory(Expression e) {
        if (!pastExpressions.contains(e)) {
            if (pastExpressions.size() >= SIZE) {
                pastExpressions.remove(SIZE - 1);
            }
            pastExpressions.add(0, e);
        }
    }
}
