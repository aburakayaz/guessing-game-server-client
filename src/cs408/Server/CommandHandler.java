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
        commands.add(new SendUserList(clientHandler));
        commands.add(new SetUsername(clientHandler));
        commands.add(new Invite(clientHandler));
        commands.add(new Accept(clientHandler));
        commands.add(new Decline(clientHandler));
        commands.add(new Surrender(clientHandler));
    }

    @Override
    protected void unknownCommand(String command) {
        clientHandler.getServer().getMessageHandler()
                .showMessage("Client is sending an unknown command: " + command);
    }
}
