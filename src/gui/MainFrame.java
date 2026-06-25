package gui;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends Frame {
    private TextArea airportsList;
    private TextArea flightsList;

    public MainFrame() {
        super("Air Traffic Simulation");

        setLayout(new BorderLayout());

        Panel rightPanel = new Panel(new GridLayout(2, 1));

        Panel airportsPanel = createAirportsPanel();
        Panel flightsPanel = createFlightsPanel();

        rightPanel.add(airportsPanel);
        rightPanel.add(flightsPanel);

        add(rightPanel, BorderLayout.EAST);

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

    private Panel createAirportsPanel() {
        Panel panel = new Panel(new BorderLayout());

        Label title = new Label("Airports", Label.CENTER);

        airportsList = new TextArea("", 12, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        airportsList.setEditable(false);
        airportsList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        Button addAirportButton = new Button("Add Airport");

        panel.add(title, BorderLayout.NORTH);
        panel.add(airportsList, BorderLayout.CENTER);
        panel.add(addAirportButton, BorderLayout.SOUTH);

        return panel;
    }

    private Panel createFlightsPanel() {
        Panel panel = new Panel(new BorderLayout());

        Label title = new Label("Flights", Label.CENTER);

        flightsList = new TextArea("", 12, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        flightsList.setEditable(false);
        flightsList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        Button addFlightButton = new Button("Add Flight");

        panel.add(title, BorderLayout.NORTH);
        panel.add(flightsList, BorderLayout.CENTER);
        panel.add(addFlightButton, BorderLayout.SOUTH);

        return panel;
    }
}