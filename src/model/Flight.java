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

    public String toCsv() {
        return from.getCode() + "," + to.getCode() + "," + String.format("%02d:%02d", departure / 60, departure % 60) + "," + duration;
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
