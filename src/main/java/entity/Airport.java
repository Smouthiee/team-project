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
    public static Airport getOrCreate(String iata) {
        if (iata == null || iata.isBlank()) {
            throw new IllegalArgumentException("Airport code must not be null or blank");
        }

        return REGISTRY.computeIfAbsent(iata, c -> new Airport(c, null,  null));
    }
    public static Airport getOrCreate(String iata, String city, String country) {
        if (iata == null || iata.isBlank()) {
            throw new IllegalArgumentException("Airport code must not be null or blank");
        }
        return REGISTRY.computeIfAbsent(iata, c -> new Airport(c, city, country))
                .updateMetadata(city, country);
    }

    private Airport updateMetadata( String city, String country) {
        if (this.city == null && city != null) this.city = city;
        if (this.country == null && country != null) this.country = country;
        return this;
    }

    public static Airport find(String iata) {
        if (iata == null) return null;
        return REGISTRY.get(iata);
    }

    public static Airport find(String city, String country) {
        if (city == null) return null;
        else return REGISTRY.get(city + "-" + country);
    }

    private Airport(String iata, String city, String country) {
        this.IATA = iata;
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

    //Operational methods:
    public void connectTo(Airport other) {
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
        return country + "," + city + "," + IATA;
    }
}
