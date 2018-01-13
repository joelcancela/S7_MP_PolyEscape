package polytech.teamf.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Plugin implements IPlugin {

    /**
     * Constructor Arguments
     */
    private List<String> args = new ArrayList<>();

    /**
     * Form Schema
     */
    protected Map<String, String> schema = new HashMap<>();

    /**
     * Validation State of The Plugin
     */
    protected boolean isValidatedState = false;
    protected static final String SUCCESS = "success";

    /**
     * Plugin Description Field
     */
    protected String description = "";

    /**
     * Description Getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Default Constructor
     */
    Plugin() {
        args.add("description");
    }

    /**
     * Shared constructor with inherited plugins
     *
     * @param description The plugin description as a short text
     */
    Plugin(String description) {
        this.description = description;
    }

    public boolean isValidatedState() {
        return isValidatedState;
    }

    @Override
    public List<String> getArgs() {
        return args;
    }

    @Override
    public Map<String, String> getSchema() {
        return schema;
    }
}
