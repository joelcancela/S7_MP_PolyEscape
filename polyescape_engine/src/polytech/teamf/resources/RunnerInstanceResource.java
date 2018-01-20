package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@code RunnerInstanceResource} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
@XmlRootElement(name = "runner_status")
public class RunnerInstanceResource {

	private String id;
	private String status;

	public RunnerInstanceResource() {
	}

	public RunnerInstanceResource(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
