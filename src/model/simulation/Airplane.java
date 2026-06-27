package model.simulation;

import model.Position;
import model.Velocity;

public class Airplane {
    private Position currentPosition;
    private Position targetPosition;
    private Velocity velocity;
    boolean flying = false;

    public Airplane(Position initialPosition, Position targetPosition) {
        this.currentPosition = initialPosition;
        this.targetPosition = targetPosition;
    }

    // Moves forward through simulation time for time minutes
    public void forward(int time) {
        if (flying) {
            currentPosition.move(velocity, time);
        }
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }
}
