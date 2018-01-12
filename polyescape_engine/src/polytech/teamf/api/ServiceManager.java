package polytech.teamf.api;

import polytech.teamf.game_engine.Runner;

public class ServiceManager {

    private static Runner runner = null;

    public static Runner getRunnerInstance(String config) {
        if (runner == null) {
            runner = new Runner(config);
        }
        return runner;
    }



}
