package cs408.Client.Game;

import cs408.Client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameController {
    @FXML
    private TextField numberTextField;

    @FXML
    private Button guessButton;

    @FXML
    private Button surrenderButton;

    @FXML
    private Label myScore;

    @FXML
    private Label enemyScore;

    @FXML
    private Label status;

    Client client;
    private boolean gameFinished = false;

    public void setGameResult(boolean win) {
        gameFinished = true;

        status.setText("You Lost!");

        if (win) {
            status.setText("You won!");
        }

        guessButton.setDisable(true);
        numberTextField.setDisable(true);
        surrenderButton.setDisable(true);
    }

    public void setScore(int roundResult) {
        if (roundResult == 0) {
            myScore.setText(Integer.toString(Integer.parseInt(myScore.getText()) + 1));
            status.setText("Round Won!");
            return;
        }

        if (roundResult == 1) {
            enemyScore.setText(Integer.toString(Integer.parseInt(enemyScore.getText()) + 1));
            status.setText("Round Lost!");
            return;
        }

        status.setText("Round Tie!");

        numberTextField.setText("");

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void guess() {
        client.guessNumber(numberTextField.getText());
        status.setText("Waiting");
    }

    public void surrender() {
        client.surrender();
    }

    public boolean isGameFinished() {
        return gameFinished;
    }
}
