package data_access;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OpenskyAirportDataAccessObject {
    private static final String TokenGetterURL = "https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token";

    private final String clientId;
    private final String clientSecret;
    private final HttpClient httpClient;

    public OpenskyAirportDataAccessObject(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.httpClient = HttpClient.newHttpClient();
    }

    public static OpenskyAirportDataAccessObject ClientNew(String clientId, String clientSecret) {
        return new OpenskyAirportDataAccessObject(clientId, clientSecret);
    }

    public String GetAccessToken() throws IOException, InterruptedException {
        String form = "grant_type=" + encode("client_credentials") +
                "&client_id=" + encode(clientId) +
                "&client_secret=" +encode(clientSecret);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TokenGetterURL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get token. HTTP " + response.statusCode() +
                    " body: " + response.body());
        }

        String body = response.body();
        // Response is JSON; we just need "access_token":"..."
        String token = extractAccessToken(body);
        if (token == null) {
            throw new IOException("access_token not found in response: " + body);
        }
        return token;

    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
    private static String extractAccessToken(String json) {
        String key = "\"access_token\"";
        int keyIndex = json.indexOf(key);
        if (keyIndex < 0) return null;

        int colonIndex = json.indexOf(':', keyIndex);
        if (colonIndex < 0) return null;

        // Find the first quote after the colon
        int firstQuote = json.indexOf('"', colonIndex + 1);
        if (firstQuote < 0) return null;

        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote < 0) return null;

        return json.substring(firstQuote + 1, secondQuote);
    }

    public static void main(String[] args) {
        try {
            OpenskyAirportDataAccessObject client = OpenskyAirportDataAccessObject.ClientNew("2much4sky-api-client", "YOFr13tuspj7xtJzzA66VahpPtbT1BBX");
            String token = client.GetAccessToken();
            System.out.println("Access token: " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
