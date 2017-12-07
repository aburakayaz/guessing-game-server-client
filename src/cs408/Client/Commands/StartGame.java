package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;
import cs408.Server.Commands.Surrender;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cs408/Client/Game/game.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("You are playing with " + username);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (IOException e) {
			// TODO: Implement errors
		}
	}

}
