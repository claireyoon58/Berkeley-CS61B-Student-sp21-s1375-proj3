package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;
import java.util.Random;

public class Room {
    public static TETile[][] randWorld;
    public static Random RANDOMSEED;
    public final int WIDTH;
    public final int HEIGHT;
    int demonsoul;
    TERenderer ter = new TERenderer();
    public HashMap<Integer, Position> roomMap;
    public HashMap roomMap2;
    public HashMap hallWays;
    int numRooms;

    public Room(int w, int h, long s) {
        WIDTH = w;
        HEIGHT = h;
        RANDOMSEED = new Random(s);
    }


    public static class Position {
        public int x;
        public int y;
        int width;
        int height;
        public boolean visited = false;

        public Position(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.height = h;
            this.width = w;
        }

        public int getx() {
            return x;
        }

        public int gety() {
            return y;
        }

        public boolean equalPos(Position p) {
            return this.getx() == p.getx() && this.gety() == p.gety();
        }

    }

    public static class Position2 {
        int x;
        int y;

        Position2(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }



//
//
//
    // Create a random room: verticalical hallway, horizontal hallway, or a standard square room
//    private static void randomRoom(TETile[][] world, Position p, Random RANDOM) {
//        int roomType = RANDOM.nextInt(3);
//        room(world, p, RANDOM);
//    }

    // Create a standard square room with randomly generated height and width
//    private static void room(TETile[][] board, Position position, Random RANDOM) {
//        int numRoom = 9;
//        int currentRoom = 0;
//        int roomWidth;
//        int roomHeight;
//        // Could use Room instead once implemented
//        LinkedHashMap roomList = new LinkedHashMap();
////        Room r;
//        ArrayList<Position> oldpositions = new ArrayList<Position>(); //bottomleft
//
//
//        while (currentRoom < numRoom) {
////            Decide width and height
//            roomWidth = RandomUtils.uniform(RANDOM,3, 8);
//            roomHeight = RandomUtils.uniform(RANDOM, 3, 8);
////            decide where to place it
//            int placementx = RANDOM.nextInt(WIDTH);
//            int placementy = RANDOM.nextInt(HEIGHT);
//            Position p =  new Position(placementx, placementy); //BottomLeft
//

