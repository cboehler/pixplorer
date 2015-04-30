package appjunkies.pixplorer.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import appjunkies.pixplorer.R;
import appjunkies.pixplorer.activities.HomeActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

public class Settings_Fragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
	
	public static final String KEY_LIST_PREFERENCE = "gameMode";
	public static final String MODE = "GameMODE";
	private ListPreference mListPreference;
	PreferenceCategory mCategory;
	Context mcontext;
	private View positiveAction;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_setting, container,
				false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTheme(R.style.mySettings);
		getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		addPreferencesFromResource(R.xml.preferences);
		
		//get the Context of the App
		mcontext = getActivity().getApplicationContext();
		
		//get PreferenceManager
		PreferenceManager manager = getPreferenceManager();
	    manager.setSharedPreferencesMode(getActivity().MODE_APPEND);
		
	 // Get a reference to the preferences
		mListPreference = (ListPreference) getPreferenceScreen().findPreference(KEY_LIST_PREFERENCE);
		mListPreference.setDefaultValue(mListPreference.getValue());

//		mListPreference.setValueIndex(getGameModePref(MODE, mcontext)-1);
		
		mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				public boolean onPreferenceChange(Preference preference,
						Object newValue) {
					if (newValue instanceof String) {
						String value = (String) newValue;
						int index = mListPreference.findIndexOfValue(value);
						/*
						 * INDEX INFO 
						 * 1 -> TOURIST 
						 * 2 -> LOCAL
						 */
						putIntegerPrefs(MODE, index, mcontext);
					}
					return true;
				}
			});
		         
//Handle Preference Clicks
		
//	    Preference resetdata = (Preference)findPreference("reset");
//	    resetdata.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//	                    @Override
//	                    public boolean onPreferenceClick(Preference arg0) { 
//	                    	//INFO NOT NEEDED until we make an option to reset the Game
//	                        return true;
//	                    }
//	    });
	    
	    Preference clearcache = (Preference)findPreference("clearcache");
	    clearcache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	                    @Override
	                    public boolean onPreferenceClick(Preference arg0) {
	                    	MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
	                        .title(R.string.clearcacheTitle)
	                        .content(R.string.clearcacheMsg)
	                        .positiveText(android.R.string.yes)
	                        .negativeText(android.R.string.cancel)
	                        .callback(new MaterialDialog.ButtonCallback() {
	                            @Override
	                            public void onPositive(MaterialDialog dialog) {
	                            	PicassoTools.clearCache(Picasso.with(mcontext));
	                            }
	                            @Override
	                            public void onNegative(MaterialDialog dialog) {
	                            	
	                            }
	                        }).build();
	                    	positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
	                		dialog.show();
	                        positiveAction.setEnabled(true);
	                    	
	                    	
	                    	return true; 
	                    }
	    }); 
		
	}
	
	 public static void putBooleanPref(String key, Boolean value, Context context) {
		    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putBoolean(key, value);
		    editor.commit();
		}
	    public static void putStringPref(String key, String value, Context context) {
		    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putString(key, value);
		    editor.commit();
		    }
		public static void putIntegerPrefs(String key, int value, Context context) {
		    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putInt(key, value);
		    editor.commit();
		}

		public static int getGameModePref(String key, Context context) {
		    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		    int gamemode = preferences.getInt(MODE, 2);
		    return gamemode;
		}
		
		
//		public static boolean getBooleanPref(String key, Context context) {
//		    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		    boolean checked = preferences.getBoolean("NAME", true);
//		    return checked;
//		}
//		public static boolean getStringPref(String key, Context context) {
//		    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		    String checked = preferences.getString("copyIMG", false);
//		    return checked;
//		}
	
	@Override
		public void onDestroy() {
			super.onDestroy();
		}
	@Override
		public void onDetach() {
			
			super.onDetach();
		}
	
	@Override
    public void onResume() {
        super.onResume();

        // Setup the initial values
        mListPreference.setSummary(getResources().getString(R.string.summary_gamemode)+" " + mListPreference.getEntry());

        // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Set new summary, when a preference value changes
        if (key.equals(KEY_LIST_PREFERENCE)) {
            mListPreference.setSummary(getResources().getString(R.string.summary_gamemode)+" "+ mListPreference.getEntry()); 
        }
    }
	
	
}	



