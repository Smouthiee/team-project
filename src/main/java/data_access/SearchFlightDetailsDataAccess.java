package data_access;

import entity.Flight;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.SearchFlightDetails.SearchFlightDetailsDataAccessInterface;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchFlightDetailsDataAccess implements SearchFlightDetailsDataAccessInterface {

    private static final String OPENSKY_URL = "https://opensky-network.org/api/states/all";

    @Override
    public Flight getFlightByNumber(String callSign) {

        try {
            String clean = callSign.toUpperCase().replaceAll("\\s+", "");
            URL url = new URL(OPENSKY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder json = new StringBuilder();
            while (scanner.hasNext()) json.append(scanner.nextLine());
            scanner.close();

            JSONObject root = new JSONObject(json.toString());
            JSONArray states = root.getJSONArray("states");

            for (int i = 0; i < states.length(); i++) {

                JSONArray state = states.getJSONArray(i);

                String callSignADS = state.isNull(1) ? "" : state.getString(1).trim().toUpperCase().replaceAll("\\s+", "");
                if (!callSignADS.equals(clean)) continue;

                String originCountry = state.optString(2, "Unknown");
                long timePosition = state.optLong(3, -1);
                String squawk = state.optString(14, "Unknown");
                boolean onGround = state.optBoolean(8, false);

                // Build your Flight entity
                return new Flight(
                        clean,
                        originCountry,
                        timePosition,
                        squawk,
                        onGround
                );
            }

            return null;

        } catch (Exception e) {
            System.out.println("Error contacting OpenSky API: " + e.getMessage());
            return null;
        }
    }
}
