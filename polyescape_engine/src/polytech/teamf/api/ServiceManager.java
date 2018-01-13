package polytech.teamf.api;

import polytech.teamf.game_engine.Runner;

/**
 * Service handles everything that every others services might need.
 */
public class ServiceManager {

    private static Runner runner = null;

    private ServiceManager() {}

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

}
