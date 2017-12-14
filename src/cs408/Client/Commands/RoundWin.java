package cs408.Client.Commands;

import cs408.Client.Client;
import javafx.application.Platform;

public class RoundWin extends CommandAbstract {
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
}
