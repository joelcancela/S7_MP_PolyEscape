package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@code PluginAttemptAnswerResource} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
@XmlRootElement(name = "answer")
public class PluginAttemptAnswerResource {
	private String attempt;
	private boolean success;

	public PluginAttemptAnswerResource() {
	}

	public PluginAttemptAnswerResource(int attempt) {

	}

	public PluginAttemptAnswerResource(String attempt) {
		this.attempt = attempt;
	}

	public String getAttempt() {
		return attempt;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
