import calculator.project2calculatorgui.DigitNode;
import calculator.project2calculatorgui.ExpressionType;
import calculator.project2calculatorgui.NameBindings;
import calculator.project2calculatorgui.Variable;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VariableTest {
    @Test
    void equalsTests() {
        Variable v1 = new Variable("a","2");
        Variable v2 = new Variable("b","-1.23");
        Variable v3 = new Variable("a","2");
        Variable v4 = new Variable("c","-1.23");

        Object o = new Object();
        assertNotEquals(v1, v2);
        assertNotEquals(v2, o);
        assertNotEquals(v2, v4);
        assertEquals(v1, v3);
        assertEquals(v1, v1);
    }

    @Test
    void toStringTests(){
        Variable v = new Variable("b","-1.23");
        String expected = "b = -1.23";
        String expected2 = "b";
        assertEquals(expected, v.toString());
        assertEquals(expected2, v.stringify(ExpressionType.PREFIX));
        assertEquals(v.stringify(ExpressionType.POSTFIX), v.stringify(ExpressionType.PREFIX));

    }

    @Test
    void hashCodeTests(){
        Variable v = new Variable("b","-1.23");
        Variable v2 = new Variable("c", "123");
        Variable v3 = new Variable("b","-1.23");
        assertEquals(v.hashCode(), v3.hashCode());
        assertNotEquals(v2.hashCode(), v3.hashCode());
    }

    @Test
    void nameBindingsTest(){
        NameBindings nameBindings = new NameBindings();
        Variable v = new Variable("a", "-.1");
        Variable v2 = new Variable("b", "1");
        Variable v3 = new Variable("c", "100");
        nameBindings.addBinding(v);
        nameBindings.addBinding(v2);
        nameBindings.addBinding(v3);
        List<Variable> expected = new ArrayList<>();
        expected.add(v);
        expected.add(v2);
        expected.add(v3);
        assertEquals(expected, nameBindings.getVariablesList());
        // test update a variable
        Variable v4 = new Variable("a", "-100");
        nameBindings.addBinding(v4);
        List<Variable> expected2 = new ArrayList<>();
        expected2.add(v4);
        expected2.add(v2);
        expected2.add(v3);
        assertEquals(expected2, nameBindings.getVariablesList());




    }

}
