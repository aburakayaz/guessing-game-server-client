package cs408.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Main class, runs the client program and sets the scene. All the main program is implemented in Controller class.
     * This is just for running the GUI.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
        primaryStage.setTitle("CS408 Client");
        primaryStage.setScene(new Scene(root, 550, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        System.exit(1);
    }
}
