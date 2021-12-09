package other;

import gameCore.Tile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaveableTest {

    private Saveable data;
    private Tile[][] tiles;
    private SaveTile[][] saveTiles;

    @Before
    public void setUp() throws Exception {
        data = new Saveable();
        data.setLastScore(1);
        tiles = new Tile[4][4];
        saveTiles = new SaveTile[4][4];
    }

    @Test
    public void getHighScore() {
        data.addNewHighScore(1000);
        assertNotEquals(999, data.getHighScore());
        assertEquals(1000, data.getHighScore());
    }

    @Test
    public void getLastBoard() {
        assertNotNull(data.getLastBoard());
    }

    @Test
    public void setLastBoard() {
        data.setLastBoard(tiles);
        assertNotEquals(saveTiles, data.getLastBoard());
    }

    @Test
    public void addNewHighScore() {
        data.addNewHighScore(1000);
        data.addNewHighScore(1200);
        data.addNewHighScore(1300);
        assertEquals(1300,data.getHighScore());
    }

    @Test
    public void setLastScore() {
        data.setLastScore(10);
        assertEquals(10, data.getLastScore());
    }

    @Test
    public void getLastScore() {
        assertEquals(1, data.getLastScore());
    }

    @Test
    public void getList() {
        assertNotNull(data.getList());
    }
}