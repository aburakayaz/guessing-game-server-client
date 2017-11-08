package cs408.Server;

import cs408.Common.ConnectionHandler;
import cs408.Common.GUIMessageHandler;
import cs408.Common.MessageHandler;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server extends Thread{

    private ServerSocket socket;
    private MessageHandler messageHandler;
    private ConnectionHandler connectionHandler;
    private int port;
    ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private boolean terminated = false;
    int idCounter = 0;

    /**
     * This class is the main Server Thread, which will listen for incoming connections from clients.
     */
    public Server(int port, MessageHandler messageHandler, ConnectionHandler connectionHandler) {
        this.messageHandler = messageHandler;
        this.port = port;
        this.connectionHandler = connectionHandler;
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
            while(!terminated) {
                try{
                    ClientHandler clientHandler = new ClientHandler(socket.accept(), messageHandler, clientHandlers, idCounter);
                    clientHandlers.add(clientHandler);
                    clientHandler.start();
                    idCounter ++;
                } catch(SocketException e){
                    //
                }
            }
        } catch (BindException e) {
            terminated = true;
            showMessage("That port is already being used!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String message) {
        messageHandler.showMessage(message);
    }

    /**
     * If the user wants to terminate the server, this thread simply destroys all the client threads
     * and closes the socket.
     */
    public void close() throws IOException {
        terminated = true;
        for(int i = 0; i < clientHandlers.size(); i++) {
            clientHandlers.get(i).interrupt();
            clientHandlers.get(i).closeSocket();
        }
        socket.close();
        connectionHandler.changeState(false);
        showMessage("Server has been terminated.");
    }

    public boolean isTerminated() {
        return terminated;
    }
}
