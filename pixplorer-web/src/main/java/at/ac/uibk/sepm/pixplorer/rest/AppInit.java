package at.ac.uibk.sepm.pixplorer.rest;

import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import at.ac.uibk.sepm.pixplorer.db.Category;
import at.ac.uibk.sepm.pixplorer.db.GPSData;
import at.ac.uibk.sepm.pixplorer.db.PersistenceManager;
import at.ac.uibk.sepm.pixplorer.db.Place;
import at.ac.uibk.sepm.pixplorer.db.User;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitReply;
import at.ac.uibk.sepm.pixplorer.rest.msg.AppInitRequest;
import at.ac.uibk.sepm.pixplorer.rest.util.RandomPlaceGenerator;

import com.google.gson.Gson;

/**
 * Web Service that is invoked when the App is started. 
 * @author cbo, cfi
 */
@Path("/init")
public class AppInit {
	/** Gson reference */
	private static final Gson gson = new Gson();	
	
	/**
	 * Method is called when Client opened App and decided to play as Tourist or Local
	 * 
	 * @param json - JSON message from client
	 * @return JSON reply to client
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String init(String json){		
		// create some test content if necessary
		createDummyContent();
		
		// parse request from client
		AppInitRequest request = gson.fromJson(json, AppInitRequest.class);
		int option = request.getOption();
		String userName = request.getGoogleId();
		
		// check if user exists
		List<User> users = PersistenceManager.get(User.class, "where x.googleId = '" + userName + "'");
		
		User user = null;
		if (users.isEmpty()) {
			user = new User();
			user.setGoogleId(request.getGoogleId());
			user.setType(request.getOption());
			PersistenceManager.save(user);			
		} else {
			user = users.get(0);
		}
		
		// check if user type is different
		if (user.getType() != option) {
			user.setType(option);
			PersistenceManager.save(user);
		}
		
		// generate some new places for the user
		RandomPlaceGenerator generator = new RandomPlaceGenerator();
		List<Place> places = generator.getPlaces(user , 10);
		
		AppInitReply reply = new AppInitReply();
		reply.setPlaces(places);
		
		return gson.toJson(reply);
	}

	/**
	 * Creates some test content.. Will be removed
	 */
	private void createDummyContent() {
		
		int maxpoints = 200;
		int minpoints = 20;
		
		List<Category> categories = PersistenceManager.getAll(Category.class);
		
		// dummy content already created??
		if (!categories.isEmpty()) {
			return;
		}
		
		
		Random random = new Random(0);
		
		User user = new User();
		user.setAdmin(true);
		user.setScore(0);
		user.setGoogleId("max.mustermann@google.com");
		PersistenceManager.save(user);
			
		//***************************************************************************************************************************
		// PLACES ----------------------------
		//***************************************************************************************************************************	
		
		//SIGHTS***********************************************
			Category cat = new Category();
			cat.setName("SIGHT");
			PersistenceManager.save(cat);
			
			//Goldenes_Dachl
			GPSData gps = new GPSData();
			gps.setLongitude(11.393275);
			gps.setLatitude(47.268653);
			PersistenceManager.save(gps);
			Place place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Goldenes Dachl");
			place.setWikiLink("http://de.wikipedia.org/wiki/Goldenes_Dachl");
			place.setPicture("https://lh3.googleusercontent.com/-qB-kF45I-cc/VWmKpOLNqHI/AAAAAAAAAHg/3FE0JVwUyVU/s1000/innsbruck_goldenes_dachl.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Schloss_Ambras
			gps = new GPSData();
			gps.setLongitude(11.433551);
			gps.setLatitude(47.256614);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Schloss Ambras");
			place.setWikiLink("http://de.wikipedia.org/wiki/Schloss_Ambras");
			place.setPicture("https://lh3.googleusercontent.com/-r7bjjTrA-m0/VWmH8KG4eVI/AAAAAAAAAGg/Xi6Cv6PZBqg/s1000/Schloss_Ambras.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Triumphpforte
			gps = new GPSData();
			gps.setLongitude(11.394777);
			gps.setLatitude(47.262600);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Triumphpforte");
			place.setWikiLink("http://de.wikipedia.org/wiki/Triumphpforte");
			place.setPicture("https://lh3.googleusercontent.com/-Dag7Md3iBy4/VWmGebmzDUI/AAAAAAAAAFY/F5eRssf28tc/s1000/Innsbruck_-_Triumphpforte.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Swarovski_Kristallwelten
			gps = new GPSData();
			gps.setLongitude(11.600792);
			gps.setLatitude(47.293773);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Kristallwelten");
			place.setWikiLink("http://de.wikipedia.org/wiki/Swarovski_Kristallwelten");
			place.setPicture("https://lh3.googleusercontent.com/-FfikwjsHSAE/VWmLrFOclgI/AAAAAAAAAIo/fe2rNKT6O0w/s1000/swarovski_Kristall_welten.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Kaiserliche Hofburg
			gps = new GPSData();
			gps.setLongitude(11.394571);
			gps.setLatitude(47.268887);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Kaiserliche Hofburg");
			place.setWikiLink("http://de.wikipedia.org/wiki/Hofburg_%28Innsbruck%29");
			place.setPicture("https://lh3.googleusercontent.com/-ang7MulmgfY/VWmFNre52uI/AAAAAAAAAEA/Vn8FMXGDKfo/s1000/Hofburg_Innsbruck_Austria.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Hofkirche mit Silberner Kapelle
			gps = new GPSData();
			gps.setLongitude(11.395421);
			gps.setLatitude(47.268523);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Hofkirche mit Silberner Kapelle");
			place.setWikiLink("http://de.wikipedia.org/wiki/Hofkirche_%28Innsbruck%29");
			place.setPicture("https://lh3.googleusercontent.com/-sFnVIshb7z0/VWmFUF6Z4yI/AAAAAAAAAEI/Qrwhjmj-nhw/s1000/Hofkirche_Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Annasäule
			gps = new GPSData();
			gps.setLongitude(11.394252);
			gps.setLatitude(47.265640);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Annasäule");
			place.setWikiLink("http://de.wikipedia.org/wiki/Annas%C3%A4ule_(Innsbruck)");
			place.setPicture("https://lh3.googleusercontent.com/-OkR5OaFGkZo/VWmECwaL-II/AAAAAAAAADE/wCdGE1NU1Es/s1000/Annasaeule__Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Dom Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.393794);
			gps.setLatitude(47.269509);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Dom");
			place.setWikiLink("http://de.wikipedia.org/wiki/Innsbrucker_Dom");
			place.setPicture("https://lh3.googleusercontent.com/-3HPgFQm_OyA/VWmKdaZDHBI/AAAAAAAAAHQ/10g_AgXp9ik/s1000/innsbruck-Dom.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			//Altes Landhaus Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.394913);
			gps.setLatitude(47.263613);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Altes Landhaus");
			place.setWikiLink("http://de.wikipedia.org/wiki/Altes_Landhaus_Innsbruck");
			place.setPicture("https://lh3.googleusercontent.com/-Meo2V6g9_UM/VWmJUnFHPTI/AAAAAAAAAGw/6rY8MGlQLCw/s1000/altes_Landhaus.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);		
			
			//Stadtturm Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.393459);
			gps.setLatitude(47.268230);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Stadtturm");
			place.setWikiLink("http://de.wikipedia.org/wiki/Stadtturm_%28Innsbruck%29");
			place.setPicture("https://lh3.googleusercontent.com/-K1GtBbLdvwk/VWmEebwHraI/AAAAAAAAADg/Wi0KbjYqzDM/s1000/City_Tower.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			
		//MUSEUM***********************************************	
			cat = new Category();
			cat.setName("MUSEUM");
			PersistenceManager.save(cat);
		
			//Glockenmuseum und Glockengiesserei
			gps = new GPSData();
			gps.setLongitude(11.398232);
			gps.setLatitude(47.256145);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Glockenmuseum und Glockengiesserei");
			place.setWikiLink("http://de.wikipedia.org/wiki/Glockenmuseum_Grassmayr");
			place.setPicture("https://lh3.googleusercontent.com/-yLFrioWuS1s/VWmE96KkFMI/AAAAAAAAADw/jTF6TtKlA80/s1000/GlockenMuseum.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Tirol Panorama Kaiserjäger Museum
			gps = new GPSData();
			gps.setLongitude(11.401608);
			gps.setLatitude(47.250521);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Tirol Panorama");
			place.setWikiLink("http://de.wikipedia.org/wiki/Das_Tirol_Panorama");
			place.setPicture("https://lh3.googleusercontent.com/-Ufl_iqdqbTA/VWmMdNphr6I/AAAAAAAAAJA/9Oa-4Ey70Wo/s1000/keiserj%2525C3%2525A4ger-museeum.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Altes Riesenrundgemälde Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.403465);
			gps.setLatitude(47.279336);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Altes Riesenrundgemälde");
			place.setWikiLink("http://de.wikipedia.org/wiki/Innsbrucker_Riesenrundgem%C3%A4lde");
			place.setPicture("https://lh3.googleusercontent.com/-KJN9ZTlxdcI/VWmHU_Bt43I/AAAAAAAAAGA/2SdkIxEUKM8/s1000/Riesenrundgemaelde_ibk.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
		//OTHER***********************************************	
			cat = new Category();
			cat.setName("OTHER");
			PersistenceManager.save(cat);
		
			//Hauptuni Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.384507);
			gps.setLatitude(47.263415);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Hauptuniversität");
			place.setWikiLink("http://de.wikipedia.org/wiki/Universit%C3%A4t_Innsbruck");
			place.setPicture("https://lh3.googleusercontent.com/-4WVDj6W_QjE/VWmFkD373NI/AAAAAAAAAEk/8As1F92fLuE/s1000/Hauptgeb%2525C3%2525A4ude_der_Universit%2525C3%2525A4t_Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			//Technische Uni Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.344598);
			gps.setLatitude(47.264089);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Technische Universität");
			place.setWikiLink("http://de.wikipedia.org/wiki/Universit%C3%A4t_Innsbruck");
			place.setPicture("https://lh3.googleusercontent.com/-K8buxjzccAw/VWmSByHtPCI/AAAAAAAAAJU/Not-cl6UIto/s1000/Technik_Uni.jpg");//TODO Take Pic of TU
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			//AlpenZoo Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.398171);
			gps.setLatitude(47.281206);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Alpenzoo");
			place.setWikiLink("http://de.wikipedia.org/wiki/Alpenzoo_Innsbruck");
			place.setPicture("https://lh3.googleusercontent.com/-4vm9TLPVM0A/VWmF5iyduxI/AAAAAAAAAE4/O59cSNBnk4M/s1000/Innsbruck-Alpenzoo-Haupteingang.JPG");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			
		//TRAVEL***********************************************	
			cat = new Category();
			cat.setName("TRAVEL");
			PersistenceManager.save(cat);
			
			//Flughafen Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.344598);
			gps.setLatitude(47.264089);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Flughafen");
			place.setWikiLink("http://de.wikipedia.org/wiki/Flughafen_Innsbruck");
			place.setPicture("https://lh3.googleusercontent.com/-4mP3X5Tn7uo/VWmFGyXO3GI/AAAAAAAAAD4/cMcqXG7Cz68/s1000/Flughafen_Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			//Bahnhof Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.400340);
			gps.setLatitude(47.263578);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Hauptbahnhof");
			place.setWikiLink("http://de.wikipedia.org/wiki/Innsbruck_Hauptbahnhof");
			place.setPicture("https://lh3.googleusercontent.com/-MlcCbc2b9Gw/VWmGMANnqpI/AAAAAAAAAFI/YqdB2kEuFMc/s1000/Innsbruck_Hbf_Stra%2525C3%25259Fenbahn.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			//HungerburgBahn Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.398595);
			gps.setLatitude(47.286516);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("HungerburgBahn");
			place.setWikiLink("http://de.wikipedia.org/wiki/Hungerburgbahn");
			place.setPicture("https://lh3.googleusercontent.com/-Dh2TONnM2XI/VWmFkkoWmzI/AAAAAAAAAEo/eq_a_wykca4/s1000/HungerburgBahn.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
		
		//MOUNTAIN***********************************************	
			cat = new Category();
			cat.setName("MOUNTAIN");
			PersistenceManager.save(cat);
		
			//Nordkette Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.380478);
			gps.setLatitude(47.306605);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Nordkette");
			place.setWikiLink("http://de.wikipedia.org/wiki/Inntalkette");
			place.setPicture("https://lh3.googleusercontent.com/-QBx9jfkTFW4/VWmJuPLPSuI/AAAAAAAAAG4/iw_OOdlDKds/s1000/gondel_sommer_0183.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
		
		//RESTAURANT***********************************************	
			cat = new Category();
			cat.setName("RESTAURANT");
			PersistenceManager.save(cat);
		
			//Goldener Adler Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.392375);
			gps.setLatitude(47.268421);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Goldener Adler");
			place.setWikiLink("http://de.wikipedia.org/wiki/Hotel_Goldener_Adler_(Innsbruck)");
			place.setPicture("https://lh3.googleusercontent.com/-LfwtXZFiMSM/VWmFXRo6XjI/AAAAAAAAAEQ/Q4y84GYycvY/s1000/Hotel_Goldener_Adler_Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);			
			
			//Ottoburg Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.391980);
			gps.setLatitude(47.268521);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Ottoburg");
			place.setWikiLink("http://de.wikipedia.org/wiki/Innenstadt_(Innsbruck)");
			place.setPicture("https://lh3.googleusercontent.com/-F13xiE_ODiM/VWmHGzw7MPI/AAAAAAAAAF4/6gEFUtjICMk/s1000/Ottoburg_Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
		
		//BAR***********************************************	
			cat = new Category();
			cat.setName("BAR");
			PersistenceManager.save(cat);
		
			//Adlers Hotel Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.401455);
			gps.setLatitude(47.266150);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Adlers Bar");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-B170-2-PuQo/VWmIeAvyULI/AAAAAAAAAGo/L4VUJ-3FY54/s1000/adlers-bar.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
		
			//Stadtcafe Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.395799);
			gps.setLatitude(47.268872);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Stadtcafe");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-uRmlWC_tbng/VWmKNtU7utI/AAAAAAAAAHA/F3aiiIE0iQ4/s1000/StadtCafe.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);		

			//Pavillion Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.395639);
			gps.setLatitude(47.269782);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Der Pavillion");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-1YB7UN4k38I/VWmLYBQfZcI/AAAAAAAAAIQ/eY0pCfB00us/s1000/pavilon-cafe.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);			
			
		//SPORT***********************************************	
			cat = new Category();
			cat.setName("SPORT");
			PersistenceManager.save(cat);
		
			//Tivoli Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.412221);
			gps.setLatitude(47.256796);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Tivoli");
			place.setWikiLink("http://de.wikipedia.org/wiki/Tivoli_(Innsbruck)");
			place.setPicture("https://lh3.googleusercontent.com/-0xpIhltq1_k/VWmKPilBbmI/AAAAAAAAAHI/45irjiWt9H4/s1000/Tivoli_Stadion.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	

			//Olympiaworld Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.410454);
			gps.setLatitude(47.258439);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Olympiaworld");
			place.setWikiLink("http://de.wikipedia.org/wiki/Olympiahalle_Innsbruck");
			place.setPicture("https://lh3.googleusercontent.com/-hZ2TAk3HZZU/VWmHCy6ja9I/AAAAAAAAAFw/xADLef1vffw/s1000/Olympiaworld_IBK.png");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			//Bergisel Schanze
			gps = new GPSData();
			gps.setLongitude(11.399444);
			gps.setLatitude(47.249074);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Bergisel");
			place.setWikiLink("http://de.wikipedia.org/wiki/Bergisel");
			place.setPicture("https://lh3.googleusercontent.com/-zpR71e5sPSs/VWmEDE0uYKI/AAAAAAAAADI/MA2qnrF30hs/s1000/Bergisel%252520Schanze.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//SkateHalle Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.414350);
			gps.setLatitude(47.277158);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Skatehalle");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-xKM4F_gTDHA/VWmLh0s0W2I/AAAAAAAAAIY/GYZCfydYOXE/s1000/skatehalle-innsbruck%2525282%252529.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
		
		//PLACES***********************************************	
			cat = new Category();
			cat.setName("PLACES");
			PersistenceManager.save(cat);
		
			//BoznerPlatz Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.397539);
			gps.setLatitude(47.264942);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Bozner Platz");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-E3zBGAGlrcA/VWmEz6lyrGI/AAAAAAAAADo/l2ab6ZMyvXs/s1000/BoznerPlatz.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	

			//Landhaus Platz Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.396167);
			gps.setLatitude(47.264034);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Landhausplatz");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-8CMDypUabbY/VWmH6RER1dI/AAAAAAAAAGY/qTifI2xJgl0/s1000/Landshausplatz.JPG");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
		
			//Stift Wilten
			gps = new GPSData();
			gps.setLongitude(11.400488);
			gps.setLatitude(47.253654);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Stift Wilten");
			place.setWikiLink("http://de.wikipedia.org/wiki/Stift_Wilten");
			place.setPicture("https://lh3.googleusercontent.com/-hPhyL6VIN4I/VWmH1FclveI/AAAAAAAAAGI/e6c3pXzYnL4/s1000/Innsbruck_Stift_Wilten.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Bunte Häuser Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.389511);
			gps.setLatitude(47.268228);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Bunte Häuser");
			place.setWikiLink("http://de.wikipedia.org/wiki/Mariahilf-St._Nikolaus");
			place.setPicture("https://lh3.googleusercontent.com/-BaWeMLGeg6Y/VWmEXPeZiBI/AAAAAAAAADY/j88ED8Ts2TE/s1000/Bunte%252520H%2525C3%2525A4user_Innsbruck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
		
		//TEST***********************************************	
			cat = new Category();
			cat.setName("TEST");
			PersistenceManager.save(cat);
		
			//Home Leo Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.355626);
			gps.setLatitude(47.268629);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Home Leo Innsbruck");
			place.setWikiLink("http://de.wikipedia.org/wiki/Leo");
			place.setPicture("https://lh3.googleusercontent.com/-pnFbRHATHTU/VWmL9igrczI/AAAAAAAAAI4/DaEEp9bqRX8/s1000/wohnungLeo.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);			
			
			
						//Home Leo Südtirol
			gps = new GPSData();
			gps.setLongitude(11.877246);
			gps.setLatitude(46.716950);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Home Leo Südtirol");
			place.setWikiLink("http://de.wikipedia.org/wiki/Leo");
			place.setPicture("https://lh3.googleusercontent.com/-dkWZpeVX7zY/VWmLXX4rUtI/AAAAAAAAAIM/TtH91BqBHmc/s1000/leo_zuhause.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);	
			
			
			//Home Max Emme Innsbruck
			gps = new GPSData();
			gps.setLongitude(11.362054);
			gps.setLatitude(47.266392);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Home WG");
			place.setWikiLink("http://de.wikipedia.org/wiki/Wohngemeinschaft");
			place.setPicture("https://lh3.googleusercontent.com/-oKXYRrYrKc4/VWmFv4jvSWI/AAAAAAAAAEw/D_ODK-bnnXk/s1000/IBK_home.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
		//SÜDTIROL***********************************************	
			cat = new Category();
			cat.setName("SÜDTIROL");
			PersistenceManager.save(cat);	
			
			//Bruneck Rathaus
			gps = new GPSData();
			gps.setLongitude(11.935422);
			gps.setLatitude(46.796305);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Bruneck Rathaus");
			place.setWikiLink("http://de.wikipedia.org/wiki/Bruneck");
			place.setPicture("https://lh3.googleusercontent.com/-UyJspBrEKN0/VWmEJ9myFII/AAAAAAAAADQ/x1zVnT6erhY/s1000/Bruneck%252520Rathaus.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Schloss Bruneck
			gps = new GPSData();
			gps.setLongitude(11.939882);
			gps.setLatitude(46.794143);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Schloss Bruneck");
			place.setWikiLink("http://de.wikipedia.org/wiki/Schloss_Bruneck");
			place.setPicture("https://lh3.googleusercontent.com/-fMMDbgpLQ34/VWmFj0ZZQYI/AAAAAAAAAEg/TlIHUJeeWqw/s1000/Bruneck_Schloss.JPG");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Ursulinen Bruneck
			gps = new GPSData();
			gps.setLongitude(11.936072);
			gps.setLatitude(46.795092);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Ursulinenkirche");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-oUar9dIXRbg/VWmLtcOsK8I/AAAAAAAAAIw/gBar13t00E0/s1000/ursulinen%252520kirche.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Hotel Post Bruneck
			gps = new GPSData();
			gps.setLongitude(11.936169);
			gps.setLatitude(46.796503);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Hotel Post");
			place.setWikiLink("");
			place.setPicture("https://lh3.googleusercontent.com/-KEmdQr9Wtos/VWmKhMqhREI/AAAAAAAAAHY/e40NT7rdjvM/s1000/hotel_Post_bruneck.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
			//Kronplatz Concordia
			gps = new GPSData();
			gps.setLongitude(11.958540);
			gps.setLatitude(46.738488);
			PersistenceManager.save(gps);
			place = new Place();
			place.setCategory(cat);
			place.setGpsData(gps);
			place.setCount(0);	
			place.setFeatured(false);
			place.setModificationDate(System.currentTimeMillis());
			place.setName("Concordia");
			place.setWikiLink("http://de.wikipedia.org/wiki/Concordia_2000");
			place.setPicture("https://lh3.googleusercontent.com/-HG-FEtZkyvY/VWmK_CNC5QI/AAAAAAAAAH4/v67Cd_ayYRU/s1000/kronplatz-concordia.jpg");
			place.setScore(random.nextInt((maxpoints-minpoints)+minpoints));
			PersistenceManager.save(place);
			
		}
}
	