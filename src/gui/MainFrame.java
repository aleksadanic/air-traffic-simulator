package gui;

import api.API;
import background.InactivityTimer;
import gui.map.MapCanvas;
import model.simulation.SimulationEngine;

import java.awt.*;
import java.awt.event.*;

import static java.lang.Thread.sleep;

public class MainFrame extends Frame {
    private API api;

    private InactivityTimer inactivityTimer;

    private MapCanvas mapCanvas;
    private AirportsPanel airportsPanel;
    private FlightsPanel flightsPanel;
    private Label timeLabel;

    public MainFrame(API api) {
        super("Air Traffic Simulation");

        this.api = api;

        setLayout(new BorderLayout());

        Panel rightPanel = new Panel(new GridLayout(2, 1));

        rightPanel.setPreferredSize(new Dimension(400, 0));

        airportsPanel = new AirportsPanel(this, api);
        flightsPanel = new FlightsPanel(this, api);

        rightPanel.add(airportsPanel);
        rightPanel.add(flightsPanel);

        Panel mapPanel = new Panel(new BorderLayout());

        timeLabel = new Label();

        mapCanvas = new MapCanvas(api, this, airportsPanel);

        Panel simulationButtonsPanel = new Panel(new FlowLayout());

        Button pauseButton = new Button("Pause");
        Button resumeButton = new Button("Resume");
        Button resetButton = new Button("Reset");
        Button startButton = new Button("Start");

        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        resetButton.setVisible(false);

        simulationButtonsPanel.add(pauseButton);
        simulationButtonsPanel.add(resumeButton);
        simulationButtonsPanel.add(resetButton);
        simulationButtonsPanel.add(startButton);

        mapPanel.add(timeLabel, BorderLayout.NORTH);
        mapPanel.add(mapCanvas, BorderLayout.CENTER);
        mapPanel.add(simulationButtonsPanel, BorderLayout.SOUTH);

        add(mapPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setMenuBar(new AppMenuBar(this, api));

        setSize(1500, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                inactivityTimer.setBlocked(true);
                dispose();
            }
        });

        pauseButton.addActionListener(e -> {
            api.pauseSimulation();
        });

        resumeButton.addActionListener(e -> {
            api.resumeSimulation();
        });

        resetButton.addActionListener(e -> {
            api.resetSimulation();

            timeLabel.setText("");

            pauseButton.setVisible(false);
            resumeButton.setVisible(false);
            resetButton.setVisible(false);
            startButton.setVisible(true);

            simulationButtonsPanel.validate();
            simulationButtonsPanel.repaint();
            mapCanvas.repaint();
        });

        startButton.addActionListener(e -> {
            api.startSimulation();

            pauseButton.setVisible(true);
            resumeButton.setVisible(true);
            resetButton.setVisible(true);
            startButton.setVisible(false);

            simulationButtonsPanel.validate();
            simulationButtonsPanel.repaint();
            mapCanvas.repaint();
        });

        inactivityTimer = new InactivityTimer(this);

        setVisible(true);
    }

    public void showSimulation() {
        Thread t = new Thread(() -> {
            while (api.getCurrentSimulationEngine() != null && api.getCurrentSimulationEngine().getRunning()) {
                int time = api.getCurrentSimulationEngine().getCurrentSimulationTime();
                timeLabel.setText(String.format("%02d:%02d", time / 60 % 24, time % 60));
                mapCanvas.repaint();
                try {
                    sleep(SimulationEngine.realRefreshRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            inactivityTimer.setPaused(false);
            mapCanvas.repaint();
        });
        t.setDaemon(true);
        t.start();
    }

    public void refreshAll() {
        airportsPanel.refreshAirportsList();
        flightsPanel.refreshFlightsList();
        mapCanvas.repaint();
    }

    public API getApi() {
        return api;
    }

    public MapCanvas getMapCanvas() {
        return mapCanvas;
    }

    public InactivityTimer getInactivityTimer() {
        return inactivityTimer;
    }
}