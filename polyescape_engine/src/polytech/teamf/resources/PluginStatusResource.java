package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plugin_status")
public class PluginStatusResource {

    @XmlElement(name = "status")
    private boolean status = false;

    public PluginStatusResource() {
    }

    public PluginStatusResource(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

}