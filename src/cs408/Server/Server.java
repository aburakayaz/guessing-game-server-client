package cs408.Server;

import cs408.Common.ConnectionHandler;
import cs408.Common.MessageHandler;
import cs408.Server.Game.GameSessions;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server extends Thread {

	private ServerSocket socket;
	private MessageHandler messageHandler;
	private ConnectionHandler connectionHandler;
	private String userList;
	private int port;
	private ClientHandlers clientHandlers;
	private GameSessions gameSessions;
	private boolean isTerminated;
	private int idCounter;

	/**
	 * This class is the main Server Thread, which will listen for incoming connections from clients.
	 */
	public Server(int port, MessageHandler messageHandler, ConnectionHandler connectionHandler) {
		this.messageHandler = messageHandler;
		this.port = port;
		this.connectionHandler = connectionHandler;

		clientHandlers = new ClientHandlers();
		gameSessions = new GameSessions();
		userList = "";
		idCounter = 0;
		isTerminated = false;
	}

	/**
	 * Creates a socket with given port, listens for incoming connections. For each connection, a new thread is created
	 * and they are appointed with an id.
	 */
	@Override
	public void run() {
		try {
			socket = new ServerSocket(port);
			connectionHandler.changeState(true);
			showMessage("Server is now listening on port " + port + ".");
			handleClients();
		} catch (BindException e) {
			isTerminated = true;
			showMessage("That port is already being used!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleClients() throws IOException {
		while (!isTerminated) {
			try {
				ClientHandler clientHandler = new ClientHandler(socket.accept(), this);
				clientHandlers.add(clientHandler);
				clientHandler.start();
				resetUserList();
				idCounter++;
			} catch (SocketException e) {
				//
			}
		}
	}

	public void showMessage(String message) {
		messageHandler.showMessage(message);
	}

	/**
	 * If the user wants to terminate the server, this thread simply destroys all the client threads
	 * and closes the socket.
	 */
	void close() throws IOException {
		isTerminated = true;

		for (ClientHandler clientHandler : clientHandlers) {
			clientHandler.interrupt();
			clientHandler.closeSocket();
		}

		socket.close();
		connectionHandler.changeState(false);
		showMessage("Server has been terminated.");
	}

	boolean isTerminated() {
		return isTerminated;
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public ClientHandlers getClientHandlers() {
		return clientHandlers;
	}

	int getIdCounter() {
		return idCounter;
	}

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	void resetUserList() {
		userList = "";
	}

	public GameSessions getGameSessions() {
		return gameSessions;
	}

	public boolean isUserListUpToDate() {
		return !userList.isEmpty();
	}
}
