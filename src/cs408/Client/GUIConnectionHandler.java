package cs408.Client;

import cs408.Common.ConnectionHandler;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class GUIConnectionHandler implements ConnectionHandler {
    private Button connect;
    private Button listUsers;

    /**
     * This class implements the ConnectionHandler interface.
     */

    public GUIConnectionHandler(Button connect, Button listUsers) {
        this.connect = connect;
        this.listUsers = listUsers;
    }

    /**
     * When connection state changes, the GUI should be updated (connect/disconnect buttons for instance)
     */
    @Override
    public void changeState(boolean connected) {
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
}
