package entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Airport {
    private String country;
    private String city;
    private final String IATA;
    private final Set<Airport> connections = new HashSet<>();

    private static final Map<String, Airport> REGISTRY = new ConcurrentHashMap<>();

    //Constructors section:
    public Airport(String iata) {
        this.IATA = iata.toUpperCase();
        this.country = "";
        this.city = "";
    }

    public Airport(String iata, String city, String country) {
        if (iata == null || iata.isEmpty()) {
            throw new IllegalArgumentException("IATA code cannot be null or empty");
        }
        this.IATA = iata.toUpperCase();
        this.city = city;
        this.country = country;
    }

    //Getters
    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIATA() {
        return IATA;
    }

    public Set<Airport> getConnections() {
        return Collections.unmodifiableSet(connections);
    }

    public void setCountry(String country) { this.country = country;}

    public void setCity(String city) { this.city = city;}

    //Operational methods:
    public void addconnectTo(Airport other) {
        if (other == null || other == this) return;
        this.connections.add(other);
        other.connections.add(this); // treat as undirected connection
    }

    public boolean isConnectedTo(Airport landingairport) {
        if (landingairport == null) return false;
        return connections.contains(landingairport);
    }

    //overrides for default methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(IATA, airport.IATA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IATA);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (!country.isEmpty()) {
            sb.append(country);
        }
        if (!city.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(city);
        }
        if (sb.length() > 0) sb.append(", ");
        sb.append(IATA);
        return sb.toString();
    }
}
