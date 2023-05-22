module calculator.project2calculatorgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens calculator.project2calculatorgui to javafx.fxml;
    exports calculator.project2calculatorgui;
}