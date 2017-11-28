package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.UsesMessage;

public class ProcessUserList extends CommandAbstract implements UsesMessage {
    public static final String NAME = "/ProcessList";

    ProcessUserList(Client client) {
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
        String[] userInfo = message.split(" : ");
        client.getGuiHandler().addUser(userInfo[1] + " - " + userInfo[2]);
    }
}
