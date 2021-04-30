package byow.Core;

import byow.TileEngine.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Random;

public class Room {

    private final Random SEED;
    private final int WIDTH;
    private final int HEIGHT;
    int demonsoul;
    TETile[][] randWorld;
    TERenderer ter = new TERenderer();
    HashMap<Integer, Position> gameRoom;
    int roomNum;

    public Room(int w, int h, long s) {
        WIDTH = w;
        HEIGHT = h;
        SEED = new Random(s);
    }


//    class Position {
//        int x;
//        int y;
//        int width;
//        int height;
//
//        Position(int xx, int yy, int width, int height) {
//            x = xx;
//            y = yy;
//            this.width = width;
//            this.height = height;
//        }

//        public boolean equalPos(Position p) {
//            return this.getx() == p.getx() && this.gety() == p.gety();
//        }


    private String straightHallway(Position room1, Position room2) {
        boolean comparehor11 = (room2.y < room1.y);
        boolean comparehor12 =  (room2.y - 3 > room1.y - room1.height);
        boolean horhall1 = (comparehor11 && comparehor12);


        boolean comparehor21 = ((room1.y < room2.y));
        boolean comparehor22 =  (room1.y - 3 > room2.y - room2.height);
        boolean horhall2 = comparehor21 && comparehor22;


        boolean comparevert11 = (room2.x > room1.x);
        boolean comparevert12 = (room2.x < room1.x + room1.width - 3);
        boolean verthall1 = comparevert11 && comparevert12;


        boolean comparevert21 = (room1.x < room2.x + room2.width - 3);
        boolean comparevert22 = ((room1.x > room2.x));
        boolean verthall2 =  comparevert21 && comparevert22;


        if (horhall1
                || horhall2) {
            return "horizontal";
        } else if (verthall1
                ||  verthall2) {
            return "vertical";
        } else {
            return "NONE";
        }
    }

    public void connecthelperX(Position room1, Position room2) {
        if (room1.x > room2.x) {
            Position store = room2;
            room2 = room1;
            room1 = store;
        }
    }

    public void connecthelperY(Position room1, Position room2) {
        if (room1.y > room2.y) {
            Position store = room2;
            room2 = room1;
            room1 = store;
        }
    }



    public void connect(Position room1,
                        Position room2) {
        String loc = straightHallway(room1, room2);
        Position starthallway = null;
        if (loc.equals("horizontal")) {
            connecthelperX(room1, room2);
            int maxroom = Math.max(room2.y - room2.height, room1.y - room1.height);
            int minroom = Math.min(room2.y, room1.y);
            int randY = RandomUtils.uniform(SEED, maxroom, minroom);
            starthallway = new Position(room1.x + room1.width, randY, 0, 0);
            int diff = room2.x - (room1.x + room1.width);
            for (int i = 0; i < 6; i++) {
                if (!hallwayhelper2(loc, starthallway, diff)) {
                    int maxroom1 = Math.max(room2.y - room2.height, room1.y - room1.height);
                    int minroom2 = Math.min(room2.y, room1.y);
                    int randY2 = RandomUtils.uniform(SEED, maxroom1, minroom2);
                    starthallway = new Position(room1.x + room1.width, randY2, 0, 0);
                }
            }
            int dif = room2.x - (room1.x + room1.width);
            drawHall(loc, starthallway, dif);
            //        test1: for (Object key : gameRoom2.keySet()) {
//            Position2 botLeft = (Position2) key;
//            Position2 topRight = (Position2) gameRoom2.get(key);
//
//            //Vertically Connect UPWARDS
//            outer: for (int x = botLeft.x; x < topRight.x + 1; x++) {
//                for (int y = topRight.y + 1; y < HEIGHT; y++) {
//                    if (randWorld[x][y] == Tileset.FLOOR) {
//                        // Fill in gap between current object and next object (vertical)
//                        System.out.println("Distance" + (y - topRight.y));
//                        System.out.println("topRight.y " + (topRight.y));
//                        System.out.println("y " + (y));
//
//                        for (int yFill = 0; yFill < (y - topRight.y); yFill++) {
//                            randWorld[x][topRight.y + yFill] = Tileset.FLOOR;
//                        }
//
//                        continue test1;

//            //Horizontally Connect RIGHT
//            yeet: for (int y = botLeft.y; y < topRight.y + 1; y++) {
//                for (int x = topRight.x + 1; x < WIDTH; x++) {
//                    if (randWorld[x][y] == Tileset.FLOOR) {
//                        for (int xFill = 0; xFill < (x - topRight.x); xFill++) {
//                            randWorld[topRight.x + xFill][y] = Tileset.FLOOR;
//                        }
//                        continue yeet;
        } else if (loc.equals("vertical")) {
            connecthelperY(room1, room2);
            int comparemax = Math.max(room1.x, room2.x);
            int comparemin = Math.min(room1.x + room1.width, room2.x + room2.width);
            int randX = RandomUtils.uniform(SEED, comparemax + 1, comparemin);
            starthallway = new Position(randX, room1.y, 0, 0);
            int diff = room2.y - room2.height - room1.y;
            for (int i = 0; i < 6; i++) {
                if (!hallwayhelper2(loc, starthallway, diff)) {
                    int maxroom1 = Math.max(room1.x, room2.x);
                    int minroom1 = Math.min(room1.x + room1.width, room2.x + room2.width);
                    int differentRandX = RandomUtils.uniform(SEED,
                            maxroom1 + 1, minroom1);
                    starthallway = new Position(differentRandX, room1.y, 0, 0);
                }
            }
            int dif = room2.y - room2.height - room1.y;
            drawHall(loc, starthallway, dif);
        } else {
            int rannum = RandomUtils.uniform(SEED, 0, 2);
            if (hallwayhelper(room1, room2)) {
                return;
            }
            if (rannum == 0) {

                verticalhallways(room1, room2);
            } else if (rannum == 1) {

                horitonzalhallway(room1, room2);
            }
        }
    }

