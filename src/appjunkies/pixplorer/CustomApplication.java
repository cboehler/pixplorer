package appjunkies.pixplorer;

import android.app.Application;
import android.content.Context;
import appjunkies.pixplorer.other.FontAwesomeFont;
import appjunkies.pixplorer.other.GoogleMaterialFont;

import com.mikepenz.iconics.Iconics;

public class CustomApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Iconics.registerFont(new GoogleMaterialFont());
        Iconics.registerFont(new FontAwesomeFont());
        context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
