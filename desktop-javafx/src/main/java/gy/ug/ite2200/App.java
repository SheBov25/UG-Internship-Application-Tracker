package gy.ug.ite2200;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        App.class.getResource("internship-view.fxml")
                );

        Scene scene =
                new Scene(loader.load(), 1150, 700);

        scene.getStylesheets().add(
                App.class.getResource("styles.css").toExternalForm()
        );

        stage.setTitle(
                "UG Internship Application Tracker"
        );

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}