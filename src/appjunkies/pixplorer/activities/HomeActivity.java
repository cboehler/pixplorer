
package appjunkies.pixplorer.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import appjunkies.pixplorer.R;
import appjunkies.pixplorer.fragments.Explore;
import appjunkies.pixplorer.fragments.TestFragment;
import appjunkies.pixplorer.other.FontAwesomeFont;
import appjunkies.pixplorer.search.SearchBox;
import appjunkies.pixplorer.search.SearchBox.MenuListener;
import appjunkies.pixplorer.search.SearchBox.SearchListener;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.example.games.basegameutils.BaseGameUtils;
/**
 * @author appjunkies
 *
 */
public class HomeActivity extends ActionBarActivity 
implements
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener
{
	
	public enum Category {
        LAST(1000),
        FAVORITE(1001),
        QUEST(1002);
//        TODO USE this IDs in for Serach and show only places from Category 
//        BUILDINGS(1),
//        RESTAURANT(2),
//        NATURE(4),
//        PEOPLE(8),
//        TECHNOLOGY(16),
//        OBJECTS(32);
        
        //QUESTION For Using only 1 Fragment it is neccessary to download the last places + favorites + specialplaces at the same time 
//        otherwise we need 3 extra fragments.

        public final int id;

        private Category(int id) {
            this.id = id;
        }
    }
	
	
	String username,useremail,userpic=null;
	Drawable usericon; 
	boolean loggedout=true;
	boolean firststart=false;
	private View positiveAction;
	
	private OnFilterChangedListener onFilterChangedListener;

    public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }
	
	private static final int PROFILE_SETTING = 1;
	
	private static final int HIDE_MENU = 1483;
	private static final int SHOW_MENU = 3841;
	int mState=0;
	//save our header or result
    public AccountHeader.Result headerResult = null;
    public Drawer.Result result = null;
    private IProfile user;
    
    FragmentManager fragmentManager;
    
	private SearchBox searchbox;

	Toolbar toolbar;
	TextView toolbartitle;
	
	//--------------------------------------------------------------------------
	//--------------------------		Login Vars      ------------------------
	//--------------------------------------------------------------------------
	GoogleApiClient mGoogleApiClient;
	private static int RC_SIGN_IN = 9001;
	private static int REQUEST_LEADERBOARD = 2548;
	private static int REQUEST_ACHIEVEMENTS = 8452;
	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInflow = true;
	private boolean mSignInClicked = false;
	boolean mExplicitSignOut = false;
	boolean mInSignInFlow = false; // set to true when you're in the middle of the
	                               // sign in flow, to know you should not attempt
	                               // to connect in onStart()
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

//		//INITS findViewById***********************************************
//			shuffleFab = (FloatingActionButton) findViewById(R.id.shuffleFab);
			toolbar = (Toolbar) findViewById(R.id.toolbar);
			toolbartitle = (TextView) toolbar.findViewById(R.id.toolbartitle);
			searchbox = (SearchBox) findViewById(R.id.searchbox);
//
		
		fragmentManager = getSupportFragmentManager();			
		fragmentManager.beginTransaction().replace(R.id.content_frame, new Explore()).commit();
			
		final Fragment testfragment = new TestFragment();
		
		// Create the Google Api Client with access to the Play Game services
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	            .addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .addApi(Games.API).addScope(Games.SCOPE_GAMES)
	            .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
	            // add other APIs and scopes here as needed
	            .build();
		loadPrefs();
		if (loggedout == true) {
			showLoginDialog();
    	}
		
		
		
		
				
