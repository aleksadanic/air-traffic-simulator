package model;

public class Airport {
    private String code;
    private String name;
    private int X;
    private int Y;

    public Airport(String code, String name, int X, int Y) {
        this.code = code;
        this.name = name;
        this.X = X;
        this.Y = Y;
    }

    public String toCsv() {
        return code + "," + name + "," + X + "," + Y;
    }

    public String toJson() {
        return "{\"code\":\"" + code + "\",\"name\":\"" + name + "\",\"x\":" + X + ",\"y\":" + Y + "}";
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
