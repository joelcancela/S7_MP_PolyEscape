package polytech.teamf.resources;

import java.util.Map;

/**
 * {@code PluginDescriptionResource} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
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

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Object> getResponseFormat() {
		return responseFormat;
	}

	public void setResponseFormat(Map<String, Object> responseFormat) {
		this.responseFormat = responseFormat;
	}
}
