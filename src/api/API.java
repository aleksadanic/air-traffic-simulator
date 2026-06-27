package api;

import exceptions.ScenarioException;
import model.data.Airport;
import model.data.Flight;
import model.data.Scenario;
import model.simulation.Airplane;
import model.simulation.SimulationEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class API {
    private Scenario currentScenario;
    private SimulationEngine currentSimulationEngine = null;

    public void startSimulation() {
        currentSimulationEngine = new SimulationEngine(currentScenario);
        new Thread(currentSimulationEngine).start();
    }

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
        Scenario newScenario = new Scenario();

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        } catch (IOException e) {
            throw new ScenarioException("Could not read JSON file");
        }

        String all = sb.toString();

        boolean readingAirports = false;
        boolean readingFlights = false;

        int balance = 0;
        int leftBound = 0;

        for (int i = 0; i < all.length(); i++) {
            if (all.startsWith("\"airports\"", i)) {
                readingAirports = true;
                readingFlights = false;
            }
            if (all.startsWith("\"flights\"", i)) {
                readingAirports = false;
                readingFlights = true;
            }
            char c = all.charAt(i);
            if (c == '{' || c == '[' || c == '(') {
                balance++;
                if (balance == 3) {
                    leftBound = i + 1;
                }
            } else if (c == '}' || c == ']' || c == ')') {
                if (balance == 3) {
                    String description = all.substring(leftBound, i);
                    if (readingAirports) {
                        String[] data = description.split(",");
                        if (data.length != 4) {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        for (int j = 0; j < 4; j++) {
                            if (data[j].split(":").length != 2) {
                                throw new ScenarioException("Invalid JSON format");
                            }
                            data[j] = data[j].split(":")[1];
                        }
                        if (data[0].charAt(0) != '"' || data[0].charAt(data[0].length() - 1) != '"') {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        if (data[1].charAt(0) != '"' || data[1].charAt(data[1].length() - 1) != '"') {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        data[0] = data[0].substring(1, data[0].length() - 1);
                        data[1] = data[1].substring(1, data[1].length() - 1);

                        newScenario.addAirport(newScenario.createAirport(data[0], data[1], data[2], data[3]));
                    } else if (readingFlights) {
                        String[] data = description.split(",");
                        if (data.length != 4) {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        for (int j = 0; j < 4; j++) {
                            if (data[j].split(":", 2).length != 2) {
                                throw new ScenarioException("Invalid JSON format");
                            }
                            data[j] = data[j].split(":", 2)[1];
                        }
                        if (data[0].charAt(0) != '"' || data[0].charAt(data[0].length() - 1) != '"') {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        if (data[1].charAt(0) != '"' || data[1].charAt(data[1].length() - 1) != '"') {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        if (data[2].charAt(0) != '"' || data[2].charAt(data[2].length() - 1) != '"') {
                            throw new ScenarioException("Invalid JSON format");
                        }
                        data[0] = data[0].substring(1, data[0].length() - 1);
                        data[1] = data[1].substring(1, data[1].length() - 1);
                        data[2] = data[2].substring(1, data[2].length() - 1);
                        newScenario.addFlight(newScenario.createFlight(data[0], data[1], data[2], data[3]));
                    } else {
                        throw new ScenarioException("Invalid JSON format");
                    }
                }
                balance--;
            }
        }

        currentScenario = newScenario;
    }

    public void saveCsvScenario(String path) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("# AIRPORTS");
            out.println("CODE,NAME,X,Y");
            out.println(currentScenario.airportsToCsv());

            out.println("# FLIGHTS");
            out.println("FROM,TO,DEPARTURE,DURATION");
            out.print(currentScenario.flightsToCsv());
        } catch (IOException e) {
            throw new ScenarioException("Could not save CSV file");
        }
    }

    public void saveJsonScenario(String path) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("{");
            out.println(currentScenario.airportsToJson() + ",");
            out.println(currentScenario.flightsToJson());
            out.print("}");
        } catch (IOException e) {
            throw new ScenarioException("Could not save CSV file");
        }
    }

    public List<Airport> getAirports() {
        return currentScenario.getAirports();
    }

    public List<Flight> getFlights() {
        return currentScenario.getFlights();
    }

    public List<Airplane> getAirplanes() {
        if (currentSimulationEngine == null) {
            return new ArrayList<>();
        }
        return currentSimulationEngine.getAirplanes();
    }

    public void setCurrentScenario(Scenario currentScenario) {
        this.currentScenario = currentScenario;
    }

    public void setCurrentSimulationEngine(SimulationEngine currentSimulationEngine) {
        this.currentSimulationEngine = currentSimulationEngine;
    }
}
