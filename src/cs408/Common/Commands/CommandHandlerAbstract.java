package cs408.Common.Commands;

public abstract class CommandHandlerAbstract {
	protected Commands commands;

	protected CommandHandlerAbstract() {
		commands = new Commands();
	}

	public void handle(String message) {
		String commandString = message.split(" ")[0];
		Command command = commands.get(commandString);

		if (command == null) {
			unknownCommand(commandString);
			return;
		}

		if (command instanceof UsesMessage) {
			((UsesMessage) command).useMessage(message);
		}

		command.act();
	}

	protected abstract void addCommands();

	protected abstract void unknownCommand(String command);
}
