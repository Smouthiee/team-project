package view;

import interface_adapter.AirportDataRS.AirportDataRSController;
import entity.Airport;

public class AirportDataRouteSearchView {
    private final AirportDataRSController controller;

    public AirportDataRouteSearchView(AirportDataRSController controller){
        this.controller = controller;
    }

    public void showAirport(Airport airport){
        System.out.println(airport.toString());
    }
}