    public void drawRoomsMap() {

        roomMap = new HashMap<>();
        roomMap2 = new HashMap();
        numRooms = RandomUtils.uniform(RANDOMSEED, 10,13);
        for (int k = 0; k < numRooms; k++) {
            int randoma = RandomUtils.uniform(RANDOMSEED, 13, 60);
            int randomb = RandomUtils.uniform(RANDOMSEED, 9, 30);
            int randomc = RandomUtils.uniform(RANDOMSEED, 2, 6);
            int randomd = RandomUtils.uniform(RANDOMSEED, 4, 13);

            Room.Position nroom = new Room.Position(randoma, randomb, randomc, randomd);
            while (roomhelper(nroom)) {
                int rpart1 = RandomUtils.uniform(RANDOMSEED, 5, 60);
                int rpart2 = RandomUtils.uniform(RANDOMSEED, 9, 30);
                int rpart3 = RandomUtils.uniform(RANDOMSEED, 2, 6);
                int rpart4 = RandomUtils.uniform(RANDOMSEED, 4, 13);


                nroom = new Room.Position(rpart1, rpart2, rpart3, rpart4);
                Position2 p = new Position2(rpart1, rpart2);
                Position2 newPos = new Position2(rpart1 + rpart3 - 1, rpart2 + rpart4 - 1);
                roomMap2.put(p, newPos);

            }
            roomMap.put(k, nroom);
            drawRoom(nroom);
        }



        test1: for (Object key : roomMap2.keySet()) {
            Position2 botLeft = (Position2) key;
            Position2 topRight = (Position2) roomMap2.get(key);

            //Vertically Connect UPWARDS
            outer: for (int x = botLeft.x; x < topRight.x + 1; x++) {
                for (int y = topRight.y+1; y < HEIGHT; y++) {
                    if (randWorld[x][y] == Tileset.FLOOR) {
                        // Fill in gap between current object and next object (vertical)
                        System.out.println("Distance" + (y - topRight.y));
                        System.out.println("topRight.y " + (topRight.y));
                        System.out.println("y " + (y));

                        for (int yFill = 0; yFill < (y - topRight.y); yFill++) {
                            randWorld[x][topRight.y + yFill] = Tileset.FLOOR;
                        }
                        System.out.println("FOUND");
                        continue test1;
                    }
                }
            }

            //Horizontally Connect RIGHT
            yeet: for (int y = botLeft.y; y < topRight.y + 1; y++) {
                for (int x = topRight.x+1; x < WIDTH; x++) {
                    if (randWorld[x][y] == Tileset.FLOOR) {
                        for (int xFill = 0; xFill < (x - topRight.x); xFill++) {
                            randWorld[topRight.x + xFill][y] = Tileset.FLOOR;
                        }
                        System.out.println("FOUND");
                        continue yeet;
                    }
                }
            }





        }
    }





//
////            Checking room validity if case if room roomhelpers with new room made
//
//
//            if (!oldpositions.contains(p) &&
//                    ((placementx + roomWidth) < WIDTH - 1)
//                    && ((placementy + roomHeight) < HEIGHT-1)
//                    && ((placementy) > 1)
//                    && ((placementx) > 1)){
//
////updates rooms on board
//                currentRoom += 1;
////                adds new room position to oldpositions
//                for (int i = 0; i < roomWidth; i++) {
//                    for (int j = 0; j < roomHeight; j++) {
//                        Position newPos = new Position(i + p.x, j + p.y);
//                        oldpositions.add(newPos);
//                        if (i == roomWidth-1 && j == roomHeight-1) {
//                            roomList.put(p, newPos);
//                            // Add top right of room
//                        }
//                    }
//                }
////placing the room on board
//                for (int x = 0; x < roomWidth; x++) {
//                    for (int y = 0; y < roomHeight; y++) {
//                        board[x + p.x][y + p.y] = Tileset.FLOOR;
//                    }
//                }
//            }
//            System.out.println(currentRoom);
//            System.out.println(numRoom);
//        }
//
//        // Go through all room postions
//        for (Object key : roomList.keySet()) {
//
//            Position botLeft = (Position) key;
//            System.out.println(botLeft.x);
//            Position topRight = (Position) roomList.get(key);
//
//            //verticalically Connect UPWARDS
//            outer: for (int x = botLeft.x; x < topRight.x + 1; x++) {
//                for (int y = topRight.y+1; y < HEIGHT; y++) {
//                    if (board[x][y] == Tileset.FLOOR) {
//                        // Fill in gap between current object and next object (verticalical)
//                        System.out.println("Distance" + (y - topRight.y));
//                        System.out.println("topRight.y " + (topRight.y));
//                        System.out.println("y " + (y));
//
//                        for (int yFill = 0; yFill < (y - topRight.y); yFill++) {
//                            board[x][topRight.y + yFill] = Tileset.FLOOR;
//                        }
//                        System.out.println("FOUND");
//                        break outer;
//                    }
//                }
//            }
//
//            //Horizontally Connect RIGHT
//            yeet: for (int y = botLeft.y; y < topRight.y + 1; y++) {
//                for (int x = topRight.x+1; x < WIDTH; x++) {
//                    if (board[x][y] == Tileset.FLOOR) {
//                        for (int xFill = 0; xFill < (x - topRight.x); xFill++) {
//                            board[topRight.x + xFill][y] = Tileset.FLOOR;
//                        }
//                        System.out.println("FOUND");
//                        break yeet;
//                    }
//                }
//            }
//
//
//
//
//
//        }
//    }




