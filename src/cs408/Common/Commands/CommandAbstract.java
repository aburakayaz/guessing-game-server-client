package cs408.Common.Commands;

import cs408.Server.ClientHandler;

abstract class CommandAbstract implements Command
{
    ClientHandler clientHandler;

    CommandAbstract(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}
