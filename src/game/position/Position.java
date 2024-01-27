package game.position;

public final class Position {

    private int X;
    private int Y;

    private Position rookPos = null; // for castling position

    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    public Position(String s) { // must be char and int i.e. a4
        this(s.charAt(0) - 'a', s.charAt(1) - '1');
    }

    public String toString() {
        return "" + (char) (X + 'a') + (Y + 1);
    }

    public Position(int x, int y, int rX, int rY) {
        this(x, y);
        rookPos = new Position(rX, rY);
    }

    public Position(String s, String r) { // For Castling
        this(s);
        setRookPos(new Position(r));
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

    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Position other = (Position) obj;
        return this.X == other.getX() && this.Y == other.getY();

    }
}
