package use_case.Util;

/*
1. Read opensky cache and output to callsign
2. Call FlightRadar api to resolve the callsigns
3. Update airport graph
 */

import data_access.AirportStatsDataAccessObject;
import data_access.Util.OpenSkyCallSignSnapShotService;
import data_access.Util.FlightRadarApiCaller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OtoABridgeJob {

    private final OpenSkyCallSignSnapShotService callsignSnapShotService;
    private final FlightRadarApiCaller flightRadarApiCaller;
    private final Path datadir = Paths.get("data");

    public OtoABridgeJob(){
        this.callsignSnapShotService = new OpenSkyCallSignSnapShotService();
        this.flightRadarApiCaller = new FlightRadarApiCaller();
    }

    public void Run(){
        try{
            OpenSkyCallSignSnapShotService.CallsignSnapshotResult snapshot =
                    callsignSnapShotService.snapshotCallsigns();

            List<String> callsigns = snapshot.getCallsigns();
            long epoch = snapshot.getSnapshotEpochSeconds();
            Path snapshotFile = snapshot.getOutputFile();

            System.out.println("OpenSky snapshot time (epoch): " + epoch);
            System.out.println("Callsigns snapshot file: " + snapshotFile);
            System.out.println("Number of callsigns in snapshot: " + callsigns.size());

            if (callsigns.isEmpty()) {
                System.out.println("No callsigns to process, aborting job.");
                return;
            }
            AirportStatsDataAccessObject dao =
                    new AirportStatsDataAccessObject(datadir);

            dao.GraphEnrichmentByCallSign(callsigns,flightRadarApiCaller );
            dao.offloadSnapshot();
            System.out.println("OpenSkyToAirportJob completed successfully.");

        } catch (IOException e) {
            System.err.println("IO error during OpenSkyToAirportJob: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during OpenSkyToAirportJob: " + e.getMessage());
        }
    }

}
