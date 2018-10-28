package cs408.Client.Commands;

import cs408.Client.Client;

public class ReceiveUserList extends CommandAbstract {
    public static final String NAME = "/ReceiveUserList";

    ReceiveUserList(Client client) {
        super(client);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        client.getGuiHandler().removeUsers();
    }
}
