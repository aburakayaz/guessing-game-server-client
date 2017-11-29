package cs408.Server;

import cs408.Common.Commands.CommandHandlerAbstract;
import cs408.Common.Commands.*;
import cs408.Server.Commands.*;

class CommandHandler extends CommandHandlerAbstract {
    private ClientHandler clientHandler;

    CommandHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        addCommands();
    }

    @Override
    protected void addCommands() {
        commands.put(SendUserList.NAME, new SendUserList(clientHandler));
        commands.put(SetUsername.NAME, new SetUsername(clientHandler));
        commands.put(Invite.NAME, new Invite(clientHandler));
        commands.put(Accept.NAME, new Accept(clientHandler));
        commands.put(Decline.NAME, new Decline(clientHandler));
        commands.put(Surrender.NAME, new Surrender(clientHandler));
    }

    @Override
    protected void unknownCommand(String command) {
        clientHandler.getServer().getMessageHandler()
                .showMessage("Client is sending an unknown command: " + command);
    }
}
