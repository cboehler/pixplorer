package appjunkies.pixplorer.model;

import java.util.ArrayList;

public class PlaceList {

    private ArrayList<Place> data;

    public ArrayList<Place> getData() {
        return data;
    }

    public void setData(ArrayList<Place> data) {
        this.data = data;
    }

    public PlaceList() {

    }

    public PlaceList(ArrayList<Place> data) {
        this.data = data;
    }
}