//		// Create the AccountHeader*****************************************************************
//		QUESTION get Account from Shared Prefs ?? 
		loadUser();
		if(loggedout&&userpic!=null){
			user = new ProfileDrawerItem().withName("Username").withNameShown(true).withEmail("user@gmail.com").withIcon(getResources().getDrawable(R.drawable.profilepic)).withIdentifier(2);
		}else{
			user = new ProfileDrawerItem().withNameShown(true).withIdentifier(2);
			Picasso.with(HomeActivity.this).load(userpic).into(new Target() {
				@Override
				public void onPrepareLoad(Drawable drawable) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
					usericon = new BitmapDrawable(getBaseContext().getResources(),bitmap);
					user.setIcon(usericon);
					
				}
				
				@Override
				public void onBitmapFailed(Drawable drawable) {
					// TODO Auto-generated method stub
					
				}
			});
			user.setName(username);
			user.setEmail(useremail);
			user.setIcon(usericon);
			
			user = new ProfileDrawerItem().withNameShown(true).withName(username).withEmail(useremail).withIcon(usericon).withIdentifier(2);
		}
			// ----------------------------------
	        headerResult = new AccountHeader()
	                .withActivity(this)
	                .withProfileImagesClickable(true)
	                .withHeaderBackground(R.drawable.flat_cover)//google title pic here
	                .addProfiles(
	                        user,
	                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
//	                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
	                        new ProfileSettingDrawerItem().withName(getResources().getString(R.string.logout)).withIcon(getResources().getDrawable(R.drawable.logout)).withIdentifier(PROFILE_SETTING)
	                )
	                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
	                    @Override
	                    public void onProfileChanged(View view, IProfile profile) {
	                        //sample usage of the onProfileChanged listener
	                        //if the clicked item has the identifier 1 add a new profile ;)
	                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == 2) {
	                        	//TODO open ProfilActivity here
	                        	loadPrefs();
	                        	if (loggedout == true) {
	                    			showLoginDialog();
	                        	}else{
	                				//TODO intent Profilactivity
	                        		Toast.makeText(HomeActivity.this, "Profil opens here", Toast.LENGTH_SHORT).show();
	                			}
	                		
	                        	
	                        }
	                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
	                        	Toast.makeText(HomeActivity.this, "Log Out HERE", Toast.LENGTH_SHORT).show();
	                        	savePrefs("LOGGEDOUT", true);
	                        	//TODO log out user from googlePlay and start App with loginactivity?
	                        	mSignInClicked = false; 
	                        	mExplicitSignOut = true;
	                             if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
	                                 Games.signOut(mGoogleApiClient);
	                                 mGoogleApiClient.disconnect();
	                                 saveUser("NAME", "username");
	                     			 saveUser("EMAIL", "user@gmail.com");
	                     			 saveUser("PIC", null);
	                     			 user.setEmail("user@gmail.com");
	                     			 user.setName("Username");
	                     			 user.setIcon(getResources().getDrawable(R.drawable.profilepic));
	                             }
	                        }
	                        
	                    }
						
	                })
	                .withSavedInstance(savedInstanceState)
	                .build();
			
//		//**********************************************	
//			
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		toolbartitle.setText(getResources().getString(R.string.app_name));
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				openSearch();			
				return true;
			}
		});

		//Create the drawer
        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.explore)).withIcon(getResources().getDrawable(R.drawable.compass)).withIdentifier(Category.LAST.id),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.favorite)).withIcon(getResources().getDrawable(R.drawable.star)).withIdentifier(Category.FAVORITE.id),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.quest)).withIcon(getResources().getDrawable(R.drawable.quest)).withIdentifier(Category.QUEST.id),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.trophy)).withIcon(getResources().getDrawable(R.drawable.trophy)).withIdentifier(4),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.highscore)).withIcon(getResources().getDrawable(R.drawable.score)).withIdentifier(5),
                        new SectionDrawerItem().withName(getResources().getString(R.string.pref)),
                        new SecondaryDrawerItem().withName(getResources().getString(R.string.setting)).withIcon(getResources().getDrawable(R.drawable.settings)).withIdentifier(6),
                        new SecondaryDrawerItem().withName(getResources().getString(R.string.help)).withIcon(FontAwesomeFont.Icon.faw_question).withIdentifier(7).setEnabled(false),
                        new DividerDrawerItem()
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        
                    	//INFO this onclick Listener changes the Content with the FilterInterface.
                    	// If we Work without this Filter we need 3 or more Fragments.
                    	
                        if (drawerItem != null) {
                        	
                        	switch (drawerItem.getIdentifier()) {
							case (1000):
//								fragmentManager.beginTransaction().remove(testfragment);
//								fragmentManager.popBackStack();
								mState = SHOW_MENU;
                            	invalidateOptionsMenu();
                            	if (onFilterChangedListener != null) {
                                    onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
                                }
                            	toolbartitle.setText(getResources().getString(R.string.explore));
								break;
							case 1001:
//								fragmentManager.beginTransaction().remove(testfragment);
//								fragmentManager.popBackStack();
								mState = HIDE_MENU;
								invalidateOptionsMenu();
                            	if (onFilterChangedListener != null) {
                                    onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
                                }
                            	toolbartitle.setText(getResources().getString(R.string.favorite));
                            	break;
							case 1002:
//								fragmentManager.beginTransaction().remove(testfragment);
//								fragmentManager.popBackStack();
								mState = HIDE_MENU;
								invalidateOptionsMenu();
								if (onFilterChangedListener != null) {
	                                onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
	                            }
								toolbartitle.setText(getResources().getString(R.string.quest));
                            	break;	
							case 4:
								if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
									startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), REQUEST_ACHIEVEMENTS);
								}else{
									showLoginDialog();
								}
								break;
							case 5:
								if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
									startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
								        getResources().getString(R.string.leaderboard)), REQUEST_LEADERBOARD);
								}else{
									showLoginDialog();
								}
								break;
							default:
								break;
							}
                        	
                        }
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        
        //disable Scrollbar
        result.getListView().setVerticalScrollBarEnabled(false);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 1
            result.setSelectionByIdentifier(1000, true);
            toolbartitle.setText(getResources().getString(R.string.app_name));
            //set the active profile
            headerResult.setActiveProfile(user);
        }
		
		

		// REVEAL CHANGE LAYOUT
