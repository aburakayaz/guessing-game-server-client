package cs408.Server.Commands;

import cs408.Client.Commands.Kick;
import cs408.Common.Commands.UsesMessage;
import cs408.Server.ClientHandler;

public class SetUsername extends CommandAbstract implements UsesMessage {
	public static final String NAME = "/SetUserName";
	private String username;

	public SetUsername(ClientHandler clientHandler) {
		super(clientHandler);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void act() {
		server.getMessageHandler().showMessage(getServerFirstInfoMessage());

		if (server.getClientHandlers().hasUsername(username)) {
			kickClient();
			return;
		}

		setClientUserName();
	}

	@Override
	public void useMessage(String message) {
		username = message.split(" ")[1];
	}

	private void setClientUserName() {
		if (!clientHandler.getClient().hasUsername()) {
			clientHandler.getClient().setUsername(username);
		}

		server.getMessageHandler().showMessage(getServerSuccessMessage());
	}

	private void kickClient() {
		server.getMessageHandler().showMessage(getServerActionInfoMessage());
		clientHandler.sendMessage(Kick.NAME + " Username is already taken.");
		server.getClientHandlers().remove(clientHandler);
		clientHandler.closeSocket();
	}

	private String getServerActionInfoMessage() {
		return "Client " +
				clientHandler.getClient().getRefName() +
				" wanted to select a nickname that is already taken. Kicking the client...";
	}

	private String getServerFirstInfoMessage() {
		return "Client " +
				clientHandler.getClient().getRefName() +
				" wants to set its username. Setting...";
	}

	private String getServerSuccessMessage() {
		return "Set client " +
				clientHandler.getClient().getId() +
				" nickname to: " + clientHandler.getClient().getUsername();
	}
}
