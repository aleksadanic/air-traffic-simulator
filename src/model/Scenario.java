package model;

import exceptions.ScenarioException;

import java.util.ArrayList;
import java.util.List;

// Responsible for creating airports and flights, as well as maintaining the whole context consistent
public class Scenario {
    private List<Airport> airports = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();

    public void addAirport(Airport airport) {
        airports.add(airport);
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public Airport createAirport(String code, String name, String X, String Y) {
        // code check
        if (code.length() != 3) {
            throw new ScenarioException("Airport code needs to be 3-letter");
        }
        for (int i = 0; i < 3; i++) {
            if (!Character.isUpperCase(code.charAt(i))) {
                throw new ScenarioException("Airport code needs to have all capital letters");
            }
        }

        if (getAirportByCode(code) != null) {
            throw new ScenarioException("3-letter code needs to be unique");
        }

        // name check
        if (name.isEmpty()) {
            throw new ScenarioException("Name can't be empty");
        }

        // coordinates check
        int X_int, Y_int;
        try {
            X_int = Integer.parseInt(X);
            Y_int = Integer.parseInt(Y);
            if (X_int < -180 || X_int > 180) {
                throw new ScenarioException("X coordinate needs to be in range [-180,180]");
            }
            if (Y_int < -90 || Y_int > 90) {
                throw new ScenarioException("Y coordinate needs to be in range [-90,90]");
            }
        } catch (NumberFormatException e) {
            throw new ScenarioException("Coordinates need to be integers");
        }

        return new Airport(code, name, X_int, Y_int);
    }

    public Flight createFlight(String fromCode, String toCode, String departureTime, String duration) {
        // codes check
        Airport from = getAirportByCode(fromCode);
        if (from == null) {
            throw new ScenarioException("From 3-letter code not found");
        }
        Airport to = getAirportByCode(toCode);
        if (to == null) {
            throw new ScenarioException("To 3-letter code not found");
        }
        if (from == to) {
            throw new ScenarioException("From and to airports must be different");
        }

        // departure check
        if (departureTime.length() != 5 || departureTime.charAt(2) != ':') {
            throw new ScenarioException("Departure time needs to be in hh:mm format");
        }
        int departure;
        try {
            int hh = Integer.parseInt(departureTime.substring(0, 2));
            int mm = Integer.parseInt(departureTime.substring(3, 5));
            if (hh < 0 || hh >= 24 || mm < 0 || mm >= 60) {
                throw new ScenarioException("Time invalid");
            }
            departure = 60 * hh + mm;
        } catch (NumberFormatException e) {
            throw new ScenarioException("Time invalid");
        }

        // duration check
        int duration_int;
        try {
            duration_int = Integer.parseInt(duration);
            if (duration_int <= 0) {
                throw new ScenarioException("Duration must be positive");
            }
        } catch (NumberFormatException e) {
            throw new ScenarioException("Duration needs to be an integer");
        }

        return new Flight(from, to, departure, duration_int);
    }

    private Airport getAirportByCode(String code) {
        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                return airport;
            }
        }
        return null;
    }

    public String airportsToCsv() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Airport airport : airports) {
            if (!first) {
                sb.append("\n");
            } else {
                first = false;
            }
            sb.append(airport.toCsv());
        }
        return sb.toString();
    }

    public String airportsToJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"airports\":[\n");
        boolean first = true;
        for (Airport airport : airports) {
            if (!first) {
                sb.append(",\n");
            } else {
                first = false;
            }
            sb.append(airport.toJson());
        }
        sb.append("\n]");
        return sb.toString();
    }

    public String flightsToCsv() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Flight flight : flights) {
            if (!first) {
                sb.append("\n");
            } else {
                first = false;
            }
            sb.append(flight.toCsv());
        }
        return sb.toString();
    }

    public String flightsToJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"flights\":[\n");
        boolean first = true;
        for (Flight flight : flights) {
            if (!first) {
                sb.append(",\n");
            } else {
                first = false;
            }
            sb.append(flight.toJson());
        }
        sb.append("\n]");
        return sb.toString();
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
