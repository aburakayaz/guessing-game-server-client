package cs408.Server;

import java.util.ArrayList;

public class ClientHandlers extends ArrayList<ClientHandler> {
    public boolean hasUsername(String username) {
        return this.stream().anyMatch(
                clientHandler -> clientHandler.getClient().getUsername().equals(username)
        );
    }
}