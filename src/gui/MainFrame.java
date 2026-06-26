package gui;

import api.API;
import gui.map.MapCanvas;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends Frame {
    private API api;

    private MapCanvas mapCanvas;
    private AirportsPanel airportsPanel;
    private FlightsPanel flightsPanel;

    public MainFrame(API api) {
        super("Air Traffic Simulation");

        this.api = api;

        setLayout(new BorderLayout());

        mapCanvas = new MapCanvas(api);

        Panel rightPanel = new Panel(new GridLayout(2, 1));

        rightPanel.setPreferredSize(new Dimension(350, 0));

        airportsPanel = new AirportsPanel(this, api);
        flightsPanel = new FlightsPanel(this, api);

        rightPanel.add(airportsPanel);
        rightPanel.add(flightsPanel);

        add(mapCanvas, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setMenuBar(new AppMenuBar(this, api));

        setSize(1500, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void refreshAll() {
        airportsPanel.refreshAirportsList();
        flightsPanel.refreshFlightsList();
        mapCanvas.repaint();
    }

    public API getApi() {
        return api;
    }
}