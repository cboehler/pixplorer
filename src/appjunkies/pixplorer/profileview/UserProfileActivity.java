package appjunkies.pixplorer.profileview;

/**
 * Created by Appjunkies on 12.05.2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import appjunkies.pixplorer.R;
import butterknife.InjectView;
import appjunkies.pixplorer.activities.HomeActivity;
import appjunkies.pixplorer.profileview.BaseActivity;

public class UserProfileActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @InjectView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @InjectView(R.id.rvUserProfile)
    RecyclerView rvUserProfile;

    private UserProfileAdapter userPhotosAdapter;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_user_profile_header, container, false);

        //GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        GoogleMap map = ((MapFragment) v.findViewById(R.id.map)).getMap();
        float zoom = 15.0f;
        Location location = null;
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, zoom));
        map.addMarker(new MarkerOptions().position(currentPosition));
        return v;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setupUserProfileGrid();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupRevealBackground(savedInstanceState);       
        
        /*GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        float zoom = 15.0f;
        Location location = null;
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, zoom));
        map.addMarker(new MarkerOptions().position(currentPosition));*/

    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
            userPhotosAdapter.setLockedAnimations(true);
        }
    }

    private void setupUserProfileGrid() {
        final StaggeredGridLayoutManager layoutManager;
        if (getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_0){
            layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }else{
            layoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL);
        }
//        rvUserProfile = (RecyclerView)findViewById(R.id.rvUserProfile);
        rvUserProfile.setLayoutManager(layoutManager);
        rvUserProfile.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                userPhotosAdapter.setLockedAnimations(true);
            }
        });
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            rvUserProfile.setVisibility(View.VISIBLE);
            userPhotosAdapter = new UserProfileAdapter(this);
            rvUserProfile.setAdapter(userPhotosAdapter);
        } else {
            rvUserProfile.setVisibility(View.INVISIBLE);
        }
    }
}