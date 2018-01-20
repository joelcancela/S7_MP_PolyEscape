package polytech.teamf.resources;

import java.util.List;

/**
 * {@code PluginInstantiationResource} is Object used to translate response of /runners/instantiate
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
public class PluginInstantiationResource {

	private Class[] types;
	private List values;
	private String name;

	public PluginInstantiationResource(Class[] types, List values, String name) {
		this.types = types;
		this.values = values;
		this.name = name;
	}


	public Class[] getTypes() {
		return types;
	}

	public void setTypes(Class[] types) {
		this.types = types;
	}

	public List getValues() {
		return values;
	}

	public void setValues(List values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
