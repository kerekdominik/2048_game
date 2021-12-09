package gameCore;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import other.SaveTile;

import static org.junit.Assert.*;

public class TileTest {

    private Tile testTile;
    private SaveTile saveTile;

    @Before
    public void setUp() throws Exception {
        testTile = new Tile(64,10,10);
    }

    @Test
    public void TestConvertTileToSaveTile() {
        saveTile = Tile.convertTileToSaveTile(testTile);
        assertEquals(saveTile.getValue(), testTile.getValue());
        assertEquals(saveTile.getX(), testTile.getX());
        assertEquals(saveTile.getY(), testTile.getY());
    }

    @Test
    public void getValue() {
        assertEquals(64, testTile.getValue());
    }

    @Test
    public void testSetValue() {
        testTile.setValue(2);
        assertNotEquals(64, 2);
    }

}