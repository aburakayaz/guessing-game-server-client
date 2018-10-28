package cs408.Server.Commands;

import cs408.Server.ClientHandler;
import cs408.Server.Game.GameSession;

public class Decline extends CommandAbstract {
    public static final String NAME = "/Decline";

    public Decline(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        GameSession gameSession = server.getGameSessions().get(clientHandler.getClient().getId());
        if (gameSession == null) {
            server.showMessage("A game session could not be found.");
            clientHandler.sendMessage("The invitation is no longer valid.");
            return;
        }
        gameSession.setInviteAccepted(false);
    }
}
