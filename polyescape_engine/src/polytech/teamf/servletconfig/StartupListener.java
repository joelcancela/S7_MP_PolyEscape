package polytech.teamf.servletconfig;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
import polytech.teamf.jarloader.JarLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Chargement des services");
		JarLoader.getInstance().loadServices("./resources/services/");
		System.out.println("Chargement des plugins");
		JarLoader.getInstance().loadPlugins("./resources/plugins/");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("Goodbye!");
	}

}