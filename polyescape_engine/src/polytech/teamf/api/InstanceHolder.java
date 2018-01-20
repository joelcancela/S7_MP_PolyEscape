package polytech.teamf.api;

import polytech.teamf.gameengine.Runner;

import java.util.HashMap;
import java.util.Map;

/**
 * Service handles everything that every others services might need.
 */
public class InstanceHolder {

    public static Map<String, Runner> runnersInstances = new HashMap<>();

    /**
     * Private constructor.
     */
    private InstanceHolder() {
    }

    /**
     * Pre-initialized unique instance.
     */
    private static InstanceHolder INSTANCE = new InstanceHolder();

    /**
     * Entry point for the unique instance of the singleton.
     */
    public static InstanceHolder getInstance() {
        return INSTANCE;
    }

    /**
     * Create a new unique runner instance.
     *
     * @param config The plugins configuration.
     */
    public static void createNewInstance(String uuid, Map<String, Object> config) {
        Runner runner = new Runner(config);
        runnersInstances.put(uuid, runner);
    }

    /**
     * Get a runner instance given its unique id.
     *
     * @param id the unique id of a runner instance
     * @return a runner instance
     */
    public static Runner getRunnerInstance(String id) {
        return runnersInstances.get(id);
    }

}
