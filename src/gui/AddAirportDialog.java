package gui;

import model.Airport;

import java.awt.*;
import java.awt.event.*;

public class AddAirportDialog extends Dialog {
    private TextField codeField;
    private TextField nameField;
    private TextField xField;
    private TextField yField;

    private Airport airport;

    public AddAirportDialog(Frame owner) {
        super(owner, "Add Airport", true);

        setLayout(new BorderLayout());

        Panel formPanel = new Panel(new GridLayout(4, 2, 5, 5));

        codeField = new TextField();
        nameField = new TextField();
        xField = new TextField();
        yField = new TextField();

        formPanel.add(new Label("Code:"));
        formPanel.add(codeField);

        formPanel.add(new Label("Name:"));
        formPanel.add(nameField);

        formPanel.add(new Label("X coordinate:"));
        formPanel.add(xField);

        formPanel.add(new Label("Y coordinate:"));
        formPanel.add(yField);

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
                String code = codeField.getText().trim();
                String name = nameField.getText().trim();
                String x = xField.getText().trim();
                String y = yField.getText().trim();

                /*
                 * TODO:
                 * Ovde iskucaj svoju logiku:
                 * - validacija unosa
                 * - parsiranje koordinata
                 * - pravljenje Airport objekta
                 *
                 * Na kraju treba da dodeliš:
                 * airport = ...
                 *
                 * Ako unos nije validan, prikaži grešku i nemoj zatvarati dialog.
                 */

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                airport = null;
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                airport = null;
                dispose();
            }
        });

        setSize(350, 200);
        setResizable(false);
    }

    public Airport showDialog() {
        airport = null;

        setLocationRelativeTo(getOwner());
        setVisible(true);

        return airport;
    }
}