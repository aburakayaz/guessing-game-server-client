package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;
import javafx.application.Platform;

public class RoundWin extends CommandAbstract implements UsesMessage {
    public static final String NAME = "/RoundWin";

    public RoundWin(Client client) {
        super(client);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        client.getMessageHandler().showMessage("You have won the round. Next round begins.");
        Platform.runLater(this::setScore);
    }

    public void setScore() {
        client.getGameController().setScore(0);
    }

    @Override
    public void useMessage(String message) {
        String shownMessage = "The number was: " + message.split(" ")[1];
        client.getMessageHandler().showMessage(shownMessage);
    }
}
