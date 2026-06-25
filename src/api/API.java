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
