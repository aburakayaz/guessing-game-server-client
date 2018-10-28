package cs408.Server.Game;

import java.util.HashMap;

public class GameSessions extends HashMap<Integer, GameSession> {
    public void put(GameSession session) {
        put(session.getHost().getClient().getId(), session);
        put(session.getInvited().getClient().getId(), session);
    }

    public void remove(GameSession session) {
        remove(session.getHost().getClient().getId(), session);
        remove(session.getInvited().getClient().getId(), session);
    }
}
