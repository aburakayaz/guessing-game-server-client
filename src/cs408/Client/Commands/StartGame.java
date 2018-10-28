package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartGame extends CommandAbstract implements UsesMessage {
    public static final String NAME = "/StartGame";
    private String username;

    StartGame(Client client) {
        super(client);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        Platform.runLater(this::startGame);
    }

    @Override
    public void useMessage(String message) {
        username = message.substring(11, message.length());
    }

    private void startGame() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cs408/Client/Game/game.fxml"));
            Parent root1 = fxmlLoader.load();

            client.setGameController(fxmlLoader.getController());
            client.getGameController().setClient(client);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("You are playing with " + username);
            stage.setScene(new Scene(root1));
            stage.setOnCloseRequest(event -> {
                if(!client.getGameController().isGameFinished()) {
                    client.surrender();
                }
            });
            stage.show();
        } catch (IOException e) {
            // TODO: Implement errors
        }
    }

}
