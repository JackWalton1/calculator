package calculator.project2calculatorgui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  A class that stores mathematical variables and their values in a hashMap.
 */
public class NameBindings {
    private final HashMap<String, String> variables;
    public NameBindings(){
        variables = new HashMap<>();
    }

    /**
     * @param variable: new variable to be added to the hashmap of variables
     */
    public void addBinding(Variable variable){
        variables.put(variable.variableName, variable.variableValue);
    }

    /**
     * @param variableName: key to look up the value in the hashMap
     * @return String: value that corresponds with the key.
     */
    public String getBinding(String variableName){
        return variables.get(variableName);
    }

    /**
     * @return List<Variable>: a list representation of all the variables in the hashMap.
     */
    public List<Variable> getVariablesList(){
        List<Variable> variableList = new ArrayList<>();
        this.variables.forEach((key, value) -> {
            // decrease value by 10%
            Variable v = new Variable(key, value);
            variableList.add(v);
        });
        return variableList;
    }

    /**
     * @param key: the variable Name for lookup.
     * @return true if hashMap contains the variable Name and value pair, else false
     */
    public boolean isVariable(String key){
        return this.variables.containsKey(key);
    }

}
