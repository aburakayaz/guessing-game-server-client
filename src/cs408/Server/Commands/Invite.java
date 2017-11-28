package cs408.Server.Commands;

import cs408.Client.Commands.ReceiveInvite;
import cs408.Common.Commands.UsesMessage;
import cs408.Server.ClientHandler;
import cs408.Server.GameSession;

public class Invite extends CommandAbstract implements UsesMessage {
    public static final String NAME = "/Invite";
    private int id;

    public Invite(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        if (!clientHandler.isAvailable()) {
            clientHandler.sendMessage("You have already invited someone else or you are in a game!");
            return;
        }

        if (id == clientHandler.getClient().getId()) {
            clientHandler.sendMessage("You cannot invite yourself!");
            return;
        }

        ClientHandler targetClient = server.getClientHandlers().getByID(id);

        if (targetClient == null) {
            clientHandler.sendMessage("The user you invited is no longer connected!");
            return;
        }

        if (!targetClient.isAvailable()) {
            clientHandler.sendMessage("The user you invited is not available right now!");
            return;
        }

        server.getGameSessions().add(new GameSession(clientHandler, targetClient));
        targetClient.sendMessage(ReceiveInvite.NAME + " " + clientHandler.getClient().getId() + " " + clientHandler.getClient().getRefName());
    }

    @Override
    public void useMessage(String message) {
        id = Integer.parseInt(message.split(" ")[1]);
    }
}
