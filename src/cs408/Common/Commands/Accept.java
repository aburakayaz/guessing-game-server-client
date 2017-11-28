package cs408.Common.Commands;

import cs408.Server.ClientHandler;
import cs408.Server.GameSession;

public class Accept extends CommandAbstract{
    public static final String NAME = "/accept";

    public Accept(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        GameSession gameSession = server.getGameSessions().getByInvited(clientHandler.getClient().getId());
        if(gameSession == null) {
            server.showMessage("A game session could not be found.");
            clientHandler.sendMessage("The invitation is no longer valid.");
            return;
        }
        gameSession.setInviteAccepted(true);
    }
}
