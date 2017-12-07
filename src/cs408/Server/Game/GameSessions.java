package cs408.Server.Game;

import java.util.HashMap;

public class GameSessions extends HashMap<Integer, GameSession> {
	public void put(GameSession session) {
		put(session.getHost().getClient().getId(), session);
		put(session.getInvited().getClient().getId(), session);
	}
}
