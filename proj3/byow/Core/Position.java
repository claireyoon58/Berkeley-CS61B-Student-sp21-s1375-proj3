package byow.Core;

public class Position {

    public int y;
    public int x;
    public int width;
    public int height;

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


