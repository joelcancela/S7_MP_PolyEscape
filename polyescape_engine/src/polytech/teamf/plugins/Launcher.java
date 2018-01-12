package polytech.teamf.plugins;

import java.util.HashMap;
import java.util.Map;

public class Launcher {

    public static void main(String[] argz) {
        Map<String, String> args = new HashMap<>();
        args.put("plain_text", "RDV KATOWITZ PALAIS GOLDSTEIN VALHALLA");
        args.put("cipher_padding", "12");
        Plugin p = PluginFactory.create("CaesarCipherPlugin", args);
        System.out.println(p.ARGS);
        System.out.println(p.SCHEMA);
        try {
            Map<String, String> form = new HashMap<>();
            args.put("attempt_text", "RDV KATOWITZ PALAIS GOLDSTEIN VALHALLA");
            System.out.println(p.play(args));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
