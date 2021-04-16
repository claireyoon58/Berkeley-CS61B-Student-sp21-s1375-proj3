package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.HexWorld;

import java.util.Random;
import java.util.ArrayList;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private static final long SEED = 123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
//     * @param ithe input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Position shift(int dx, int dy) {
            return new Position(this.x + dx, this.y + dy);
        }
    }

    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    // Fill in the board with nothing tiles
    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x+= 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    // Keep track of current position





    // Create a random room: vertical hallway, horizontal hallway, or a standard square room
    private static TETile randomRoom(TETile[][] world, Position p) {
        int roomType = RANDOM.nextInt(3);
        switch (roomType) {
            case 0: horizontalHallway(world, p);
            case 1: verticalHallway(world, p);
            case 2: room(world, p);
            default: return Tileset.NOTHING;
        }
    }

    // Create a standard square room with randomly generated length and width
    private static void room(TETile[][] board, Position position) {
        int numRoom = 10;
        int currentRoom = 0;
        int roomWidth;
        int roomHeight;



        Room r;
        ArrayList<Position> oldpositions = new ArrayList<Position>(); //bottomleft


        while (currentRoom < numRoom) {
//            Decide width and height
            roomWidth = RandomUtils.uniform(RANDOM,3, 8);
            roomHeight = RandomUtils.uniform(RANDOM, 3, 8);
//            decide where to place it
            int placementx = RANDOM.nextInt(WIDTH);
            int placementy = RANDOM.nextInt(HEIGHT);
            Position p =  new Position(placementx, placementy); //BottomLeft

//            Checking room validity if case if room overlaps with new room made


            if (!oldpositions.contains(p) &&
                    ((placementx + roomWidth) < WIDTH)
                    && ((placementy + roomHeight) < HEIGHT) ) {


//updates rooms on board
                currentRoom += 1;
//                adds new room position to oldpositions
                for (int i = 0; i < roomWidth; i++) {
                    for (int j = 0; j < roomHeight; j++) {
                        Position newPos = new Position(i + p.x, j + p.y);
                        oldpositions.add(newPos);
                    }
                }
//placing the room on board
                for (int x = 0; x < roomWidth; x++) {
                    for (int y = 0; y < roomHeight; y++) {
                        board[x + p.x][y + p.y] = Tileset.FLOOR;
                    }
                }
            }
            System.out.println(currentRoom);
            System.out.println(numRoom);
        }
    }

    // Create a horizontal hallway with randomly generated length and width
    private static void horizontalHallway(TETile[][] board, Position position) {
        int roomWidth = RANDOM.nextInt(20) + 2;
        int roomHeight = RANDOM.nextInt(2) + 1;
        for (int x = 0; x < roomWidth; x++) {
            for (int y = 0; y < roomHeight; y++) {
                board[x + position.x][y + position.y] = Tileset.FLOOR;
            }
        }
    }

    // Create a vertical hallway with randomly generated length and width
    private static void verticalHallway(TETile[][] board, Position position) {
        int roomWidth = RANDOM.nextInt(2) + 1;
        int roomHeight = RANDOM.nextInt(20) + 2;
        for (int x = 0; x < roomWidth; x++) {
            for (int y = 0; y < roomHeight; y++) {
                board[x + position.x][y + position.y] = Tileset.FLOOR;
            }
        }
    }

    //    have randomly located rooms that are of random size

//    public static void makeRoom(TETile[][] board) {
//        int numRoom;
//        Room r;
//        int w = RandomUtils.(seed);
//        int h;
//        Position p;
//        Random r = new Random(SEED);
//        for (int i = 0; i < numRoom; i ++) {
//
//        }
//
//        Room room = new Room(width, height, x, y);
////        if case if room overlaps with new room made
//        ArrayList<> positions = new ArrayList<Position>();
//        for (int i = 0; i < w; i ++) {
//            for (int j = 0; j < h; j++) {
//                Position newPos = new Position(i + p.x, j + p.y);
//                positions.add(newPos);
//            }
//        }
//    }

    // Fill in the world with shapes
    public static void drawWorld(TETile[][] tiles, Position p) {
        /**
         *  TODO: Find position to add randomized shape
         *      Maybe we can choose a side of the shape and choose random position
         *      within the side?
         *  TODO: Figure out a way to connect random shapes together
         *  TODO: Update position correctly
         */

        for (int x = 0; x < 5; x+=2) {
            randomRoom(tiles, p);
            p.y = x + p.y;
        }
    }

    // Scan through the world and fill in walls
    public static void fillWalls(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 1; x < width; x+= 1) {
            for (int y = 1; y < height; y += 1) {
                if (tiles[x][y] == Tileset.FLOOR) {
                    if (tiles[x-1][y] == Tileset.NOTHING) {
                        tiles[x-1][y] = Tileset.WALL;
                    }
                    if (tiles[x-1][y-1] == Tileset.NOTHING) {
                        tiles[x-1][y-1] = Tileset.WALL;
                    }
                    if (tiles[x-1][y+1] == Tileset.NOTHING) {
                        tiles[x-1][y+1] = Tileset.WALL;
                    }
                    if (tiles[x+1][y] == Tileset.NOTHING) {
                        tiles[x+1][y] = Tileset.WALL;
                    }
                    if (tiles[x+1][y-1] == Tileset.NOTHING) {
                        tiles[x+1][y-1] = Tileset.WALL;
                    }
                    if (tiles[x+1][y+1] == Tileset.NOTHING) {
                        tiles[x+1][y+1] = Tileset.WALL;
                    }
                    if (tiles[x][y-1] == Tileset.NOTHING) {
                        tiles[x][y-1] = Tileset.WALL;
                    }
                    if (tiles[x][y+1] == Tileset.NOTHING) {
                        tiles[x][y+1] = Tileset.WALL;
                    }
                }
            }
        }
    }




    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillBoardWithNothing(world);
        Position anchor = new Position(10, 15);
        drawWorld(world, anchor);
        fillWalls(world);
        ter.renderFrame(world);
    }
}



//Focus on placing rooms on theboard, and then connect them with hallways