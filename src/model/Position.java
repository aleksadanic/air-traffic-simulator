package model;

public class Position {
    private double X;
    private double Y;

    public Position(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    // Moves position with given velocity for a given time
    public void move(Velocity velocity, int time) {
        X += velocity.getX() * time;
        Y += velocity.getY() * time;
    }

    public static double distance(Position A, Position B) {
        return Math.sqrt((A.X - B.X) * (A.X - B.X) + (A.Y - B.Y) * (A.Y - B.Y));
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }
}
