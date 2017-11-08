package cs408.Server;

import cs408.Common.Commands.*;

class CommandHandler {
    private Commands commands;
    private ClientHandler clientHandler;

    CommandHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        commands = new Commands();
        commands.add(new SendUserList(clientHandler));
        commands.add(new SetUsername(clientHandler));
    }

    void handle(String message) {
        String commandString = message.split(" ")[0];
        Command command = commands.find(commandString);

        if (command == null) {
            clientHandler.getServer().getMessageHandler()
                    .showMessage("Client is sending an unknown command: " + commandString);
            return;
        }

        if (command instanceof UsesMessage) {
            ((UsesMessage) command).useMessage(message);
        }

        command.act();
    }
}
