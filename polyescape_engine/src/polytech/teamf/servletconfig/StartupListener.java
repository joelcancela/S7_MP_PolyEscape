package polytech.teamf.servletconfig;

import polytech.teamf.jarloader.JarLoader;
import polytech.teamf.plugins.MetaPlugin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

/**
 * Loads every services and plugins at the servlet initialization.
 *
 * @author Joël CANCELA VAZ
 */
@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Chargement des services");
        JarLoader.getInstance().loadServices("./resources/services/");
        System.out.println("Chargement des plugins");
        JarLoader.getInstance().loadPlugins("./resources/plugins/");
        try {
            JarLoader.getInstance().addLocalPlugins();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        for (MetaPlugin plugin : JarLoader.getInstance().getMetaPlugins()) {
            System.out.println(plugin.toString());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Goodbye!");
    }

}