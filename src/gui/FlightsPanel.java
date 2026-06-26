package gui;

import api.API;
import model.Airport;
import model.Flight;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightsPanel extends Panel {
    private MainFrame owner;
    private API api;
    private Panel flightsList;

    public FlightsPanel(MainFrame owner, API api) {
        super(new BorderLayout());

        this.owner = owner;
        this.api = api;

        flightsList = new Panel(new GridLayout(0, 1));

        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(flightsList);

        flightsList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        Button addFlightButton = new Button("Add flight");

        addFlightButton.addActionListener(e -> {
            AddFlightDialog dialog = new AddFlightDialog(owner);
            dialog.showDialog();
            owner.refreshAll();
        });

        add(new Label("Flights", Label.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(addFlightButton, BorderLayout.SOUTH);
    }

    public void refreshFlightsList() {
        flightsList.removeAll();
        for (Flight flight : api.getFlights()) {
            flightsList.add(createFlightRow(flight));
        }
        validate();
        repaint();
    }

    private Panel createFlightRow(Flight flight) {
        Panel row = new Panel(new BorderLayout());
        Label label = new Label(
            String.format(
                "%-5s %-5s %-6s %4d",
                flight.getFrom().getCode(),
                flight.getTo().getCode(),
                String.format("%02d:%02d", flight.getDeparture() / 60, flight.getDeparture() % 60),
                flight.getDuration()
            )
        );

        row.add(label, BorderLayout.CENTER);

        return row;
    }
}
