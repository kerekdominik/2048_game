package other;

import gameCore.Tile;

import java.io.Serializable;

public class SaveTile implements Serializable {

    private int value;
    private int x;
    private int y;

    public SaveTile(Tile tile) {
        value = tile.getValue();
        x = tile.getX();
        y = tile.getY();
    }

    public Tile convertSaveTileToTile() {
        Tile asd = new Tile(value, x, y);

        return asd;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
