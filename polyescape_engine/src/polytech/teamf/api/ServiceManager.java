package polytech.teamf.api;

import org.json.JSONObject;
import polytech.teamf.game_engine.Runner;

/**
 * Service handles everything that every others services might need.
 */
public class ServiceManager {

    private static Runner runner = null;

    private static JSONObject lastResult = new JSONObject();

    private ServiceManager() {
    }

    /**
     * Get the unique runner instance.
     *
     * @param config The plugins configurations.
     * @return The unique runner instance.
     */
    public static Runner getRunnerInstance(String config) {
        if (runner == null) {
            runner = new Runner(config);
        }
        return runner;
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
