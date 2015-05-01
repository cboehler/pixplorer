package at.ac.uibk.sepm.pixplorer.db;


public class DBContentGenerator {
	
	static boolean filled = false;
	
	public static void main(String[] args) {

		if(!filled){
			for(int i = 0;i<10;i++){
			User u = new User();
			u.setGoogleId("i@google.com");
			Category c = new Category();
			c.setName("category"+i);
			Place to_save = new Place("Sehenswurdigkeit"+i,"http://de.wikipedia.org/wiki/Goldenes_Dachl",0,null,c);
			PersistenceManager.save(u);
			PersistenceManager.save(c);
			PersistenceManager.save(to_save);
			filled = true;
		}
			
		}
	}

}
