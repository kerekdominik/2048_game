package gameCore;

import other.Saveable;

import java.io.File;
import java.util.ArrayList;

public class ScoreManager {

    //variables
    private Saveable data;
    private GameBoard gameBoard; //last state

    //constructor
    public ScoreManager(GameBoard gBoard) {

        this.gameBoard = gBoard;
        data = new Saveable();

        if (!isNewGame())
            data = data.loadGame();
    }

    public void saveLastState() {
        data.setLastBoard(gameBoard.getBoard());
    }

    public Tile[][] loadLastState() {
        return data.getLastBoard();
    }

    public boolean isNewGame() {
        File tmp = new File("SavedGame");

        return !tmp.exists();
    }

    public int getHighScore() {
        return data.getHighScore();
    }

    public void saveGame() {
        saveLastState();
        data.saveGame();
    }

    public void addNewHighScore(int currentScore) {
        data.addNewHighScore(currentScore);
    }

    public void setLastScore(int currentScore) {
        data.setLastScore(currentScore);
    }

    public int getLastScore() {
        return data.getLastScore();
    }

    public ArrayList<? extends Number> getList() {
        return data.getList();
    }
}
