package model.simulation;

import exceptions.SimulationException;
import model.Position;
import model.Velocity;
import model.data.Airport;
import model.data.Flight;
import model.data.Scenario;

import java.util.*;
import java.util.jar.JarOutputStream;

import static java.lang.Thread.sleep;

public class SimulationEngine implements Runnable {
    private static final int N = 2880; // Amount of minutes in two days
    private static final int waitingTime = 10; // Amount of minutes required between two departures from the same airport
    public static final int realRefreshRate = 200; // Simulation refresh period in ms
    public static final int Q = 100; // Real 1000ms <=> simulation 10min, Q is the ratio of these
    private final Object lock = new Object(); // We need mutex on airplane positions
    private boolean running;
    private int currentSimulationTime;
    private int eventsCount;
    private volatile boolean simulationPaused = false;

    List<Airplane> airplanes = new ArrayList<>();
    Map<Airport, Integer> latestDeparture = new HashMap<>();
    List<Airplane>[] departures = new List[N];
    List<Airplane>[] arrivals = new List[N];

    public SimulationEngine(Scenario scenario) {
        for (int i = 0; i < N; i++) {
            departures[i] = new ArrayList<>();
            arrivals[i] = new ArrayList<>();
        }
        eventsCount = 0;

        List<Flight> sortedFlights = new ArrayList<>(scenario.getFlights());
        sortedFlights.sort(Comparator.comparing(Flight::getDeparture));
        for (Flight flight : sortedFlights) {
            Airplane airplane = new Airplane(flight.getFrom().getPosition(), flight.getTo().getPosition());
            airplane.setVelocity(new Velocity(
                (flight.getTo().getPosition().getX() - flight.getFrom().getPosition().getX()) / flight.getDuration(),
                (flight.getTo().getPosition().getY() - flight.getFrom().getPosition().getY()) / flight.getDuration())
            );
            airplanes.add(airplane);

            int departureTime = flight.getDeparture();
            if (latestDeparture.getOrDefault(flight.getFrom(), -waitingTime - 1) + waitingTime > departureTime) {
                departureTime = latestDeparture.getOrDefault(flight.getFrom(), -departureTime - 1) + waitingTime;
            }
            latestDeparture.put(flight.getFrom(), departureTime);

            if (departureTime + flight.getDuration() >= N) {
                throw new SimulationException("Not enough time to handle all flights");
            }
            eventsCount++;
            departures[departureTime].add(airplane);
            arrivals[departureTime + flight.getDuration()].add(airplane);
        }
    }

    public void pauseSimulation() {
        simulationPaused = true;
    }

    public void resumeSimulation() {
        simulationPaused = false;
    }

    @Override
    public void run() {
        int currentRealTime = 0;
        currentSimulationTime = 0;
        int nextRefreshRealTime = 0;
        while (currentSimulationTime < N && eventsCount > 0) {
            while (simulationPaused) { }
            if (currentRealTime > nextRefreshRealTime) {
                try {
                    sleep(realRefreshRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nextRefreshRealTime += realRefreshRate;
            }

            for (Airplane airplane : departures[currentSimulationTime]) {
                airplane.setFlying(true);
            }
            for (Airplane airplane : arrivals[currentSimulationTime]) {
                eventsCount--;
                airplane.setFlying(false);
            }
            synchronized (lock) {
                for (Airplane airplane : airplanes) {
                    airplane.forward(1);
                }
            }

            currentSimulationTime++;
            currentRealTime += Q;
        }
        running = false;
    }

    public List<Airplane> getAirplanes() {
        synchronized (lock) {
            return new ArrayList<>(airplanes);
        }
    }

    public boolean getRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getCurrentSimulationTime() {
        return currentSimulationTime;
    }
}