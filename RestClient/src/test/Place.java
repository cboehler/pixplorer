package test;


import java.io.Serializable;



public class Place implements Serializable {

    private int ID;
    private String image_src;
    private boolean featured =false; //INFO featured means these are Places for QUEST Section
    private int category;
    private int points;
    private String name;
    private String wikipedia_entry;    
    private String image;
    
    
    
    public Place(int ID,String image_src,boolean featured,int category,int points,String name,String wikipedia_entry, String image){
    	this.ID = ID;
    	this.image_src=image_src;
    	this.featured = featured;
    	this.category=category;
    	this.points = points;
    	this.name = name;
    	this.wikipedia_entry = wikipedia_entry;
    	this.image = image;
    }



	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	public String getImage_src() {
		return image_src;
	}



	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}



	public boolean isFeatured() {
		return featured;
	}



	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	/**
     * The categorys are:
     * <br><br>
     * #1 Sights<br>
     * #2 Bars/Clubs<br>
     * #3 Restaurant<br>
     * #4 Sport<br>
     * #5 Mountains<br>
     * #6 Shopping <br>
     * <br>
     * Set Category of Place
     * @param category number
     */

	public int getCategory() {
		return category;
	}



	public void setCategory(int category) {
		this.category = category;
	}



	public int getPoints() {
		return points;
	}



	public void setPoints(int points) {
		this.points = points;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getWikipedia_entry() {
		return wikipedia_entry;
	}



	public void setWikipedia_entry(String wikipedia_entry) {
		this.wikipedia_entry = wikipedia_entry;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}


    
        
    
}