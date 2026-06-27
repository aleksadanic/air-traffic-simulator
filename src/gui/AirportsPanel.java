package gui;

import api.API;
import model.data.Airport;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AirportsPanel extends Panel {
    private MainFrame owner;
    private API api;
    private Panel airportsList;
    private Set<Airport> hiddenAirports = new HashSet<>();

    public AirportsPanel(MainFrame owner, API api) {
        super(new BorderLayout());

        this.owner = owner;
        this.api = api;

        airportsList = new Panel(new GridLayout(0, 1));

        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(airportsList);

        airportsList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        Button addAirportButton = new Button("Add airport");

        addAirportButton.addActionListener(e -> {
            AddAirportDialog dialog = new AddAirportDialog(owner);
            dialog.showDialog();
            owner.refreshAll();
        });

        add(new Label("Airports", Label.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(addAirportButton, BorderLayout.SOUTH);
    }

    public boolean isVisible(Airport airport) {
        return !hiddenAirports.contains(airport);
    }

    public void refreshAirportsList() {
        airportsList.removeAll();
        for (Airport airport : api.getAirports()) {
            airportsList.add(createAirportRow(airport));
        }
        validate();
        repaint();
    }

    private Panel createAirportRow(Airport airport) {
        Panel row = new Panel(new BorderLayout(5, 0));
        Checkbox checkbox = new Checkbox("", true);
        Label label = new Label(
            String.format(
                "%-5s %-30s %5d %5d",
                airport.getCode(),
                airport.getName(),
                (int) airport.getPosition().getX(),
                (int) airport.getPosition().getY()
            )
        );

        checkbox.addItemListener(e -> {
            if (checkbox.getState()) {
                hiddenAirports.remove(airport);
            } else {
                hiddenAirports.add(airport);
            }
            owner.getMapCanvas().repaint();
        });

        row.add(checkbox, BorderLayout.WEST);
        row.add(label, BorderLayout.CENTER);

        return row;
    }
}
