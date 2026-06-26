package gui;

import api.API;
import model.Airport;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapCanvas extends Canvas {
    private API api;

    private static final int REAL_MAX_X = 180;
    private static final int REAL_MAX_Y = 90;
    private static final int Q = 2;
    private static final int MARGIN = 30;
    private static final int AIRPORT_SIZE = 8;
    private static Rectangle mapBounds = null;

    private Map<Airport, Boolean> isBlinking = new HashMap<>();
    private boolean redPhase = false;
    private Timer blinkingTimer = new Timer(true);

    public MapCanvas(API api) {
        this.api = api;

        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        blinkingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                redPhase = !redPhase;
                repaint();
            }
        }, 0, 500);
    }

    @Override
    public void paint(Graphics g) {
        drawMapBackground(g);
        drawAirports(g);
    }

    private void drawMapBackground(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(getMapBounds().x, getMapBounds().y, getMapBounds().width, getMapBounds().height);
    }

    private void drawAirports(Graphics g) {
        for (Airport airport : api.getAirports()) {
            int X = mapX(airport.getX());
            int Y = mapY(airport.getY());

            if (redPhase && isBlinking.getOrDefault(airport, false)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GRAY);
            }
            g.fillRect(X - AIRPORT_SIZE / 2, Y - AIRPORT_SIZE / 2, AIRPORT_SIZE, AIRPORT_SIZE);

            g.setColor(Color.BLACK);
            g.drawString(airport.getCode(), X + AIRPORT_SIZE / 2, Y - AIRPORT_SIZE / 2);
        }
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        for (Airport airport : api.getAirports()) {
            Rectangle bounds = getAirportBounds(airport);
            if (bounds.contains(mouseX, mouseY)) {
                isBlinking.put(airport, isBlinking.getOrDefault(airport, false) ^ true);
                return;
            }
        }
    }

    private Rectangle getAirportBounds(Airport airport) {
        int X = mapX(airport.getX());
        int Y = mapY(airport.getY());
        return new Rectangle(X - AIRPORT_SIZE / 2, Y - AIRPORT_SIZE / 2, AIRPORT_SIZE, AIRPORT_SIZE);
    }

    private int mapX(int x) {
        return getMapBounds().x + (x + REAL_MAX_X) * getMapBounds().width / (2 * REAL_MAX_X);
    }

    private int mapY(int y) {
        return getMapBounds().y + (REAL_MAX_Y - y) * getMapBounds().height / (2 * REAL_MAX_Y);
    }

    private Rectangle getMapBounds() {
        if (mapBounds == null) {
            int maxWidth = getWidth() - 2 * MARGIN;
            int maxHeight = getHeight() - 2 * MARGIN;

            int width = maxWidth;
            int height = maxHeight;

            if (Q * maxHeight < maxWidth) {
                width = Q * maxHeight;
            } else {
                height = maxWidth / Q;
            }

            mapBounds = new Rectangle((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
        }
        return mapBounds;
    }
}