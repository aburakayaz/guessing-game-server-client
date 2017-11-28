package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;

public class Kick extends CommandAbstract implements UsesMessage {
    public static final String NAME = "/Kick";

    Kick(Client client) {
        super(client);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {

    }

    @Override
    public void useMessage(String message) {
        client.getMessageHandler().showMessage(message.substring(6, message.length()));
    }
}
