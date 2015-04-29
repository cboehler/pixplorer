package restServer.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {
		
		Category category = new Category();
		category.setName("Test1");
		
		Place place1=new Place();
		place1.setName("Goldenes Dachl");
		place1.setCount(0);
		place1.setCategory(category);
		
		Place place2=new Place();
		place2.setName("Goldenes Dachl");
		place2.setCount(0);
		place2.setCategory(category);
		
		GPSData newGPS = new GPSData();
		newGPS.setLatitude(1.02132);
		newGPS.setLongitude(1.254536);
		newGPS.setPlace(place1);
		
	
		
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());

		Session session= factory.openSession();
		session.beginTransaction();

		session.save(newGPS);
		session.save(place2);
		
		session.getTransaction().commit();
		session.close();
		factory.close();
		
		

	}

}
