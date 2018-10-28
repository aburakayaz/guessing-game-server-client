package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;
import cs408.Server.Commands.Accept;
import cs408.Server.Commands.Decline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ReceiveInvite extends CommandAbstract implements UsesMessage {
    public static final String NAME = "/ReceiveInvite";
    private String username;

    ReceiveInvite(Client client) {
        super(client);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        Platform.runLater(this::alertUser);
    }

    @Override
    public void useMessage(String message) {
        username = message.split(" ")[2];
    }

    private void alertUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Invitation");
        alert.setHeaderText(username + " challenges you to a game!");
        alert.setContentText("You can accept and face your fate or you may escape from it");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            client.sendMessage(Accept.NAME);
            return;
        }

        client.sendMessage(Decline.NAME);
    }
}
