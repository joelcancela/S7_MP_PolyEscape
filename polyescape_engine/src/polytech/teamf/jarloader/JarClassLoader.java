package polytech.teamf.jarloader;

public class JarClassLoader extends MultiClassLoader {

    private JarResources jarResources;

    /**
     * JarClassLoader class public constructor.
     * It creates a new {@code JarResources} given a jar file name.
     *
     * @param jarName the jar file name
     */
    public JarClassLoader(String jarName) {
        // Create the JarResource and suck in the jar file.
        jarResources = new JarResources(jarName);
    }

    /**
     * Produces a byte array ({@code byte[]})
     *
     * @param className
     * @return
     */
    protected byte[] loadClassBytes(String className) {
        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);
        // Attempt to get the class data from the JarResource.
        return (jarResources.getResource(className));
    }

}