package gui;

import api.API;
import model.Flight;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightsPanel extends Panel {
    private Frame owner;
    private API api;
    private TextArea flightsList;

    public FlightsPanel(Frame owner, API api) {
        super(new BorderLayout());

        this.owner = owner;
        this.api = api;

        Label title = new Label("Flights", Label.CENTER);

        flightsList = new TextArea("", 12, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        flightsList.setEditable(false);
        flightsList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        Button addFlightButton = new Button("Add flight");

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFlightDialog dialog = new AddFlightDialog(owner);
                dialog.showDialog();
                refreshFlightsList();
            }
        });

        add(title, BorderLayout.NORTH);
        add(flightsList, BorderLayout.CENTER);
        add(addFlightButton, BorderLayout.SOUTH);
    }

    public void refreshFlightsList() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-6s %-6s %-10s %-10s\n", "FROM", "TO", "DEPART", "DURATION"));
        sb.append("-------------------------------------\n");

        for (Flight flight : api.getFlights()) {
            sb.append(String.format(
                    "%-6s %-6s %-10s %-10d\n",
                    flight.getFrom().getCode(),
                    flight.getTo().getCode(),
                    String.format("%02d:%02d", flight.getDeparture() / 60, flight.getDeparture() % 60),
                    flight.getDuration()
            ));
        }

        flightsList.setText(sb.toString());
    }
}
