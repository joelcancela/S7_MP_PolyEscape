package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "plugin")
public class PluginResource {

    private String type;
    private String description;
    private String ans_format;
    private Map<String, Object> args = new HashMap<>();

    public PluginResource() {
    }

    public PluginResource(String description, String ans_format) {
        this.description = description;
    }

    @XmlElement(name = "type")
    public String getType() {
        return type;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    @XmlElement(name = "args")
    public Map<String, Object> getArgs() {
        return args;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PluginResource addToArgs(String key, Object value) {
        args.put(key, value);
        return this;
    }

}
