package polytech.teamf.plugins;

public abstract class Plugin implements IPlugin {

    static {
        Plugin.ARGS.add("description");
    }

    protected boolean isValidatedState = false;

    protected String description = "";

    Plugin(String description) {
        this.description = description;
    }

    public boolean isValidatedState() {
        return isValidatedState;
    }

    public String getDescription() {
        return description;
    }
}
