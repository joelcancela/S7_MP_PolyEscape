package polytech.teamf.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Plugin implements IPlugin {

    /**
     * Constructor Arguments.
     */
    private List<String> args = new ArrayList<>();

    /**
     * Form Schema.
     */
    protected Map<String, String> schema = new HashMap<>();

    /**
     * Validation State of The Plugin.
     */
    protected boolean isValidatedState = false;
    protected static final String SUCCESS = "success";

    protected boolean isSuccess = false;

    /**
     * Plugin Description Field.
     */
    private String description = "";

    /**
     * Plugin Name.
     */
    private String name = "";


    /**
     * The format of the answer.
     */
    protected String ans_format = "";

    /**
     * Description Getter.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Name Getter.
     *
     * @return the plugin name
     */
    public String getName() {
        return name;
    }

    /**
     * Answer format getter.
     *
     * @return the plugin answer format
     */
    public String getAns_format() {
        return ans_format;
    }

    /**
     * Shared constructor with inherited plugins.
     *
     * @param description the plugin description as a short text
     * @param name        the plugin name
     */
    Plugin(String description, String name) {
        this.description = description;
        this.name = name;

        // ARGS
        args.add("description");
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

    @Override
    public boolean getStatus() {
        return isSuccess;
    }

}