    private boolean hallwayhelper(Position room1, Position room2) {
        for (int i = room1.y; i > room1.y - room1.height - 1; i--) {
            boolean vertfloorcheck1 = randWorld[room1.x + room1.width][i] == Tileset.FLOOR;
            boolean vertfloorcheck2 = randWorld[room1.x][i] == Tileset.FLOOR;
            if (vertfloorcheck1
                    || vertfloorcheck2) {
                verticalhallways(room1, room2);
                return true;
            }
        }
        for (int i = room1.x; i < room1.x + room1.width; i++) {
            boolean horfloorcheck1 = randWorld[i][room1.y - room1.height] == Tileset.FLOOR;
            boolean horfloorcheck2 = randWorld[i][room1.y] == Tileset.FLOOR;
            if (horfloorcheck1
                    || horfloorcheck2) {
                horitonzalhallway(room1, room2);
                return true;
            }
        }
        return false;
    }

    private void drawHall(String loc, Position currstart, int dif) {

        if (loc.equals("horizontal")) {

            for (int i = 0; i <= dif; i++) {
                boolean checkfloor = randWorld[currstart.x + i][currstart.y] == Tileset.FLOOR;
                boolean checkwall = randWorld[currstart.x + i][currstart.y] == Tileset.WALL;
                boolean checknothing = randWorld[currstart.x + i][currstart.y] == Tileset.NOTHING;

                if (checknothing) {
                    randWorld[currstart.x + i][currstart.y] = Tileset.FLOOR;
                    randWorld[currstart.x + i][currstart.y + 1] = Tileset.WALL;
                    randWorld[currstart.x + i][currstart.y - 1] = Tileset.WALL;
                } else if (checkfloor) {
                    if (randWorld[currstart.x + i][currstart.y - 1] == Tileset.NOTHING) {
                        randWorld[currstart.x + i][currstart.y - 1] = Tileset.WALL;
                    }
                    if (randWorld[currstart.x + i][currstart.y + 1] == Tileset.NOTHING) {
                        randWorld[currstart.x + i][currstart.y + 1] = Tileset.WALL;
                    }
                } else if (checkwall) {
                    randWorld[currstart.x + i][currstart.y] = Tileset.FLOOR;
                    if (randWorld[currstart.x + i][currstart.y - 1] == Tileset.NOTHING) {
                        randWorld[currstart.x + i][currstart.y - 1] = Tileset.WALL;
                    }
                    if (randWorld[currstart.x + i][currstart.y + 1] == Tileset.NOTHING) {
                        randWorld[currstart.x + i][currstart.y + 1] = Tileset.WALL;
                    }
                }
            }
        } else {

            for (int i = 0; i <= dif; i++) {

                boolean checknothing2 = randWorld[currstart.x][currstart.y + i] == Tileset.NOTHING;
                boolean checkfloor2 = randWorld[currstart.x][currstart.y + i] == Tileset.FLOOR;
                boolean checkwall2 = randWorld[currstart.x][currstart.y + i] == Tileset.WALL;

                if (checknothing2) {
                    randWorld[currstart.x][currstart.y + i] = Tileset.FLOOR;
                    randWorld[currstart.x + 1][currstart.y + i] = Tileset.WALL;
                    randWorld[currstart.x - 1][currstart.y + i] = Tileset.WALL;
                } else if (checkfloor2) {
                    if (randWorld[currstart.x + 1][currstart.y + i] == Tileset.NOTHING) {
                        randWorld[currstart.x + 1][currstart.y + i] = Tileset.WALL;
                    }
                    if (randWorld[currstart.x - 1][currstart.y + i] == Tileset.NOTHING) {
                        randWorld[currstart.x - 1][currstart.y + i] = Tileset.WALL;
                    }
                } else if (checkwall2) {
                    randWorld[currstart.x][currstart.y + i] = Tileset.FLOOR;
                    if (randWorld[currstart.x - 1][currstart.y + i] == Tileset.NOTHING) {
                        randWorld[currstart.x - 1][currstart.y + i] = Tileset.WALL;
                    }
                    if (randWorld[currstart.x + 1][currstart.y + i] == Tileset.NOTHING) {
                        randWorld[currstart.x + 1][currstart.y + i] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void horizontalhelper(Position r1, Position r2) {
        boolean swCheck = direction(r1, r2).equals("SW");
        boolean seCheck = direction(r1, r2).equals("SE");
        if (swCheck
                || seCheck) {
            Position store = r1;
            r1 = r2;
            r2 = store;
        }

    }

    private void horitonzalhallway(Position room1, Position room2) {
        horizontalhelper(room1, room2);
//        boolean swCheck = direction(room1, room2).equals("SW");
//        boolean seCheck = direction(room1, room2).equals("SE");
//        if (swCheck
//                || seCheck) {
//            Position store = room1;
//            room1 = room2;
//            room2 = store;
//        }
        String roomdirect = direction(room1, room2);
        if (roomdirect.equals("NE")) {
            int areaWithinRoomx = room2.x - (room1.x + room1.width);
            int areaWithinRoomy = room1.y - room1.height + 1;
            int hallwayheight = RandomUtils.uniform(SEED,
                    areaWithinRoomx + 1, areaWithinRoomx + room2.width);
            int randY = RandomUtils.uniform(SEED,
                    areaWithinRoomy, room1.y);
            int newhorposxx = room1.x + room1.width;
            int newvertposxx = room1.x + room1.width
                    + hallwayheight;
            Position horWall = new Position(newhorposxx, randY, 0, 0);


            Position vertWall = new Position(newvertposxx, randY, 0, 0);


            drawHall("horizontal", horWall, hallwayheight);
            drawHall("vertical", vertWall, room2.y - room2.height - randY + 2);
            Position cornerPoint = new Position(vertWall.x, vertWall.y
                    + (room2.y - room2.height - randY) - 2, 0, 0);
//            roomcorner(cornerPoint, "bottopright");
        } else if (roomdirect.equals("NW")) {
            int areaWithinRoom = room1.x - (room2.x + room2.width);
            int hallwayheight = RandomUtils.uniform(SEED, areaWithinRoom + 1,
                    areaWithinRoom + room1.width);
            int ranheight = room2.y - room2.height + 1;
            int randY = RandomUtils.uniform(SEED, ranheight, room2.y);
            int horposx = room2.x + room2.width;
            int verposx = room2.x + room2.width + hallwayheight;
            Position horWall = new Position(horposx, randY, 0, 0);
            Position vertWall = new Position(verposx, room1.y, 0, 0);



            drawHall("horizontal", horWall, hallwayheight);
            drawHall("vertical", vertWall, randY - room1.y);
//            roomcorner(vertWall, "bottopright");
        }
    }

//    public void verticalhelper(Position room1, Position room2) {
//        boolean sedirect = direction(room1, room2).equals("SE");
//        boolean swdirect = direction(room1, room2).equals("SW");
//
//        if (swdirect
//                || sedirect) {
//            Position store = room1;
//            room1 = room2;
//            room2 = store;
//        }
//    }

    private void verticalhallways(Position room1, Position room2) {
//        verticalhelper(room1, room2);
        boolean sedirect = direction(room1, room2).equals("SE");
        boolean swdirect = direction(room1, room2).equals("SW");

        if (swdirect
                || sedirect) {
            Position store = room1;
            room1 = room2;
            room2 = store;
        }

        boolean nedirect = direction(room1, room2).equals("NE");
        boolean nwdirect = direction(room1, room2).equals("NW");
        if (nedirect) {
            int areaWithinRoom = room2.y - room2.height - room1.y;
            int hallwayheight = RandomUtils.uniform(SEED, areaWithinRoom + 1, room2.y - room1.y);
            int randX = RandomUtils.uniform(SEED, room1.x + 1, room1.x + room1.width);
            Position vertWall = new Position(randX, room1.y, 0, 0);
            Position horWall = new Position(randX, room1.y + hallwayheight, 0, 0);

            drawHall("vertical", vertWall, hallwayheight);
            drawHall("horizontal", horWall, room2.x - randX);
//            roomcorner(horWall, "topcorner");
        } else if (nwdirect) {
            int areaWithinRoom = room2.y - room2.height - room1.y;
            int hallwayheight = RandomUtils.uniform(SEED, areaWithinRoom + 1, room2.y - room1.y);
            int rana = room1.x + 1;
            int ranb = room1.x + room1.width;
            int randX = RandomUtils.uniform(SEED, rana, ranb);
            Position vertWall = new Position(randX, room1.y, 0, 0);

            int posxx = room2.x + room2.width;
            int posyy = room1.y + hallwayheight;
            Position horWall = new Position(posxx, posyy, 0, 0);

            drawHall("vertical", vertWall, hallwayheight);

            int diffnum = randX - room2.x - room2.width;
            drawHall("horizontal", horWall, diffnum);
//            roomcorner(horWall, "bottom left");
        }
    }

//    private void roomcorner(Position currstart, String pos) {
//        if (pos.equals("bottom left")) {
//            randWorld[currstart.x - 1][currstart.y - 1] = Tileset.WALL;
//            randWorld[currstart.x][currstart.y - 1] = Tileset.WALL;
//            randWorld[currstart.x + 1][currstart.y - 1] = Tileset.WALL;
//        } else if (pos.equals("topcorner")) {
//            randWorld[currstart.x - 1][currstart.y + 1] = Tileset.WALL;
//            randWorld[currstart.x][currstart.y + 1] = Tileset.WALL;
//            randWorld[currstart.x + 1][currstart.y + 1] = Tileset.WALL;
//        } else if (pos.equals("bottopright")) {
//            randWorld[currstart.x + 1][currstart.y + 1] = Tileset.WALL;
//            randWorld[currstart.x + 1][currstart.y] = Tileset.WALL;
//            randWorld[currstart.x + 1][currstart.y - 1] = Tileset.WALL;
//        }
//    }

    private boolean hallwayhelper2(String loc, Position currstart, int dif) {
        if (loc.equals("vertical")) {
            for (int i = 0; i < dif; i++) {
                if (randWorld[currstart.x][currstart.y + i] == Tileset.WALL
                        && randWorld[currstart.x][currstart.y + i + 1] == Tileset.WALL) {
                    return false;
                }
            }
            return true;
        } else if (loc.equals("horizontal")) {
            for (int i = 0; i < dif; i++) {
                if (randWorld[currstart.x + i][currstart.y] == Tileset.WALL
                        && randWorld[currstart.x + i + 1][currstart.y] == Tileset.WALL) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void drawMapHallways() {
        for (int i = 0; i < (roomNum - 1); i++) {
            Position r1 = gameRoom.get(i);
            Position r2 = gameRoom.get(i + 1);
            connect(r1, r2);
        }
        Position r11 = gameRoom.get(roomNum - 1);
        Position r22 =  gameRoom.get(0);
        connect(r11, r22);

    }



    public Position drawGameRooms(TETile avatar) {
        ter.initialize(WIDTH, HEIGHT);
        randWorld = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                randWorld[i][j] = Tileset.NOTHING;
            }
        }

        drawRoomsMap();
        drawMapHallways();
        drawWalls(randWorld);

        int wid = gameRoom.get(0).x + gameRoom.get(0).width / 2;
        int he = gameRoom.get(0).y - gameRoom.get(0).height / 2;

        randWorld[wid][he] = avatar;
        demon(avatar);
        ter.renderFrame(randWorld);
        ter.renderFrame(randWorld);
        int newx = gameRoom.get(0).x + gameRoom.get(0).width / 2;
        int newy = gameRoom.get(0).y - gameRoom.get(0).height / 2;



        return new Position(newx, newy, 0, 0);
    }

    private void drawRoomsMap() {


//
//            Position nroom = new Position(randoma, randomb, randomc, randomd);
//            while (roomchecker(nroom)) {
//
//
//                nroom = new Position(rpart1, rpart2, rpart3, rpart4);
////                Position p = new Position(rpart1, rpart2);
////                Position newPos = new Position(rpart1 + rpart3 - 1, rpart2 + rpart4 - 1);
////                gameRoom.put(p, newPos);
//            }
//
//            gameRoom.put(x, nroom);
//            drawRoom(nroom);
//        }
        gameRoom = new HashMap<>();

        roomNum = RandomUtils.uniform(SEED, 10, 15);
        for (int x = 0; x < roomNum; x++) {
            int randoma = RandomUtils.uniform(SEED, 10, 70);
            int randomb = RandomUtils.uniform(SEED, 12, 50);
            int randomc = RandomUtils.uniform(SEED, 6, 12);
            int randomd = RandomUtils.uniform(SEED, 6, 8);

            Position nroom = new Position(randoma, randomb, randomc, randomd);
            while (roomchecker(nroom)) {
                int rpart1 = RandomUtils.uniform(SEED, 5, 60);
                int rpart2 = RandomUtils.uniform(SEED, 9, 52);
                int rpart3 = RandomUtils.uniform(SEED, 4, 9);
                int rpart4 = RandomUtils.uniform(SEED, 4, 9);

                nroom = new Position(rpart1, rpart2, rpart3, rpart4);
            }

            gameRoom.put(x, nroom);
            drawRoom(nroom);
        }

    }



    private void drawRoom(Position topcorner) {
        for (int a = topcorner.x;
             a < topcorner.x + topcorner.width; a++) {
            randWorld[a][topcorner.y] = Tileset.WALL;
            randWorld[a][topcorner.y - topcorner.height] = Tileset.WALL;
        }
        for (int c = topcorner.x + 1;
             c < topcorner.x + topcorner.width; c++) {
            for (int cc = topcorner.y - 1;
                 cc > topcorner.y - topcorner.height; cc--) {
                randWorld[c][cc] = Tileset.FLOOR;
            }
        }
        for (int d = topcorner.y + 1;
             d > topcorner.y - topcorner.height - 1; d--) {
            for (int e = topcorner.x - 1;
                 e < topcorner.x - topcorner.height; e--) {
                randWorld[d][e] = Tileset.FLOOR;
            }
        }
        for (int b = topcorner.y;
             b > topcorner.y - topcorner.height - 1; b--) {
            randWorld[topcorner.x][b] = Tileset.WALL;
            randWorld[topcorner.x + topcorner.width][b] = Tileset.WALL;
        }


    }


    private boolean roomchecker(Position topcorner) {
        for (int i = topcorner.x; i < topcorner.x + topcorner.width; i++) {
            boolean checktopcorner = randWorld[i][topcorner.y] != Tileset.NOTHING;
            int heightt = topcorner.y - topcorner.height;
            boolean checktopheight = randWorld[i][heightt] != Tileset.NOTHING;
            int heighttt = topcorner.y + 1;
            boolean checktop = randWorld[i][heighttt] != Tileset.NOTHING;
            int cc = topcorner.y - topcorner.height - 1;
            boolean checkdiff = randWorld[i][cc] != Tileset.NOTHING;
            if (checkdiff || checktop || checktopcorner || checktopheight) {
                return true;
            }
        }
        for (int j = topcorner.y; j > topcorner.y - topcorner.height; j--) {
            boolean checktopcorner2 = randWorld[topcorner.x][j] != Tileset.NOTHING;
            int xx = topcorner.x + topcorner.width;
            boolean checktopheight2 = randWorld[xx][j] != Tileset.NOTHING;
            boolean checktop2 = randWorld[topcorner.x - 1][j] != Tileset.NOTHING;
            boolean checkdiff2 = randWorld[xx + 1][j] != Tileset.NOTHING;

            if (checkdiff2 || checktop2 || checktopcorner2 || checktopheight2) {
                return true;
            }
        }
        for (Position nroom : gameRoom.values()) {
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

    private String direction(Position room1, Position room2) {
        if (room2.x > room1.x && room2.y > room1.y) {
            return "NE";
        } else if (room2.x < room1.x && room2.y > room1.y) {
            return "NW";
        } else if (room2.x > room1.x && room2.y < room1.y) {
            return "SE";
        } else if (room2.x < room1.x && room2.y < room1.y) {
            return "SW";
        } else if (room2.x == room1.x && room2.y > room1.y) {
            return "N";
        } else if (room2.x == room1.x && room2.y < room1.y) {
            return "S";
        } else if (room2.x < room1.x) {
            return "W";
        } else {
            return "E";
        }
//        boolean ncheck = room2.x == room1.x && room2.y > room1.y;
//        boolean scheck = room2.x == room1.x && room2.y < room1.y;
//        boolean wcheck = room2.x < room1.x;
//        boolean necheck = room2.x > room1.x && room2.y > room1.y ;
//        boolean secheck = room2.x > room1.x && room2.y < room1.y;
//        boolean nwcheck = room2.x < room1.x && room2.y > room1.y;
//        boolean swcheck = room2.x < room1.x && room2.y < room1.y;
//
//        if (ncheck) {
//            return "N";
//        } else if (scheck) {
//            return "S";
//        } else if (wcheck) {
//            return "W";
//        } else if (necheck) {
//            return "NE";
//        } else if (secheck) {
//            return "SE";
//        } else if (nwcheck) {
//            return "NW";
//        } else if (swcheck) {
//            return "SW";
//        } else {
//            return "E";
//        }
    }

    public static void drawWalls(TETile[][] tiles) {
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




    public Ingame move(char direction,
                       TETile[][] worldTiles,
                       Position avatarxy,
                       TETile avatarType)
            throws LineUnavailableException,
            IOException, UnsupportedAudioFileException {

        Ingame gameavatar = new Ingame(worldTiles, avatarxy);
        int[] directions = directionMover(direction, avatarxy);

        boolean checkfreewall = worldTiles[directions[0]][directions[1]] == Tileset.WALL;
        boolean checkfreespace = worldTiles[directions[0]][directions[1]] == Tileset.NOTHING;
        boolean checksoul = worldTiles[directions[0]][directions[1]] == Tileset.SOUL;

        if (checkfreewall
                || checkfreespace) {
            ter.renderFrame(worldTiles);
            return gameavatar;
        } else if (checksoul) {
            if (avatarType == Tileset.AVATAR) {
                int soundver = RandomUtils.uniform(SEED, 0, 3);
                if (soundver == 0) {
                    playSound("../proj3/killing.wav");

                } else if (soundver == 1) {
                    playSound("../proj3/killing2.wav");

//                    horitonzalhallway(room1, room2);
                } else if (soundver == 2) {
                    playSound("../proj3/killing3.wav");
                }
            }
            if (avatarType == Tileset.NEZUKO) {
                int nezukover = RandomUtils.uniform(SEED, 0, 3);
                if (nezukover == 0) {
                    playSound("../proj3/nezukill2.wav");
                } else if (nezukover == 1) {
                    playSound("../proj3/nezukill.wav");
                }
            }
            demonsoul += 1;
        }

        worldTiles[gameavatar.avatarxy.x][gameavatar.avatarxy.y] = Tileset.FLOOR;
        gameavatar.avatarxy = new Position(directions[0], directions[1], 0, 0);
        worldTiles[directions[0]][directions[1]] = avatarType;
        ter.renderFrame(worldTiles);
        return gameavatar;
    }

    private void playSound(String fileName)
            throws LineUnavailableException,
            IOException, UnsupportedAudioFileException {
        File kill = new File(fileName);
        AudioInputStream playtheme = AudioSystem.getAudioInputStream(kill);

        AudioFormat play = playtheme.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, play);

        Clip audio = (Clip) AudioSystem.getLine(info);
        audio.open(playtheme);
        audio.start();
    }

    private int[] directionMover(char direction, Position gameavatar) {
        int[] directioner = new int[2];
        if (direction == 'w') {
            directioner[0] = gameavatar.x;
            directioner[1] = gameavatar.y + 1;
        } else if (direction == 'a') {
            directioner[0] = gameavatar.x - 1;
            directioner[1] = gameavatar.y;
        } else if (direction == 's') {
            directioner[0] = gameavatar.x;
            directioner[1] = gameavatar.y - 1;
        } else if (direction == 'd') {
            directioner[0] = gameavatar.x + 1;
            directioner[1] = gameavatar.y;
        }
        return directioner;
    }

    private void demon(TETile atype) {
        if (atype == Tileset.AVATAR) {
            randWorld[gameRoom.get(2).x + gameRoom.get(2).width / 2]
                    [gameRoom.get(2).y - gameRoom.get(2).height / 2] = Tileset.SOUL;
            randWorld[gameRoom.get(3).x + gameRoom.get(3).width / 2]
                    [gameRoom.get(3).y - gameRoom.get(3).height / 2] = Tileset.SOUL;
            randWorld[gameRoom.get(4).x + gameRoom.get(4).width / 2]
                    [gameRoom.get(4).y - gameRoom.get(4).height / 2] = Tileset.SOUL;
            randWorld[gameRoom.get(5).x + gameRoom.get(5).width / 2]
                    [gameRoom.get(5).y - gameRoom.get(5).height / 2] = Tileset.SOUL;
            randWorld[gameRoom.get(6).x + gameRoom.get(6).width / 2]
                    [gameRoom.get(6).y - gameRoom.get(6).height / 2] = Tileset.SOUL;
        } else {
            randWorld[gameRoom.get(5).x + gameRoom.get(5).width / 2]
                    [gameRoom.get(5).y - gameRoom.get(5).height / 2] = Tileset.SOUL;
            randWorld[gameRoom.get(6).x + gameRoom.get(6).width / 2]
                    [gameRoom.get(6).y - gameRoom.get(6).height / 2] = Tileset.SOUL;
            randWorld[gameRoom.get(8).x + gameRoom.get(8).width / 2]
                    [gameRoom.get(8).y - gameRoom.get(8).height / 2] = Tileset.SOUL;
        }
        if (atype == Tileset.AVATAR) {
            demonsoul = 0;
        } else {
            demonsoul = 2;
        }
    }

    class Ingame {
        TETile[][] worldTiles;
        Position avatarxy;

        Ingame(TETile[][] worldTiles, Position avatar) {
            this.worldTiles = worldTiles;
            this.avatarxy = avatar;
        }
    }

}

//    static TETile[][] randWorld;
//    static Random SEED;
//    final int WIDTH;
//    final int HEIGHT;
//    int demonsoul;
//    TERenderer ter = new TERenderer();
//    HashMap<Integer, Position> gameRoom;
//    HashMap gameRoom2;
//
//    int roomNum;
//
//    public Room(int w, int h, long s) {
//        WIDTH = w;
//        HEIGHT = h;
//        SEED = new Random(s);
//    }
//
//
//    public static class Position {
//        int x;
//        int y;
//        int width;
//        int height;
//        boolean visited = false;
//
//        public Position(int x, int y, int w, int h) {
//            this.x = x;
//            this.y = y;
//            this.height = h;
//            this.width = w;
//        }
//
//        public int getx() {
//            return x;
//        }
//
//        public int gety() {
//            return y;
//        }
//
//        public boolean equalPos(Position p) {
//            return this.getx() == p.getx() && this.gety() == p.gety();
//        }
//
//    }
//
//    public static class Position2 {
//        int x;
//        int y;
//
//        Position2(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//
//    public static void fillBoardWithNothing(TETile[][] tiles) {
//        int height = tiles[0].height;
//        int width = tiles.height;
//        for (int x = 0; x < width; x += 1) {
//            for (int y = 0; y < height; y += 1) {
//                tiles[x][y] = Tileset.NOTHING;
//            }
//        }
//    }
//
//
//
////
////
////
//    // Create a random room: verticalical hallway, horizontal hallway, or a standard square room
////    private static void randomRoom(TETile[][] world, Position p, Random RANDOM) {
////        int roomType = RANDOM.nextInt(3);
////        room(world, p, RANDOM);
////    }
//
//    // Create a standard square room with randomly generated height and width
////    private static void room(TETile[][] board, Position position, Random RANDOM) {
////        int numRoom = 9;
////        int currentRoom = 0;
////        int roomWidth;
////        int roomHeight;
////        // Could use Room instead once implemented
////        LinkedHashMap roomList = new LinkedHashMap();
//////        Room r;
////        ArrayList<Position> oldpositions = new ArrayList<Position>(); //bottomleft
////
////
////        while (currentRoom < numRoom) {
//////            Decide width and height
////            roomWidth = RandomUtils.uniform(RANDOM,3, 8);
////            roomHeight = RandomUtils.uniform(RANDOM, 3, 8);
//////            decide where to place it
////            int placementx = RANDOM.nextInt(WIDTH);
////            int placementy = RANDOM.nextInt(HEIGHT);
////            Position p =  new Position(placementx, placementy); //BottomLeft
////
//
//    public void drawRoomsMap()() {
//
//        gameRoom = new HashMap<>();
//        gameRoom2 = new HashMap();
//        roomNum = RandomUtils.uniform(SEED, 10, 13);
//        for (int k = 0; k < roomNum; k++) {
//            int randoma = RandomUtils.uniform(SEED, 13, 60);
//            int randomb = RandomUtils.uniform(SEED, 9, 30);
//            int randomc = RandomUtils.uniform(SEED, 2, 6);
//            int randomd = RandomUtils.uniform(SEED, 4, 13);
//
//            Position nroom = new Position(randoma, randomb, randomc, randomd);
//            while (roomhelper(nroom)) {
//                int rpart1 = RandomUtils.uniform(SEED, 5, 60);
//                int rpart2 = RandomUtils.uniform(SEED, 9, 30);
//                int rpart3 = RandomUtils.uniform(SEED, 2, 6);
//                int rpart4 = RandomUtils.uniform(SEED, 4, 13);
//
//
//                nroom = new Position(rpart1, rpart2, rpart3, rpart4);
//                Position2 p = new Position2(rpart1, rpart2);
//                Position2 newPos = new Position2(rpart1 + rpart3 - 1, rpart2 + rpart4 - 1);
//                gameRoom2.put(p, newPos);
//
//            }
//            gameRoom.put(k, nroom);
//            drawRoom(nroom);
//        }
//
//
//

//
//
//
//
//        }
//    }
//
//
//
//
//
////
//////            Checking room validity if case if room roomhelpers with new room made
////
////
////            if (!oldpositions.contains(p) &&
////                    ((placementx + roomWidth) < WIDTH - 1)
////                    && ((placementy + roomHeight) < HEIGHT-1)
////                    && ((placementy) > 1)
////                    && ((placementx) > 1)){
////
//////updates rooms on board
////                currentRoom += 1;
//////                adds new room position to oldpositions
////                for (int i = 0; i < roomWidth; i++) {
////                    for (int j = 0; j < roomHeight; j++) {
////                        Position newPos = new Position(i + p.x, j + p.y);
////                        oldpositions.add(newPos);
////                        if (i == roomWidth-1 && j == roomHeight-1) {
////                            roomList.put(p, newPos);
////                            // Add top right of room
////                        }
////                    }
////                }
//////placing the room on board
////                for (int x = 0; x < roomWidth; x++) {
////                    for (int y = 0; y < roomHeight; y++) {
////                        board[x + p.x][y + p.y] = Tileset.FLOOR;
////                    }
////                }
////            }
////            System.out.println(currentRoom);
////            System.out.println(numRoom);
////        }
////
////        // Go through all room postions
////        for (Object key : roomList.keySet()) {
////
////            Position botLeft = (Position) key;
////            System.out.println(botLeft.x);
////            Position topRight = (Position) roomList.get(key);
////
////            //verticalically Connect UPWARDS
////            outer: for (int x = botLeft.x; x < topRight.x + 1; x++) {
////                for (int y = topRight.y+1; y < HEIGHT; y++) {
////                    if (board[x][y] == Tileset.FLOOR) {
////                        // Fill in gap between current object and next object (verticalical)
////                        System.out.println("Distance" + (y - topRight.y));
////                        System.out.println("topRight.y " + (topRight.y));
////                        System.out.println("y " + (y));
////
////                        for (int yFill = 0; yFill < (y - topRight.y); yFill++) {
////                            board[x][topRight.y + yFill] = Tileset.FLOOR;
////                        }
////                        System.out.println("FOUND");
////                        break outer;
////                    }
////                }
////            }
////
////            //Horizontally Connect RIGHT
////            yeet: for (int y = botLeft.y; y < topRight.y + 1; y++) {
////                for (int x = topRight.x+1; x < WIDTH; x++) {
////                    if (board[x][y] == Tileset.FLOOR) {
////                        for (int xFill = 0; xFill < (x - topRight.x); xFill++) {
////                            board[topRight.x + xFill][y] = Tileset.FLOOR;
////                        }
////                        System.out.println("FOUND");
////                        break yeet;
////                    }
////                }
////            }
////
////
////
////
////
////        }
////    }
//
//
//
//
//    public void drawhallwaysMap() {
//        for (int i = 0; i < (roomNum - 1); i++) {
//            Position room1 = gameRoom.get(i);
//            Position room2 =  gameRoom.get(i + 1);
//
//            Hallway.connect(room1, room2);
//        }
//
//        Position connectroom = gameRoom.get(0);
//        Position connectroom2 = gameRoom.get(roomNum - 1);
//        Hallway.connect(connectroom2, connectroom);
//    }
//
//
//
//
//
//
//
////
////    public static void drawWorld(TETile[][] tiles, Position p, Random RANDOM) {
//
////
////        randomRoom(tiles, p, RANDOM);
////    }
////
////    // Scan through the world and fill in walls

////
////    private int[] moving(char direction, Position curr) {
////        int[] directioner = new int[2];
////        if (direction == 'w') {
////            directioner[0] = curr.x;
////            directioner[1] = curr.y + 1;
////        } else if (direction == 'a') {
////            directioner[0] = curr.x - 1;
////            directioner[1] = curr.y;
////        } else if (direction == 's') {
////            directioner[0] = curr.x;
////            directioner[1] = curr.y - 1;
////        } else if (direction == 'd') {
////            directioner[0] = curr.x + 1;
////            directioner[1] = curr.y;
////        }
////        return directioner;
////    }
//
//
//
//    //
//    private void demon(TETile atype) {
//        if (atype == Tileset.AVATAR) {
//            randWorld[gameRoom.get(3).x + gameRoom.get(3).width / 2]
//                    [gameRoom.get(3).y - gameRoom.get(3).height / 2] = Tileset.SOUL;
//            randWorld[gameRoom.get(6).x + gameRoom.get(6).width / 2]
//                    [gameRoom.get(6).y - gameRoom.get(6).height / 2] = Tileset.SOUL;
//            randWorld[gameRoom.get(4).x + gameRoom.get(4).width / 2]
//                    [gameRoom.get(4).y - gameRoom.get(4).height / 2] = Tileset.SOUL;
//            randWorld[gameRoom.get(5).x + gameRoom.get(5).width / 2]
//                    [gameRoom.get(5).y - gameRoom.get(5).height / 2] = Tileset.SOUL;
//            randWorld[gameRoom.get(7).x + gameRoom.get(7).width / 2]
//                    [gameRoom.get(7).y - gameRoom.get(7).height / 2] = Tileset.SOUL;
//        } else {
//            randWorld[gameRoom.get(3).x + gameRoom.get(3).width / 2]
//                    [gameRoom.get(3).y - gameRoom.get(3).height / 2] = Tileset.SOUL;
//            randWorld[gameRoom.get(6).x + gameRoom.get(6).width / 2]
//                    [gameRoom.get(6).y - gameRoom.get(6).height / 2] = Tileset.SOUL;
//            randWorld[gameRoom.get(4).x + gameRoom.get(4).width / 2]
//                    [gameRoom.get(4).y - gameRoom.get(4).height / 2] = Tileset.SOUL;
//        }
//        if (atype == Tileset.AVATAR) {
//            demonsoul = 0;
//        } else {
//            demonsoul = 2;
//        }
//    }
////
//
//
//
//
//
////    public static void main(String[] args) {
////        Scanner i = new Scanner(System.in);
////        String userinput = i.nextLine();
////        TETile[][] returnWorld = byow.Core.Engine.interactWithInputString(userinput);
////        TETile[][] returnWorld2 = byow.Core.Engine.interactWithInputString(userinput);
////        ter.initialize(WIDTH, HEIGHT);
//
//    private void drawRoom(Position topcorner) {
//        for (int i = topcorner.x;
//             i < topcorner.width; i++) {
//            randWorld[i][topcorner.y] = Tileset.WALL;
//            randWorld[i][topcorner.y - topcorner.height] = Tileset.WALL;
//        }
//        for (int j = topcorner.y;
//             j > topcorner.y - topcorner.height + 2; j--) {
//            randWorld[topcorner.x][j] = Tileset.WALL;
//            randWorld[topcorner.x + topcorner.width][j] = Tileset.WALL;
//        }
//        for (int k = topcorner.x + 1;
//             k < topcorner.x + topcorner.width; k++) {
//            for (int l = topcorner.y - 1;
//                 l > topcorner.y - topcorner.height + 2; l--) {
//                randWorld[k][l] = Tileset.FLOOR;
//            }
//        }
//
//
//    }
//
//
//
//    public static String direction(Position a, Position b) {
//
//        if (room2.x > room1.x && room2.y > room1.y) {
//            return "NE";
//        } else if (room2.x < room1.x && room2.y > room1.y) {
//            return "NW";
//        } else if (room2.x > room1.x && room2.y < room1.y) {
//            return "SE";
//        } else if (room2.x < room1.x && room2.y < room1.y) {
//            return "SW";
//        } else if (room2.x == room1.x && room2.y > room1.y) {
//            return "N";
//        } else if (room2.x == room1.x && room2.y < room1.y) {
//            return "S";
//        } else if (room2.x < room1.x) {
//            return "W";
//        } else {
//            return "E";
//        }
//    }
//
//    private int[] moving(char d, Position avatar1) {
//        int[] directioner = new int[2];
//        if (d == 'w') {
//            directioner[0] = avatar1.x;
//            directioner[1] = avatar1.y + 1;
//        } else if (d == 'a') {
//            directioner[0] = avatar1.x - 1;
//            directioner[1] = avatar1.y;
//        } else if (d == 's') {
//            directioner[0] = avatar1.x;
//            directioner[1] = avatar1.y - 1;
//        } else if (d == 'd') {
//            directioner[0] = avatar1.x + 1;
//            directioner[1] = avatar1.y;
//        }
//        return directioner;
//    }

//        Ingame avatar1 = new Ingame(worldTiles, avatarPosition);
//        int[] directions = moving(d, avatarPosition);
//        boolean movewall = worldTiles[directions[0]][directions[1]] == Tileset.NOTHING;
//        boolean movewall2 = worldTiles[directions[0]][di


