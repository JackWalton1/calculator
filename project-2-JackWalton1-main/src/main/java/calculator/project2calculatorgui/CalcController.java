package calculator.project2calculatorgui;

import javafx.beans.binding.BooleanExpression;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.util.Arrays;
import java.util.List;

public class CalcController {

    @FXML
    private GridPane mathGridPane;
    @FXML
    private TextField enteredField;
    @FXML
    private TextField varField;
    @FXML
    private TextField valueField;
    @FXML
    private Button addVarsButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button useHistoryButton;
    @FXML
    private Button useVarButton;
    @FXML
    private ComboBox<Expression> historyBox;
    @FXML
    private RadioButton prefixRadioButton;
    @FXML
    private RadioButton postfixRadioButton;
    @FXML
    private ComboBox<Variable> varsBox;
    @FXML
    private Label errorLabel;
    @FXML
    private Label varsErrorLabel;
    private final History history = new History();
    private final NameBindings nameBindings = new NameBindings();
    private final double scalar = Math.pow(10, 7);

    /**
     * changes the comboBox's default printing method of toString() to stringify(ExpressionType type)
     */
    Callback<ListView<Expression>, ListCell<Expression>> cellFactory = l ->
        new ListCell<>() { //SL gives me parent warning on "new". Ayaan said this doesn't matter too much.
            @Override
            protected void updateItem(Expression item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else if (prefixRadioButton.isSelected()) {
                    setText(item.stringify(ExpressionType.PREFIX));
                } else {
                    setText(item.stringify(ExpressionType.POSTFIX));
                }
            }
        };

    /**
     * event handler that links with calculator buttons and handles calculator logic.
     */
    private final EventHandler<ActionEvent> handleButtonClick = e -> {
        if(!this.errorLabel.getText().isEmpty()){
            this.errorLabel.setText(" ");
        }
        Button clicked = (Button) e.getSource(); // get the thing that was clicked
        if(clicked.getText().equals("=")){
            Parser parser;
            if(this.prefixRadioButton.isSelected()){
                parser = new PrefixParser(this.enteredField.getText(), nameBindings);
            }else{
                parser = new PostfixParser(this.enteredField.getText(), nameBindings);
            }
            if (parser.isThisTypeExpression(this.enteredField.getText())) { //VALID EXPRESSION OPTION
                Expression root = parser.buildExpressionTree();
                double result = root.evaluate();
                if(result == Double.POSITIVE_INFINITY){ // HANDLE DIVIDE/MODULO BY 0
                    this.errorLabel.setText("Cannot divide by zero.");
                }else {                                 // OR PRINT OUTPUT OF EVALUATED EXPRESSION
                    this.enteredField.setText(String.valueOf(Math.round(result * scalar) / scalar));
                    this.history.addHistory(root);
                    this.historyBox.setItems(FXCollections.observableList(this.history.getHistory()));
                }
            }else{
                this.errorLabel.setText("Invalid expression.");
            }
        } else if(clicked.getText().equals("space")){
            this.enteredField.setText(this.enteredField.getText() + " ");
        }else if (clicked.getText().equals("C/E")){
            this.enteredField.setText("");
        }else{
            this.enteredField.setText(this.enteredField.getText() + clicked.getText());
        }
    };
    /**
     * event handler that links with historyButton that will grab the selected item in the comboBox
     */
    private final EventHandler<ActionEvent> handleHistoryClick = e -> {
        StringBuilder stringBuilder = new StringBuilder();

        if(this.historyBox.getValue() != null){
            if(!this.enteredField.getText().equals("")){
                stringBuilder.append(this.enteredField.getText()).append(" ");
            }
            if(this.prefixRadioButton.isSelected()){
                stringBuilder.append(this.historyBox.getValue().stringify(ExpressionType.PREFIX));
            }else{
                stringBuilder.append(this.historyBox.getValue().stringify(ExpressionType.POSTFIX));
            }
            this.enteredField.setText(stringBuilder.toString());
            // updates the format of all the items
            this.historyBox.setItems(FXCollections.observableList(this.history.getHistory()));
        }
    };
    /**
     * event handler that links with addVarsButton that will add vars to NameBindings
     */
    private final EventHandler<ActionEvent> handleAddVarsClick = e -> {
        // make sure that the value is not just "-", ".", or "-."
        if (!this.valueField.getText().equals("-") && !this.valueField.getText().equals(".") &&
                !this.valueField.getText().equals("-.")) {
            Variable v = new Variable(this.varField.getText(), this.valueField.getText());
            this.nameBindings.addBinding(v);
            this.varsBox.setItems(FXCollections.observableList(this.nameBindings.getVariablesList()));
            this.clearButton.fire();

        }else{
            this.varsErrorLabel.setText("Invalid variable-value pair.");
            this.valueField.clear();
            this.varField.clear();
        }
    };
    /**
     * event handler that links with clearButton that clears variable textFields
     */
    private final EventHandler<ActionEvent> handleClearClick = e -> {
        varsErrorLabel.setText("");
        this.valueField.clear();
        this.varField.clear();
    };
    /**
     * event handler that loads vars into the expression textField
     */
    private final EventHandler<ActionEvent> handleUseVarClick = e -> {
        if(this.varsBox.getValue() != null){
            this.enteredField.setText(this.enteredField.getText() + this.varsBox.getValue().variableName);
        }
    };
    /**
     * event handler that links with radio buttons and updates the item selected in history when switched.
     */
    private final EventHandler<ActionEvent> handleNotationUpdate= e ->
            this.historyBox.setItems(FXCollections.observableList(this.history.getHistory()));

