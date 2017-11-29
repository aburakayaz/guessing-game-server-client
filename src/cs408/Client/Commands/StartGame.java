package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;
import cs408.Server.Commands.Surrender;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class StartGame extends CommandAbstract implements UsesMessage {
	public static final String NAME = "/StartGame";
	private String username;

	StartGame(Client client) {
		super(client);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void act() {
		Platform.runLater(this::startGame);
	}

	@Override
	public void useMessage(String message) {
		username = message.substring(11, message.length());
	}

	private void startGame() {
		ButtonType surrender = new ButtonType("Surrender", ButtonBar.ButtonData.OK_DONE);
		client.getGuiHandler().setGame(new Alert(Alert.AlertType.INFORMATION,
				"You can surrender or you can keep playing",
				surrender));
		client.getGuiHandler().getGame().setTitle("Game Window");
		client.getGuiHandler().getGame().setHeaderText("You are playing with " + username);

		Optional<ButtonType> result = client.getGuiHandler().getGame().showAndWait();
		if (result.isPresent() && result.get() == surrender) {
			client.sendMessage(Surrender.NAME);
		}
	}

}
