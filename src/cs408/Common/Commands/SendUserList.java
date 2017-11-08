package cs408.Common.Commands;

import cs408.Server.ClientHandler;

public class SendUserList extends CommandAbstract {
    public static final String NAME = "/SendUserList";

    public SendUserList(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        clientHandler.getServer().getMessageHandler().showMessage(getServerMessage());

        clientHandler.sendMessage(getClientMessage());
    }

    private String getServerMessage() {
        return "Client" +
                clientHandler.getClient().getRefName() +
                " wants the current player list. Sending...";
    }

    private String getClientMessage() {
        if (clientHandler.getServer().isUserListUpToDate()) {
            return clientHandler.getServer().getUserList();
        }

        StringBuilder message = new StringBuilder("User List:");

        for (int i = 0; i < clientHandler.getServer().getClientHandlers().size(); i++) {
            message.append("\n");
            message.append(clientHandler.getServer().getClientHandlers().get(i).getClient().getId());
            message.append(": ");
            message.append(clientHandler.getServer().getClientHandlers().get(i).getClient().getRefName());
        }

        clientHandler.getServer().setUserList(message.toString());

        return message.toString();
    }
}
