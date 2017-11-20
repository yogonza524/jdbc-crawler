package hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import config.Config;
import model.Content;


public class HibernateInitializr {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	  public static SessionFactory getSessionFactory() {
	    if (sessionFactory == null) {
	      try {
	    	  
	    	  	Properties prop= new Properties();
				prop.setProperty("hibernate.connection.url", "jdbc:" + Config.get().get("dbadmin") + "://" + Config.get().get("host") + ":" + Config.get().get("port") + "/" + Config.get().get("database") + "?createDatabaseIfNotExist=true");
				prop.setProperty("hibernate.connection.username", Config.get().get("username"));
				prop.setProperty("hibernate.connection.password", Config.get().get("password"));
				prop.setProperty("hibernate.dialect", "org.hibernate.dialect." + Config.get().get("dialect"));
				prop.setProperty("hibernate.connection.driver_class", Config.get().get("driver"));
				prop.setProperty("hibernate.connection.requireSSL", Config.get().get("requireSSL") != null? Config.get().get("requireSSL") : "true");
				prop.setProperty("hibernate.hbm2ddl.auto", "create"); //Create DB if not exists
				prop.setProperty("hibernate.current_session_context_class", "thread");
//				prop.setProperty("hibernate.show_sql", "true");
				prop.setProperty("hibernate.format_sql", "true");
				
				Configuration configuration = new Configuration();
				
				configuration
					.addAnnotatedClass(Content.class)
					;

		        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		        builder.applySettings(prop);
		        ServiceRegistry serviceRegistry = builder.build();
		        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	        
	      } catch (Exception e) {
	        e.printStackTrace();
	        if (registry != null) {
	          StandardServiceRegistryBuilder.destroy(registry);
	        }
	      }
	    }
	    return sessionFactory;
	  }

	  public static void shutdown() {
	    if (registry != null) {
	      StandardServiceRegistryBuilder.destroy(registry);
	    }
	  }
}