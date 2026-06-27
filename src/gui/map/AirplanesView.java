package gui.map;

import model.Position;
import model.simulation.Airplane;

import java.awt.*;

public class AirplanesView {
    private MapCanvas mapCanvas;

    public AirplanesView(MapCanvas mapCanvas) {
        this.mapCanvas = mapCanvas;
    }

    void drawAirplanes(Graphics g) {
        for (Airplane airplane : mapCanvas.api.getAirplanes()) {
            if (!airplane.getFlying()) {
                continue;
            }

            Position position = airplane.getCurrentPosition();

            int X = mapCanvas.coordinatesService.mapX(position.getX());
            int Y = mapCanvas.coordinatesService.mapY(position.getY());

            g.setColor(Color.BLUE);
            g.fillOval(X - CoordinatesService.AIRPLANE_SIZE / 2, Y - CoordinatesService.AIRPLANE_SIZE / 2, CoordinatesService.AIRPLANE_SIZE, CoordinatesService.AIRPLANE_SIZE);
        }
    }
}
