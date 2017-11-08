package cs408.Server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import cs408.Common.GUIMessageHandler;

import java.io.IOException;

public class Controller {
    Server server;

    @FXML
    private TextField port;
    @FXML
    private TextArea console;
    @FXML
    private Button listen;

    /**
     * This class is the bridge between GUI and Server Thread
     */

    /**
     * When Listen/Terminate button is pressed and if all the fields are completed, a new Server will be created to connect to the
     * given IP and port.
     */
    public void listenPort() {

        if(server != null && !server.isTerminated()) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if(port.getText().equals("") || port.getText() == null) {
            console.appendText("Please enter a port number. \n");
            return;
        }

        int portNumber = Integer.parseInt(port.getText());

        server = new Server(portNumber, new GUIMessageHandler(console), new GUIConnectionHandler(listen));
        server.start();
    }


}
