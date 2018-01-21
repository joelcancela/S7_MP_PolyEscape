package polytech.teamf.plugins;

import org.ini4j.Profile;
import org.ini4j.Wini;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@XmlRootElement(name = "metaplugin")
public class MetaPlugin {

    /**
     * Class name.
     */
    @XmlElement(name = "name")
    private String type;

    /**
     * Map of the constructor parameters. The key is the parameter name,
     * and the value is the parameter type.
     */
    @XmlElement(name = "args")
    private Map<Object, Class> constructorArgs = new HashMap<>();

    /**
     * the expected format of the input for the plugin execution.
     */
    @XmlElement(name = "schema")
    private Map<String, Object> schema = new HashMap<>();

    /**
     * Plugin dependencies.
     */
    @XmlElement(name = "pluginDependencies")
    private List<Class> plugins = new ArrayList<>();

    /**
     * Service dependencies.
     */
    @XmlElement(name = "serviceDependencies")
    private List<Class> services = new ArrayList<>();

    public MetaPlugin() {
    }

    private MetaPlugin(
            String type,
            Map<String, Object> args,
            Map<String, Object> schema,
            List<String> plugins,
            List<String> services
    ) throws ClassNotFoundException {

        this.type = type;

        for (Map.Entry e : args.entrySet()) {
            Class t = Class.forName("java.lang." + e.getValue().toString()); // Triggers exception if type not found
            constructorArgs.put(e.getKey(), t);
        }

        Map<String, Object> schemaUpdated = new HashMap<>();
        for (Map.Entry e : schema.entrySet()) {
            Class t = Class.forName("java.lang." + e.getValue().toString()); // Triggers exception if type not found
            schemaUpdated.put((String) e.getKey(), t);
        }

        this.schema = schemaUpdated;

        for (String clazz : plugins) {
            Class t = Class.forName("polytech.teamf.plugins." + clazz);
            this.plugins.add(t);
        }

        for (String clazz : services) {
            Class t = Class.forName("polytech.teamf.services." + clazz);
            this.services.add(t);
        }
    }

    /**
     * INI File Parser.
     *
     * @param iniFile the ini file URL
     * @return the meta plugin obtained from the iniFile parameter
     * @throws IOException triggers if any resource can't be loaded
     */
    public static MetaPlugin parseIniFile(URL iniFile) throws IOException {
        String type = "";
        Map<String, Object> args = new HashMap<>();
        Map<String, Object> schema = new HashMap<>();

        List<String> plugins = new ArrayList<>();
        List<String> services = new ArrayList<>();

        // read plugin sections
        Wini ini = new Wini(iniFile);
        Collection<Profile.Section> list = ini.values();

        for (Profile.Section section : list) {
            switch (section.getName()) {
                case "SCHEMA":
                    for (Map.Entry entry : section.entrySet()) {
                        schema.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                    break;
                case "PLUGINS":
                    for (Map.Entry entry : section.entrySet()) {
                        plugins.add(entry.getValue().toString());
                    }
                    break;
                case "SERVICES":
                    for (Map.Entry entry : section.entrySet()) {
                        services.add(entry.getValue().toString());
                    }
                    break;
                default:
                    type = section.getName();
                    for (Map.Entry entry : section.entrySet()) {
                        args.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                    break;
            }
        }

        try {
            return new MetaPlugin(type, args, schema, plugins, services);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the Simple Class Name of the plugin.
     *
     * @return a string which is the plugin name
     */
    public String getName() {
        return this.type;
    }

    /**
     * Get the args' names and types of the constructor {@link #MetaPlugin(String, Map, Map, List, List)}
     *
     * @return a map whose keys are objects and values are classes
     */
    public Map<Object, Class> getConstructorArgs() {
        return constructorArgs;
    }

    public Class[] toClassArray() {
        List<Class> classesArgs = new ArrayList<>();
        classesArgs.addAll(constructorArgs.values());
        return classesArgs.toArray(new Class[classesArgs.size()]);
    }

    /**
     * Get the expected format of the input for the plugin execution.
     *
     * @return the plugin schema
     */
    public Map<String, Object> getSchema() {
        return this.schema;
    }

    /**
     * Get the plugin dependencies of the plugin.
     *
     * @return a list of class where each class is a plugin needed by the plugin
     */
    public List<Class> getPluginDependencies() {
        return this.plugins;
    }

    /**
     * Get the service dependencies of the plugin.
     *
     * @return a list of class where each class is a service needed by the plugin
     */
    public List<Class> getServiceDependencies() {
        return this.services;
    }

    @Override
    public String toString() {
        StringBuilder ctrArgsBuilder = new StringBuilder();

        constructorArgs.keySet().forEach(o -> ctrArgsBuilder
                .append(o.toString())
                .append(" => ")
                .append(constructorArgs.get(o).getSimpleName())
                .append(","));

        return "{ type: " +
                this.getName() +
                ", args: [" +
                ctrArgsBuilder.toString().substring(0, ctrArgsBuilder.toString().length() - 1) +
                "], schema: " +
                this.getSchema().toString() +
                "}";
    }

}