    /**
     * basically "main" for this file. Runs when GUI is launched and sets up everything above.
     */
    @FXML
    protected void initialize() {
        // without these two lines, the actual value stored in the HistoryBox will not update.
        this.postfixRadioButton.setOnAction(this.handleNotationUpdate);
        this.prefixRadioButton.setOnAction(this.handleNotationUpdate);
        // setup history comboBox functionality
        this.historyBox.setButtonCell(cellFactory.call(null));
        this.historyBox.setCellFactory(cellFactory);
        // setup history button functionality
        this.useHistoryButton.setOnAction(this.handleHistoryClick);
        // setup variables add button, so it only works when textFields are not empty
        this.addVarsButton.disableProperty().bind(
                BooleanExpression.booleanExpression(this.valueField.textProperty().isEmpty()
                        .or(this.varField.textProperty().isEmpty()))
        );
        this.addVarsButton.setOnAction(this.handleAddVarsClick);
        this.clearButton.setOnAction(this.handleClearClick);
        this.useVarButton.setOnAction(this.handleUseVarClick);
        // setup variable textFields so they cannot be set to invalid values
        this.valueField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            Parser p = new PrefixParser("", nameBindings);
            if (!newText.contains(" ") && p.isOperand(newText) || newText.equals("-") ||
                    newText.equals(".") || newText.equals("-.") || newText.isEmpty()) {
                return change; // allow the change
            } else {
                return null; // don't allow the change
            }
        }));
        this.varField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            Parser p = new PrefixParser("", nameBindings);
            if (!p.isOperand(newText) && !p.isOperator(newText) && !newText.contains(" ") || newText.isEmpty()) {
                return change; // allow the change
            } else {
                return null; // don't allow the change
            }
        }));
        // setup calc buttons functionality
        List<String> options = Arrays.asList("0", "3", "6", "space", "1", "4", "7", "9",
                "2", "5", "8", ".", "+", "*", "%", "=", "-", "/", "^", "C/E");
        int optionChoice = 0;
        for (int i = 0; i < this.mathGridPane.getColumnCount(); i++) {
            for (int j = 0; j < this.mathGridPane.getRowCount(); j++) {
                // Create a new Button with no text
                Button button = new Button(options.get(optionChoice));
                // Make the button really wide, regardless of its text
                button.setMaxWidth(Integer.MAX_VALUE);
                // Add the button to the grid pane
                this.mathGridPane.add(button, i, j);
                optionChoice++;
                button.setOnAction(this.handleButtonClick);
            }
        }
    }
}