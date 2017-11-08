package cs408.Server;

import cs408.Common.MessageHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandler messageHandler;
    private ArrayList<ClientHandler> clientHandlers;
    private int id;
    private String username;

    /**
     * Since we want multiple users to enter the server, for each client, this Class is created as a thread.
     */
    public ClientHandler(Socket socket, MessageHandler messageHandler, ArrayList<ClientHandler> clientHandlers, int id) {
        this.socket = socket;
        this.messageHandler = messageHandler;
        this.clientHandlers = clientHandlers;
        this.id = id;
    }

    /**
     * Initializes the output/input and starts the infinite loop.
     */
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            messageHandler.showMessage("Client " + getRefName()  + " has connected to the server.");

            connectionLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            messageHandler.showMessage("Client " + getRefName()  + " has been disconnected.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To send a message to client.
     */
    public void sendMessage(String message) {
        messageHandler.showMessage("Sending message to the client "+ getRefName() +": " + message);
        out.println(message);
        out.flush();
    }

    /**
     * If the user has an username (which they don't have at the very first step of their connection) this function will return the id
     * instead of username.
     */
    private String getRefName() {
        if(username == null) {
            return String.valueOf(id);
        }
        return username;
    }

    /**
     * The infinite loop which listens for client messages.
     * If client requests the user list, the server sends the user list.
     * If client wants to set their username, sets their username.
     * If that username is already taken, kicks the client.
     * After each of these steps, the client is informed.
     * In case of connection end, the closeSocket function is called.
     */
    private void connectionLoop(){
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null && !isInterrupted()) {
                messageHandler.showMessage("Received message from the client " + getRefName() + ": " + inputLine);
                /*
                TODO: Refactor
                 */
                if("/sendList".equals(inputLine)) {
                    messageHandler.showMessage("Client " + getRefName() + " wants the current player list. Sending...");
                    sendMessage("User List:");
                    for(int i = 0; i < clientHandlers.size(); i++) {
                        sendMessage(clientHandlers.get(i).id + ": " + clientHandlers.get(i).getRefName());
                    }
                } else if(inputLine.split(" ")[0].equals("/setUserName")) {
                    messageHandler.showMessage("Client " + getRefName() + " wants to set its username. Setting...");
                    String sentUserName = inputLine.split(" ")[1];
                    boolean rejected = false;
                    for(int i = 0; i < clientHandlers.size(); i++) {
                        if(sentUserName.equals(clientHandlers.get(i).username)) {
                            rejected = true;
                            break;
                        }
                    }
                    if(rejected) {
                        messageHandler.showMessage("Client " + getRefName() + " wanted to select a nickname that is already taken. Kicking the client...");
                        sendMessage("/kick Username is already taken.");
                        clientHandlers.remove(this);
                        closeSocket();
                       continue;
                    }
                    if(username == null) {
                        username = sentUserName;
                    }
                    messageHandler.showMessage("Setted client " + id + " nickname to: " + username);
                }
            }

        } catch (IOException e) {
            //
        } finally {
            if(!socket.isClosed()) {
                clientHandlers.remove(this);
                closeSocket();
            }
        }

    }
}
