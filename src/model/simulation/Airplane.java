package model.simulation;

import model.Position;
import model.Velocity;

public class Airplane {
    private Position currentPosition;
    private Position targetPosition;
    private Velocity velocity;
    boolean flying = false;

    public Airplane(Position initialPosition, Position targetPosition) {
        this.currentPosition = new Position(initialPosition.getX(), initialPosition.getY());
        this.targetPosition = new Position(targetPosition.getX(), targetPosition.getY());
    }

    // Moves forward through simulation time for time minutes
    public void forward(int time) {
        if (flying) {
            currentPosition.move(velocity, time);
        }
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public boolean getFlying() {
        return flying;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }
}
