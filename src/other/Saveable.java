package other;

import gameCore.GameBoard;
import gameCore.Tile;

import java.io.*;
import java.util.ArrayList;

public class Saveable implements Serializable {

    private int ROWS = GameBoard.ROWS;
    private int COLS = GameBoard.COLS;

    private SaveTile[][] lastBoard;
    private int lastScore;
    private ArrayList<Integer> highScores;


    public Saveable() {
        lastBoard = new SaveTile[ROWS][COLS];
        highScores = new ArrayList<>();
        lastScore = 0;
    }

    public int getHighScore() {
        if (highScores.size() != 0)
            return highScores.get(0);
        else
            return 0;
    }

    public Tile[][] getLastBoard() {
        Tile[][] tiles = new Tile[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (lastBoard[row][col] != null)
                    tiles[row][col] = lastBoard[row][col].convertSaveTileToTile();
                else
                    tiles[row][col] = null;
            }
        }

        return tiles;
    }

    public void setLastBoard(Tile[][] currentBoard) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (currentBoard[row][col] != null)
                    lastBoard[row][col] = Tile.convertTileToSaveTile(currentBoard[row][col]);
                else
                    lastBoard[row][col] = null;
            }
        }
    }

    public void saveGame(){
        try {

            FileOutputStream f = new FileOutputStream("SavedGame");
            ObjectOutputStream output = new ObjectOutputStream(f);
            output.writeObject(this);
            f.close();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Saveable loadGame() {
        Saveable load = null;
        try {

            FileInputStream f = new FileInputStream("SavedGame");
            ObjectInputStream input = new ObjectInputStream(f);
            load = (Saveable) input.readObject();
            f.close();
            input.close();
            System.out.println("Game loaded");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return load;
    }

    public void addNewHighScore(int currentScore) {
        highScores.add(0, currentScore);
        for (Integer highScore : highScores) {
            System.out.println("ArrayList: "+ highScore);
        }
    }

    public void setLastScore(int currentScore) {
        lastScore = currentScore;
    }

    public int getLastScore() {
        return lastScore;
    }

    public ArrayList<? extends Number> getList() {
        return highScores;
    }
}
