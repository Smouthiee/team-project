package data_access.Util;
/*
Note to self: call the extractor, get file data, get callsigns and make new json offload to data/output
 */
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.Instant;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OpenSkyCallSignSnapShotService {
    private final Path openSkyCacheFile = Paths.get("src", "OpenSky_cache.json");
    private final Path callSignOutputDir = Paths.get("data", "output");

    public final class CallsignSnapshotResult {
        private final Path outputFile;
        private final long snapshotEpochSeconds;
        private final List<String> callsigns;

        public CallsignSnapshotResult(
                Path outputFile,
                long snapshotEpochSeconds,
                List<String> callsigns
        ) {
            this.outputFile = outputFile;
            this.snapshotEpochSeconds = snapshotEpochSeconds;
            this.callsigns = callsigns;
        }

        public Path outputFile() {
            return outputFile;
        }

        public long snapshotEpochSeconds() {
            return snapshotEpochSeconds;
        }

        public List<String> callsigns() {
            return callsigns;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (CallsignSnapshotResult) obj;
            return Objects.equals(this.outputFile, that.outputFile) &&
                    this.snapshotEpochSeconds == that.snapshotEpochSeconds &&
                    Objects.equals(this.callsigns, that.callsigns);
        }

        @Override
        public int hashCode() {
            return Objects.hash(outputFile, snapshotEpochSeconds, callsigns);
        }

        @Override
        public String toString() {
            return "CallsignSnapshotResult[" +
                    "outputFile=" + outputFile + ", " +
                    "snapshotEpochSeconds=" + snapshotEpochSeconds + ", " +
                    "callsigns=" + callsigns + ']';
        }
    }
    public CallsignSnapshotResult snapshotCallsigns() throws IOException{
        String rawJSON = Files.readString(openSkyCacheFile, StandardCharsets.UTF_8);
        JSONObject rawRoot = new JSONObject(rawJSON);
        long snapshotEpoch = rawRoot.getLong("lastUpdated");
        JSONArray states = rawRoot.getJSONArray("states");

        Set<String> uniqueCallsigns = new LinkedHashSet<>();
        for (int i = 0; i < states.length(); i++) {
            JSONArray state = states.getJSONArray(i);
            if (state.isNull(1)) {
                continue;
            }
            String callsign = state.getString(1).trim();
            if (!callsign.isEmpty()) {
                uniqueCallsigns.add(callsign);
            }
        }

        List<String> callsignList = new ArrayList<>(uniqueCallsigns);
        JSONObject output = new JSONObject();
        output.put("snapshotEpoch", snapshotEpoch);
        output.put("snapshotIsoUtc", Instant.ofEpochSecond(snapshotEpoch).toString());
        output.put("sourceFile", openSkyCacheFile.toString());
        output.put("callsignCount", callsignList.size());
        output.put("callsigns", new JSONArray(callsignList));
        Files.createDirectories(callSignOutputDir);
        // Actual work:
        String fileName = "callsigns_" + snapshotEpoch + ".json";
        Path outputFile = callSignOutputDir.resolve(fileName);

        Files.writeString(outputFile, output.toString(2), StandardCharsets.UTF_8);
        return new CallsignSnapshotResult(outputFile, snapshotEpoch, callsignList);
    }
}
