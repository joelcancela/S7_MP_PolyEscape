package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * {@code PluginDescriptionResource} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
@XmlRootElement(name = "plugin_description")
public class PluginDescriptionResource {

    private Map<String, Object> attributes;
    private Map<String, Object> responseFormat;

    public PluginDescriptionResource() {
    }

    public PluginDescriptionResource(Map<String, Object> attributes, Map<String, Object> schema) {
        this.attributes = attributes;
        this.responseFormat = schema;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Map<String, Object> getResponseFormat() {
        return responseFormat;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setResponseFormat(Map<String, Object> responseFormat) {
        this.responseFormat = responseFormat;
    }
}
