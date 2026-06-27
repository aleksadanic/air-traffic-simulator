package gui.map;

import model.data.Airport;

import java.awt.*;

public class CoordinatesService {
    private MapCanvas mapCanvas;
    public static final int REAL_MAX_X = 180;
    public static final int REAL_MAX_Y = 90;
    public static final int Q = 2;
    public static final int MARGIN = 30;
    public static final int AIRPORT_SIZE = 8;
    public static Rectangle mapBounds = null;

    public CoordinatesService(MapCanvas mapCanvas) {
        this.mapCanvas = mapCanvas;
    }

    void drawMapBackground(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(
            mapCanvas.coordinatesService.getMapBounds().x,
            mapCanvas.coordinatesService.getMapBounds().y,
            mapCanvas.coordinatesService.getMapBounds().width,
            mapCanvas.coordinatesService.getMapBounds().height
        );
    }

    public Rectangle getAirportBounds(Airport airport) {
        int X = (int) mapX(airport.getPosition().getX());
        int Y = (int) mapY(airport.getPosition().getY());
        return new Rectangle(X - AIRPORT_SIZE / 2, Y - AIRPORT_SIZE / 2, AIRPORT_SIZE, AIRPORT_SIZE);
    }

    public double mapX(double x) {
        return getMapBounds().x + (x + REAL_MAX_X) * getMapBounds().width / (2 * REAL_MAX_X);
    }

    public double mapY(double y) {
        return getMapBounds().y + (REAL_MAX_Y - y) * getMapBounds().height / (2 * REAL_MAX_Y);
    }

    public Rectangle getMapBounds() {
        if (mapBounds == null) {
            int maxWidth = mapCanvas.getWidth() - 2 * MARGIN;
            int maxHeight = mapCanvas.getHeight() - 2 * MARGIN;

            int width = maxWidth;
            int height = maxHeight;

            if (Q * maxHeight < maxWidth) {
                width = Q * maxHeight;
            } else {
                height = maxWidth / Q;
            }

            mapBounds = new Rectangle((mapCanvas.getWidth() - width) / 2, (mapCanvas.getHeight() - height) / 2, width, height);
        }
        return mapBounds;
    }
}
