package polytech.teamf.jarloader;

import polytech.teamf.plugins.MetaPlugin;
import polytech.teamf.plugins.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class JarLoader {


	private JarLoader() {
	}

	private static JarLoader INSTANCE = new JarLoader();

	public static JarLoader getInstance() {
		return INSTANCE;
	}

	/**
	 * the list of services classes load from jar files
	 */
	private Map<String,Class> servicesClasses = new HashMap<>();

	/**
	 * the list of plugin classes load from jar files
	 */
	private Map<String,Class> pluginClasses = new HashMap<>();

	private List<MetaPlugin> metaPlugins = new ArrayList<>();


	public void loadServices(String path) {
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (file.isFile() && isAJarFile(file)) {
				loadServicesFromJar(file.getPath());
			}
		}
	}

	public void loadPlugins(String path) {
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (file.isFile() && isAJarFile(file)) {
				loadPluginFromJar(file.getPath());
				getMetaPluginFromIniFile(file.getPath());
			}
		}
	}

	private boolean isAJarFile(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1).equals("jar");
		else return false;
	}


	/**
	 * load the classes in the jar file at jarPth
	 *
	 * @param jarFullPath
	 */
	public void loadServicesFromJar(String jarFullPath) {


		ArrayList<String> servicesName = getClassNamesFromJar(jarFullPath);

		for (String name : servicesName) {

			try {
				File file = new File(jarFullPath);
				URL jarUrl = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");
				Class loadedClass;
				try (URLClassLoader cl = new URLClassLoader(new URL[]{jarUrl}, JarLoader.class.getClassLoader())) {
					loadedClass = cl.loadClass(name);
				}
				System.out.println("Loading class: "+loadedClass.toString());
				servicesClasses.put(name,loadedClass);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}


	/**
	 * load the classes in the jar file at jarPth
	 *
	 * @param jarFullPath
	 */
	public void loadPluginFromJar(String jarFullPath) {

		ArrayList<String> pluginName = getClassNamesFromJar(jarFullPath);

		/*
		 * Create the jar class loader and use the first argument
		 * passed in from the command line as the jar file to use.
		 */

		/* Load the class from the jar file and resolve it. */
		for (String name : pluginName) {

			try {
				File file = new File(jarFullPath);
				URL jarUrl = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");
				Class loadedClass;
				try (URLClassLoader cl = new URLClassLoader(new URL[]{jarUrl}, JarLoader.class.getClassLoader())) {
					loadedClass = cl.loadClass(name);
				}
				System.out.println("Loading class: "+loadedClass.toString());
				pluginClasses.put(name,loadedClass);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}


	public ArrayList<String> getClassNamesFromJar(String crunchifyJarName) {
		ArrayList<String> res = new ArrayList<>();

		try {
			try (JarInputStream crunchifyJarFile = new JarInputStream(new FileInputStream(crunchifyJarName))) {
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
			}
		} catch (Exception e) {
			System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
		}
		return res;
	}


	public List<MetaPlugin> getMetaPluginFromIniFile(String jarPath) {

		try {
			try (JarInputStream crunchifyJarFile = new JarInputStream(new FileInputStream(jarPath))) {
				JarEntry crunchifyJar;

				while (true) {
					crunchifyJar = crunchifyJarFile.getNextJarEntry();
					if (crunchifyJar == null) {
						break;
					}
					if ((crunchifyJar.getName().endsWith(".ini"))) {
						File f = new File(crunchifyJar.getName());
						URL jarUrl = new URL("jar", "", "file:" + jarPath + "!/" + crunchifyJar.getName());
						MetaPlugin metaPlugin = MetaPlugin.parseIniFile(jarUrl);
						metaPlugins.add(metaPlugin);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
		}
		return metaPlugins;
	}


	public Map<String,Class> getServicesClasses() {
		return servicesClasses;
	}

	public Map<String,Class> getPluginClasses() {
		return pluginClasses;
	}

	public List<MetaPlugin> getMetaPlugins() {
		return metaPlugins;
	}

	public void addLocalPlugins() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		//List Plugins from polytech.teamf.plugins
		Class[] classArray = getClasses("polytech.teamf.plugins");
		for (Class c : classArray) {
			if (Plugin.class.isAssignableFrom(c) && c != Plugin.class) {
				Plugin p = (Plugin) c.newInstance();
				metaPlugins.add(new MetaPlugin("polytech.teamf.plugins."+c.getSimpleName(),p.args));
			}
		}
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
