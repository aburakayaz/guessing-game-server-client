package cs408.Client.Commands;

import cs408.Client.Client;
import javafx.application.Platform;

public class RoundTie extends CommandAbstract {
	public static final String NAME = "/RoundTie";

	public RoundTie(Client client) {
		super(client);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void act() {
		client.getMessageHandler().showMessage("Tie! Next round begins.");
		Platform.runLater(this::setScore);
	}

	public void setScore() {
		client.getGameController().setScore(2);
	}
}
