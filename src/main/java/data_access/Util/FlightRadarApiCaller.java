package data_access.Util;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class FlightRadarApiCaller {
    private final HttpClient client;
    private final String token;

    private static final String BASE_URL =
            "https://fr24api.flightradar24.com/api/live/flight-positions/full?callsigns=";

    public FlightRadarApiCaller() {
        this.client = HttpClient.newHttpClient();
        this.token = FileTokenGetter.getToken("Frapi_token");
    }

    public ArrayList<String> getFlightByCallsign(String callsign) throws IOException, InterruptedException {
        if ( callsign == null || callsign.isEmpty() ) {
            return null;
        }

        String url = BASE_URL + callsign;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("User-Agent", "Java FR24 Client")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Flight Radar 24 API error: " + response.statusCode() +
                    " Callsign: " + callsign +
                    " Body: " + response.body());
        }

        String body = response.body();
        return parseFlightInfo(body, callsign);
    }

    private ArrayList<String> parseFlightInfo(String body, String queryCallsign) {
        try {
            JSONObject root = new JSONObject(body);
            JSONArray dataArray = root.optJSONArray("data");
            if (dataArray == null || dataArray.isEmpty()) {
                System.err.println("No 'data' array in response for " + queryCallsign);
                return null;
            }
            JSONObject obj = dataArray.getJSONObject(0);

            String flight    = obj.optString("flight", "");
            String callsign  = obj.optString("callsign", queryCallsign);
            String origIata  = obj.optString("orig_iata", "");
            String destIata  = obj.optString("dest_iata", "");
            String origIcao  = obj.optString("orig_icao", "");
            String destIcao  = obj.optString("dest_icao", "");

            ArrayList<String> result = new ArrayList<>();
            result.add(flight);    // index 0
            result.add(callsign);  // index 1
            result.add(origIata);  // index 2
            result.add(destIata);  // index 3
            result.add(origIcao);  // index 4
            result.add(destIcao);  // index 5

            return result;

        }
        catch (Exception e)
        {
            System.err.println("Failed to parse FlightRadar response for " + queryCallsign + ": " + e.getMessage());
            return null;
        }
    }
}