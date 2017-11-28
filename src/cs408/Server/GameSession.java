package cs408.Server;

public class GameSession {
    private ClientHandler host, invited;
    private boolean inviteAccepted;
    private int winner;

    public GameSession(ClientHandler host, ClientHandler invited) {
        this.host = host;
        this.invited = invited;

        this.host.setAvailable(false);
        this.invited.setAvailable(false);
    }

    public boolean isInviteAccepted() {
        return inviteAccepted;
    }

    public void setInviteAccepted(boolean inviteAccepted) {
        this.inviteAccepted = inviteAccepted;
        if(!inviteAccepted) {
            informUsersNegative();
            return;
        }
        informUsersPositive();
    }

    private void informUsersPositive() {
        host.sendMessage(invited.getClient().getRefName() + " has accepted your game invite!");
        host.sendMessage("The game is starting");
        invited.sendMessage("The game is starting");
        //start the game somehow
    }

    private void informUsersNegative() {
        host.sendMessage(invited.getClient().getRefName() + " has declined your game invite!");
        endSession();
    }

    private void gameOver() {
        //send winner and loser who has won or lost
        endSession();
    }

    public void endSession() {
        host.setAvailable(true);
        invited.setAvailable(true);
        host.getServer().getGameSessions().remove(this);
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
        gameOver();
    }

    public ClientHandler getHost() {
        return host;
    }

    public ClientHandler getInvited() {
        return invited;
    }

}
