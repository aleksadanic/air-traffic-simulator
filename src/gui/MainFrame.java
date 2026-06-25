package gui;

import api.API;
import model.Airport;
import model.Flight;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends Frame {
    private API api;
    private TextArea airportsList;
    private TextArea flightsList;

    public MainFrame(API api) {
        super("Air Traffic Simulation");

        this.api = api;

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

        Button addAirportButton = new Button("Add airport");

        addAirportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAirportDialog dialog = new AddAirportDialog(MainFrame.this);
                dialog.showDialog();
                refreshAirportsList();
            }
        });

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

        Button addFlightButton = new Button("Add flight");

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFlightDialog dialog = new AddFlightDialog(MainFrame.this);
                dialog.showDialog();
                refreshFlightsList();
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(flightsList, BorderLayout.CENTER);
        panel.add(addFlightButton, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshAirportsList() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-6s %-30s %8s %8s\n", "CODE", "NAME", "X", "Y"));
        sb.append("-------------------------------------\n");

        for (Airport airport : api.getAirports()) {
            sb.append(String.format(
                    "%-6s %-30s %8d %8d\n",
                    airport.getCode(),
                    airport.getName(),
                    airport.getX(),
                    airport.getY()
            ));
        }

        airportsList.setText(sb.toString());
    }

    private void refreshFlightsList() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-6s %-6s %-10s %-10s\n", "FROM", "TO", "DEPART", "DURATION"));
        sb.append("-------------------------------------\n");

        for (Flight flight : api.getFlights()) {
            sb.append(String.format(
                    "%-6s %-6s %-10s %-10d\n",
                    flight.getFrom().getCode(),
                    flight.getTo().getCode(),
                    formatTime(flight.getDeparture()),
                    flight.getDuration()
            ));
        }

        flightsList.setText(sb.toString());
    }

    private String formatTime(int timeInMinutes) {
        int hh = timeInMinutes / 60;
        int mm = timeInMinutes % 60;

        return String.format("%02d:%02d", hh, mm);
    }

    public API getApi() {
        return api;
    }
}