//		mViewAnimator = (ViewRevealAnimator) findViewById(R.id.animator);
//		mViewAnimator.setHideBeforeReveal(false);

	}// endOnCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
	
	 if (mState == HIDE_MENU)
	    {
	        for (int i = 0; i < menu.size(); i++)
	            menu.getItem(i).setVisible(false);
	    }
	 
	 return true;
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
	
    public void showLoginDialog() {
    	MaterialDialog dialog = new MaterialDialog.Builder(HomeActivity.this)
        .title(R.string.pleaseLogin)
        .content(R.string.login_neccessary)
        .iconRes(R.drawable.game_green)
        .positiveText(R.string.login)
        .negativeText(android.R.string.cancel)
        .callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
            	mSignInClicked = true;
                mGoogleApiClient.connect();
            }
            @Override
            public void onNegative(MaterialDialog dialog) {
            	
            }
        }).build();
		positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
		
        dialog.show();
        positiveAction.setEnabled(true);
	     
	}	
	
	public void openSearch() {
		toolbartitle.setText("");
		searchbox.revealFromMenuItem(R.id.action_search, this);
//		for (int x = 0; x < 10; x++) {
//			SearchResult option = new SearchResult("Result "
//					+ Integer.toString(x), getResources().getDrawable(
//					R.drawable.trophy));
//			searchbox.addSearchable(option);
//		}
		searchbox.setMenuListener(new MenuListener() {

			@Override
			public void onMenuClick() {
			}

		});
		searchbox.setSearchListener(new SearchListener() {

			@Override
			public void onSearchOpened() {
				// Use this to tint the screen

			}

			@Override
			public void onSearchClosed() {
				// Use this to un-tint the screen
				closeSearch();
				toolbartitle.setText(getResources().getString(R.string.app_name));

			}

			@Override
			public void onSearchTermChanged() {
				// React to the search term changing
				// Called after it has updated results
			}

			@Override
			public void onSearch(String searchTerm) {
				Toast.makeText(HomeActivity.this, searchTerm + " Searched",
						Toast.LENGTH_LONG).show();
				
//				startFragment with searched_content here
				
//				toolbartitle.setText(searchTerm);

			}

			@Override
			public void onSearchCleared() {
				
			}

		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1234 && resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			searchbox.populateEditText(matches);
		}
		if (requestCode == RC_SIGN_IN) {
	        mSignInClicked = false;
	        mResolvingConnectionFailure = false;
	        if (resultCode == RESULT_OK) {
	            mGoogleApiClient.connect();
	        } else {
	            // Bring up an error dialog to alert the user that sign-in
	            // failed. The R.string.signin_failure should reference an error
	            // string in your strings.xml file that tells the user they
	            // could not be signed in, such as "Unable to sign in."
	            BaseGameUtils.showActivityResultError(this,
	                requestCode, resultCode, R.string.signin_failure);
	        }
	    }
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void mic(View v) {
		searchbox.micClick(this);
	}

	protected void closeSearch() {
		searchbox.hideCircularly(this);
		if(searchbox.getSearchText().isEmpty())toolbar.setTitle("");
	}

	public interface OnFilterChangedListener {
        public void onFilterChanged(int filter);
    }
	
	
	private void loadPrefs() {
		SharedPreferences sp = getSharedPreferences("INIT", MODE_APPEND);
		loggedout = sp.getBoolean("LOGGEDOUT", true);
		firststart = sp.getBoolean("FIRST", true);
	}

	private void savePrefs(String key, boolean value) {
		SharedPreferences sp = getSharedPreferences("INIT", MODE_APPEND);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}
	
	private void saveUser(String key, String value) {
		SharedPreferences sp = getSharedPreferences("User", MODE_APPEND);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	private void loadUser() {
		SharedPreferences sp = getSharedPreferences("User", MODE_APPEND);
		username = sp.getString("NAME", "username");
		useremail = sp.getString("EMAIL", "username@gmail.com");
		userpic = sp.getString("PIC", null);
	}

	public void setUserName(String Uname) {
		username=Uname;
		user.setName(username);
	}

	public void setUserEmail(String Uemail) {
		useremail=Uemail;
		user.setEmail(useremail);		
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------		Google Play Games Functions			-------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------
	
	
	@Override
	protected void onStart() {
	    super.onStart();
	    if (!mInSignInFlow && !mExplicitSignOut&&!firststart) {
	        // auto sign in
	        mGoogleApiClient.connect();
	    }
	}

	@Override
	protected void onStop() {
	    super.onStop();
	    mGoogleApiClient.disconnect();
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {
	    // The player is signed in. Hide the sign-in button and allow the
	    // player to proceed.
		savePrefs("LOGGEDOUT", false);
		if(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null){
			Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
			currentPerson.getCover();
			username = currentPerson.getDisplayName();
			
			user.setName(username);
			useremail = Games.getCurrentAccountName(mGoogleApiClient);
			user.setEmail(useremail);
			Picasso.with(HomeActivity.this).load(currentPerson.getImage().getUrl()).into(new Target() {
				@Override
				public void onPrepareLoad(Drawable drawable) {
				}
				
				@Override
				public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
					Drawable icon = new BitmapDrawable(getBaseContext().getResources(),bitmap);
					user.setIcon(icon);
					
				}
				
				@Override
				public void onBitmapFailed(Drawable drawable) {
					
				}
			});
			saveUser("NAME", username);
			saveUser("EMAIL", useremail);
			saveUser("PIC", currentPerson.getImage().getUrl());
		}	
			
		if(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null){
            Person mCurrentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String displayName = mCurrentPerson.getDisplayName();
            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE", "DisplayName: "+displayName);
            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE","Id: "+ mCurrentPerson.getId());
            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE","Image: "+mCurrentPerson.getImage().getUrl());
            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE","List of Urls: "+mCurrentPerson.getUrls());
            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE","Current Url: "+mCurrentPerson.getUrl());
//            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE","Cover: "+mCurrentPerson.g
            Log.d("LOG_TAG_LOAD_VISIBLE_PEOPLE","Object type: "+mCurrentPerson.getObjectType());
        }
		
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    if (mResolvingConnectionFailure) {
	        // already resolving
	        return;
	    }

	    // if the sign-in button was clicked or if auto sign-in is enabled,
	    // launch the sign-in flow
	    if (mSignInClicked || mAutoStartSignInflow) {
	        mAutoStartSignInflow = false;
	        mSignInClicked = false;
	        mResolvingConnectionFailure = true;

	        // Attempt to resolve the connection failure using BaseGameUtils.
	        // The R.string.signin_other_error value should reference a generic
	        // error string in your strings.xml file, such as "There was
	        // an issue with sign-in, please try again later."
	        if (!BaseGameUtils.resolveConnectionFailure(this,
	                mGoogleApiClient, connectionResult,
	                RC_SIGN_IN, getResources().getString(R.string.signin_other_error))) {
	            mResolvingConnectionFailure = false;
	        }
	    }

	    // Put code here to display the sign-in button
	}

	@Override
	public void onConnectionSuspended(int i) {
	    // Attempt to reconnect
	    mGoogleApiClient.connect();
	    Games.Players.getCurrentPlayer(mGoogleApiClient);
	    
	}
	
//	final static String MY_ACHIEVEMEMENT_ID = "...."; // your achievement ID here

//	if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//	    // Call a Play Game services API method, for example:
//	    Games.Achievements.unlock(mGoogleApiClient, "MY_ACHIEVEMENT_ID");
	
//		Games.Leaderboards.submitScore(mGoogleApiClient, R.ids.leadboard, score);
	
//	} else {
//	    // Alternative implementation (or warn user that they must
//	    // sign in to use this feature)
//	}

}






