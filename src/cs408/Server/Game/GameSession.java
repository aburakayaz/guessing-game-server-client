package cs408.Server.Game;

import cs408.Client.Commands.Lose;
import cs408.Client.Commands.StartGame;
import cs408.Client.Commands.Win;
import cs408.Server.ClientHandler;

import java.util.concurrent.ThreadLocalRandom;

public class GameSession {
	private ClientHandler host, invited, loser, winner;
	private boolean inviteAccepted;
	private int randomNumber, hostGuess, invitedGuess;
	private int hostWins, invitedWins;

	public GameSession(ClientHandler host, ClientHandler invited) {
		this.host = host;
		this.invited = invited;

		this.host.setSession(this);
		this.invited.setSession(this);

		hostWins = invitedWins = 0;
		resetGame();
	}

	private void resetGame() {
		pickRandomNumber();
		hostGuess = invitedGuess = -1;
	}

	private void pickRandomNumber() {
		randomNumber = ThreadLocalRandom.current().nextInt(1, 101);
	}

	public boolean isInviteAccepted() {
		return inviteAccepted;
	}

	public void setInviteAccepted(boolean inviteAccepted) {
		this.inviteAccepted = inviteAccepted;
		if (!inviteAccepted) {
			informUsersNegative();
			return;
		}
		informUsersPositive();
	}

	private void informUsersPositive() {
		host.sendMessage(invited.getClient().getRefName() + " has accepted your game invite!");
		host.sendMessage("The game is starting...");
		invited.sendMessage("The game is starting...");

		host.sendMessage(StartGame.NAME + ' ' + invited.getClient().getRefName());
		invited.sendMessage(StartGame.NAME + ' ' + host.getClient().getRefName());
	}

	private void informUsersNegative() {
		host.sendMessage(invited.getClient().getRefName() + " has declined your game invite!");
		endSession();
	}

	public void gameOver() {
		winner.sendMessage(Win.NAME);
		loser.sendMessage(Lose.NAME);
		endSession();
	}

	public void endSession() {
		host.setSession(null);
		invited.setSession(null);
		host.getServer().getGameSessions().remove(this);
	}

	public void setLoser(ClientHandler loser) {
		this.loser = loser;

		if (loser == host) {
			setWinner(invited);
			return;
		}

		setWinner(host);
	}

	private void setWinner(ClientHandler winner) {
		this.winner = winner;
	}

	ClientHandler getHost() {
		return host;
	}

	ClientHandler getInvited() {
		return invited;
	}
}
