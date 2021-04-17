package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.HexWorld;

import java.util.*;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static int WIDTH;
    public static int HEIGHT;
    private static long SEED;
    private static Random RANDOM = new Random(SEED);
    private static HashMap roomList;
    public static TETile[][] world;
    public static TERenderer t = new TERenderer();


//    public String startscreen() {
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 10);
//        Font f = new Font("Monotype", Font.BOLD, 30);
//        StdDraw.setFont(f);
//        StdDraw.clear(Color.BLACK); //clear frame
//        StdDraw.setPenColor(Color.WHITE); //set font color
//
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4, "CS61B: The Game");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 8, "New Game (N)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 12, "Load Game (L)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 16, "Quit Game (Q)");
//
//        StdDraw.show();
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//                System.out.println("User pressed: " + curr);
//                if (curr == 'n') {
//                    return "new game";
//                } else if (curr == 'l') {
//                    return "load game";
//                } else if (curr == 'q') {
//                    return "quit game";
//                }
//            }
//        }
//    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public static void init_(int w, int h, long s) {
        WIDTH = w;
        HEIGHT = h;
        SEED = s;
        RANDOM = new Random(SEED);
    }
    public void interactWithKeyboard() {
//        String start = startscreen();
//        if (start.equals("quit game")) {
//            System.exit(0);
//        } else if (start.equals("load game")) {
//            randomRoom();
//        }
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

    public static TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        long user = Long.parseLong((input.substring(1, input.length() - 1)));
        WIDTH = RANDOM.nextInt(60) + 20;
        HEIGHT = RANDOM.nextInt(80) + 20;

        init_(WIDTH, HEIGHT, user);

        world = new TETile[WIDTH][HEIGHT];
        fillBoardWithNothing(world);
        Position anchor = new Position(10, 15);
        drawWorld(world, anchor);
        fillWalls(world);

//        TETile[][] finalWorldFrame = world;
        return world;
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
    private static void randomRoom(TETile[][] world, Position p) {
        int roomType = RANDOM.nextInt(3);
        room(world, p);
    }

    // Create a standard square room with randomly generated length and width
    private static void room(TETile[][] board, Position position) {
        int numRoom = 10;
        int currentRoom = 0;
        int roomWidth;
        int roomHeight;
        // Could use Room instead once implemented
        roomList = new HashMap();
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
                    ((placementx + roomWidth) < WIDTH - 1)
                    && ((placementy + roomHeight) < HEIGHT-1)
                    && ((placementy) > 1)
                    && ((placementx) > 1)){

//updates rooms on board
                currentRoom += 1;
//                adds new room position to oldpositions
                for (int i = 0; i < roomWidth; i++) {
                    for (int j = 0; j < roomHeight; j++) {
                        Position newPos = new Position(i + p.x, j + p.y);
                        oldpositions.add(newPos);
                        if (i == roomWidth-1 && j == roomHeight-1) {
                            roomList.put(p, newPos);
                            // Add top right of room
                        }
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

        // Go through all room postions
        for (Object key : roomList.keySet()) {

            Position botLeft = (Position) key;
            System.out.println(botLeft.x);
            Position topRight = (Position) roomList.get(key);

            //Vertically Connect UPWARDS
            outer: for (int x = botLeft.x; x < topRight.x + 1; x++) {
                for (int y = topRight.y+1; y < HEIGHT; y++) {
                    if (board[x][y] == Tileset.FLOOR) {
                        // Fill in gap between current object and next object (vertical)
                        System.out.println("Distance" + (y - topRight.y));
                        System.out.println("topRight.y " + (topRight.y));
                        System.out.println("y " + (y));

                        for (int yFill = 0; yFill < (y - topRight.y); yFill++) {
                            board[x][topRight.y + yFill] = Tileset.FLOOR;
                        }
                        System.out.println("FOUND");
                        break outer;
                    }
                }
            }

            //Horizontally Connect RIGHT
            yeet: for (int y = botLeft.y; y < topRight.y + 1; y++) {
                for (int x = topRight.x+1; x < WIDTH; x++) {
                    if (board[x][y] == Tileset.FLOOR) {
                        for (int xFill = 0; xFill < (x - topRight.x); xFill++) {
                            board[topRight.x + xFill][y] = Tileset.FLOOR;
                        }
                        System.out.println("FOUND");
                        break yeet;
                    }
                }
            }





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

        randomRoom(tiles, p);
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
        Scanner i = new Scanner(System.in);
        String userinput = i.nextLine();

        byow.Core.Engine.interactWithInputString(userinput);
        t.initialize(WIDTH, HEIGHT);
        t.renderFrame(world);
    }
}



//Focus on placing rooms on theboard, and then connect them with hallways