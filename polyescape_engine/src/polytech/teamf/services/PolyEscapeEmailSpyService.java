package polytech.teamf.services;

public class PolyEscapeEmailSpyService extends Service {

    public PolyEscapeEmailSpyService() {
        this.name = "Service de Mail Zapier";
        this.inputService = true;
        this.serviceHost = "https://zapier.com/";
    }
}
