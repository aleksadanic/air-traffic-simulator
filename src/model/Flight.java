package model;

public class Flight {
    private Airport from;
    private Airport to;
    private int departure; // Minutes from 00:00
    private int duration;

    public Flight(Airport from, Airport to, int departure, int duration) {
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.duration = duration;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public int getDeparture() {
        return departure;
    }

    public int getDuration() {
        return duration;
    }
}
