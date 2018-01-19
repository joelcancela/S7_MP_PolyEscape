package polytech.teamf.gameengine;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
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
     * INI File Parser
     * @param iniFile
     * @return
     * @throws IOException
     */
    public static MetaPlugin parseIniFile(File iniFile) throws IOException {

        if (!iniFile.exists() || !iniFile.isFile() || !iniFile.getName().endsWith(".ini")) {
            return null;
        }

        String type = "";
        Map<String, Object> args = new HashMap<>();
        Map<String, Object> schema = new HashMap<>();

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
                default:
                    type = section.getName();
                    for(Map.Entry entry : section.entrySet()){
                        args.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                    break;
            }
        }

        try {
            return new MetaPlugin(type, args, schema);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private MetaPlugin (String type, Map<String, Object> args, Map<String, Object> schema) throws ClassNotFoundException {

        this.type = type;

        for (Map.Entry e : args.entrySet()) {
            Class t = Class.forName( "java.lang." + e.getValue().toString()); // Triggers exception if type not found
            this.argsTypes.add(t);
            this.argsValues.add(null);
        }

        this.schema = schema;
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

    // TODO : drop the main method below

    /*
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = "plugins".replace('.', '/');
            Enumeration resources = classLoader.getResources(path);

            List<File> directories = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = (URL) resources.nextElement();
                directories.add(new File(resource.getFile()));
            }

            // Read all INI files in resources directory
            for (File directory : directories) {
                if (directory.listFiles() == null) {
                    continue;
                }
                for (File ini : directory.listFiles()) {
                    if (ini.isFile() && ini.getName().endsWith(".ini")) {
                        System.err.println(MetaPlugin.parseIniFile(ini));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
