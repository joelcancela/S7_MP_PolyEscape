package main.plugins;

public abstract class Plugin implements IPlugin {

    protected boolean isValidatedState = false;

    public boolean isValidatedState() {
        return isValidatedState;
    }
}
