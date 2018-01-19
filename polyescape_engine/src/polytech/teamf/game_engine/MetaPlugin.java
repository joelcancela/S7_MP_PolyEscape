package polytech.teamf.game_engine;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyPair;
import java.util.*;

public class MetaPlugin {

    private String type;
    private Map<String, Object> args;
    private Map<String, Object> schema;

    public static MetaPlugin parseJarFile(File jarFile) throws IOException {

        // Extract INI From JAR

        return MetaPlugin.parseIniFile(null);
    }

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

        return new MetaPlugin(type, args, schema);
    }

    private MetaPlugin (String type, Map<String, Object> args, Map<String, Object> schema) {
        this.type = type;
        this.args = args;
        this.schema = schema;
    }

    String getType() {
        return this.type;
    }

    Map<String, Object> getConstructorArgs() {
        return this.args;
    }

    Map<String, Object> getSchema() {
        return this.schema;
    }

    @Override
    public String toString() {
        return "{ type: " + this.getType() + ", args: " + this.getConstructorArgs().toString() + ", schema: " + this.getSchema().toString() + "}";
    }

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
