package byow.Core;

public class Position {

    int y;
    int x;
    int width;
    int height;

    Position(int xx, int yy, int width, int height) {
        x = xx;
        y = yy;
        this.width = width;
        this.height = height;
    }

//        public boolean equalPos(Position p) {
//            return this.getx() == p.getx() && this.gety() == p.gety();
//        }
}


