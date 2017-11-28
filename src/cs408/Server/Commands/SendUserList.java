package cs408.Server.Commands;

import cs408.Client.Commands.ProcessUserList;
import cs408.Client.Commands.ReceiveUserList;
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
        server.getMessageHandler().showMessage(getServerMessage());

        clientHandler.sendMessage(getClientMessage());
    }

    private String getServerMessage() {
        return "Client" +
                clientHandler.getClient().getRefName() +
                " wants the current player list. Sending...";
    }

    private String getClientMessage() {
        if (server.isUserListUpToDate()) {
            return server.getUserList();
        }

        StringBuilder message = new StringBuilder(ReceiveUserList.NAME + " User List:");

        for (int i = 0; i < server.getClientHandlers().size(); i++) {
            message.append("\n");
            message.append(ProcessUserList.NAME);
            message.append(" User : ");
            message.append(server.getClientHandlers().get(i).getClient().getId());
            message.append(" : ");
            message.append(server.getClientHandlers().get(i).getClient().getRefName());
        }

        server.setUserList(message.toString());

        return message.toString();
    }
}
