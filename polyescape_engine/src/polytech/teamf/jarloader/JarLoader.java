package polytech.teamf.jarloader;

import polytech.teamf.plugins.MetaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
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
	private List<Class> servicesClasses = new ArrayList<>();

	/**
	 * the list of plugin classes load from jar files
	 */
	private List<Class> pluginClasses = new ArrayList<>();

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

		System.out.println(jarFullPath);
		for (String name : servicesName) {

			try {
				File file = new File(jarFullPath);
				String classToLoad = name;
				URL jarUrl = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");
				Class loadedClass;
				try (URLClassLoader cl = new URLClassLoader(new URL[]{jarUrl}, JarLoader.class.getClassLoader())) {
					loadedClass = cl.loadClass(classToLoad);
				}
				servicesClasses.add(loadedClass);
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
		System.out.println(jarFullPath);
		for (String name : pluginName) {

			try {
				File file = new File(jarFullPath);
				String classToLoad = name;
				URL jarUrl = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");
				Class loadedClass;
				try (URLClassLoader cl = new URLClassLoader(new URL[]{jarUrl}, JarLoader.class.getClassLoader())) {
					loadedClass = cl.loadClass(classToLoad);
				}
				System.out.println("Loading class: "+loadedClass.toString());
				pluginClasses.add(loadedClass);
			} catch (Exception e) {
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
					URL jarUrl = new URL("jar", "", "file:" + jarPath + "!/"+crunchifyJar.getName());
					MetaPlugin metaPlugin = MetaPlugin.parseIniFile(jarUrl);
					metaPlugins.add(metaPlugin);
				}
			}
		} catch (Exception e) {
			System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
		}
		return metaPlugins;
	}


	public List<Class> getServicesClasses() {
		return servicesClasses;
	}

	public List<Class> getPluginClasses() {
		return pluginClasses;
	}

	public List<MetaPlugin> getMetaPlugins() {
		return metaPlugins;
	}
}
