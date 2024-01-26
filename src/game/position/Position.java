package game.position;

public class Position {

    private int X;
    private int Y;

    private Position rookPos = null; // for castling position

    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    public Position(String s) { // must be char and int i.e. a4
        this((int) s.charAt(0) - (int) 'a' + 1, 8 - Character.getNumericValue(s.charAt(1)));
    }

    public Position(int x, int y, int rX, int rY) {
        this(x, y);
        rookPos = new Position(rX, rY);
    }

    public int getX() {
        return this.X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return this.Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public Position getRookPos() {
        return rookPos;
    }

    public void setRookPos(Position rookPos) {
        this.rookPos = rookPos;
    }

    public boolean equals(Position a, Position b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }
}
