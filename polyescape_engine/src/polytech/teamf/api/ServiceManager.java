package polytech.teamf.api;

import org.json.JSONObject;
import polytech.teamf.game_engine.Runner;

import java.util.HashMap;
import java.util.Map;

/**
 * Service handles everything that every others services might need.
 */
public class ServiceManager {

    public static Map<String, Runner> runnersInstances = new HashMap<>();

    private static JSONObject lastResult = new JSONObject();

    /**
     * Create a new unique runner instance.
     *
     * @param config The plugins configurations.
     */
    public static void createNewInstance(String uuid, String config) {
        Runner runner = new Runner(config);
        runnersInstances.put(uuid, runner);
    }

    /**
     * Get the last player's answer result.
     */
    public static JSONObject getLastResult() {
        return lastResult;
    }

    public static void setLastResult(JSONObject newLastResult) {
        lastResult = newLastResult;
    }

}
