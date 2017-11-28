package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.CommandHandlerAbstract;

public class CommandHandler extends CommandHandlerAbstract {
    private Client client;

    public CommandHandler(Client client) {
        this.client = client;
        addCommands();
    }

    @Override
    protected void addCommands() {
        commands.add(new StartGame(client));
        commands.add(new Kick(client));
        commands.add(new ProcessUserList(client));
        commands.add(new ReceiveInvite(client));
        commands.add(new ReceiveUserList(client));
        commands.add(new Win(client));
        commands.add(new Lose(client));
    }

    @Override
    protected void unknownCommand(String command) {
        if (command.charAt(0) != '/') {
            return;
        }
        client.getMessageHandler().showMessage("Server is sending an unknown command: " + command);
    }
}
