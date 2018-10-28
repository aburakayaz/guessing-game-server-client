package cs408.Client.Commands;

import cs408.Client.Client;
import javafx.application.Platform;

public class Win extends CommandAbstract {
    public static final String NAME = "/Win";

    Win(Client client) {
        super(client);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        client.getMessageHandler().showMessage("You have won the game.");
        Platform.runLater(this::setGameResult);
    }

    public void setGameResult() {
        client.getGameController().setGameResult(true);
    }
}
