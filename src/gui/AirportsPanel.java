package gui;

import api.API;
import model.Airport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirportsPanel extends Panel {
    private Frame owner;
    private API api;
    private List airportsList;

    public AirportsPanel(Frame owner, API api) {
        super(new BorderLayout());

        this.owner = owner;
        this.api = api;

        airportsList = new List();
        airportsList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        Button addAirportButton = new Button("Add airport");

        addAirportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAirportDialog dialog = new AddAirportDialog(owner);
                dialog.showDialog();
                refreshAirportsList();
            }
        });

        add(new Label("Airports", Label.CENTER), BorderLayout.NORTH);
        add(airportsList, BorderLayout.CENTER);
        add(addAirportButton, BorderLayout.SOUTH);
    }

    public void refreshAirportsList() {
        airportsList.removeAll();
        for (Airport airport : api.getAirports()) {
            airportsList.add(String.format(
                    "%-5s %-28s %6d %6d",
                    airport.getCode(),
                    airport.getName(),
                    airport.getX(),
                    airport.getY()
            ));
        }
    }
}
