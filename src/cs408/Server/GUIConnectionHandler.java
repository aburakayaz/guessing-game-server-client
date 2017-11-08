package cs408.Server;

import cs408.Common.ConnectionHandler;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class GUIConnectionHandler implements ConnectionHandler {
    private Button listen;

    /**
     * This class implements the ConnectionHandler interface.
     */

    public GUIConnectionHandler(Button listen) {
        this.listen = listen;
    }

    /**
     * When connection state changes, the GUI should be updated (listen for instance)
     */
    @Override
    public void changeState(boolean connected) {
        Platform.runLater(() -> {
            if (connected) {
                listen.setText("Terminate");
                return;
            }
            listen.setText("Listen");
        });
    }
}
