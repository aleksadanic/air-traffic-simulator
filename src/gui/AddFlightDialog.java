package gui;

import exceptions.ScenarioException;
import model.Flight;

import java.awt.*;
import java.awt.event.*;

public class AddFlightDialog extends Dialog {
    private TextField fromCodeField;
    private TextField toCodeField;
    private TextField departureTimeField;
    private TextField durationField;

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

                try {
                    ((MainFrame) owner).getApi().addFlight(fromCode, toCode, departureTime, duration);
                    dispose();
                } catch (ScenarioException ex) {
                    ErrorDialog errorDialog = new ErrorDialog(owner, ex.getMessage());
                    errorDialog.showDialog();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(400, 200);
        setResizable(false);
    }

    public void showDialog() {
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }
}