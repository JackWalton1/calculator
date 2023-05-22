package calculator.project2calculatorgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalcApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CalcApplication.class.getResource("mainView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 240);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("calculator.css")));
            stage.setTitle("Calculator");
            stage.setScene(scene);
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
            throw new IOException("FXML or CSS file not found.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}