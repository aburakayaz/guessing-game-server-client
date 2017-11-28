package cs408.Server.Commands;

import cs408.Server.ClientHandler;

public class Surrender extends CommandAbstract {
    public static final String NAME = "/Surrender";

    public Surrender(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void act() {
        clientHandler.getSession().setLoser(clientHandler);
        clientHandler.getSession().gameOver();
    }
}
