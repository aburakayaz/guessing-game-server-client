package cs408.Server;

import java.util.ArrayList;

public class GameSessions extends ArrayList<GameSession> {
    public GameSession getByHost(int id) {
        for(int i = 0; i < size(); i++) {
            if(get(i).getHost().getClient().getId() == id) {
                return get(i);
            }
        }
        return null;
    }

    public GameSession getByInvited(int id) {
        for(int i = 0; i < size(); i++) {
            if(get(i).getInvited().getClient().getId() == id) {
                return get(i);
            }
        }
        return null;
    }
}
