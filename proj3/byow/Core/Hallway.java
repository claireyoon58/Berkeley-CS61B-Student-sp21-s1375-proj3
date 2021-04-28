package byow.Core;




import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

public class Hallway {
//    public static TETile[][] Room.randWorld;
//    private static Random Room.RANDOMSEED;
//    private static HashMap<Object, Object> roomMap;
//    TERenderer ter = new TERenderer();
//    HashMap<Integer, Room.Position> roomMap;
//    private final Random Room.RANDOMSEED;

    //    public Hallway(Random Room.RANDOMSEED) {
//        Room.RANDOMSEED = Room.RANDOMSEED;
//    }
    public static int test = 0;
    private static boolean hallwayhelper(Room.Position room1, Room.Position room2) {
        for (int i = room1.y; i > room1.y - room1.height + 4; i--) {
            if (Room.randWorld[room1.x][i] == Tileset.FLOOR
                    || Room.randWorld[room1.x + room1.width][i] == Tileset.FLOOR) {
                verticalhallways(room1, room2);
                return true;
            }
        }
        for (int i = room1.x; i < room1.x + room1.width - 15; i++) {
            System.out.println("room1.x:" + room1.x);
            System.out.println("room1.width:" + room1.width);
            System.out.println(test);
            if (Room.randWorld[i][room1.y] == Tileset.FLOOR
                    || Room.randWorld[i][room1.y - room1.height] == Tileset.FLOOR) {
                horitonzalhallway(room1, room2);
                return true;
            }
        }
        return false;
    }


    private static String straightHallway(Room.Position room1, Room.Position room2) {

        if (((room1.y - 3 > room2.y - room2.height) && (room1.y < room2.y))
                || ((room2.y - 3 > room1.y - room1.height) && (room2.y < room1.y))) {
            return "horizontal";
        } else if (((room1.x > room2.x) && (room1.x < room2.x + room2.width - 3))
                || ((room2.x > room1.x) && (room2.x < room1.x + room1.width - 3))) {
            return "vertical";
        } else {
            return "NONE";
        }
    }

    public static void connect(Room.Position room1, Room.Position room2) {
        String rotate = straightHallway(room1, room2);
        Room.Position startPoint = null;
        if (rotate.equals("horizontal")) {
            if (room1.x > room2.x) {
                Room.Position temp = room2;
                room2 = room1;
                room1 = temp;
            }
            int randY = RandomUtils.uniform(Room.RANDOMSEED, Math.max(room2.y - room2.height,
                    room1.y - room1.height), Math.min(room2.y, room1.y));
            startPoint = new Room.Position(room1.x + room1.width, randY, 0, 0);
            int dif1 = room2.x - (room1.x + room1.width);
            for (int i = 0; i < 6; i++) {
                if (!hallwayhelper2(rotate, startPoint, dif1)) {
                    int differentRandY = RandomUtils.uniform(Room.RANDOMSEED,
                            Math.max(room2.y - room2.height, room1.y - room1.height), Math.min(room2.y, room1.y));
                    startPoint = new Room.Position(room1.x + room1.width, differentRandY, 0, 0);
                }
            }
            int dif = room2.x - (room1.x + room1.width);
            hallwaydraw(rotate, startPoint, dif);
        } else if (rotate.equals("vertical")) {
            if (room1.y > room2.y) {
                Room.Position temp = room2;
                room2 = room1;
                room1 = temp;
            }
            int ran = RandomUtils.uniform(Room.RANDOMSEED, Math.max(room1.x, room2.x) + 1,
                    Math.min(room1.x + room1.width, room2.x + room2.width));
            startPoint = new Room.Position(ran, room1.y, 0, 0);
            int dif1 = room2.y - room2.height - room1.y;
            for (int i = 0; i < 6; i++) {
                boolean checkhelper = hallwayhelper2(rotate, startPoint, dif1);
                if (!checkhelper) {
                    int differentran = RandomUtils.uniform(Room.RANDOMSEED,
                            Math.max(room1.x, room2.x) + 1, Math.min(room1.x + room1.width, room2.x + room2.width));
                    startPoint = new Room.Position(differentran, room1.y, 0, 0);
                }
            }
            int dif = room2.y - room2.height - room1.y;
            hallwaydraw(rotate, startPoint, dif);
        } else {
            int r = RandomUtils.uniform(Room.RANDOMSEED, 0, 2);
            if (hallwayhelper(room1, room2)) {
                return;
            }
            if (r == 0) {

                verticalhallways(room1, room2);
            } else if (r == 1) {

                horitonzalhallway(room1, room2);
            }
        }
    }