    public void drawhallwaysMap() {
        for (int i = 0; i < (numRooms - 1); i++) {
            Position room1 = roomMap.get(i);
            Position room2 =  roomMap.get(i + 1);

            Hallway.connect(room1, room2);
        }

        Position connectroom = roomMap.get(0);
        Position connectroom2 = roomMap.get(numRooms - 1);
        Hallway.connect(connectroom2, connectroom);
    }







//
//    public static void drawWorld(TETile[][] tiles, Position p, Random RANDOM) {

//
//        randomRoom(tiles, p, RANDOM);
//    }
//
//    // Scan through the world and fill in walls
//    public static void fillWalls(TETile[][] tiles) {
//        int height = tiles[0].height;
//        int width = tiles.height;
//        for (int x = 1; x < width; x+= 1) {
//            for (int y = 1; y < height; y += 1) {
//                if (tiles[x][y] == Tileset.FLOOR) {
//                    if (tiles[x-1][y] == Tileset.NOTHING) {
//                        tiles[x-1][y] = Tileset.WALL;
//                    }
//                    if (tiles[x-1][y-1] == Tileset.NOTHING) {
//                        tiles[x-1][y-1] = Tileset.WALL;
//                    }
//                    if (tiles[x-1][y+1] == Tileset.NOTHING) {
//                        tiles[x-1][y+1] = Tileset.WALL;
//                    }
//                    if (tiles[x+1][y] == Tileset.NOTHING) {
//                        tiles[x+1][y] = Tileset.WALL;
//                    }
//                    if (tiles[x+1][y-1] == Tileset.NOTHING) {
//                        tiles[x+1][y-1] = Tileset.WALL;
//                    }
//                    if (tiles[x+1][y+1] == Tileset.NOTHING) {
//                        tiles[x+1][y+1] = Tileset.WALL;
//                    }
//                    if (tiles[x][y-1] == Tileset.NOTHING) {
//                        tiles[x][y-1] = Tileset.WALL;
//                    }
//                    if (tiles[x][y+1] == Tileset.NOTHING) {
//                        tiles[x][y+1] = Tileset.WALL;
//                    }
//                }
//            }
//        }
//    }
//
//    private int[] moving(char direction, Position curr) {
//        int[] directioner = new int[2];
//        if (direction == 'w') {
//            directioner[0] = curr.x;
//            directioner[1] = curr.y + 1;
//        } else if (direction == 'a') {
//            directioner[0] = curr.x - 1;
//            directioner[1] = curr.y;
//        } else if (direction == 's') {
//            directioner[0] = curr.x;
//            directioner[1] = curr.y - 1;
//        } else if (direction == 'd') {
//            directioner[0] = curr.x + 1;
//            directioner[1] = curr.y;
//        }
//        return directioner;
//    }



    //
    private void demon(TETile atype) {
        if (atype == Tileset.AVATAR) {
            randWorld[roomMap.get(3).x + roomMap.get(3).width / 2][roomMap.get(3).y - roomMap.get(3).height / 2] = Tileset.SOUL;
            randWorld[roomMap.get(6).x + roomMap.get(6).width / 2][roomMap.get(6).y - roomMap.get(6).height / 2] = Tileset.SOUL;
            randWorld[roomMap.get(4).x + roomMap.get(4).width / 2][roomMap.get(4).y - roomMap.get(4).height / 2] = Tileset.SOUL;
            randWorld[roomMap.get(5).x + roomMap.get(5).width / 2][roomMap.get(5).y - roomMap.get(5).height / 2] = Tileset.SOUL;
            randWorld[roomMap.get(7).x + roomMap.get(7).width / 2][roomMap.get(7).y - roomMap.get(7).height / 2] = Tileset.SOUL;
        } else {
            randWorld[roomMap.get(3).x + roomMap.get(3).width / 2][roomMap.get(3).y - roomMap.get(3).height / 2] = Tileset.SOUL;
            randWorld[roomMap.get(6).x + roomMap.get(6).width / 2][roomMap.get(6).y - roomMap.get(6).height / 2] = Tileset.SOUL;
            randWorld[roomMap.get(4).x + roomMap.get(4).width / 2][roomMap.get(4).y - roomMap.get(4).height / 2] = Tileset.SOUL;
        }
        if (atype == Tileset.AVATAR) {
            demonsoul = 0;
        } else {
            demonsoul = 2;
        }
    }
//





//    public static void main(String[] args) {
//        Scanner i = new Scanner(System.in);
//        String userinput = i.nextLine();
//        TETile[][] returnWorld = byow.Core.Engine.interactWithInputString(userinput);
//        TETile[][] returnWorld2 = byow.Core.Engine.interactWithInputString(userinput);
//        ter.initialize(WIDTH, HEIGHT);

