package polytech.teamf.resources;

import java.util.Map;

/**
 * {@code PluginInstantiationResource} is Object used to translate response of /runners/instantiate
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
public class PluginInstantiationResource {

	private String name;
	private Map<String, Object> args;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "PluginInstantiationResource{" +
				"name='" + name + '\'' +
				", args=" + args +
				'}';
	}
}
