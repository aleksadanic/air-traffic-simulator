package gui;

import api.API;
import model.Airport;
import model.Flight;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends Frame {
    private API api;
    private AirportsPanel airportsPanel;
    private FlightsPanel flightsPanel;

    public MainFrame(API api) {
        super("Air Traffic Simulation");

        this.api = api;

        setLayout(new BorderLayout());

        Panel rightPanel = new Panel(new GridLayout(2, 1));

        airportsPanel = new AirportsPanel(this, api);
        flightsPanel = new FlightsPanel(this, api);

        rightPanel.add(airportsPanel);
        rightPanel.add(flightsPanel);

        add(rightPanel, BorderLayout.EAST);

        setMenuBar(new AppMenuBar(this, api));

        setSize(1500, 1000);
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
    }

    public API getApi() {
        return api;
    }
}