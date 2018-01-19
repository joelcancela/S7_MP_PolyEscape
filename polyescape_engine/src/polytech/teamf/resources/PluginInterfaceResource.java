package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "plugin_interface")
public class PluginInterfaceResource {

    private String name;
    private String type;

    private List<String> args = new ArrayList<>();
    private Map<String, String> schema = new HashMap<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getArgs() {
        return args;
    }

    public Map<String, String> getSchema() {
        return schema;
    }

    public PluginInterfaceResource addArgs(String arg) {
        args.add(arg);
        return this;
    }

    public void setArgs(List<String> listArgs) {
        this.args = listArgs;
    }

    public PluginInterfaceResource addToSchema(String key, String value) {
        schema.put(key, value);
        return this;
    }

    public void setSchema(Map<String, String> mapSchema) {
        this.schema = mapSchema;
    }

}
