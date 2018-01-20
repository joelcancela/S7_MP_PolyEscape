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
	 * Constructeur privé
	 */
	private InstanceHolder() {
	}

	/**
	 * Instance unique pré-initialisée
	 */
	private static InstanceHolder INSTANCE = new InstanceHolder();

	/**
	 * Point d'accès pour l'instance unique du singleton
	 */
	public static InstanceHolder getInstance() {
		return INSTANCE;
	}

	/**
	 * Create a new unique runner instance.
	 *
	 * @param config The plugins configurations.
	 */
	public static void createNewInstance(String uuid, Map<String,Object> config) {
		Runner runner = new Runner(config);
		runnersInstances.put(uuid, runner);
	}


	public static Runner getRunnerInstance(String id) {
		return runnersInstances.get(id);
	}

}
