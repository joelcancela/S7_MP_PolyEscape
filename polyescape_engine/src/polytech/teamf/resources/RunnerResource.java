package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "runner_status")
public class RunnerResource {

    private String id;
    private String status;

    public RunnerResource() {
    }

    public RunnerResource(String status) {
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
