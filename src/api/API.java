package api;

import exceptions.ScenarioException;
import model.Airport;
import model.Flight;
import model.Scenario;

import java.io.*;
import java.util.List;

public class API {
    private Scenario currentScenario;

    public void addAirport(String code, String name, String X, String Y) {
        currentScenario.addAirport(currentScenario.createAirport(code, name, X, Y));
    }

    public void addFlight(String from, String to, String departure, String duration) {
        currentScenario.addFlight(currentScenario.createFlight(from, to, departure, duration));
    }

    public void loadCsvScenario(String path) {
        try {
            Scenario newScenario = new Scenario();

            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            boolean readingAirports = false;
            boolean readingFlights = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.equals("# AIRPORTS")) {
                    readingAirports = true;
                    readingFlights = false;
                    continue;
                }
                if (line.equals("# FLIGHTS")) {
                    readingAirports = false;
                    readingFlights = true;
                    continue;
                }
                if (line.equals("CODE,NAME,X,Y") || line.equals("FROM,TO,DEPARTURE,DURATION")) {
                    continue;
                }

                String[] data = line.split(",");
                if (data.length != 4) {
                    throw new ScenarioException("Invalid CSV format");
                }
                if (readingAirports) {
                    newScenario.addAirport(newScenario.createAirport(data[0], data[1], data[2], data[3]));
                } else if (readingFlights) {
                    newScenario.addFlight(newScenario.createFlight(data[0], data[1], data[2], data[3]));
                } else {
                    throw new ScenarioException("Invalid CSV format");
                }
            }

            currentScenario = newScenario;
        } catch (IOException e) {
            throw new ScenarioException("Couldn't read CSV file");
        }
    }

    public void loadJsonScenario(String path) {
        System.out.println("Hehe2");
    }

    public void saveCsvScenario(String path) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("# AIRPORTS");
            out.println("CODE,NAME,X,Y");
            for (Airport airport : currentScenario.getAirports()) {
                out.println(airport.toCsv());
            }

            out.println("# FLIGHTS");
            out.println("FROM,TO,DEPARTURE,DURATION");
            for (Flight flight : currentScenario.getFlights()) {
                out.println(flight.toCsv());
            }
        } catch (IOException e) {
            throw new ScenarioException("Could not save CSV file");
        }
    }

    public void saveJsonScenario(String path) {
        System.out.println("Hehe4");
    }

    public List<Airport> getAirports() {
        return currentScenario.getAirports();
    }

    public List<Flight> getFlights() {
        return currentScenario.getFlights();
    }

    public void setCurrentScenario(Scenario currentScenario) {
        this.currentScenario = currentScenario;
    }
}