    private void drawRoom(Room.Position topcorner) {
        for (int i = topcorner.x;
             i < topcorner.width; i++) {
            randWorld[i][topcorner.y] = Tileset.WALL;
            randWorld[i][topcorner.y - topcorner.height] = Tileset.WALL;
        }
        for (int j = topcorner.y;
             j > topcorner.y - topcorner.height + 2; j--) {
            randWorld[topcorner.x][j] = Tileset.WALL;
            randWorld[topcorner.x + topcorner.width][j] = Tileset.WALL;
        }
        for (int k = topcorner.x + 1;
             k < topcorner.x + topcorner.width; k++) {
            for (int l = topcorner.y - 1;
                 l > topcorner.y - topcorner.height + 2; l--) {
                randWorld[k][l] = Tileset.FLOOR;
            }
        }


    }



    public static String direction(Room.Position a, Room.Position b) {

        if (b.x > a.x && b.y > a.y) {
            return "NE";
        } else if (b.x < a.x && b.y > a.y) {
            return "NW";
        } else if (b.x > a.x && b.y < a.y) {
            return "SE";
        } else if (b.x < a.x && b.y < a.y) {
            return "SW";
        } else if (b.x == a.x && b.y > a.y) {
            return "N";
        } else if (b.x == a.x && b.y < a.y) {
            return "S";
        } else if (b.x < a.x) {
            return "W";
        } else {
            return "E";
        }
    }

    private int[] moving(char d, Room.Position avatar1) {
        int[] directioner = new int[2];
        if (d == 'w') {
            directioner[0] = avatar1.x;
            directioner[1] = avatar1.y + 1;
        } else if (d == 'a') {
            directioner[0] = avatar1.x - 1;
            directioner[1] = avatar1.y;
        } else if (d== 's') {
            directioner[0] = avatar1.x;
            directioner[1] = avatar1.y - 1;
        } else if (d== 'd') {
            directioner[0] = avatar1.x + 1;
            directioner[1] = avatar1.y;
        }
        return directioner;
    }



    public ingame move(char d, TETile[][] worldTiles, Room.Position avatarPosition, TETile atype) {

        ingame avatar1 = new ingame(worldTiles, avatarPosition);
        int[] directions = moving(d, avatarPosition);
        boolean movewall = worldTiles[directions[0]][directions[1]] == Tileset.NOTHING;
        boolean movewall2 = worldTiles[directions[0]][directions[1]] == Tileset.WALL;
        boolean soulcheck = worldTiles[directions[0]][directions[1]] == Tileset.SOUL;
        int movex = directions[0];
        int movey = directions[1];

        if (movewall || movewall2) {
//            ter.renderFrame(worldTiles);
            return avatar1;
        } else if (soulcheck) {
            demonsoul += 1;
        }

        int newposx = avatar1.avatarPosition.x;
        int newposy = avatar1.avatarPosition.y;
        worldTiles[newposx][newposy] = Tileset.FLOOR;

        avatar1.avatarPosition = new Room.Position(movex, movey , 0, 0);
        worldTiles[movex][movey] = atype;
//        ter.renderFrame(worldTiles);
        return avatar1;
    }

    public Room.Position drawWorldRooms(TETile avatar) {
//        ter.initialize(WIDTH, HEIGHT);
        randWorld = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                randWorld[i][j] = Tileset.NOTHING;
            }
        }

        drawRoomsMap();
        drawhallwaysMap();

        int wid = roomMap.get(0).x + roomMap.get(0).width / 2;
        int he = roomMap.get(0).y - roomMap.get(0).height / 2;

        randWorld[wid][he] = avatar;
        demon(avatar);
        fillWalls(randWorld);
//        ter.renderFrame(randWorld);

        int newx = roomMap.get(0).x + roomMap.get(0).width / 2;
        int newy = roomMap.get(0).y - roomMap.get(0).height / 2;



