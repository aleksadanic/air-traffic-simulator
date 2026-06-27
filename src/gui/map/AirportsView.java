package gui.map;

import gui.AirportsPanel;
import model.data.Airport;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class AirportsView {
    private MapCanvas mapCanvas;
    private Set<Airport> blinking = new HashSet<>();
    private boolean redPhase = false;
    private Timer blinkingTimer;
    private AirportsPanel airportsPanel;

    public AirportsView(MapCanvas mapCanvas, AirportsPanel airportsPanel) {
        this.mapCanvas = mapCanvas;
        this.airportsPanel = airportsPanel;
    }

    void drawAirports(Graphics g) {
        for (Airport airport : mapCanvas.api.getAirports()) {
            if (!airportsPanel.isVisible(airport)) {
                continue;
            }

            int X = (int) mapCanvas.coordinatesService.mapX(airport.getPosition().getX());
            int Y = (int) mapCanvas.coordinatesService.mapY(airport.getPosition().getY());

            if (redPhase && blinking.contains(airport)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GRAY);
            }
            g.fillRect(
                X - CoordinatesService.AIRPORT_SIZE / 2,
                Y - CoordinatesService.AIRPORT_SIZE / 2,
                CoordinatesService.AIRPORT_SIZE,
                CoordinatesService.AIRPORT_SIZE
            );

            g.setColor(Color.BLACK);
            g.drawString(airport.getCode(), X + CoordinatesService.AIRPORT_SIZE / 2, Y - CoordinatesService.AIRPORT_SIZE / 2);
        }
    }

    void toggleBlinking(Airport airport) {
        if (blinking.contains(airport)) {
            blinking.remove(airport);
            if (blinking.isEmpty()) {
                blinkingTimer.cancel();
                blinkingTimer = null;
                redPhase = false;
                mapCanvas.repaint();
            }
        } else {
            if (blinking.isEmpty()) {
                blinkingTimer = new Timer(true);
                blinkingTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        redPhase = !redPhase;
                        mapCanvas.repaint();
                    }
                }, 0, 500);
            }
            blinking.add(airport);
        }
    }
}
