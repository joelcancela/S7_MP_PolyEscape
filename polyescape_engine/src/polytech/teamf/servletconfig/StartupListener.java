package polytech.teamf.servletconfig;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Lancé au démarrage du serveur #Chargement des plugins");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("Lancé au shutdown du serveur");
	}

}