    private static void hallwaydraw(String rotate, Room.Position startPoint, int dif) {

        if (rotate.equals("horizontal")) {
            for (int i = 0; i <= dif; i++) {

                if (Room.randWorld[startPoint.x + i][startPoint.y] == Tileset.WALL) {
                    Room.randWorld[startPoint.x + i][startPoint.y] = Tileset.FLOOR;
                    if (Room.randWorld[startPoint.x + i][startPoint.y - 1] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x + i][startPoint.y - 1] = Tileset.WALL;
                    }
                    if (Room.randWorld[startPoint.x + i][startPoint.y + 1] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x + i][startPoint.y + 1] = Tileset.WALL;
                    }
                } else if (Room.randWorld[startPoint.x + i][startPoint.y] == Tileset.NOTHING) {
                    Room.randWorld[startPoint.x + i][startPoint.y] = Tileset.FLOOR;
                    Room.randWorld[startPoint.x + i][startPoint.y + 1] = Tileset.WALL;
                    Room.randWorld[startPoint.x + i][startPoint.y - 1] = Tileset.WALL;
                } else if (Room.randWorld[startPoint.x + i][startPoint.y] == Tileset.FLOOR) {
                    if (Room.randWorld[startPoint.x + i][startPoint.y - 1] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x + i][startPoint.y - 1] = Tileset.WALL;
                    }
                    if (Room.randWorld[startPoint.x + i][startPoint.y + 1] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x + i][startPoint.y + 1] = Tileset.WALL;
                    }
                }
            }
        } else {

            for (int i = 0; i <= dif; i++) {

                if (Room.randWorld[startPoint.x][startPoint.y + i] == Tileset.WALL) {
                    Room.randWorld[startPoint.x][startPoint.y + i] = Tileset.FLOOR;
                    if (Room.randWorld[startPoint.x - 1][startPoint.y + i] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x - 1][startPoint.y + i] = Tileset.WALL;
                    }
                    if (Room.randWorld[startPoint.x + 1][startPoint.y + i] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x + 1][startPoint.y + i] = Tileset.WALL;
                    }
                } else if (Room.randWorld[startPoint.x][startPoint.y + i] == Tileset.NOTHING) {
                    Room.randWorld[startPoint.x][startPoint.y + i] = Tileset.FLOOR;
                    Room.randWorld[startPoint.x + 1][startPoint.y + i] = Tileset.WALL;
                    Room.randWorld[startPoint.x - 1][startPoint.y + i] = Tileset.WALL;
                } else if (Room.randWorld[startPoint.x][startPoint.y + i] == Tileset.FLOOR) {
                    if (Room.randWorld[startPoint.x + 1][startPoint.y + i] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x + 1][startPoint.y + i] = Tileset.WALL;
                    }
                    if (Room.randWorld[startPoint.x - 1][startPoint.y + i] == Tileset.NOTHING) {
                        Room.randWorld[startPoint.x - 1][startPoint.y + i] = Tileset.WALL;
                    }
                }
            }
        }
    }


    public static void horitonzalhallway(Room.Position room1, Room.Position room2) {

        if (Room.direction(room1, room2).equals("SE") || Room.direction(room1, room2).equals("SW")) {
            Room.Position placeholder = room1;
            room1 = room2;
            room2 = placeholder;
        }
        String directionAB = Room.direction(room1, room2);
        if (directionAB.equals("NE")) {
            int room = room2.x - (room1.x + room1.width);
            int hallwayheight = RandomUtils.uniform(Room.RANDOMSEED, room + 1, room + room2.width);
            int randY = RandomUtils.uniform(Room.RANDOMSEED, room1.y - room1.height + 1, room1.y);
            Room.Position horizontalpoint = new Room.Position(room1.x + room1.width, randY, 0, 0);

            Room.Position verticalicalpoint = new Room.Position(room1.x + room1.width
                    + hallwayheight, randY, 0, 0);



            hallwaydraw("horizontal", horizontalpoint, hallwayheight);
            hallwaydraw("vertical", verticalicalpoint, room2.y - room2.height - randY + 2);
            Room.Position cornerPoint = new Room.Position(verticalicalpoint.x, verticalicalpoint.y
                    + (room2.y - room2.height - randY) - 2, 0, 0);
            roomcorner(cornerPoint, "toprightandbot");
        } else if (directionAB.equals("NW")) {
            int room = room1.x - (room2.x + room2.width);
            int hallwayheight = RandomUtils.uniform(Room.RANDOMSEED, room + 1, room + room1.width);
            int randY = RandomUtils.uniform(Room.RANDOMSEED, room2.y - room2.height + 1, room2.y);
            Room.Position horizontalpoint = new Room.Position(room2.x + room2.width, randY, 0, 0);
            Room.Position verticalicalpoint = new Room.Position(room2.x + room2.width
                    + hallwayheight, room1.y, 0, 0);



            hallwaydraw("horizontal", horizontalpoint, hallwayheight);
            hallwaydraw("vertical", verticalicalpoint, randY - room1.y);
            roomcorner(verticalicalpoint, "toprightandbot");
        }
    }


    public static void verticalhallways(Room.Position room1, Room.Position room2) {
        boolean checkverticalhallway = Room.direction(room1, room2).equals("SW");
        boolean checkverticalhallway2 = Room.direction(room1, room2).equals("SE");

        if (checkverticalhallway || checkverticalhallway2 ) {
            Room.Position placeholder = room1;
            room1 = room2;
            room2 = placeholder;
        }
        String directionAB = Room.direction(room1, room2);
        if (directionAB.equals("NE")) {
            int room = room2.y - room2.height - room1.y;
            int hallwayheight = RandomUtils.uniform(Room.RANDOMSEED, room + 1, room2.y - room1.y);
            int r = RandomUtils.uniform(Room.RANDOMSEED, room1.x + 1, room1.x + room1.width);
            Room.Position verticalicalpoint = new Room.Position(r, room1.y, 0, 0);
            Room.Position horizontalpoint = new Room.Position(r, room1.y + hallwayheight, 0, 0);



            hallwaydraw("vertical", verticalicalpoint, hallwayheight);
            hallwaydraw("horizontal", horizontalpoint, room2.x - r);
            roomcorner(horizontalpoint, "top left");
        } else if (directionAB.equals("NW")) {
            int room = room2.y - room2.height - room1.y;
            int hallwayheight = RandomUtils.uniform(Room.RANDOMSEED, room + 1, room2.y - room1.y);
            int ran = RandomUtils.uniform(Room.RANDOMSEED, room1.x + 1, room1.x + room1.width);
            Room.Position verticalicalpoint = new Room.Position(ran, room1.y, 0, 0);
            Room.Position horizontalpoint = new Room.Position(room2.x + room2.width, room1.y + hallwayheight, 0, 0);



            hallwaydraw("vertical", verticalicalpoint, hallwayheight);
            hallwaydraw("horizontal", horizontalpoint, ran - room2.x - room2.width);
            roomcorner(horizontalpoint, "leftbottom");
        }
    }


    private static void roomcorner(Room.Position startPoint, String situation) {
        if (situation.equals("top left")) {
            Room.randWorld[startPoint.x - 1][startPoint.y + 1] = Tileset.WALL;
            Room.randWorld[startPoint.x][startPoint.y + 1] = Tileset.WALL;
            Room.randWorld[startPoint.x + 1][startPoint.y + 1] = Tileset.WALL;
        } else if (situation.equals("leftbottom")) {
            Room.randWorld[startPoint.x - 1][startPoint.y - 1] = Tileset.WALL;
            Room.randWorld[startPoint.x][startPoint.y - 1] = Tileset.WALL;
            Room.randWorld[startPoint.x + 1][startPoint.y - 1] = Tileset.WALL;
        } else if (situation.equals("toprightandbot")) {
            Room.randWorld[startPoint.x + 1][startPoint.y + 1] = Tileset.WALL;
            Room.randWorld[startPoint.x + 1][startPoint.y] = Tileset.WALL;
            Room.randWorld[startPoint.x + 1][startPoint.y - 1] = Tileset.WALL;
        }
    }

    private static boolean hallwayhelper2(String rotate, Room.Position startPoint, int dif) {

        if (rotate.equals("horizontal")) {
            for (int i = 0; i < dif; i++) {
                boolean checkwall = Room.randWorld[startPoint.x + i][startPoint.y] == Tileset.WALL;
                boolean checkwall2 = Room.randWorld[startPoint.x + i + 1][startPoint.y] == Tileset.WALL;
                if (checkwall && checkwall2) {
                    return false;
                }
            }
            return true;
        } else if (rotate.equals("vertical")) {

            for (int i = 0; i < dif; i++) {
                boolean checkver = Room.randWorld[startPoint.x][startPoint.y + i + 1] == Tileset.WALL;
                boolean checkver2 = Room.randWorld[startPoint.x][startPoint.y + i] == Tileset.WALL;
                if (checkver && checkver2) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


}
