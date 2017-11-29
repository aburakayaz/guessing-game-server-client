package cs408.Client;

import cs408.Common.GUIMessageHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
	Client client;
	String currentSelectedUser;

	@FXML
	private TextField port;
	@FXML
	private TextField ipAdress;
	@FXML
	private TextField userName;
	@FXML
	private Button connect;
	@FXML
	private Button listUsers;
	@FXML
	private TextArea console;
	@FXML
	private ListView onlineList;

	/**
	 * This class is the bridge between GUI and Client Thread
	 */
	public Controller() {

	}

	public void inviteUser() {
		if (currentSelectedUser != null) {
			client.inviteUser(Integer.parseInt(currentSelectedUser.split(" - ")[0]));
		}
	}

	/**
	 * When List Users button pressed, client will request the user list from the server, if it is initialized.
	 */
	public void listUsers() {
		if (client != null && client.isConnected()) {
			client.requestUserList();
			return;
		}
	}

	/**
	 * When Connect/Disconnect button is pressed and if all the fields are completed, a new Client will be created to
	 * connect to the
	 * given IP and port.
	 */
	public void connectServer() {
		if (client != null && client.isConnected()) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.interrupt();
			return;
		}

		if (ipAdress.getText().equals("") || ipAdress.getText() == null) {
			console.appendText("Please enter an IP adress. \n");
			return;
		}

		if (port.getText().equals("") || port.getText() == null) {
			console.appendText("Please enter a port number. \n");
			return;
		}
		if (userName.getText().equals("") || userName.getText() == null) {
			console.appendText("Please enter an username. \n");
			return;
		}

		int portNumber = Integer.parseInt(port.getText());

		client = new Client(ipAdress.getText(), portNumber, new GUIMessageHandler(console), new GUIHandler(connect,
                listUsers, onlineList), userName.getText());
		client.start();

		onlineList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				currentSelectedUser = newValue;
			}
		});
	}
}
