//package appjunkies.pixplorer.activities;
//
//import java.util.ArrayList;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.speech.RecognizerIntent;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.TextView;
//import android.widget.Toast;
//import appjunkies.pixplorer.R;
//import appjunkies.pixplorer.fragments.Explore;
//import appjunkies.pixplorer.fragments.Favorite;
//import appjunkies.pixplorer.other.FontAwesomeFont;
//import appjunkies.pixplorer.search.SearchBox;
//import appjunkies.pixplorer.search.SearchBox.MenuListener;
//import appjunkies.pixplorer.search.SearchBox.SearchListener;
//
//import com.mikepenz.materialdrawer.Drawer;
//import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
//import com.mikepenz.materialdrawer.model.DividerDrawerItem;
//import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
//import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
//import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
//import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
//import com.mikepenz.materialdrawer.model.SectionDrawerItem;
//import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
//import com.mikepenz.materialdrawer.model.interfaces.IProfile;
//import com.mikepenz.materialdrawer.model.interfaces.Nameable;
//
///**
// * @author appjunkies
// *
// */
//public class HomeActivity extends ActionBarActivity 
////		implements ViewRevealAnimator.OnViewChangedListener,ViewRevealAnimator.OnViewAnimationListener 
//{
//	public enum Category {
//        ALL(1000),
//        FEATURED(1001),
//        FAVORITE(1002);
////        TODO USE this IDs in for Serach and show only places from Category 
////        BUILDINGS(1),
////        RESTAURANT(2),
////        NATURE(4),
////        PEOPLE(8),
////        TECHNOLOGY(16),
////        OBJECTS(32);
//        
//        //QUESTION For Using only 1 Fragment it is neccessary to download the last places + favorites + specialplaces at the same time 
////        otherwise we need 3 extra fragments.
//
//        public final int id;
//
//        private Category(int id) {
//            this.id = id;
//        }
//    }
//	
//	private static final int PROFILE_SETTING = 1;
//	
//	private static final int HIDE_MENU = 1483;
//	private static final int SHOW_MENU = 3841;
//	int mState=0;
//	//save our header or result
//    private AccountHeader.Result headerResult = null;
//    public Drawer.Result result = null;
//    private IProfile user;
//    
//	private SearchBox searchbox;
//
//	Toolbar toolbar;
//	TextView toolbartitle;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.drawer_main);
//
////		//INITS findViewById***********************************************
////			shuffleFab = (FloatingActionButton) findViewById(R.id.shuffleFab);
//			toolbar = (Toolbar) findViewById(R.id.toolbar);
//			toolbartitle = (TextView) toolbar.findViewById(R.id.toolbartitle);
//			searchbox = (SearchBox) findViewById(R.id.searchbox);
////
////		
////		// Create the AccountHeader*****************************************************************
////		QUESTION get Account from Shared Prefs ?? 
////		QUESTION ask if Login is neccesary or optional	
//			user = new ProfileDrawerItem().withName("Username").withNameShown(true).withEmail("username@gmail.com").withIcon(getResources().getDrawable(R.drawable.profilepic)).withIdentifier(2);
//			
//			// ----------------------------------
//	        headerResult = new AccountHeader()
//	                .withActivity(this)
//	                .withProfileImagesClickable(true)
//	                .withHeaderBackground(R.drawable.flat_cover)//google title pic here
//	                .addProfiles(
//	                        user,
//	                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
////	                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
//	                        new ProfileSettingDrawerItem().withName(getResources().getString(R.string.logout)).withIcon(getResources().getDrawable(R.drawable.logout)).withIdentifier(PROFILE_SETTING)
//	                )
//	                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//	                    @Override
//	                    public void onProfileChanged(View view, IProfile profile) {
//	                        //sample usage of the onProfileChanged listener
//	                        //if the clicked item has the identifier 1 add a new profile ;)
//	                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == 2) {
//	                        	Toast.makeText(HomeActivity.this, "Profil Clicked", Toast.LENGTH_SHORT).show();
//	                        	//TODO open ProfilActivity here
//	                        }
//	                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
//	                        	Toast.makeText(HomeActivity.this, "Log Out HERE", Toast.LENGTH_SHORT).show();
//	                        	//TODO log out user from googlePlay and start App with loginactivity?
//	                        	
//	                        	
////	                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.ambras));
////	                            if (headerResult.getProfiles() != null) {
////	                                //we know that there are 2 setting elements. set the new profile above them ;)
////	                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
////	                            } else {
////	                                headerResult.addProfiles(newProfile);
////	                            }
//	                        }
//	                        
//	                    }
//	                })
//	                .withSavedInstance(savedInstanceState)
//	                .build();
//			
////		//**********************************************	
////			
//		if (toolbar != null) {
//			setSupportActionBar(toolbar);
//		}
//		toolbartitle.setText(getResources().getString(R.string.app_name));
//		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				openSearch();			
//				return true;
//			}
//		});
//
//		//Create the drawer
//        result = new Drawer()
//                .withActivity(this)
//                .withToolbar(toolbar)
//                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
//                .addDrawerItems(
//                        new PrimaryDrawerItem().withName(getResources().getString(R.string.explore)).withIcon(getResources().getDrawable(R.drawable.compass)).withIdentifier(1),
//                        new PrimaryDrawerItem().withName(getResources().getString(R.string.favorite)).withIcon(getResources().getDrawable(R.drawable.star)).withIdentifier(2),
//                        new PrimaryDrawerItem().withName(getResources().getString(R.string.quest)).withIcon(getResources().getDrawable(R.drawable.quest)).withIdentifier(3),
//                        new PrimaryDrawerItem().withName(getResources().getString(R.string.trophy)).withIcon(getResources().getDrawable(R.drawable.trophy)).withIdentifier(4),
//                        new PrimaryDrawerItem().withName(getResources().getString(R.string.highscore)).withIcon(getResources().getDrawable(R.drawable.score)).withIdentifier(5),
//                        new SectionDrawerItem().withName(getResources().getString(R.string.pref)),
//                        new SecondaryDrawerItem().withName(getResources().getString(R.string.setting)).withIcon(getResources().getDrawable(R.drawable.settings)).withIdentifier(6),
//                        new SecondaryDrawerItem().withName(getResources().getString(R.string.help)).withIcon(FontAwesomeFont.Icon.faw_question).withIdentifier(7).setEnabled(false),
//                        new DividerDrawerItem()
//                ) // add the items we want to use with our Drawer
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
//                        
//                    	FragmentManager fragmentManager = getSupportFragmentManager();		
//                        if (drawerItem != null) {
//                        	
//                        	switch (drawerItem.getIdentifier()) {
//							case 1:
//								mState = SHOW_MENU;
//                            	invalidateOptionsMenu();
//                            	fragmentManager.beginTransaction().replace(R.id.content_frame, new Explore()).commit();
//                            	toolbartitle.setText(getResources().getString(R.string.explore));
//								break;
//							case 2:
//								invalidateOptionsMenu();
//                            	mState = HIDE_MENU;
//                            	fragmentManager.beginTransaction().replace(R.id.content_frame, new Favorite()).commit();
//                            	toolbartitle.setText(getResources().getString(R.string.favorite));
//                            	break;
//							case 3:
//								toolbartitle.setText(getResources().getString(R.string.quest));
//                            	break;	
//                            	
//							default:
//								break;
//							}
//                        	
//                        }
//                    }
//                })
//                .withSavedInstance(savedInstanceState)
//                .build();
//        
//        //disable Scrollbar
//        result.getListView().setVerticalScrollBarEnabled(false);
//
//        //only set the active selection or active profile if we do not recreate the activity
//        if (savedInstanceState == null) {
//            // set the selection to the item with the identifier 1
//            result.setSelectionByIdentifier(1, true);
//            toolbartitle.setText(getResources().getString(R.string.app_name));
//            //set the active profile
//            headerResult.setActiveProfile(user);
//        }
//		
//		
//
//		// REVEAL CHANGE LAYOUT
////		mViewAnimator = (ViewRevealAnimator) findViewById(R.id.animator);
////		mViewAnimator.setHideBeforeReveal(false);
//
//	}// endOnCreate
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
//		
//	
//	 if (mState == HIDE_MENU)
//	    {
//	        for (int i = 0; i < menu.size(); i++)
//	            menu.getItem(i).setVisible(false);
//	    }
//	 
//	 return true;
//	}
//	
//	@Override
//    protected void onSaveInstanceState(Bundle outState) {
//        //add the values which need to be saved from the drawer to the bundle
//        outState = result.saveInstanceState(outState);
//        //add the values which need to be saved from the accountHeader to the bundle
//        outState = headerResult.saveInstanceState(outState);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onBackPressed() {
//        //handle the back press :D close the drawer first and if the drawer is closed close the activity
//        if (result != null && result.isDrawerOpen()) {
//            result.closeDrawer();
//        } else {
//            super.onBackPressed();
//        }
//    }
//	
//	
////	// Reveal
////	private void reveal(View v, boolean showshuffle) {
////
////		View rootLayout = v.getRootView().findViewById(R.id.homelayout);
////		View reveallayout = v.getRootView().findViewById(R.id.shufflelist);
////		int[] viewLocation = new int[2];
////		v.getLocationOnScreen(viewLocation);
////		int[] rootLocation = new int[2];
////		reveallayout.getLocationOnScreen(rootLocation);
////		int relativeX = viewLocation[0] - rootLocation[0];
////		int relativeY = viewLocation[1] - rootLocation[1];
////
////		mViewAnimator.setStartPoint(relativeX + 50, relativeY + 50);
////		if (!showshuffle) {
////			mViewAnimator.showPrevious();
////		} else {
//////			mViewAnimator.showNext();
////			reveallayout.setVisibility(View.VISIBLE);
////			int finalRadius = (int) Math.max(rootLayout.getWidth(), rootLayout.getHeight());
////			SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
////					reveallayout, relativeX+50, relativeY+50, 0, finalRadius);
////			animator.setInterpolator(new AccelerateDecelerateInterpolator());
////			animator.setDuration(500);
////			animator.addListener(new SupportAnimator.AnimatorListener(){
////
////				@Override
////				public void onAnimationCancel() {
////					
////				}
////
////				@Override
////				public void onAnimationEnd() {	
////					
////				}
////
////				@Override
////				public void onAnimationRepeat() {
////					
////				}
////
////				@Override
////				public void onAnimationStart() {
////					
////				}
////				
////			});
////			animator.start();
////		}
////		mViewAnimator.setOnViewChangedListener(this);
////		mViewAnimator.setOnViewAnimationListener(this);
////
////	}
////
////	@Override
////	public void onViewAnimationStarted(int previousIndex, int currentIndex) {
////		// Log.d("showPlace", "onViewanimationStarted(" + previousIndex + ":" +
////		// currentIndex + ")");
////
////	}
////
////	@Override
////	public void onViewAnimationCompleted(int previousIndex, int currentIndex) {
////		// Log.d("showPlace", "onViewanimationCompleted(" + previousIndex + ":"
////		// + currentIndex + ")");
////
////	}
////
////	@Override
////	public void onViewChanged(int previousIndex, int currentIndex) {
////		// Log.d("showPlace", "onViewChanged(" + previousIndex + ":" +
////		// currentIndex + ")");
////
////	}
//	
//	
//	public void openSearch() {
//		toolbartitle.setText("");
//		searchbox.revealFromMenuItem(R.id.action_search, this);
////		for (int x = 0; x < 10; x++) {
////			SearchResult option = new SearchResult("Result "
////					+ Integer.toString(x), getResources().getDrawable(
////					R.drawable.trophy));
////			searchbox.addSearchable(option);
////		}
//		searchbox.setMenuListener(new MenuListener() {
//
//			@Override
//			public void onMenuClick() {
//			}
//
//		});
//		searchbox.setSearchListener(new SearchListener() {
//
//			@Override
//			public void onSearchOpened() {
//				// Use this to tint the screen
//
//			}
//
//			@Override
//			public void onSearchClosed() {
//				// Use this to un-tint the screen
//				closeSearch();
//				toolbartitle.setText(getResources().getString(R.string.app_name));
//
//			}
//
//			@Override
//			public void onSearchTermChanged() {
//				// React to the search term changing
//				// Called after it has updated results
//			}
//
//			@Override
//			public void onSearch(String searchTerm) {
//				Toast.makeText(HomeActivity.this, searchTerm + " Searched",
//						Toast.LENGTH_LONG).show();
//				
////				startFragment with searched_content here
//				
////				toolbartitle.setText(searchTerm);
//
//			}
//
//			@Override
//			public void onSearchCleared() {
//				
//			}
//
//		});
//
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == 1234 && resultCode == RESULT_OK) {
//			ArrayList<String> matches = data
//					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//			searchbox.populateEditText(matches);
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//
//	public void mic(View v) {
//		searchbox.micClick(this);
//	}
//
//	protected void closeSearch() {
//		searchbox.hideCircularly(this);
//		if(searchbox.getSearchText().isEmpty())toolbar.setTitle("");
//	}
//
//	
//
//}
//



package appjunkies.pixplorer.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import appjunkies.pixplorer.R;
import appjunkies.pixplorer.database.DataPlaces;
import appjunkies.pixplorer.fragments.Explore;
import appjunkies.pixplorer.fragments.TestFragment;
import appjunkies.pixplorer.model.Place;
import appjunkies.pixplorer.other.FontAwesomeFont;
import appjunkies.pixplorer.search.SearchBox;
import appjunkies.pixplorer.search.SearchBox.MenuListener;
import appjunkies.pixplorer.search.SearchBox.SearchListener;

import com.mikepenz.iconics.IconicsDrawable;
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

/**
 * @author appjunkies
 *
 */
public class HomeActivity extends ActionBarActivity 
//		implements ViewRevealAnimator.OnViewChangedListener,ViewRevealAnimator.OnViewAnimationListener 
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
	
	private OnFilterChangedListener onFilterChangedListener;

    public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }
	
	private static final int PROFILE_SETTING = 1;
	
	private static final int HIDE_MENU = 1483;
	private static final int SHOW_MENU = 3841;
	int mState=0;
	//save our header or result
    private AccountHeader.Result headerResult = null;
    public Drawer.Result result = null;
    private IProfile user;
    
    FragmentManager fragmentManager;
    
	private SearchBox searchbox;

	Toolbar toolbar;
	TextView toolbartitle;

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
//		
			
		fragmentManager = getSupportFragmentManager();			
		fragmentManager.beginTransaction().replace(R.id.content_frame, new Explore()).commit();
			
		final Fragment testfragment = new TestFragment();	
				
