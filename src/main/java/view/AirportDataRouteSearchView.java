package view;

import entity.Airport;
import interface_adapter.AirportDataRS.AirportDataRSController;
import use_case.AirportDataRouteSearch.AirportDataRouteSearchOutputData;

public class AirportDataRouteSearchView {
    private final AirportDataRSController controller;

    public AirportDataRouteSearchView(AirportDataRSController controller){
        this.controller = controller;
    }

    public void search(String takeoffCode, String landingCode) {
        controller.searchRoute(takeoffCode, landingCode);
    }

    public void showResult(AirportDataRouteSearchOutputData data) {
        System.out.println(data.getMessage());

        Airport takeOff = data.getTakeOffAirport();
        Airport landing = data.getLandingAirport();

        if (takeOff != null) {
            System.out.println("Takeoff airport: " + takeOff);
        }
        if (landing != null) {
            System.out.println("Landing airport: " + landing);
        }
    }
}
