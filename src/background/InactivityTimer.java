package background;

import gui.MainFrame;

import java.awt.*;
import java.awt.event.*;

import static java.lang.Thread.sleep;

public class InactivityTimer implements Runnable {
    private static final int TIMEOUT = 60;
    private static final int WARNING = 5;

    private final MainFrame owner;

    private long lastActionTime;
    private boolean blocked = false;
    private boolean paused = false;

    private Dialog warningDialog;
    private Label warningLabel;

    public InactivityTimer(MainFrame owner) {
        this.owner = owner;
        this.lastActionTime = System.currentTimeMillis();

        Toolkit.getDefaultToolkit().addAWTEventListener(e -> resetTimer(),
            AWTEvent.KEY_EVENT_MASK |
            AWTEvent.ACTION_EVENT_MASK |
            AWTEvent.ITEM_EVENT_MASK
        );

        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        while (!blocked) {
            if (!paused) {
                int remaining = TIMEOUT - (int) ((System.currentTimeMillis() - lastActionTime) / 1000);
                if (remaining <= 0) {
                    warningDialog.dispose();
                    owner.dispose();
                    return;
                }
                if (remaining <= WARNING) {
                    updateWarning(remaining);
                }
            } else {
                closeWarning();
                lastActionTime = System.currentTimeMillis();
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetTimer() {
        lastActionTime = System.currentTimeMillis();
        closeWarning();
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        resetTimer();
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    private void updateWarning(int remaining) {
        if (warningDialog == null) {
            warningDialog = new Dialog(owner, "Inactivity warning", false);
            warningDialog.setLayout(new BorderLayout());

            warningLabel = new Label("", Label.CENTER);
            Button continueButton = new Button("Continue");

            continueButton.addActionListener(e -> resetTimer());

            warningDialog.add(warningLabel, BorderLayout.CENTER);
            warningDialog.add(continueButton, BorderLayout.SOUTH);

            warningDialog.setSize(350, 150);
            warningDialog.setResizable(false);
            warningDialog.setLocationRelativeTo(owner);

            warningDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    resetTimer();
                }
            });

            warningDialog.setVisible(true);
        }

        warningLabel.setText("Program closes in " + remaining + " seconds");
    }

    private void closeWarning() {
        if (warningDialog != null) {
            warningDialog.dispose();
            warningDialog = null;
            warningLabel = null;
        }
    }
}