package polytech.teamf.api;

import org.json.JSONArray;
import org.json.JSONObject;
import polytech.teamf.plugins.Plugin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Path("/plugins")
public class PluginServices {

    /**
     * @api {get} /plugins/list The list of available plugins
     * @apiName PluginsList
     * @apiGroup Plugins
     * @apiVersion 0.1.0
     *
     * @apiSuccess (200) {String} plugins The plugins list
     *
     * @apiSuccessExample Example data on success
     * [{
     *     "type": "polytech.teamf.plugins.CaesarCipherPlugin",
     *     "args":
     *     [
     *     "description",
     *     "plain_text",
     *     "cipher_padding"
     *     ]
     * }]
     */

    /**
     * Get all the classes which inherits the Plugin class and return their full name and the list of args needed to use them.
     *
     * @return A string obtained from a JSONArray filled with the full class names.
     * @throws IOException            See {@link #getClasses(String)}.
     * @throws ClassNotFoundException See {@link #findClasses(File, String)}.
     */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllStepsType() throws IOException, ClassNotFoundException {

        Class[] classArray = getClasses("polytech.teamf.plugins");

        JSONArray jsonPlugins = new JSONArray();

        for (Class c : classArray) {

            JSONObject jsonPlugin = new JSONObject();
            JSONArray jsonPluginArgs = new JSONArray();
            JSONObject jsonPluginSchema = new JSONObject();

            if (Plugin.class.isAssignableFrom(c) && c != Plugin.class) {

                jsonPlugin.put("type", c.getSimpleName());
                jsonPlugin.put("args", jsonPluginArgs);
                jsonPlugin.put("schema", jsonPluginSchema);
                //TODO Handle Service args?

                jsonPlugins.put(jsonPlugin);
            }
        }

        return jsonPlugins.toString();
    }

    /**
     * @api {get} /plugins/{id}/description The current played plugin description on the runner with id {id}
     * @apiName PluginsDescription
     * @apiGroup Plugins
     * @apiVersion 0.1.0
     *
     * @apiSuccess (200) {String} description The plugin description
     *
     * @apiSuccessExample Example data on success
     * {
     *     "description": "Plugin description"
     * }
     */

    /**
     * Get the current played plugin description.
     *
     * @return A string obtained from a JSONObject filled with the current played plugin description.
     */
    @GET
    @Path("/{id}/description")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPluginDescription(@PathParam("id") String id) {
        return ServiceManager.runnersInstances.get(id).getDescription().toString();
    }

    /**
     * @api {get} /plugins/{id}/status The current played plugin status
     * @apiName PluginsStatus
     * @apiGroup Plugins
     * @apiVersion 0.1.0
     *
     * @apiSuccess (200) {String} status The plugin status
     *
     * @apiSuccessExample Example data on success
     * {
     *     "status": "true"
     * }
     */

    /**
     * Get the current played plugin status
     *
     * @return The current played plugin status. The status is about the plugin's resolution.
     * It can be true or false.
     */
    @GET
    @Path("/{id}/status")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatus(@PathParam("id") String id) {
        return new JSONObject().put("status", ServiceManager.runnersInstances.get(id).getStatus()).toString();
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package.
     * @return The classes found in the package.
     * @throws ClassNotFoundException See {@link #findClasses(File, String)}.
     * @throws IOException            Triggered if the classLoader cant get the resource given the package name.
     */
    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory.
     * @param packageName The package name for classes found inside the base directory.
     * @return The classes found in the package contained in the directory.
     * @throws ClassNotFoundException Triggered if no class has been found in a package.
     */
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}