package data_access;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OpenSkyTokenProvider {

    private static final String CLIENT_ID = "macqueenpzt-api-client";
    private static final String CLIENT_SECRET = "QirHHQUuqMaGVQxbxlTw2GLQJjkOZQso";

    private static String cachedToken = null;
    private static long tokenExpiryTime = 0;

    public static String getAccessToken() {
        try {
            long now = System.currentTimeMillis();

            if (cachedToken != null && now < tokenExpiryTime) {
                return cachedToken;
            }

            String urlParameters =
                    "grant_type=client_credentials" +
                            "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
                            "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8");

            URL url = new URL
                    ("https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlParameters.getBytes());
            }

            StringBuilder response = new StringBuilder();
            try (var scanner = new java.util.Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
            }

            JSONObject json = new JSONObject(response.toString());

            cachedToken = json.getString("access_token");
            int expiresIn = json.getInt("expires_in");
            tokenExpiryTime = now + (expiresIn * 1000L);

            return cachedToken;

        } catch (Exception e) {
            System.out.println("Failed to get OAuth token: " + e.getMessage());
            return null;
        }
    }
}

