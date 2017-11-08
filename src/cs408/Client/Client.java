package cs408.Client;

import cs408.Common.ConnectionHandler;
import cs408.Common.MessageHandler;

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
    private MessageHandler messageHandler;
    private ConnectionHandler connectionHandler;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = true;
    private String username;

    /**
     * This class is the client thread, in which the application will listen the server for incoming messages and sending messages.
     */
    public Client(String ip, int port, MessageHandler messageHandler, ConnectionHandler connectionHandler, String username) {
        this.messageHandler = messageHandler;
        this.connectionHandler = connectionHandler;
        this.ip = ip;
        this.port = port;
        this.username = username;
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
            connectionHandler.changeState(true);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            messageHandler.showMessage("Client has connected to the server.");
            messageHandler.showMessage("Sending username information to the server.");
            sendMessage("/setUserName " + username);
            connectionLoop();
        } catch(ConnectException e) {
            messageHandler.showMessage("Connection was refused.");
            connected = false;
        } catch(UnknownHostException e) {
            messageHandler.showMessage("Unknown host. Could not connect.");
            connected = false;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends server to /sendList command. Server will reply this command as sending player list.
     */
    public void requestUserList() {
        messageHandler.showMessage("Requesting player list from the server.");
        sendMessage("/sendList");
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
            connectionHandler.changeState(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * While connected to the server, client will listen for incoming messages and print them. In case of any exception
     * the connection will be lost and it will call the loseConnection function.
     */
    private void connectionLoop(){
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                messageHandler.showMessage("Received message from the server: " + inputLine);
                if(inputLine.indexOf("/kick") == 0) {
                    messageHandler.showMessage(inputLine.substring(6, inputLine.length()));
                }
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
}
