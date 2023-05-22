import calculator.project2calculatorgui.DigitNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DigitNodeTest {
    @Test
    void equalsTests() {
        DigitNode d1 = new DigitNode("2");
        DigitNode d2 = new DigitNode("2");
        DigitNode d3 = new DigitNode("5");
        Object o = new Object();
        assertEquals(new DigitNode("2"), d1);
        assertEquals(d1, d2);
        assertEquals(d1, d1);
        assertNotEquals(d1, d3);
        assertNotEquals(d1, o);
    }
}
