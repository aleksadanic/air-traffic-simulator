package api;

import exceptions.ScenarioException;
import model.Airport;
import model.Flight;
import model.Scenario;

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
        System.out.println("Hehe1");
    }

    public void loadJsonScenario(String path) {
        System.out.println("Hehe2");
    }

    public void saveCsvScenario(String path) {
        System.out.println("Hehe3");
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
