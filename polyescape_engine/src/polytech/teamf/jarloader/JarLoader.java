package polytech.teamf.jarloader;

import polytech.teamf.plugins.MetaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarLoader {

    /**
     * Private constructor.
     */
    private JarLoader() {
    }

    /**
     * Pre-initialized unique instance.
     */
    private static JarLoader INSTANCE = new JarLoader();

    /**
     * Entry point for the unique instance of the singleton.
     */
    public static JarLoader getInstance() {
        return INSTANCE;
    }

    /**
     * The list of services classes load from jar files.
     */
    private List<Class> servicesClasses;

    /**
     * The list of plugin classes load from jar files.
     */
    private List<Class> pluginClasses;

    /**
     * Fills {@link #servicesClasses}.
     *
     * @param path the file path from which to load the services
     */
    public void loadServices(String path) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile() && isAJarFile(file)) {
                loadServicesFromJar(file.getPath());
            }
        }
    }

    /**
     * Fills {@link #pluginClasses}.
     *
     * @param path the file path from which to load the plugins
     */
    public void loadPlugins(String path) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile() && isAJarFile(file)) {
                loadPluginFromJar(file.getPath());
            }
        }
    }

    /**
     * Tells if {@code file} is a jar file.
     *
     * @param file the file to test
     * @return true if {@code file} is a jar file
     */
    private boolean isAJarFile(File file) {
        String fileName = file.getName();
        return fileName.lastIndexOf('.') != -1
                && fileName.lastIndexOf('.') != 0
                && fileName.substring(fileName.lastIndexOf('.') + 1).equals("jar");
    }

    /**
     * Load the classes in the jar file at {@code jarPath}.
     *
     * @param jarPath the jar file path
     */
    public void loadServicesFromJar(String jarPath) {
        ArrayList<String> servicesName = getClassNamesFromJar(jarPath);

        /*
         * Create the jar class loader and use the first argument
         * passed in from the command line as the jar file to use.
         */
        JarClassLoader jarLoader = new JarClassLoader(jarPath);
        /* Load the class from the jar file and resolve it. */

        for (String name : servicesName) {

            try {
                Class c = jarLoader.loadClass(name, true);
                servicesClasses.add(c);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Load the classes in the jar file at {@code jarPath}.
     *
     * @param jarPath the jar file path
     */
    public void loadPluginFromJar(String jarPath) {
        ArrayList<String> pluginName = getClassNamesFromJar(jarPath);

        /*
         * Create the jar class loader and use the first argument
         * passed in from the command line as the jar file to use.
         */
        JarClassLoader jarLoader = new JarClassLoader(jarPath);
        /* Load the class from the jar file and resolve it. */

        for (String name : pluginName) {

            try {
                Class c = jarLoader.loadClass(name, true);
                pluginClasses.add(c);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public ArrayList<String> getClassNamesFromJar(String crunchifyJarName) {
        ArrayList<String> res = new ArrayList<>();

        try {
            JarInputStream crunchifyJarFile = new JarInputStream(new FileInputStream(crunchifyJarName));
            JarEntry crunchifyJar;

            while (true) {
                crunchifyJar = crunchifyJarFile.getNextJarEntry();

                if (crunchifyJar == null) {
                    break;
                }

                if ((crunchifyJar.getName().endsWith(".class"))) {
                    String className = crunchifyJar.getName().replaceAll("/", "\\.");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    res.add(myClass);
                }

            }

        } catch (Exception e) {
            System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
        }

        return res;
    }

    public List<MetaPlugin> getMetaPluginFromIniFile(String jarPath) {
        ArrayList<MetaPlugin> res = new ArrayList<>();

        try {
            JarInputStream crunchifyJarFile = new JarInputStream(new FileInputStream(jarPath));
            JarEntry crunchifyJar;

            while (true) {
                crunchifyJar = crunchifyJarFile.getNextJarEntry();

                if (crunchifyJar == null) {
                    break;
                }

                if ((crunchifyJar.getName().endsWith(".ini"))) {
                    File f = new File(crunchifyJar.getName());
                    res.add(MetaPlugin.parseIniFile(f));
                }

            }

        } catch (Exception e) {
            System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
        }

        return res;
    }

    public List<Class> getServicesClasses() {
        return servicesClasses;
    }

    public List<Class> getPluginClasses() {
        return pluginClasses;
    }

}
