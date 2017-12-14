package cs408.Client.Commands;

import cs408.Client.Client;
import javafx.application.Platform;

public class Lose extends CommandAbstract {
	public static final String NAME = "/Lose";

	Lose(Client client) {
		super(client);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void act() {
		client.getMessageHandler().showMessage("You have lost the game.");
		Platform.runLater(this::setGameResult);
	}

	public void setGameResult() {
		client.getGameController().setGameResult(false);
	}
}
