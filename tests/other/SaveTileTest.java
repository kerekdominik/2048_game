package other;

import gameCore.Tile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaveTileTest {

    private SaveTile saveTile;
    private Tile tile;

    @Before
    public void setUp() throws Exception {
        tile = new Tile(16,10,10);
        saveTile = new SaveTile(tile);
    }

    @Test
    public void convertSaveTileToTile() {
        Tile expected = saveTile.convertSaveTileToTile();
        assertEquals(expected.getValue(),tile.getValue());
        assertEquals(expected.getX(),tile.getX());
        assertEquals(expected.getY(),tile.getY());
    }
}