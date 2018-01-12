package main.plugins;

import java.util.HashMap;

public class PluginFactory {

    public static IPlugin create(String className, HashMap<String, String> args) {

        IPlugin plugin = null;

        if (className.equals("CaesarCipherPlugin")) {
            plugin = new CaesarCipherPlugin(args.get("plain_text"), Integer.parseInt(args.get("cipher_padding")));
        }

        return plugin;
    }
}
