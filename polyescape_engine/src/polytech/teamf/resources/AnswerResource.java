package polytech.teamf.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "answer")
public class AnswerResource {

    private String attempt;
    private boolean success;

    public AnswerResource() {}

    public AnswerResource(int attempt) {

    }

    public AnswerResource(String attempt) {
        this.attempt = attempt;
    }

    public String getAttempt() {
        return attempt;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
