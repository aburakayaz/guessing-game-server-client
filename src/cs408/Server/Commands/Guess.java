package cs408.Server.Commands;

import cs408.Common.Commands.UsesMessage;
import cs408.Server.ClientHandler;

public class Guess extends CommandAbstract implements UsesMessage {
    public static String NAME = "/Guess";
    private int guess;

    public Guess(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        clientHandler.guessInGame(guess);
    }

    @Override
    public void useMessage(String message) {
        guess = Integer.parseInt(message.split(" ")[1]);
    }
}
