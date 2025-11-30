package data_access.Util;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;

public class FileTokenGetter {
    public static String getToken(String theKey){
        try {
            // Load config.json from resources
            InputStream stream = FileTokenGetter.class.getClassLoader()
                    .getResourceAsStream("secret.json");

            if (stream == null) {
                throw new RuntimeException("secret.json not found in resources/");
            }

            // Parse using the org.json library
            JSONTokener tokener = new JSONTokener(stream);
            JSONObject json = new JSONObject(tokener);

            // Extract the token (key contains a space)
            return json.getString(theKey);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load token from config.json", e);
        }
    }
    }
}
