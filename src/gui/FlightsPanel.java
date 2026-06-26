package gui;

import api.API;
import model.Airport;
import model.Flight;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightsPanel extends Panel {
    private Frame owner;
    private API api;
    private List flightsList;

    public FlightsPanel(Frame owner, API api) {
        super(new BorderLayout());

        this.owner = owner;
        this.api = api;

        flightsList = new List();
        flightsList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        Button addFlightButton = new Button("Add flight");

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFlightDialog dialog = new AddFlightDialog(owner);
                dialog.showDialog();
                refreshFlightsList();
            }
        });

        add(new Label("Flights", Label.CENTER), BorderLayout.NORTH);
        add(flightsList, BorderLayout.CENTER);
        add(addFlightButton, BorderLayout.SOUTH);
    }

    public void refreshFlightsList() {
        flightsList.removeAll();
        for (Flight flight : api.getFlights()) {
            flightsList.add(String.format(
                    "%-5s %-5s %-8s %-8d",
                    flight.getFrom().getCode(),
                    flight.getTo().getCode(),
                    String.format("%02d:%02d", flight.getDeparture() / 60, flight.getDeparture() % 60),
                    flight.getDuration()
            ));
        }
    }
}
