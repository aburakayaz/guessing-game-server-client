package cs408.Common;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class GUIMessageHandler implements MessageHandler {
    private TextArea console;

    public GUIMessageHandler(TextArea console) {
        this.console = console;
    }

    /**
     * Simply appends a message in GUI console.
     */
    @Override
    public void showMessage(String message) {
        Platform.runLater(() -> {
            console.appendText(message + "\n");
        });

    }
}
