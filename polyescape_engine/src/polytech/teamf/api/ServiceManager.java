package polytech.teamf.api;

import polytech.teamf.game_engine.Runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Service handles everything that every others services might need.
 */
public class ServiceManager {

    private static ServiceServices services = new ServiceServices();

    public static Map<String, Runner> runnersInstances = new HashMap<>();

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
     * Send a message to a specific service and retrieve the service's answer.
     *
     * @param method The method name which correspond to the service to call.
     *               The method should be find in {@link ServiceServices} class.
     * @param args   The args.
     * @return The service call response.
     * @throws NoSuchMethodException     Triggers if the method doesn't exists.
     * @throws InvocationTargetException Triggers if the object service isn't valid.
     * @throws IllegalAccessException    Triggers if the method to invoke is unreachable.
     */
    public static String sendMessage(String method, Object... args) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Method methodToCall = ServiceServices.class.getMethod(method, Object[].class);
        return (String) methodToCall.invoke(services, new Object[]{args});
    }

}
