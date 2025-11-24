package data_access;

import entity.FlightStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TrackFlightStatusDataAccess implements TrackFlightStatusDataAccessInterface {

    @Override
    public FlightStatus getStatusByFlightNumber(String flightNumber) {

        // 统一大写 + 去掉空格，跟 ViewActive 一样的清洗方式
        String target = flightNumber.toUpperCase().trim().replaceAll("\\s+", "");

        try {
            String token = OpenSkyTokenProvider.getAccessToken();
            if (token == null) {
                System.out.println("ERROR: OAuth token is null");
                return null;
            }

            URL url = new URL("https://opensky-network.org/api/states/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Authenticated OpenSky error: " + responseCode);
                return null;
            }

            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(stringBuilder.toString());
            JSONArray states = json.getJSONArray("states");

            // 遍历所有航班，只要找到一个 callsign 匹配就 return
            for (int i = 0; i < states.length(); i++) {
                JSONArray flightData = states.getJSONArray(i);

                // index 1 是 callsign
                String callSign = flightData.isNull(1) ? null : flightData.optString(1).trim();
                if (callSign == null || callSign.isEmpty()) continue;

                String cleanCall = callSign.toUpperCase().replaceAll("\\s+", "");

                if (!cleanCall.equals(target)) {
                    continue; // 不是这班航班，跳过
                }

                // 找到了对应航班，取经纬度、高度、速度、时间等
                double longitude = flightData.optDouble(5, 0.0);
                double latitude = flightData.optDouble(6, 0.0);
                double speed = flightData.optDouble(9, -1);    // velocity
                double altitude = flightData.optDouble(13, -1); // geo_altitude
                long lastUpdateTime = flightData.optLong(4, -1); // last_contact

                // 这里的 FlightStatus 构造器按照你自己的类来改
                // 下面只是一个示例：假设 FlightStatus 有这些字段：
                // (String flightNumber, double latitude, double longitude,
                //  double altitude, double speed, long lastUpdateTime)

                FlightStatus status = new FlightStatus(
                        cleanCall,
                        latitude,
                        longitude,
                        altitude,
                        speed,
                        lastUpdateTime
                );

                return status; // 找到就直接返回
            }

        } catch (Exception e) {
            System.out.println("Error calling authenticated OpenSky API: " + e.getMessage());
        }

        // 没找到匹配的 callsign，返回 null
        return null;
    }
}
