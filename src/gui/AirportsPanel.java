package gui;

import api.API;
import model.Airport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirportsPanel extends Panel {
    private Frame owner;
    private API api;
    private TextArea airportsList;

    public AirportsPanel(Frame owner, API api) {
        super(new BorderLayout());

        this.owner = owner;
        this.api = api;

        Label title = new Label("Airports", Label.CENTER);

        airportsList = new TextArea("", 12, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        airportsList.setEditable(false);
        airportsList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        Button addAirportButton = new Button("Add airport");

        addAirportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAirportDialog dialog = new AddAirportDialog(owner);
                dialog.showDialog();
                refreshAirportsList();
            }
        });

        add(title, BorderLayout.NORTH);
        add(airportsList, BorderLayout.CENTER);
        add(addAirportButton, BorderLayout.SOUTH);
    }

    public void refreshAirportsList() {
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
}
