package cs408.Server.Game;

import cs408.Client.Commands.*;
import cs408.Server.ClientHandler;

import java.util.concurrent.ThreadLocalRandom;

public class GameSession {
	private ClientHandler host, invited, loser, winner;
	private boolean inviteAccepted;
	private int randomNumber, hostGuess, invitedGuess;
	private int hostWinCounter, invitedWinCounter, tieCounter;

	public GameSession(ClientHandler host, ClientHandler invited) {
		this.host = host;
		this.invited = invited;

		this.host.setSession(this);
		this.invited.setSession(this);

		hostWinCounter = invitedWinCounter = tieCounter = 0;
		resetGame();
	}

	private void resetGame() {
		pickRandomNumber();
		hostGuess = invitedGuess = -1;
	}

	private void pickRandomNumber() {
		randomNumber = ThreadLocalRandom.current().nextInt(1, 101);
	}

	public void guess(int playerId, int guess) {
		setGuess(playerId, guess);

		checkForRoundEnding();
	}

	private void setGuess(int playerId, int guess) {
		if (playerId == host.getClient().getId()) {
			hostGuess = guess;
			return;
		}

		invitedGuess = guess;
	}

	private void checkForRoundEnding() {
		if (hostGuess == -1 || invitedGuess == -1) {
			return;
		}

		endCurrentRound();
	}

	private void endCurrentRound() {
		int hostResult = Math.abs(randomNumber - hostGuess);
		int invitedResult = Math.abs(randomNumber - invitedGuess);

		resetGame();

		if (hostResult == invitedResult) {
			roundTie();
			return;
		}

		if (hostResult < invitedResult) {
			roundHostWin();
			return;
		}

		roundInvitedWin();
	}

	private void roundHostWin() {
		hostWinCounter++;

		host.sendMessage(RoundWin.NAME);
		invited.sendMessage(RoundLose.NAME);

		checkForGameEnding();
	}

	private void roundInvitedWin() {
		invitedWinCounter++;

		host.sendMessage(RoundLose.NAME);
		invited.sendMessage(RoundWin.NAME);

		checkForGameEnding();
	}

	private void roundTie() {
		invitedWinCounter++;
		hostWinCounter++;
		tieCounter++;

		host.sendMessage(RoundTie.NAME);
		invited.sendMessage(RoundTie.NAME);
	}

	private void checkForGameEnding() {
		int difference = Math.abs(hostWinCounter - invitedWinCounter);

		if (difference == 0 || (difference == 1 &&
				(hostWinCounter - tieCounter == 0 || invitedWinCounter - tieCounter == 0))) {
			return;
		}

		endGame();
	}

	private void endGame() {
		winner = host;
		loser = invited;

		if (hostWinCounter < invitedWinCounter) {
			loser = host;
			winner = invited;
		}

		gameOver();
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

		winner.getClient().incrementScore();

		winner.getServer().resetUserList();

		endSession();
	}

	public void endSession() {
		host.setSession(null);
		invited.setSession(null);
		host.getServer().getGameSessions().remove(this);
	}

	public void connectionEnd(ClientHandler connectionEnder) {
		winner = host;
		loser = invited;

		if(connectionEnder == host) {
			winner = invited;
			loser = host;
		}

		gameOver();
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
