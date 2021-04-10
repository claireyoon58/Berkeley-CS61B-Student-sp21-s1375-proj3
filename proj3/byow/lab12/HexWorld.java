package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int w = 60;
    private static final int h = 60;

    private static final Random r = new Random(4);
    public static void main(String[] args) {

        TETile[][] Hworld = new TETile[w][h];
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                Hworld[x][y] = Tileset.NOTHING;
            }
        }

        TERenderer ter = new TERenderer();
        ter.initialize(w, h);
        TETile t = Tileset.FLOWER;


        drawRandomBigHexagon(world, p, 3, 4);

        // draws the world to the screen
        ter.renderFrame(world);
    }
    public addHexagon(leng )



}
