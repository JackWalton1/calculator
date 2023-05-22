package calculator.project2calculatorgui;

public class Variable implements Expression{
    String variableName;
    String variableValue;
    public Variable(String variableName, String variableValue){
        this.variableName = variableName;
        this.variableValue = variableValue;
    }
    /**
     * Evaluates the digit value of this variable as a double.
     *
     * @return double: the value of this variable converted from string to double
     */
    @Override
    public double evaluate() {
        return Double.parseDouble(this.variableValue);
    }
    /**
     * Returns the string representation of this variable node, which is simply its saved value.
     *
     * @return the string representation of this variable node
     */
    public String stringify(ExpressionType type) {
        return this.variableName;
    }
    /**
     * Determines whether this variable node is equal to another object. Two vars are considered equal if they have
     * the same name-value pair (Although that will never occur since NameBindings is a hashMap -> no repeats).
     *
     * @param o the object to compare this digit node to
     * @return true if the object is equal to this digit node, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        return this.variableValue.equals(((Variable) o).variableValue) &&
                this.variableName.equals(((Variable) o).variableName);
    }

    /**
     * Unused in this project, but SL wanted me to override hashCode() after overriding equals()
     *
     * @return int: hashCode
     */
    @Override
    public int hashCode() {
        int result = variableName.hashCode();
        result = 31 * result + variableValue.hashCode();
        return result;
    }

    /**
     * @return String: the string representation of variables to go in the GUI's comboBox
     */
    @Override
    public String toString(){
        return this.variableName + " = " + this.variableValue;
    }
}
