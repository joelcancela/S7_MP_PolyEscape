package polytech.teamf.services;

public class EmailSpyService extends Service {

    public EmailSpyService() {
        this.name = "Service de Mail Zapier";
        this.inputService = true;
        this.serviceHost = "https://zapier.com/";
    }
}
