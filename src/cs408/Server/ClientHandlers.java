package cs408.Server;

import java.util.ArrayList;

public class ClientHandlers extends ArrayList<ClientHandler> {
    public boolean hasUsername(String username) {
        return this.stream().anyMatch(
                clientHandler -> clientHandler.getClient().getUsername().equals(username)
        );
    }

    public ClientHandler getByID(int id) {
        for(int i = 0; i < size(); i++) {
            if(get(i).getClient().getId() == id) {
                return get(i);
            }
        }
        return null;
    }
}