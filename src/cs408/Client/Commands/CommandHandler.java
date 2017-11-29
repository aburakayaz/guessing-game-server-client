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
        commands.put(StartGame.NAME, new StartGame(client));
        commands.put(Kick.NAME, new Kick(client));
        commands.put(ProcessUserList.NAME, new ProcessUserList(client));
        commands.put(ReceiveInvite.NAME, new ReceiveInvite(client));
        commands.put(ReceiveUserList.NAME, new ReceiveUserList(client));
        commands.put(Win.NAME, new Win(client));
        commands.put(Lose.NAME, new Lose(client));
    }

    @Override
    protected void unknownCommand(String command) {
        if (command.charAt(0) != '/') {
            return;
        }
        client.getMessageHandler().showMessage("Server is sending an unknown command: " + command);
    }
}
