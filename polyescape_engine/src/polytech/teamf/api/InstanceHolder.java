package polytech.teamf.api;

import polytech.teamf.gameengine.Runner;
import polytech.teamf.resources.PluginInstantiationResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds all instances of the runner
 */
public class InstanceHolder {

    private Map<String, Runner> runnersInstances = new HashMap<>();

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
     * @param config The plugins configuration for the new instance
     */
    public void createNewInstance(String uuid, List<PluginInstantiationResource> config) {
        Runner runner = new Runner(config);
        runnersInstances.put(uuid, runner);
    }

    /**
     * Clear the runner instances.
     */
    public void clear() {
        runnersInstances.clear();
    }

    public int getRunnerInstanceNumber() {
        return runnersInstances.size();
    }

    /**
     * Get a runner instance given its unique id.
     *
     * @param id the unique id of a runner instance
     * @return a runner instance
     */
    public Runner getRunnerInstance(String id) {
        return runnersInstances.get(id);
    }

}
