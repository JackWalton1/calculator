import calculator.project2calculatorgui.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryTest {
    @Test
    void historyTests(){
        History history = new History();
        List<Expression> pastExpressions = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            Expression en = new ExpNode(new DigitNode(String.valueOf(i)), new DigitNode("3"));
            history.addHistory(en);
            pastExpressions.add(0, en);
        }
        assertEquals(pastExpressions, history.getHistory());
        Expression en = new ExpNode(new DigitNode("1000"), new DigitNode("3"));
        history.addHistory(en);
        pastExpressions.remove(pastExpressions.size() -1);
        pastExpressions.add(0, en);
        assertEquals(pastExpressions, history.getHistory());
        // test adding a repeated expression should do nothing
        history.addHistory(en);
        assertEquals(pastExpressions, history.getHistory());


    }

}
