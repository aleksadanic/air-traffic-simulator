package model.data;

import model.Position;

public class Airport {
    private String code;
    private String name;
    private Position position;

    public Airport(String code, String name, int X, int Y) {
        this.code = code;
        this.name = name;
        position = new Position(X, Y);
    }

    public String toCsv() {
        return code + "," + name + "," + (int) position.getX() + "," + (int) position.getY();
    }

    public String toJson() {
        return "{\"code\":\"" + code + "\",\"name\":\"" + name + "\",\"x\":" + (int) position.getX() + ",\"y\":" + (int) position.getY() + "}";
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }
}
