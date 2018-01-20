package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plugin_status")
public class PluginStatusResource {

	private boolean status;

	public PluginStatusResource() {
	}

	public PluginStatusResource(boolean status) {
		this.status = status;
	}

	private boolean getStatus() {
		return status;
	}

}