        return new Room.Position(newx, newy, 0, 0);
    }


    public boolean roomhelper(Room.Position topcorner) {
        for (int i = topcorner.x; i < topcorner.width; i++) {
            boolean checktopcorner = randWorld[i][topcorner.y] != Tileset.NOTHING;
            boolean checktopheight = randWorld[i][topcorner.y - topcorner.height] != Tileset.NOTHING;
            boolean checktop = randWorld[i][topcorner.y + 1] != Tileset.NOTHING;
            boolean checkdiff = randWorld[i][topcorner.y - topcorner.height - 1] != Tileset.NOTHING;
            if (checkdiff || checktop || checktopcorner || checktopheight) {
                return true;
            }
        }
        for (int j = topcorner.y; j < topcorner.height; j++) {
            boolean checktopcorner2 = randWorld[topcorner.y][j] != Tileset.NOTHING;
            boolean checktopheight2 = randWorld[topcorner.x + topcorner.width][j] != Tileset.NOTHING;
            boolean checktop2 = randWorld[topcorner.x - 1][j] != Tileset.NOTHING;
            boolean checkdiff2 = randWorld[topcorner.x + topcorner.width + 1][j] != Tileset.NOTHING;

            if (checkdiff2 || checktop2 || checktopcorner2 || checktopheight2) {
                return true;
            }
        }
        for (Room.Position nroom : roomMap.values()) {
            boolean checkroom1 = nroom.y < topcorner.y;
            boolean checkroom2 = nroom.x < (topcorner.x + topcorner.width);
            boolean checkroom3 = nroom.y > (topcorner.y - topcorner.height);
            boolean checkroom4 = nroom.x > topcorner.x;
            if (checkroom1 && checkroom2 && checkroom3  && checkroom4) {
                return true;
            }
        }
        return false;
    }

    public static void fillWalls(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 1; x < width; x += 1) {
            for (int y = 1; y < height; y += 1) {
                boolean fillwallhelper = tiles[x][y] == Tileset.FLOOR;
                boolean fillwallsoulhelper = tiles[x][y] == Tileset.SOUL;
                if (fillwallhelper || fillwallsoulhelper) {
                    if (tiles[x - 1][y] == Tileset.NOTHING) {
                        tiles[x - 1][y] = Tileset.WALL;
                    }
                    if (tiles[x + 1][y] == Tileset.NOTHING) {
                        tiles[x + 1][y] = Tileset.WALL;
                    }
                    if (tiles[x - 1][y - 1] == Tileset.NOTHING) {
                        tiles[x - 1][y - 1] = Tileset.WALL;
                    }
                    if (tiles[x + 1][y - 1] == Tileset.NOTHING) {
                        tiles[x + 1][y - 1] = Tileset.WALL;
                    }
                    if (tiles[x][y - 1] == Tileset.NOTHING) {
                        tiles[x][y - 1] = Tileset.WALL;
                    }
                    if (tiles[x - 1][y + 1] == Tileset.NOTHING) {
                        tiles[x - 1][y + 1] = Tileset.WALL;
                    }

                    if (tiles[x + 1][y + 1] == Tileset.NOTHING) {
                        tiles[x + 1][y + 1] = Tileset.WALL;
                    }

                    if (tiles[x][y + 1] == Tileset.NOTHING) {
                        tiles[x][y + 1] = Tileset.WALL;
                    }
                }
            }
        }
    }

    class ingame {
        TETile[][] worldTiles;
        Position avatarPosition;

        ingame(TETile[][] Tiles, Position avatarPos) {
            this.worldTiles = Tiles;
            this.avatarPosition = avatarPos;
        }
    }



//        ter.initialize(WIDTH, HEIGHT);
//        randWorld = new TETile[WIDTH][HEIGHT];
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                randWorld[i][j] = Tileset.NOTHING;
//            }

//        return new Room.Position(roomMap.get(0).x + roomMap.get(0).width / 2, roomMap.get(0).y - roomMap.get(0).y / 2, 0, 0);
//    }



}
