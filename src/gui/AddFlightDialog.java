package gui;

import model.Flight;

import java.awt.*;
import java.awt.event.*;

public class AddFlightDialog extends Dialog {
    private TextField fromCodeField;
    private TextField toCodeField;
    private TextField departureTimeField;
    private TextField durationField;

    private Flight flight;

    public AddFlightDialog(Frame owner) {
        super(owner, "Add Flight", true);

        setLayout(new BorderLayout());

        Panel formPanel = new Panel(new GridLayout(4, 2, 5, 5));

        fromCodeField = new TextField();
        toCodeField = new TextField();
        departureTimeField = new TextField();
        durationField = new TextField();

        formPanel.add(new Label("From airport code:"));
        formPanel.add(fromCodeField);

        formPanel.add(new Label("To airport code:"));
        formPanel.add(toCodeField);

        formPanel.add(new Label("Departure time (hh:mm):"));
        formPanel.add(departureTimeField);

        formPanel.add(new Label("Duration in minutes:"));
        formPanel.add(durationField);

        Panel buttonPanel = new Panel(new FlowLayout());

        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromCode = fromCodeField.getText().trim();
                String toCode = toCodeField.getText().trim();
                String departureTime = departureTimeField.getText().trim();
                String duration = durationField.getText().trim();

                /*
                 * TODO:
                 * Ovde iskucaj svoju logiku:
                 * - validacija kodova aerodroma
                 * - nalaženje referenci na aerodrome preko scenarija
                 * - validacija vremena u formatu hh:mm
                 * - parsiranje trajanja
                 * - pravljenje Flight objekta
                 *
                 * Na kraju treba da dodeliš:
                 * flight = ...
                 *
                 * Ako unos nije validan, prikaži grešku i nemoj zatvarati dialog.
                 */

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flight = null;
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                flight = null;
                dispose();
            }
        });

        setSize(400, 200);
        setResizable(false);
    }

    public Flight showDialog() {
        flight = null;

        setLocationRelativeTo(getOwner());
        setVisible(true);

        return flight;
    }
}