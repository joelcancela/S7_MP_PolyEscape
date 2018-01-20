package polytech.teamf.plugins;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MetaPlugin {

    /**
     * Class name
     */
    private String type;

    /**
     * Constructor args types
     */
    private List<Class> argsTypes = new ArrayList<>();

    /**
     * Values to pass back to the plugin constructor
     */
    private List<Object> argsValues = new ArrayList<>();

    /**
     * the expected format of the input for the plugin execution
     */
    private Map<String, Object> schema;

    /**
     * Plugins dependencies
     */
    private List<Class> plugins = new ArrayList<>();

    /**
     * Service dependencies
     */
    private List<Class> services = new ArrayList<>();

    /**
     * INI File Parser
     * @param iniFile
     * @return
     * @throws IOException
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

        for(Profile.Section section : list){
            switch (section.getName()) {
                case "SCHEMA":
                    for(Map.Entry entry : section.entrySet()){
                        schema.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                    break;
                case "PLUGINS":
                    for(Map.Entry entry : section.entrySet()){
                        plugins.add(entry.getValue().toString());
                    }
                    break;
                case "SERVICES":
                    for(Map.Entry entry : section.entrySet()){
                        services.add(entry.getValue().toString());
                    }
                    break;
                default:
                    type = section.getName();
                    for(Map.Entry entry : section.entrySet()){
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

    private MetaPlugin(
            String type,
            Map<String, Object> args,
            Map<String, Object> schema,
            List<String> plugins,
            List<String> services
    ) throws ClassNotFoundException {

        this.type = type;

        for (Map.Entry e : args.entrySet()) {
            Class t = Class.forName( "java.lang." + e.getValue().toString()); // Triggers exception if type not found
            this.argsTypes.add(t);
            this.argsValues.add(e.getKey());
        }

        this.schema = schema;

        for (String clazz : plugins) {
            Class t = Class.forName("polytech.teamf.plugins."+ clazz );
            this.plugins.add(t);
        }

        for (String clazz : services) {
            Class t = Class.forName("polytech.teamf.services."+ clazz );
            this.services.add(t);
        }
    }

    /**
     * Returns the Simple Class Name of the plugin
     * @return
     */
    public String getName() {
        return this.type;
    }

    /**
     * Returns the Constructor Types
     * @return
     */
    public Class[] getTypes() {
        Class[] clazz = new Class[ this.argsTypes.size() ];
        int i = 0;
        for (Class c : this.argsTypes) {
            clazz[i] = c;
            i++;
        }
        return clazz;
    }

    /**
     * Returns the Constructor Initialized Values (i.e : the concrete ones)
     * @return
     */
    public ArrayList getValues() {
        return (ArrayList) this.argsValues;
    }

    /**
     * Initialize the values to pass back to the plugin constructor
     * @param initializationList
     */
    public void initialize(ArrayList initializationList) {
        this.argsValues = initializationList;
    }

    /**
     * Returns the expected format of the input for the plugin execution
     * @return
     */
    public Map<String, Object> getSchema() {
        return this.schema;
    }

    /**
     * Returns the list of required plugins
     */
    List<Class> getPluginDependencies() {
        return this.plugins;
    }

    /**
     * Returns the list of required services
     */
    List<Class> getServiceDependencies() {
        return this.services;
    }

    @Override
    public String toString() {

        StringBuilder constructorArgs = new StringBuilder();

        int i = 0;
        for (Class c : this.argsTypes) {

            Object o = this.argsValues.get(i);
            if (o == null) {
                o = "<empty>";
            }

            constructorArgs
                    .append(o.toString())
                    .append(" => ")
                    .append(c.getSimpleName())
                    .append(",");
            i++;
        }

        return "{ type: " +
                this.getName() +
                ", args: [" +
                constructorArgs.toString().substring(0, constructorArgs.toString().length() - 1) +
                "], schema: " +
                this.getSchema().toString() +
                "}";
    }
}
