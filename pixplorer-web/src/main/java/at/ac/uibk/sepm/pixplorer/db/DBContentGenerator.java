package at.ac.uibk.sepm.pixplorer.db;

import java.util.ArrayList;
import java.util.List;

public class DBContentGenerator {
	
	public static void main(String[] args) {
		
		/* Create some Categorys */
		Category sehenswürdigkeit = new Category();
		sehenswürdigkeit.setName("sehenswürdigkeit");
		
		/*Create Places and save to Database*/
		
		for(int i = 0;i<10;i++){
			Place to_save = new Place("Sehenswürdigkeit"+i,"http://de.wikipedia.org/wiki/Goldenes_Dachl",0,null,sehenswürdigkeit);
			PersistenceManager.save(to_save);
		}
		
		System.out.println(PersistenceManager.get(Place.class,"where x.name = 'Sehenswürdigkeit1'"));
		
		
		
		
	}

}
