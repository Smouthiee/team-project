package interface_adapter.AirportDataRS;

import use_case.AirportDataRouteSearch.AirportDataRouteSearchOutputBoundary;
import use_case.AirportDataRouteSearch.AirportDataRouteSearchOutputData;
import view.AirportDataRouteSearchView;

public class AirportDataRSPresenter implements AirportDataRouteSearchOutputBoundary {
    private final AirportDataRouteSearchView view;

    public AirportDataRSPresenter(AirportDataRouteSearchView view){
        this.view = view;
    }

    @Override
    public void present(AirportDataRouteSearchOutputData OutputData){
        view.showAirport(OutputData);
    }
}
