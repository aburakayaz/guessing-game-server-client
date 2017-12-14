package cs408.Client.Commands;

import cs408.Client.Client;
import javafx.application.Platform;

public class RoundLose extends CommandAbstract {
	public static final String NAME = "/RoundLose";

	public RoundLose(Client client) {
		super(client);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void act() {
		client.getMessageHandler().showMessage("You have lost the round. Next round begins.");
		Platform.runLater(this::setScore);
	}

	public void setScore() {
		client.getGameController().setScore(1);
	}
}
