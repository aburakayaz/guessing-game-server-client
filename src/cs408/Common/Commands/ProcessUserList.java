package cs408.Common.Commands;

import cs408.Server.ClientHandler;

public class ProcessUserList extends CommandAbstract {
    public static final String NAME = "/processList";

    ProcessUserList(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {

    }
}
