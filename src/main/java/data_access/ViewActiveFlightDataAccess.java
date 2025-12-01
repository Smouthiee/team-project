package data_access;

import entity.AirlineFlight;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.ViewActiveFlights.ViewActiveFlightsDataAccessInterface;

import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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

            String jsonText;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine());
                }

                jsonText = stringBuilder.toString();
                saveCache(jsonText, airlinePrefix);
            } catch (Exception e) {
                System.out.println("API failed. Falling back to cache...");
                jsonText = loadCache();
            }
            if (jsonText == null) {
                throw new RuntimeException("No API or cached data available.");
            }

            JSONObject object = new JSONObject(jsonText);
            JSONArray states = object.getJSONArray("states");

            String prefix = airlinePrefix.toUpperCase().trim();

            for (int i = 0; i < states.length(); i++) {

                JSONArray flightData = states.getJSONArray(i);

                String callSign = flightData.isNull(1) ? null : flightData.optString(1).trim();

                if (callSign == null || callSign.isEmpty()) continue;

                String cleanCall = callSign.toUpperCase().replaceAll("\\s+", "");
                if (!cleanCall.startsWith(prefix)) continue;

                String registeredCountry = flightData.optString(2, "Unknown");
                double longitude = flightData.optDouble(5, 0.0);
                double latitude = flightData.optDouble(6, 0.0);
                double speed = flightData.optDouble(9, -1);
                double altitude = flightData.optDouble(13, -1);
                long lastUpdateTime = flightData.optLong(4, -1);
                flights.add(new AirlineFlight(cleanCall, registeredCountry, latitude, longitude, altitude,
                        speed,  lastUpdateTime));
            }

        } catch (Exception e) {
            System.out.println("Error calling authenticated OpenSky API: " + e.getMessage());
        }

        return flights;
    }

    private void saveCache(String jsonText, String prefix) {
        try {

            JSONObject root;
            // If cache exists, load old file first
            if (Files.exists(Paths.get("src/OpenSky_cache.json"))) {
                String existing = Files.readString(Paths.get("src/OpenSky_cache.json"));
                root = new JSONObject(existing);
            } else {
                root = new JSONObject();
            }
            // Read previous search count (if any)
            int oldCount = root.optInt("searchCount", 0);

            // Update data
            root.put("searchCount", oldCount + 1);
            root.put("lastPrefixSearched", prefix);
            root.put("lastUpdated", System.currentTimeMillis() / 1000);

            root.put("cachedFlights", new JSONObject(jsonText));

            // Write formatted JSON
            try (FileWriter writer = new FileWriter("src/OpenSky_cache.json")) {
                writer.write(root.toString(2));   // pretty print
                writer.flush();
                System.out.println("Cache updated: JSON data + API data saved.");
            }

        } catch (Exception e) {
            System.out.println("Cache save failed: " + e.getMessage());
        }
    }

    private String loadCache() {

        try {
            if (!Files.exists(Paths.get("src/OpenSky_cache.json"))) {
                System.out.println("No cache file found.");
                return null;
            }

            String content = Files.readString(Paths.get("src/OpenSky_cache.json"));
            JSONObject root = new JSONObject(content);

            int searchCount = root.optInt("searchCount", 0);
            String prefix = root.optString("lastPrefixSearched", "N/A");
            long lastUpdated = root.optLong("lastUpdated", 0);

            System.out.println("Loaded cache metadata:");
            System.out.println("Search count: " + searchCount);
            System.out.println("Last prefix: " + prefix);
            System.out.println("Last updated: " + lastUpdated);

            JSONObject cachedFlights = root.getJSONObject("cachedFlights");
            return cachedFlights.toString();

        } catch (Exception e) {
            System.out.println("Cache read failed: " + e.getMessage());
            return null;
        }
    }

}
