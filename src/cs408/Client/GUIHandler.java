package cs408.Client;

import cs408.Common.Commands.Accept;
import cs408.Common.Commands.Decline;
import cs408.Common.ConnectionHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.util.Optional;

public class GUIHandler {
    private Button connect;
    private Button listUsers;
    private ListView onlineList;
    private Client client;
    private ObservableList<String> onlineUsers;

    public GUIHandler(Button connect, Button listUsers, ListView onlineList) {
        this.connect = connect;
        this.listUsers = listUsers;
        this.onlineList = onlineList;
        this.onlineUsers = FXCollections.observableArrayList();
    }

    public void changeConnectionState(boolean connected) {
        Platform.runLater(() -> {
            if (connected) {
                connect.setText("Disconnect");
                listUsers.setDisable(false);
                return;
            }
            connect.setText("Connect");
            listUsers.setDisable(true);
        });
    }

    public void addUser(String user) {
        Platform.runLater(() -> {
            onlineUsers.add(user);
            onlineList.setItems(onlineUsers);
        });
    }

    public void removeUsers() {
        Platform.runLater(() -> {
            onlineUsers.clear();
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void showGameInvitation(String username) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Invitation");
            alert.setHeaderText(username + " challenges you to a game!");
            alert.setContentText("You can accept and face your fate or you may escape from it");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                client.sendMessage(Accept.NAME);
            } else {
                client.sendMessage(Decline.NAME);
            }
        });
    }

}
