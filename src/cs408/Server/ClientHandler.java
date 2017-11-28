package cs408.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Server server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private CommandHandler commandHandler;

    private boolean isAvailable = true;

    /**
     * Since we want multiple users to enter the server, for each client, this Class is created as a thread.
     */
    ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        client = new Client(server.getIdCounter());
        commandHandler = new CommandHandler(this);
    }

    /**
     * Initializes the output/input and starts the infinite loop.
     */
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            server.getMessageHandler().showMessage(getClientConnectedMessage());

            connectionLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getClientConnectedMessage() {
        return "Client " + client.getRefName() + " has connected to the server.";
    }

    /**
     * If the user is diconnected in anyway, this function will be called.
     * This function closes the socket and input/output.
     */
    public void closeSocket() {
        try {
            socket.close();
            in.close();
            out.close();
            server.getMessageHandler().showMessage(getClientDisconnectedMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getClientDisconnectedMessage() {
        return "Client " + client.getRefName() + " has been disconnected.";
    }

    /**
     * To send a message to client.
     */
    public void sendMessage(String message) {
        server.getMessageHandler().showMessage(getSendingToClientMessage(message));
        out.println(message);
        out.flush();
    }

    private String getSendingToClientMessage(String message) {
        return "Sending message to the client " + client.getRefName() + ": " + message;
    }

    /**
     * The infinite loop which listens for client messages.
     * If client requests the user list, the server sends the user list.
     * If client wants to set their username, sets their username.
     * If that username is already taken, kicks the client.
     * After each of these steps, the client is informed.
     * In case of connection end, the closeSocket function is called.
     */
    private void connectionLoop() {
        try {
            handleIncomingMessages();
        } catch (IOException e) {
            //
        } finally {
            terminateConnection();
        }
    }

    private void handleIncomingMessages() throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null && !isInterrupted()) {
            displayReceivedMessage(inputLine);

            commandHandler.handle(inputLine);
        }
    }

    private void displayReceivedMessage(String message) {
        server.getMessageHandler().showMessage(getReceivedFromClientMessage(message));
    }

    private String getReceivedFromClientMessage(String message) {
        return "Received message from the client " + client.getRefName() + ": " + message;
    }

    private void terminateConnection() {
        if (socket.isClosed()) {
            return;
        }

        server.resetUserList();
        server.getClientHandlers().remove(this);
        closeSocket();
    }

    public Server getServer() {
        return server;
    }

    public Client getClient() {
        return client;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