//		// Create the AccountHeader*****************************************************************
//		QUESTION get Account from Shared Prefs ?? 
//		QUESTION ask if Login is neccesary or optional	
			user = new ProfileDrawerItem().withName("Username").withNameShown(true).withEmail("username@gmail.com").withIcon(getResources().getDrawable(R.drawable.profilepic)).withIdentifier(2);
			
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
	                        	Toast.makeText(HomeActivity.this, "Profil Clicked", Toast.LENGTH_SHORT).show();
	                        	//TODO open ProfilActivity here
	                        }
	                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
	                        	Toast.makeText(HomeActivity.this, "Log Out HERE", Toast.LENGTH_SHORT).show();
	                        	//TODO log out user from googlePlay and start App with loginactivity?
	                        	
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
								fragmentManager.beginTransaction().remove(testfragment);
								fragmentManager.popBackStack();
								mState = SHOW_MENU;
                            	invalidateOptionsMenu();
                            	if (onFilterChangedListener != null) {
                                    onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
                                }
                            	toolbartitle.setText(getResources().getString(R.string.explore));
								break;
							case 1001:
								fragmentManager.beginTransaction().remove(testfragment);
								fragmentManager.popBackStack();
								mState = HIDE_MENU;
								invalidateOptionsMenu();
                            	if (onFilterChangedListener != null) {
                                    onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
                                }
                            	toolbartitle.setText(getResources().getString(R.string.favorite));
                            	break;
							case 1002:
								fragmentManager.beginTransaction().remove(testfragment);
								fragmentManager.popBackStack();
								mState = HIDE_MENU;
								invalidateOptionsMenu();
								if (onFilterChangedListener != null) {
	                                onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
	                            }
								toolbartitle.setText(getResources().getString(R.string.quest));
                            	break;	
							case 4:
								fragmentManager.beginTransaction().add(R.id.content_frame,testfragment).addToBackStack("Trophy").commit();
								break;
							case 5:
								fragmentManager.beginTransaction().add(R.id.content_frame,testfragment).addToBackStack("Highscore").commit();
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
	
	
//	// Reveal
//	private void reveal(View v, boolean showshuffle) {
//
//		View rootLayout = v.getRootView().findViewById(R.id.homelayout);
//		View reveallayout = v.getRootView().findViewById(R.id.shufflelist);
//		int[] viewLocation = new int[2];
//		v.getLocationOnScreen(viewLocation);
//		int[] rootLocation = new int[2];
//		reveallayout.getLocationOnScreen(rootLocation);
//		int relativeX = viewLocation[0] - rootLocation[0];
//		int relativeY = viewLocation[1] - rootLocation[1];
//
//		mViewAnimator.setStartPoint(relativeX + 50, relativeY + 50);
//		if (!showshuffle) {
//			mViewAnimator.showPrevious();
//		} else {
////			mViewAnimator.showNext();
//			reveallayout.setVisibility(View.VISIBLE);
//			int finalRadius = (int) Math.max(rootLayout.getWidth(), rootLayout.getHeight());
//			SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
//					reveallayout, relativeX+50, relativeY+50, 0, finalRadius);
//			animator.setInterpolator(new AccelerateDecelerateInterpolator());
//			animator.setDuration(500);
//			animator.addListener(new SupportAnimator.AnimatorListener(){
//
//				@Override
//				public void onAnimationCancel() {
//					
//				}
//
//				@Override
//				public void onAnimationEnd() {	
//					
//				}
//
//				@Override
//				public void onAnimationRepeat() {
//					
//				}
//
//				@Override
//				public void onAnimationStart() {
//					
//				}
//				
//			});
//			animator.start();
//		}
//		mViewAnimator.setOnViewChangedListener(this);
//		mViewAnimator.setOnViewAnimationListener(this);
//
//	}
//
//	@Override
//	public void onViewAnimationStarted(int previousIndex, int currentIndex) {
//		// Log.d("showPlace", "onViewanimationStarted(" + previousIndex + ":" +
//		// currentIndex + ")");
//
//	}
//
//	@Override
//	public void onViewAnimationCompleted(int previousIndex, int currentIndex) {
//		// Log.d("showPlace", "onViewanimationCompleted(" + previousIndex + ":"
//		// + currentIndex + ")");
//
//	}
//
//	@Override
//	public void onViewChanged(int previousIndex, int currentIndex) {
//		// Log.d("showPlace", "onViewChanged(" + previousIndex + ":" +
//		// currentIndex + ")");
//
//	}
	
	
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
	

}


