package polytech.teamf.gameengine;

import org.json.JSONObject;
import polytech.teamf.events.GoodResponseEvent;
import polytech.teamf.events.IEvent;
import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.plugins.MetaPlugin;
import polytech.teamf.plugins.Plugin;
import polytech.teamf.resources.PluginInstantiationResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Runner {

	/**
	 * The list of plugins (ordered).
	 */
	private List<Plugin> plugins = new ArrayList<>();

	/**
	 * Handle on the current plugin.
	 */
	private Plugin currentPlugin;

	/**
	 * The current plugin state.
	 */
	private boolean currentPluginStatus = false;

	/**
	 * The current plugin number in {@link #plugins}.
	 */
	private int it = 0;

	/**
	 * Runner that parse the json & instantiate plugins.
	 *
	 * @param config the plugins configuration
	 */
	public Runner(List<PluginInstantiationResource> config) {
		for (PluginInstantiationResource plugin : config) {
			instantiatePlugin(plugin);
		}
	}



	public Plugin getPlugin() {
		return this.currentPlugin;
	}

	/**
	 * Notify the plugin of an incoming message.
	 * Notify the plugin plus its nested plugin of the event.
	 *
	 * @param args plugin arguments
	 * @return an event if the plugin executed properly
	 */
	public IEvent sendMessage(Map<String, Object> args) {

		try {
			IEvent e = this.currentPlugin.execute(args);

			if (e instanceof GoodResponseEvent) {
				this.currentPluginStatus = true;
			}

			this.sendEvent(e);
			return e;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Notify the plugin plus its nested plugin of the event.
	 *
	 * @return
	 */
	public void sendEvent(IEvent e) {
		this.currentPlugin.sendEvent(e);
	}

	/**
	 * The current plugin becomes the next in the list if there is another plugin to play (next step in the game).
	 *
	 * @return A JSONObject containing the status of the game. The status will be "finish" in case there are no more
	 * plugins to play or "ok" otherwise.
	 */
	public JSONObject nextPlugin() {

		this.currentPluginStatus = false; // Reset plugin state

		// TODO : refactor this method
		// Go to next plugin
		// Require to remove the JSON OBject

		it++;

		if (it >= plugins.size())
			return new JSONObject().put("status", "finish");

		currentPlugin = plugins.get(it);
		return new JSONObject().put("status", "ok");
	}

	public Boolean getStatus() {
		return this.currentPluginStatus;
	}



	private void instantiatePlugin(PluginInstantiationResource plugin) {
		String className = plugin.getName();
		JarLoader jarloader = JarLoader.getInstance();
		Class pluginClass = jarloader.getPluginClasses().get(className);
		System.out.println(pluginClass);
		List<MetaPlugin> pluginMeta = jarloader.getMetaPlugins();
		Class[] types = null;
		for(MetaPlugin metaPlugin : pluginMeta){
			if(metaPlugin.getName().equals(className)){
				types = metaPlugin.toClassArray();
			}
		}
		System.out.println("DEBUG");
		for (int i = 0; i < types.length; i++) {
			System.out.println(types[i]);
		}
		Collection<Object> objects = plugin.getArgs().values();
		Object[] values = objects.toArray(new Object[objects.size()]);
		Object p = null;
		try {
			System.out.println(types);
			System.out.println(values);
			System.out.println(pluginClass);
			Constructor ct = pluginClass.getConstructor(types);
			System.out.println(ct);
			p = ct.newInstance(values);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		plugins.add((Plugin) p);
	}
}
