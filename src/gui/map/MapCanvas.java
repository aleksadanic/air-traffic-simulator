package gui.map;

import api.API;
import gui.AirportsPanel;
import model.data.Airport;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapCanvas extends Canvas {
    API api;
    CoordinatesService coordinatesService = new CoordinatesService(this);
    AirportsView airportsView;
    AirplanesView airplanesView;

    public MapCanvas(API api, AirportsPanel airportsPanel) {
        this.api = api;

        airportsView = new AirportsView(this, airportsPanel);
        airplanesView = new AirplanesView(this);

        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        coordinatesService.drawMapBackground(g);
        airportsView.drawAirports(g);
        airplanesView.drawAirplanes(g);
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        for (Airport airport : api.getAirports()) {
            Rectangle bounds = coordinatesService.getAirportBounds(airport);
            if (bounds.contains(mouseX, mouseY)) {
                airportsView.toggleBlinking(airport);
                return;
            }
        }
    }

}