package cs408.Server.Commands;

import cs408.Common.Commands.Command;
import cs408.Server.ClientHandler;
import cs408.Server.Server;

abstract class CommandAbstract implements Command {
	ClientHandler clientHandler;
	Server server;

	public CommandAbstract(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
		this.server = clientHandler.getServer();
	}
}
