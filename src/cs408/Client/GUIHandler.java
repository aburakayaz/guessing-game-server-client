package cs408.Client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class GUIHandler {
	private Button connect;
	private Button listUsers;
	private ListView onlineList;
	private ObservableList<String> onlineUsers;
	private Alert game;

	GUIHandler(Button connect, Button listUsers, ListView onlineList) {
		this.connect = connect;
		this.listUsers = listUsers;
		this.onlineList = onlineList;
		this.onlineUsers = FXCollections.observableArrayList();
	}

	void changeConnectionState(boolean connected) {
		Platform.runLater(() -> {
			if (connected) {
				connect.setText("Disconnect");
				listUsers.setDisable(false);
				return;
			}
			connect.setText("Connect");
			listUsers.setDisable(true);
		});
	}

	public void addUser(String user) {
		Platform.runLater(() -> {
			onlineUsers.add(user);
			onlineList.setItems(onlineUsers);
		});
	}

	public void removeUsers() {
		Platform.runLater(() -> {
			onlineUsers.clear();
		});
	}

	public void closeGame() {
		Platform.runLater(() -> game.close());
	}

	public Alert getGame() {
		return game;
	}

	public void setGame(Alert game) {
		this.game = game;
	}
}
