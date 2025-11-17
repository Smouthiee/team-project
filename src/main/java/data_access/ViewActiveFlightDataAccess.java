package data_access;

import entity.AirlineFlight;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.ViewActiveFlights.ViewActiveFlightsDataAccessInterface;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewActiveFlightDataAccess implements ViewActiveFlightsDataAccessInterface {

    @Override
    public List<AirlineFlight> getActiveFlightsByAirline(String airlinePrefix) {

        List<AirlineFlight> flights = new ArrayList<>();

        try {
            String token = OpenSkyTokenProvider.getAccessToken();
            if (token == null) {
                System.out.println("ERROR: OAuth token is null");
                return flights;
            }

            URL url = new URL("https://opensky-network.org/api/states/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Authenticated OpenSky error: " + responseCode);
                return flights;
            }

            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(stringBuilder.toString());
            JSONArray states = json.getJSONArray("states");

            String prefix = airlinePrefix.toUpperCase().trim();

            for (int i = 0; i < states.length(); i++) {

                JSONArray flightData = states.getJSONArray(i);
                String callSign = flightData.isNull(1) ? null : flightData.optString(1).trim();

                if (callSign == null || callSign.isEmpty()) continue;

                String cleanCall = callSign.toUpperCase().replaceAll("\\s+", "");

                if (!cleanCall.startsWith(prefix)) continue;

                double longitude = flightData.optDouble(5, 0.0);
                double latitude = flightData.optDouble(6, 0.0);
                double speed = flightData.optDouble(9, -1);
                double altitude = flightData.optDouble(13, -1);

                flights.add(new AirlineFlight(cleanCall, latitude, longitude, altitude, speed));

                if (flights.size() >= 10) break;
            }

        } catch (Exception e) {
            System.out.println("Error calling authenticated OpenSky API: " + e.getMessage());
        }

        return flights;
    }
}

