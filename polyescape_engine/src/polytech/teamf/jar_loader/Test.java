package polytech.teamf.jar_loader;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/*
 * Internal Testing application.
 */
public class Test
{


    public static ArrayList<String> getCrunchifyClassNamesFromJar(String crunchifyJarName) {
        ArrayList<String>res = new ArrayList<>();

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



    public static void main(String[] args) throws Exception
    {


        ArrayList<String> myList = getCrunchifyClassNamesFromJar("/home/user/ProjetJanvier/private/Prototypes_jar_import/src/jars/test.jar");
        System.out.println(myList);
       /*
         * Create the jar class loader and use the first argument
         * passed in from the command line as the jar file to use.
         */
        JarClassLoader jarLoader = new JarClassLoader ("/home/user/ProjetJanvier/private/Prototypes_jar_import/src/jars/test.jar");
        /* Load the class from the jar file and resolve it. */
        Class c = jarLoader.loadClass (myList.get(0) ,true);
        /*
         * Create an instance of the class.
         *
         * Note that created object's constructor-taking-no-arguments
         * will be called as part of the object's creation.
         */
        Object o = c.newInstance();
        /* Are we using a class we specifically know about? */
        if (o.getClass().getName().equals(myList.get(0)))
        {

            System.out.println("youpi");
            Method method = null;

            try {
                method = o.getClass().getMethod("test");
            } catch (SecurityException e) {
                System.out.println(e);
            }
            catch (NoSuchMethodException e) {
                System.out.println(e);
            }

            try {
                method.invoke(o);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
            catch (IllegalAccessException e) {
                System.out.println(e);
            }
            catch (InvocationTargetException e) {
                System.out.println(e);
            }

        }
    }
}   // End of nested Class Test.