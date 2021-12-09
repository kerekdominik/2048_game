package gameCore;

import control.Keys;
import other.Point;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameBoard {

    //varibales
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public static final int ROWS = 4;
    public static final int COLS = 4;

    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    private final int startingTiles = 2;
    private int x;
    private int y;
    private int currentScore;

    private boolean dead;
    private boolean hasStarted;

    private Leaderboards leaderBoard;
    private ScoreManager scores;
    private Tile[][] board;
    private BufferedImage gameBoard;

    private int saveCount = 0;

    //constructor
    public GameBoard(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        createBoardImage();

        //score things
        scores = new ScoreManager(this);

        if (scores.isNewGame()) {
            System.out.println("new");
            reset();

        } else {
            System.out.println("not new");

            currentScore = scores.getLastScore();
            System.out.println("Loaded last score: "+currentScore);

            hasStarted = false;
            board = scores.loadLastState();
            dead = checkDead();

        }

        leaderBoard = Leaderboards.getInstance();
        leaderBoard.setScoreManager(scores);

    }

    //getters, setters

    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        if(!this.dead && dead){
            if (currentScore > scores.getHighScore()) {
                scores.addNewHighScore(currentScore);
            }

        }
        this.dead = dead;
    }

    public Tile[][] getBoard() {
        return board;
    }

    //start és reset
    public void reset(){
        board = new Tile[ROWS][COLS];
        currentScore = 0;
        scores.setLastScore(currentScore);
        start();

        dead = false;
        hasStarted = false;
        saveCount = 0;
    }

    private void start() {
        for (int i = 0; i < startingTiles; i++) {
            System.out.println("spawning");
            spawnRandom();
        }
    }

    private void createBoardImage() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        }
    }

    //movements, positions
    private void resetPosition(Tile tile, int row, int col) {
        if (tile == null) return;

        int x = getTileX(col);
        int y = getTileY(row);

        int distX = tile.getX() - x;
        int distY = tile.getY() - y;

        if (Math.abs(distX) < Tile.SLIDE_SPEED) {
            tile.setX(tile.getX() - distX);
        }

        if (Math.abs(distY) < Tile.SLIDE_SPEED) {
            tile.setY(tile.getY() - distY);
        }

        if (distX < 0) {
            tile.setX(tile.getX() + Tile.SLIDE_SPEED);
        }
        if (distY < 0) {
            tile.setY(tile.getY() + Tile.SLIDE_SPEED);
        }
        if (distX > 0) {
            tile.setX(tile.getX() - Tile.SLIDE_SPEED);
        }
        if (distY > 0) {
            tile.setY(tile.getY() - Tile.SLIDE_SPEED);
        }
    }

    private boolean move(int row, int col, int horizontalDirection, int verticalDirection, int direction) {
        boolean canMove = false;
        Tile current = board[row][col];
        if (current == null) return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while (move) {
            newCol += horizontalDirection;
            newRow += verticalDirection;
            if (checkOutOfBounds(direction, newRow, newCol)) break;
            if (board[newRow][newCol] == null) {
                board[newRow][newCol] = current;
                canMove = true;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new other.Point(newRow, newCol));
            }
            else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) {
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));

                currentScore = currentScore + board[newRow][newCol].getValue();
                scores.setLastScore(currentScore);

            }
            else {
                move = false;
            }
        }
        return canMove;
    }

    public void moveTiles(int direction) {
        boolean canMove = false;
        int horizontalDirection = 0;
        int verticalDirection = 0;

        if (direction == LEFT) {
            horizontalDirection = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove)
                        canMove = move(row, col, horizontalDirection, verticalDirection, direction);
                    else move(row, col, horizontalDirection, verticalDirection, direction);
                }
            }
        }
        else if (direction == RIGHT) {
            horizontalDirection = 1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS - 1; col >= 0; col--) {
                    if (!canMove)
                        canMove = move(row, col, horizontalDirection, verticalDirection, direction);
                    else move(row, col, horizontalDirection, verticalDirection, direction);
                }
            }
        }
        else if (direction == UP) {
            verticalDirection = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove)
                        canMove = move(row, col, horizontalDirection, verticalDirection, direction);
                    else move(row, col, horizontalDirection, verticalDirection, direction);
                }
            }
        }
        else if (direction == DOWN) {
            verticalDirection = 1;
            for (int row = ROWS - 1; row >= 0; row--) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove)
                        canMove = move(row, col, horizontalDirection, verticalDirection, direction);
                    else move(row, col, horizontalDirection, verticalDirection, direction);
                }
            }
        }
        else {
            System.out.println(direction + " is not a valid direction.");
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.setCanCombine(true);
            }
        }

        if (canMove) {
            spawnRandom();
            setDead(checkDead());
            scores.saveGame();
        }
    }

    private void spawnRandom() {
        Random random = new Random();

        boolean notValid = true;


            while (notValid) {
                int location = random.nextInt(16);
                int row = location / ROWS;
                int col = location % COLS;
                Tile current = board[row][col];
                if (current == null) {
                    int value = random.nextInt(10) < 9 ? 2 : 4;
                    Tile tile = new Tile(value, getTileX(col), getTileY(row));
                    board[row][col] = tile;
                    notValid = false;
                }
            }
    }

    //checkers
    private boolean checkOutOfBounds(int direction, int row, int col) {
        if (direction == LEFT) {
            return col < 0;
        }
        else if (direction == RIGHT) {
            return col > COLS - 1;
        }
        else if (direction == UP) {
            return row < 0;
        }
        else if (direction == DOWN) {
            return row > ROWS - 1;
        }
        return false;
    }

    private boolean checkSurroundingTiles(int row, int col, Tile tile) {
        if (row > 0) {
            Tile check = board[row - 1][col];
            if (check == null) return true;
            if (tile.getValue() == check.getValue()) return true;
        }
        if (row < ROWS - 1) {
            Tile check = board[row + 1][col];
            if (check == null) return true;
            if (tile.getValue() == check.getValue()) return true;
        }
        if (col > 0) {
            Tile check = board[row][col - 1];
            if (check == null) return true;
            if (tile.getValue() == check.getValue()) return true;
        }
        if (col < COLS - 1) {
            Tile check = board[row][col + 1];
            if (check == null) return true;
            if (tile.getValue() == check.getValue()) return true;
        }
        return false;
    }

    private void checkKeys() {
        if (!Keys.pressed[KeyEvent.VK_LEFT] && Keys.prev[KeyEvent.VK_LEFT]) {
            moveTiles(LEFT);
            if (!hasStarted) hasStarted = !dead;
        }
        else if (!Keys.pressed[KeyEvent.VK_RIGHT] && Keys.prev[KeyEvent.VK_RIGHT]) {
            moveTiles(RIGHT);
            if (!hasStarted) hasStarted = !dead;
        }
        else if (!Keys.pressed[KeyEvent.VK_UP] && Keys.prev[KeyEvent.VK_UP]) {
            moveTiles(UP);
            if (!hasStarted) hasStarted = !dead;
        }
        else if (!Keys.pressed[KeyEvent.VK_DOWN] && Keys.prev[KeyEvent.VK_DOWN]) {
            moveTiles(DOWN);
            if (!hasStarted) hasStarted = !dead;
        }
    }

    private boolean checkDead() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == null) return false;
                boolean canCombine = checkSurroundingTiles(row, col, board[row][col]);
                if (canCombine) {
                    return false;
                }
            }
        }
        return true;
    }

    //render és update
    public void update() {
        checkKeys();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.update();
                resetPosition(current, row, col);
            }
        }
    }

    public void render(Graphics2D g) {
        BufferedImage finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g2d.drawImage(gameBoard, 0, 0, null);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.render(g2d);
            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }

    public ScoreManager getScores() {
        return scores;
    }
}