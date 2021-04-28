//package byow.Core;
//import byow.TileEngine.Tileset;
//import byow.TileEngine.TETile;
//import java.util.*;
//
//public class Horizontal {
//    private final TETile tile;
//    private final List<Room.Position> tilePos;
//
//
//    public Horizontal(int begin, int end, int ypos) {
//        this.tile = Tileset.FLOOR;
//        this.tilePos = new ArrayList<>();
//        for (int x = Math.min(begin, end); x <= Math.max(begin, end); x++) {
//            this.tilePos.add(new Room.Position(x, ypos));
//        }
//    }
//
//    public TETile getTile() {
//        return tile;
//    }
//
//    public List<Room.Position> getTilePos() {
//        return tilePos;
//    }
//
//
//}
