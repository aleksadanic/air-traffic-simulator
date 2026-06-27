package gui;

import api.API;
import gui.map.MapCanvas;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends Frame {
    private API api;

    private MapCanvas mapCanvas;
    private AirportsPanel airportsPanel;
    private FlightsPanel flightsPanel;

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

        mapCanvas = new MapCanvas(api, airportsPanel);

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
                dispose();
            }
        });

        pauseButton.addActionListener(e -> {
            // TODO:
            // api.stopSimulation();
        });

        resumeButton.addActionListener(e -> {
            // TODO:
            // api.resumeSimulation();
        });

        resetButton.addActionListener(e -> {
            // TODO:
            // api.resetSimulation();
            // mapCanvas.repaint();

            pauseButton.setVisible(false);
            resumeButton.setVisible(false);
            resetButton.setVisible(false);
            startButton.setVisible(true);

            simulationButtonsPanel.validate();
            simulationButtonsPanel.repaint();
        });

        startButton.addActionListener(e -> {
            // TODO:
            // api.resetSimulation();
            // mapCanvas.repaint();

            api.startSimulation();

            pauseButton.setVisible(true);
            resumeButton.setVisible(true);
            resetButton.setVisible(true);
            startButton.setVisible(false);

            simulationButtonsPanel.validate();
            simulationButtonsPanel.repaint();
        });

        setVisible(true);
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
}