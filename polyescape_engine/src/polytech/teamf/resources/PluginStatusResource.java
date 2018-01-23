package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "plugin_status")
public class PluginStatusResource {

    private boolean status = false;
    private Object attempt;

    public PluginStatusResource() {
    }

    public PluginStatusResource(boolean status) {
        this.status = status;
    }

    public PluginStatusResource(Object attempt) {
        this.attempt = attempt;
    }

    public boolean getStatus() {
        return status;
    }

    public Object getAttempt() {
        return attempt;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAttempt(Object attempt) {
        this.attempt = attempt;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("attempt", attempt);
        return map;
    }

}