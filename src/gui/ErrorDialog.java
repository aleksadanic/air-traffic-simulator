package gui;

import java.awt.*;
import java.awt.event.*;

public class ErrorDialog extends Dialog {
    public ErrorDialog(Frame owner, String message) {
        super(owner, "Error", true);

        setLayout(new BorderLayout());

        Label messageLabel = new Label(message, Label.CENTER);
        Button okButton = new Button("OK");

        add(messageLabel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
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

        setSize(400, 150);
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public void showDialog() {
        setVisible(true);
    }
}