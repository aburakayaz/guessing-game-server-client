package cs408.Common.Commands;

import cs408.Server.ClientHandler;
import cs408.Server.Server;

abstract class CommandAbstract implements Command {
    ClientHandler clientHandler;
    Server server;
    String fullInput;

    CommandAbstract(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.server = clientHandler.getServer();
    }

    @Override
    public void setFullInput(String fullInput) {
        this.fullInput = fullInput;
    }


}
