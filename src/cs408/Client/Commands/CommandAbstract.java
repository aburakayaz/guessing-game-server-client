package cs408.Client.Commands;

import cs408.Client.Client;
import cs408.Common.Commands.Command;

abstract class CommandAbstract implements Command {
    Client client;

    public CommandAbstract(Client client) {
        this.client = client;
    }
}
