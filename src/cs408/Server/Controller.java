package cs408.Server;

import cs408.Common.GUIMessageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * This class is the bridge between GUI and Server Thread
 */
public class Controller {
    private Server server;

    @FXML
    private TextField port;
    @FXML
    private TextArea console;
    @FXML
    private Button listen;

    /**
     * When Listen/Terminate button is pressed and if all the fields are completed, a new Server will be created to
     * connect to the
     * given IP and port.
     */
    public void listenPort() {
        if (server != null && !server.isTerminated()) {
            terminateServer();
            return;
        }

        if (port.getText().equals("") || port.getText() == null) {
            console.appendText("Please enter a port number. \n");
            return;
        }

        int portNumber = Integer.parseInt(port.getText());

        server = new Server(portNumber, new GUIMessageHandler(console), new GUIConnectionHandler(listen));
        server.start();
    }

    private void terminateServer() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
