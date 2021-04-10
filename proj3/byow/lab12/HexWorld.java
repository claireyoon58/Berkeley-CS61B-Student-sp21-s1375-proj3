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
    private static final int w = 50;
    private static final int h = 50;
    private static final long SEED = 2873123;

    private static final Random r = new Random(SEED);
//Draw a row of tiles to the board, anchored at a given position
    public static void drawRow(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int dx = 0; dx < length; dx++) {
            tiles[p.x + dx][p.y] = tile;
        }
    }


    public static void fillBoard(TETile[][] tiles) {
        int h = tiles[0].length;
        int w= tiles.length;
        for (int x = 0; x < w; x+= 1) {
            for (int y = 0; y < h; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static TETile randomTile() {
        int tile = r.nextInt(3);
        switch (tile) {
            case 0:
                return Tileset.GRASS;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.SAND;
            case 3:
                return Tileset.MOUNTAIN;
            case 4:
                return Tileset.TREE;

            default:
                return Tileset.NOTHING;
        }
    }

    private static class Position {
        int x;
        int y;
        Position (int x, int y) {
            this.x = x;
            this.y = y;

        }

        public Position shift(int dx, int dy) {
            return new Position(this.x + dx, this.y + dy);
        }
    }

    public static void addHexhelper(TETile[][] tiles, Position p, TETile tile, int n, int b, int t) {
        Position startr = p.shift(b, 0);
        drawRow(tiles, startr, tile, t);
        if (b > 0) {
            Position next = p.shift(0, -1);
            addHexhelper(tiles, next, tile, n, b - 1, t + 2);

        Position startofreflection = startr, shift(0, -(2 * b - 1);
        drawRow(tiles, startofreflection, tile, t);
    }
    }


    public static void addHexagon(TETile[][] tiles, Position p,TETile t, int size) {
        if (size < 2) return;

        addHexhelper(tiles, p, t, size - 1, size - 1,  size);
    }

    public static void addHexColumn(TETile[][] tiles, Position p, int size, int num) {
        if (num < 1) return;

        addHexagon(tiles, p, randomTile(), size);

        if (num > 1){
            Position bottomNeighbor = getBottomNeighbor(p, size);
            addHexColumn(tiles, bottomNeighbor, size, num - 1);
        }
    }

    public static Position getBottomNeighbor(Position p, int n) {
        return p.shift(0, -2*n);
    }

    public static Position getBottomRightNeighbor(Position p, int n) {
        return p.shift(2 * n - 1, -n);
    }

    public static Position getTopRightNeighbor(Position p, int n) {
        return p.shift(2*n-1, n);
    }


    public static void drawWorld(TETile[][] tiles, Position p, int h, int t) {
        addHexColumn(tiles, p, h, t);

        for (int i = 1; i < t; i++) {
            p = getTopRightNeighbor(p, h);
            addHexColumn(tiles, p, h, t + i);
        }
//
//        p = getBottomRightNeighbor(p, h);
//        addHexColumn(tiles, p, h, t + 1);
//        addHexColumn(tiles, p, h, t + 0);

        for (int i = t - 2; i >= 0; i--) {
            p = getBottomRightNeighbor(p, h);
            addHexColumn(tiles, p, h, t + i);
        }
    }
//        fillBoard(tiles);
////        Position p = new Position(20, 20);
////        drawRow(tiles, p, Tileset.WATER, 10);
//        Position p = new Position(20, 20);
//        addHexhelper(tiles, p, Tileset.FLOWER, 3, 2, 3);
////        drawRow(tiles, p, Tileset.FLOWER, 3, 3, 3);


    public static void main(String[] args) {
    TERenderer ter = new TERenderer();
    ter.initialize(w, h);

    TETile[][] world = new TETile[w][h];
    fillBoard(world);
    Position anchor = new Position(12, 34);
    drawWorld(world, anchor, 4, 3);
    ter.renderFrame(world);

//        drawRandomBigHexagon(world, p, 3, 4);

                // draws the world to the screen

    }





}
