package cs408.Client;

import cs408.Client.Commands.CommandHandler;
import cs408.Client.Game.GameController;
import cs408.Common.MessageHandler;
import cs408.Server.Commands.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread {
	private Socket socket;
	private String ip;
	private int port;
	private CommandHandler commandHandler;
	private MessageHandler messageHandler;
	private GUIHandler guiHandler;
	private PrintWriter out;
	private BufferedReader in;
	private boolean connected = true;
	private String username;
	private ObservableList<String> onlineUsers;
	public GameController gameController;

	/**
	 * This class is the client thread, in which the application will listen the server for incoming messages and
	 * sending messages.
	 */
	public Client(String ip, int port, MessageHandler messageHandler, GUIHandler guiHandler, String username) {
		this.messageHandler = messageHandler;
		this.guiHandler = guiHandler;
		this.ip = ip;
		this.port = port;
		this.username = username;

		commandHandler = new CommandHandler(this);
		onlineUsers = FXCollections.observableArrayList();
	}

	/**
	 * Connecting to the socket with given IP address and port.
	 * Updating GUI as connected.
	 * Initializing the input / output.
	 * Sends the server /setUserName command to set its username as given by the user.
	 */
	public void run() {
		try {
			socket = new Socket(ip, port);
			guiHandler.changeConnectionState(true);
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			messageHandler.showMessage("Client has connected to the server.");
			messageHandler.showMessage("Sending username information to the server.");
			sendMessage(SetUsername.NAME + " " + username);
			connectionLoop();
		} catch (ConnectException e) {
			messageHandler.showMessage("Connection was refused.");
			connected = false;
		} catch (UnknownHostException e) {
			messageHandler.showMessage("Unknown host. Could not connect.");
			connected = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void surrender() {
		messageHandler.showMessage("Surrendering...");
		sendMessage(Surrender.NAME);
	}

	/**
	 * Guesses a number
	 */
	public void guessNumber(String number) {
		messageHandler.showMessage("Guessing the number " + number);
		sendMessage(Guess.NAME + " " + number);
	}

	/**
	 * Sends server to /sendList command. Server will reply this command as sending player list.
	 */
	public void requestUserList() {
		messageHandler.showMessage("Requesting player list from the server.");
		sendMessage(SendUserList.NAME);
	}

	/**
	 * Sends server a /invite command to invite a user to a game.
	 */
	public void inviteUser(int id) {
		messageHandler.showMessage("Inviting " + id + " to a game, sending message to the server.");
		sendMessage(Invite.NAME + " " + id);
	}

	/**
	 * Sends a message to the server using PrintWriter
	 */
	public void sendMessage(String message) {
		messageHandler.showMessage("Sending message to the server: " + message);
		out.println(message);
		out.flush();
	}

	/**
	 * Closes the socket
	 */
	public void close() throws IOException {
		socket.close();
	}

	/**
	 * In case of losing connection, this function will run and close all the input and output. Will set
	 * the connected property to false and set GUI as disconnected.
	 */
	private void loseConnection() {
		try {
			in.close();
			out.close();
			messageHandler.showMessage("Disconnected from the server.");
			connected = false;
			guiHandler.changeConnectionState(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * While connected to the server, client will listen for incoming messages and print them. In case of any exception
	 * the connection will be lost and it will call the loseConnection function.
	 */
	private void connectionLoop() {
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				messageHandler.showMessage("[SERVER]: " + inputLine);

				commandHandler.handle(inputLine);
			}
			loseConnection();
		} catch (SocketException e2) {
			loseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public GUIHandler getGuiHandler() {
		return guiHandler;
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
}
