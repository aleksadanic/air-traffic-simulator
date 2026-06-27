package model.simulation;

import exceptions.SimulationException;
import model.Position;
import model.Velocity;
import model.data.Airport;
import model.data.Flight;
import model.data.Scenario;

import java.util.*;

import static java.lang.Thread.sleep;

public class SimulationEngine implements Runnable {
    private final int N = 2880; // Amount of minutes in two days
    private final int waitingTime = 10; // Amount of minutes required between two departures from the same airport
    private final int realRefreshRate = 200; // Simulation refresh period in ms
    private final int Q = 100; // Real 1000ms <=> simulation 10min, Q is the ratio of these

    List<Airplane> airplanes = new ArrayList<>();
    Map<Airport, Integer> latestDeparture = new HashMap<>();
    List<Airplane>[] departures = new List[N];
    List<Airplane>[] arrivals = new List[N];

    public SimulationEngine(Scenario scenario) {
        for (int i = 0; i < N; i++) {
            departures[i] = new ArrayList<Airplane>();
            arrivals[i] = new ArrayList<Airplane>();
        }

        List<Flight> sortedFlights = scenario.getFlights();
        sortedFlights.sort(Comparator.comparing(Flight::getDeparture));
        for (Flight flight : sortedFlights) {
            Airplane airplane = new Airplane(flight.getFrom().getPosition(), flight.getTo().getPosition());
            airplane.setVelocity(new Velocity(
                (flight.getTo().getPosition().getX() - flight.getFrom().getPosition().getX()) / flight.getDuration(),
                (flight.getTo().getPosition().getY() - flight.getFrom().getPosition().getY()) / flight.getDuration())
            );
            airplanes.add(airplane);

            int departureTime = flight.getDeparture();
            if (latestDeparture.getOrDefault(flight.getFrom(), -departureTime - 1) + waitingTime > departureTime) {
                departureTime = latestDeparture.getOrDefault(flight.getFrom(), -departureTime - 1) + waitingTime;
            }

            if (departureTime + flight.getDuration() >= N) {
                throw new SimulationException("Not enough time to handle all flights");
            }
            departures[departureTime].add(airplane);
            arrivals[departureTime + flight.getDuration()].add(airplane);
        }
    }

    @Override
    public void run() {
        int currentRealTime = 0, currentSimulationTime = 0;
        int nextRefreshRealTime = 0;
        while (currentSimulationTime < N) {
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
                airplane.setFlying(false);
            }
            for (Airplane airplane : airplanes) {
                airplane.forward(1);
            }

            currentSimulationTime++;
            currentRealTime += Q;
        }
    }
}