package restServer;

import java.io.Serializable;



public class Place implements Serializable {

    private int ID;
    private String image_src;
    private boolean featured =false; //INFO featured means these are Places for QUEST Section
    private int category;
    private int points;
    private String name;
    private String wikipedia_entry;
    
    /*private Bitmap image_bmp;
    
    private String color;
    private float ratio;
    private int width;
    private int height;
    //transient private Palette.Swatch swatch;*/
    
    public Place(int ID,String image_src,boolean featured,int category,int points,String name,String wikipedia_entry){
    	this.ID = ID;
    	this.image_src=image_src;
    	this.featured = featured;
    	this.category=category;
    	this.points = points;
    	this.name = name;
    	this.wikipedia_entry = wikipedia_entry;
    }

    
    /**
     * @return name of the Place
     */
    public String getName()	 {
		return name;
	}

	/**
	 * Set the Name of the Place
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <strong>PrimeID</strong> of the Place on the Server
	 * 
	 * @return ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Set the ID of the Place. Here its the <strong>PrimeID</strong> of the Item on the Server!
	 * @param iD
	 */
	public void setID(int iD) {
		this.ID = iD;
	}

	/**
	 * Get the Infos of the Places from Wikipedia
	 * @return wikipedia_entry
	 */
	public String getWikipedia_entry() {
		return wikipedia_entry;
	}

	/**
	 * Set the Text to Display as Wikipedia Entry
	 * @param wikipedia_entry
	 */
	public void setWikipedia_entry(String wikipedia_entry) {
		this.wikipedia_entry = wikipedia_entry;
	}

	

	/**
	 * get the Points the user gets for this Item.
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Set the points that the user gets for finding this Place
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/*public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }*/

    public String getUrl() {
        return image_src;
    }

    /*public String getHighResImage(int minWidth, int minHeight) {
        String url = image_src + "?fm=png";

        if (minWidth > 0 && minHeight > 0) {
            float phoneRatio = (1.0f * minWidth) / minHeight;
            if (phoneRatio < getRatio()) {
                if (minHeight < 1080) {
                    //url = url + "&h=" + minHeight;
                    url = url + "&h=" + 1080;
                }
            } else {
                if (minWidth < 1920) {
                    //url = url + "&w=" + minWidth;
                    url = url + "&w=" + 1920;
                }
            }
        }

        return url;
    }*/

    /**
     * get the ImageURL for loading and showing it
     * @param screenWidth that is calculated per Device
     * @return the ImageURL to load in Picasso
     */
    /*public String getImageSrc(int screenWidth) {
        return image_src + "?q=75&fm=jpg&w=" + Utils.optimalImageWidth(screenWidth);

    }*/

    /**
     * Set the Image URL like -> http://appjunkies.serverimages.com/image.jpg
     * @param image_src
     */
    public void setImageSrc(String image_src) {
        this.image_src = image_src;
    }
    
    /*public void setImageBitmap(Bitmap bmp){
    	this.image_bmp=bmp;
    }
    
    public Bitmap getImageBitmap(){
    	return this.image_bmp;
    }*/
    
   /* public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /*public Palette.Swatch getSwatch() {
        return swatch;
    }

    public void setSwatch(Palette.Swatch swatch) {
        this.swatch = swatch;
    }*/

    /**
     * true if the Place is Special. In this case the Points should be increased dynamically
     * @return if its featured
     */
    public boolean getFeatured() {
        return featured;
    }

    /**
     * Set the value to true if the Place is special and should be featured in the Quest-Section
     * @param featured
     * 
     */
    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    /**
     * The categorys are:
     * <br><br>#
     * #1 Sights<br>
     * #2 Bars/Clubs<br>
     * #3 Restaurant<br>
     * #4 Sport<br>
     * #5 Mountains<br>
     * #6 Shopping <br>
     * <br>
     * @return a number that defines the category
     */
    public int getCategory() {
        return category;
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
    public void setCategory(int category) {
        this.category = category;
    }
}