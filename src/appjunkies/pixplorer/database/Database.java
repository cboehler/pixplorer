package appjunkies.pixplorer.database;

	
	import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

//______________________________________________________________________________________________________________________
//*********************************************************************************************************************
//															DATABASE for Places
//*********************************************************************************************************************
//______________________________________________________________________________________________________________________
/**
 * @author appjunkies
 *
 */	
	public class Database extends SQLiteOpenHelper{


	    public static final String DATABASE_NAME = "pixplorer.db";
	    public static final int DATABASE_VERSION = 1; 
		
		public static final String PRIME_ID ="id";

		// Last Places - column names
		public static final String TABLE_LASTLIST = "Last_Places";
	    public static final String NAME ="Name";
	    public static final String LAST_ID = "last_place_id";
	    
	    // Favorites - column names
	    public static final String TABLE_FAVORITE = "my_favorites";
	    public static final String FAV_PLACE_ID = "fav_place_id";
	 
	    // Done Places - column names
	    public static final String TABLE_MYPLACES = "my_places";
	    public static final String PLACE_ID = "done_place_id";
	   
	    private static final String CREATE_TABLE_LastPLACE = "CREATE TABLE "
	    		+ TABLE_LASTLIST	+ "("
				+ PRIME_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
	    		+ NAME			+ " TEXT NOT NULL,"
	    		+ LAST_ID		+ " INTEGER"
				+")";
	    
	    private static final String CREATE_TABLE_FavPLACE = "CREATE TABLE "
	    		+ TABLE_FAVORITE	+ "("
				+ PRIME_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
	    		+ FAV_PLACE_ID		+ " INTEGER"
				+")";
	    
	    private static final String CREATE_TABLE_DonePLACE = "CREATE TABLE "
	    		+ TABLE_MYPLACES	+ "("
				+ PRIME_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
	    		+ PLACE_ID		+ " INTEGER"
				+")";
	    Context mycontext;
	    
	    public Database(Context context) 
	    {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        this.mycontext=context;
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) 
	    {
	    	db.execSQL(CREATE_TABLE_LastPLACE);
	    	db.execSQL(CREATE_TABLE_FavPLACE);
	    	db.execSQL(CREATE_TABLE_DonePLACE);
	    	
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	    {
	        Log.w(Database.class.getName(),
	                "Upgrading database from version " + oldVersion + " to "
	                    + newVersion + ", which will destroy all old data");
	           
//			        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//	        db.execSQL(CREATE_TABLE_FavPLACE);
//	    	db.execSQL(CREATE_TABLE_DonePLACE);
			exportnewData();
	    }
	    public void exportnewData(){
	    	File direct = new File(Environment.getExternalStorageDirectory() + "/PixplorerBackup");
        	if(!direct.exists())
            {
                 if(direct.mkdir()){}

            }
			exportDB();
	    }
	  //exporting database 
	    private void exportDB() {
	        try {
	            File sd = Environment.getExternalStorageDirectory();
	            File data = Environment.getDataDirectory();

	            if (sd.canWrite()) {
	                String  currentDBPath= "//data//" +mycontext.getPackageName()
	                        + "//databases//" + "pixplorer.db";
	                String backupDBPath  = "/PixplorerBackup/pixplorer.db";
	                File currentDB = new File(data, currentDBPath);
	                File backupDB = new File(sd, backupDBPath);

	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
					

	            }
	        } catch (Exception ex) {
	        	Log.w("Backupcrash in databaseupgrade", ex.toString());
	        	
	        }
	    }
			    
	} 
			


