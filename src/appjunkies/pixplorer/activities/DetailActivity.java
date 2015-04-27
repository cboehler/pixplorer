package appjunkies.pixplorer.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import appjunkies.pixplorer.R;
import appjunkies.pixplorer.database.DataPlaces;
import appjunkies.pixplorer.fragments.Explore;
import appjunkies.pixplorer.model.Place;
import appjunkies.pixplorer.other.CustomAnimatorListener;
import appjunkies.pixplorer.other.CustomTransitionListener;
import appjunkies.pixplorer.other.FontAwesomeFont;
import appjunkies.pixplorer.other.GoogleMaterialFont;
import appjunkies.pixplorer.other.PaletteTransformation;
import appjunkies.pixplorer.other.Utils;

import com.koushikdutta.ion.ProgressCallback;
import com.koushikdutta.ion.future.ResponseFuture;
import com.mikepenz.iconics.IconicsDrawable;
//import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * @author appjunkies
 *
 */
public class DetailActivity extends ActionBarActivity {
    private static final int ACTIVITY_CROP = 13451;
    private static final int ACTIVITY_SHARE = 13452;

    private static final int ANIMATION_DURATION_SHORT = 150;
    private static final int ANIMATION_DURATION_MEDIUM = 300;
    private static final int ANIMATION_DURATION_LONG = 450;
    private static final int ANIMATION_DURATION_EXTRA_LONG = 850;

    private ImageView mFabPhoto;
    private ImageView mFabFavorite;
    private ImageView mFabSpecial;
    private View mTitleContainer;
    private View mTitlesContainer;
    private Place mSelectedImage;

    private Drawable mDrawablePhoto;
    private Drawable mDrawableClose;
    private Drawable mDrawableSuccess;
    private Drawable mDrawableError;

    private ResponseFuture<InputStream> future = null;

    private int mWallpaperWidth;
    private int mWallpaperHeight;

    private Animation mProgressFabAnimation;

    boolean featured= false;
    boolean ISfavorite =false;
    
    public interface Listener {
		public void givePoints(int mpoints);
    }
    
    Listener mListener = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //get the desired wallpaper size so older phones won't die :D
        mWallpaperWidth = WallpaperManager.getInstance(DetailActivity.this).getDesiredMinimumWidth();
        mWallpaperHeight = WallpaperManager.getInstance(DetailActivity.this).getDesiredMinimumHeight();

        // Recover items from the intent
        final int position = getIntent().getIntExtra("position", 0);
        mSelectedImage = (Place) getIntent().getSerializableExtra("selected_image");

        mDrawablePhoto = new IconicsDrawable(this, FontAwesomeFont.Icon.faw_camera).color(Color.WHITE).sizeDp(24);
        mDrawableClose = new IconicsDrawable(this, GoogleMaterialFont.Icon.gmd_close).color(Color.WHITE).sizeDp(24);
        mDrawableSuccess = new IconicsDrawable(this, GoogleMaterialFont.Icon.gmd_check).color(Color.WHITE).sizeDp(24);
        mDrawableError = new IconicsDrawable(this, FontAwesomeFont.Icon.faw_exclamation).color(Color.WHITE).sizeDp(24);
        
        mTitlesContainer = findViewById(R.id.activity_detail_titles);

        // Fab progress
//        mFabProgress = (DonutProgress) findViewById(R.id.activity_detail_progress);
//        mFabProgress.setMax(100);
//        mFabProgress.setScaleX(0);
//        mFabProgress.setScaleY(0);

        // Fab button
        mFabPhoto = (ImageView) findViewById(R.id.activity_detail_fab);
        mFabPhoto.setScaleX(0);
        mFabPhoto.setScaleY(0);
        mFabPhoto.setImageDrawable(mDrawablePhoto);
        mFabPhoto.setOnClickListener(onFabButtonListener);
        //just allow the longClickAction on Devices newer than api level v19
        if (Build.VERSION.SDK_INT >= 19) {
            mFabPhoto.setOnLongClickListener(onFabButtonLongListener);
        }

        // Fab Favorite button
        mFabFavorite = (ImageView) findViewById(R.id.activity_detail_fab_share);
        mFabFavorite.setScaleX(0);
        mFabFavorite.setScaleY(0);
        mFabFavorite.setOnClickListener(onFabFavoriteButtonListener);
        DataPlaces favorites = new DataPlaces(DetailActivity.this);
		favorites.open();
		
		int favcount =favorites.getFavoriteCount();
		if (favcount>0) {
			List<Place> favplaces = favorites.getFavorites();
			for (int i = 0; i < favcount; i++) {
				System.out.println("all Fav: "+favplaces.get(i).getID());	
				System.out.println("mselectedInit: "+mSelectedImage.getID());	
				if (mSelectedImage.getID()==favplaces.get(i).getID()){
					mFabFavorite.setImageDrawable(new IconicsDrawable(this,
							FontAwesomeFont.Icon.faw_heart).color(
							getResources().getColor(R.color.white)).sizeDp(20));
					ISfavorite=true;
					System.out.println("favID: "+favplaces.get(i).getID());	
					break;
				}
				else{
					mFabFavorite.setImageDrawable(new IconicsDrawable(this,
							FontAwesomeFont.Icon.faw_heart_o).color(
							getResources().getColor(R.color.white)).sizeDp(16));
					ISfavorite=false;
					
				}
			}
		}else{
			mFabFavorite.setImageDrawable(new IconicsDrawable(this,
					FontAwesomeFont.Icon.faw_heart_o).color(
					getResources().getColor(R.color.white)).sizeDp(16));
			ISfavorite=false;
		}
		
		favorites.close();
        
        

        // Fab Featured button
        mFabSpecial = (ImageView) findViewById(R.id.activity_detail_fab_download);
        mFabSpecial.setScaleX(0);
        mFabSpecial.setScaleY(0);
		mFabSpecial.setEnabled(true);
		if (mSelectedImage.getFeatured()==true){
					mFabSpecial.setImageDrawable(new IconicsDrawable(this,
							GoogleMaterialFont.Icon.gmd_star).color(
							getResources().getColor(R.color.white)).sizeDp(20));
					featured=true;
		}
		else{
			mFabSpecial.setImageDrawable(new IconicsDrawable(this,
					GoogleMaterialFont.Icon.gmd_star_outline).color(
					getResources().getColor(R.color.white)).sizeDp(16));
			featured=false;
			
		}
        mFabSpecial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(featured){
					Toast.makeText(DetailActivity.this, "This is a featured Place!", Toast.LENGTH_SHORT).show();
					mFabSpecial.setClickable(false);
				}else{
					Toast.makeText(DetailActivity.this, "This place is not featured!", Toast.LENGTH_SHORT).show();
					mFabSpecial.setClickable(false);
				}
			}
		});
		
//        mFabSpecial.setOnLongClickListener(onFabDownloadButtonLongListener);

        
        if(ISfavorite){
        	animateFavoriteSuccess();
        }
        
        
        // Title container
        mTitleContainer = findViewById(R.id.activity_detail_title_container);
        Utils.configuredHideYView(mTitleContainer);

        // Define toolbar as the shared element
        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_detail_toolbar);
        setSupportActionBar(toolbar);

        //get the imageHeader and set the coverImage
        final ImageView image = (ImageView) findViewById(R.id.activity_detail_image);
        Bitmap imageCoverBitmap = Explore.photoCache.get(position);
        //safety check to prevent nullPointer in the palette if the detailActivity was in the background for too long
        if (imageCoverBitmap == null || imageCoverBitmap.isRecycled()) {
            this.finish();
            return;
        }
        image.setImageBitmap(imageCoverBitmap);

        //override text
        setTitle("");

        if (Build.VERSION.SDK_INT >= 21) {
            image.setTransitionName("cover");
            // Add a listener to get noticed when the transition ends to animate the fab button
            getWindow().getSharedElementEnterTransition().addListener(new CustomTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    animateActivityStart();
                }
            });
        } else {
            Utils.showViewByScale(image).setDuration(ANIMATION_DURATION_LONG).start();
            animateActivityStart();
        }

        //check if we already had the colors during click
        int swatch_title_text_color = getIntent().getIntExtra("swatch_title_text_color", -1);
        int swatch_rgb = getIntent().getIntExtra("swatch_rgb", -1);

        if (swatch_rgb != -1 && swatch_title_text_color != -1) {
            setColors(swatch_title_text_color, swatch_rgb);
        } else {
            // Generate palette colors
            Palette palette = PaletteTransformation.getPalette(imageCoverBitmap);
            if (palette != null) {
                Palette.Swatch s = palette.getVibrantSwatch();
                if (s == null) {
                    s = palette.getDarkVibrantSwatch();
                }
                if (s == null) {
                    s = palette.getLightVibrantSwatch();
                }
                if (s == null) {
                    s = palette.getMutedSwatch();
                }

                if (s != null) {
                    setColors(s.getTitleTextColor(), s.getRgb());
                }
            }
        }

        final SharedPreferences sp = getSharedPreferences("pixplorer", Context.MODE_PRIVATE);
        if (!sp.getBoolean("help-understand", false)) {
        	
        }
    }

    private View.OnClickListener onFabFavoriteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (future == null) {
////                prepare the call
//                future = Ion.with(DetailActivity.this)
//                        .load(mSelectedImage.getHighResImage(mWallpaperWidth, mWallpaperHeight))
//                        .progressHandler(progressCallback)
//                        .asInputStream();
//
        		DataPlaces favorites = new DataPlaces(DetailActivity.this);
        		favorites.open();
        		int favcount =favorites.getFavoriteCount();
        		boolean delete= false;
        		int delID =-1;
        		if (favcount>0) {
					List<Place> favplaces = favorites.getFavorites();
					for (int i = 0; i < favcount; i++) {
						
						System.out.println("all onclickIDs: "+favplaces.get(i).getID());	
						System.out.println("mselected ID: "+mSelectedImage.getID());
						if (favplaces.get(i).getID()==mSelectedImage.getID()){
							delete =true;
							delID=favplaces.get(i).getID();
							System.out.println("deletedID "+delID);
						}
						else{
							delete =false;
							
						}
					}
				}else{
					favorites.addFavoritePlace(mSelectedImage);
					mSelectedImage.setFavorite(true);
					Toast.makeText(DetailActivity.this, "ADDED TO FAVORITES", Toast.LENGTH_SHORT).show();
					animateFavoriteSuccess();
				}
        		
        		if(delete ==true){
        			favorites.deleteFavorite(delID);
        			mSelectedImage.setFavorite(false);
        			Toast.makeText(DetailActivity.this, "DELETE FROM FAVORITES", Toast.LENGTH_SHORT).show();
        			animateFavoriteRemoved();
        			delete=false;
        		}else{
        			mSelectedImage.setFavorite(true);
        			favorites.addFavoritePlace(mSelectedImage);
					Toast.makeText(DetailActivity.this, "ADDED TO FAVORITES", Toast.LENGTH_SHORT).show();
					animateFavoriteSuccess();
        		}
        		
        		favorites.close();
        	
                
//
                mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_LONG).setListener(new CustomAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        downloadAndSetOrShareImage(false);
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
//                        downloadAndSetOrShareImage(false);
                        super.onAnimationCancel(animation);
                    }
                }).start();
//            } else {
//                animateReset(false);
//            }
        }
    };

    private View.OnClickListener onFabDownloadButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (future == null) {
//                if (!Utils.isExternalStorageWritable()) {
//                    Toast.makeText(DetailActivity.this, R.string.error_no_storage, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                //prepare the call
//                future = Ion.with(DetailActivity.this)
//                        .load(mSelectedImage.getUrl())
//                        .progressHandler(progressCallback)
//                        .asInputStream();
//
//                hideFabs();
//
//                mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_LONG).setListener(new CustomAnimatorListener() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        downloadImage(null);
//                        super.onAnimationEnd(animation);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        downloadImage(null);
//                        super.onAnimationCancel(animation);
//                    }
//                }).start();
//            } else {
//                animateReset(false);
//            }
        }
    };

//    private View.OnLongClickListener onFabDownloadButtonLongListener = new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View v) {
//
//            return false;
//        }
//    };


    private View.OnClickListener onFabButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (future == null) {
//                //prepare the call
//                future = Ion.with(DetailActivity.this)
//                        .load(mSelectedImage.getHighResImage(mWallpaperWidth, mWallpaperHeight))
//                        .progressHandler(progressCallback)
//                        .asInputStream();
//
//                hideFabs();
//
//                mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_LONG).setListener(new CustomAnimatorListener() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        streamAndSetImage();
//                        super.onAnimationEnd(animation);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        streamAndSetImage();
//                        super.onAnimationCancel(animation);
//                    }
//                }).start();
//            } else {
//                animateReset(false);
//            }
        }
    };

    private View.OnLongClickListener onFabButtonLongListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            if (future == null) {
//                //prepare the call
//                future = Ion.with(DetailActivity.this)
//                        .load(mSelectedImage.getHighResImage(mWallpaperWidth, mWallpaperHeight))
//                        .progressHandler(progressCallback)
//                        .asInputStream();
//
//                hideFabs();
//
//                mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_LONG).setListener(new CustomAnimatorListener() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        downloadAndSetOrShareImage(true);
//                        super.onAnimationEnd(animation);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        downloadAndSetOrShareImage(true);
//                        super.onAnimationCancel(animation);
//                    }
//                }).start();
//
//            } else {
//                animateReset(false);
//            }
//
            return true;
        }
    };

    private ProgressCallback progressCallback = new ProgressCallback() {
        @Override
        public void onProgress(long downloaded, long total) {
            int progress = (int) (downloaded * 100.0 / total);
            if (progress < 1) {
                progress = progress + 1;
            }

//            mFabProgress.setProgress(progress);
        }
    };

    /**
     * download an InputStream of the image and set as Wallpaper
     * Animate
     */
    private void streamAndSetImage() {
//        if (future != null) {
//            //set the callback and start downloading
//            future.withResponse().setCallback(new FutureCallback<Response<InputStream>>() {
//                @Override
//                public void onCompleted(Exception e, Response<InputStream> result) {
//                    boolean success = false;
//                    if (e == null && result != null && result.getResult() != null) {
//                        try {
//                            WallpaperManager.getInstance(DetailActivity.this).setStream(result.getResult());
//
//                            //animate the first elements
//                            animateFavoriteSuccess(true);
//
//                            success = true;
//                        } catch (Exception ex) {
//                            Log.e("un:splash", ex.toString());
//                        }
//
//                        //animate after complete
//                        animateComplete(success);
//                    } else {
//                        animateReset(true);
//                    }
//                }
//            });
//        }
    }

    private void downloadImage(final String customLocation) {
//        if (future != null) {
//            //set the callback and start downloading
//            future.withResponse().setCallback(new FutureCallback<Response<InputStream>>() {
//                @Override
//                public void onCompleted(Exception e, Response<InputStream> result) {
//                    boolean success = false;
//                    if (e == null && result != null && result.getResult() != null) {
//                        try {
//                            //prepare the file name
//                            String url = mSelectedImage.getUrl();
//                            String fileName = url.substring(url.lastIndexOf('/') + 1, url.length()) + ".jpg";
//
//                            File dir;
//                            if (TextUtils.isEmpty(customLocation)) {
//                                //create a temporary directory within the cache folder
//                                dir = Utils.getAlbumStorageDir("wall-splash");
//                            } else {
//                                dir = new File(customLocation);
//                            }
//                            //create the file
//                            File file = new File(dir, fileName);
//                            if (!file.exists()) {
//                                file.createNewFile();
//                            }
//
//                            //copy the image onto this file
//                            Utils.copyInputStreamToFile(result.getResult(), file);
//
//                            //animate the first elements
//                            animateFavoriteSuccess(true);
//
//                            success = true;
//                        } catch (Exception ex) {
//                            Log.e("un:splash", ex.toString());
//                        }
//
//                        //animate after complete
//                        animateComplete(success);
//                    } else {
//                        animateReset(true);
//                    }
//                }
//            });
//        }
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void downloadAndSetOrShareImage(final boolean set) {
//        if (future != null) {
//            //set the callback and start downloading
//            future.withResponse().setCallback(new FutureCallback<Response<InputStream>>() {
//                @Override
//                public void onCompleted(Exception e, Response<InputStream> result) {
//                    boolean success = false;
//                    if (e == null && result != null && result.getResult() != null) {
//                        try {
//                            //create a temporary directory within the cache folder
//                            File dir = new File(DetailActivity.this.getCacheDir() + "/images");
//                            if (!dir.exists()) {
//                                dir.mkdirs();
//                            }
//
//                            //create the file
//                            File file = new File(dir, "unsplash.jpg");
//                            if (!file.exists()) {
//                                file.createNewFile();
//                            }
//
//                            //copy the image onto this file
//                            Utils.copyInputStreamToFile(result.getResult(), file);
//
//                            //get the contentUri for this file and start the intent
//                            Uri contentUri = FileProvider.getUriForFile(DetailActivity.this, "com.mikepenz.fileprovider", file);
//
//                            if (set) {
//                                //get crop intent
//                                Intent intent = WallpaperManager.getInstance(DetailActivity.this).getCropAndSetWallpaperIntent(contentUri);
//                                //start activity for result so we can animate if we finish
//                                DetailActivity.this.startActivityForResult(intent, ACTIVITY_CROP);
//                            } else {
//                                //share :D
//                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                                shareIntent.setData(contentUri);
//                                shareIntent.setType("image/jpg");
//                                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//                                //start activity for result so we can animate if we finish
//                                DetailActivity.this.startActivityForResult(Intent.createChooser(shareIntent, "Share Via"), ACTIVITY_SHARE);
//                            }
//
//                            success = true;
//                        } catch (Exception ex) {
//                            Log.e("un:splash", ex.toString());
//                        }
//
//                        //animate after complete
//                        animateComplete(success);
//                    } else {
//                        animateReset(true);
//                    }
//                }
//            });
//        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean success = resultCode == -1;

       

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * animate the start of the activity
     */
    private void animateActivityStart() {
        ViewPropertyAnimator showTitleAnimator = Utils.showViewByScale(mTitleContainer);
        showTitleAnimator.setListener(new CustomAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                mTitlesContainer.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this, R.anim.alpha_on));
                mTitlesContainer.setVisibility(View.VISIBLE);

                //animate the fab
                Utils.showViewByScale(mFabPhoto).setDuration(ANIMATION_DURATION_MEDIUM).start();

                //animate the share fab
                Utils.showViewByScale(mFabFavorite)
                        .setDuration(ANIMATION_DURATION_MEDIUM * 2)
                        .start();
                mFabFavorite.animate()
                        .translationX((-1) * Utils.pxFromDp(DetailActivity.this, 58))
                        .setStartDelay(ANIMATION_DURATION_MEDIUM)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .start();

                //animate the download fab
                Utils.showViewByScale(mFabSpecial)
                        .setDuration(ANIMATION_DURATION_MEDIUM * 2)
                        .start();
                mFabSpecial.animate()
                        .translationX((-1) * Utils.pxFromDp(DetailActivity.this, 108))
                        .setStartDelay(ANIMATION_DURATION_MEDIUM)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .start();
            }
        });

        showTitleAnimator.start();
    }


    /**
     * animate the start
     * @param success 
     */
    private void hideFabs() {
        //reset progress to prevent jumping
//        mFabProgress.setProgress(0);

        //hide the fabs
        mFabFavorite.animate().translationX(0).setDuration(ANIMATION_DURATION_SHORT).start();
        mFabSpecial.animate().translationX(0).setDuration(ANIMATION_DURATION_SHORT).start();

        //some nice button animations
//        Utils.showViewByScale(mFabProgress).setDuration(ANIMATION_DURATION_MEDIUM).start();
//        mFabProgress.setProgress(1);

//        mProgressFabAnimation = new RotateAnimation(0.0f, 360.0f, mFabProgress.getWidth() / 2, mFabProgress.getHeight() / 2);
//        mProgressFabAnimation.setDuration(ANIMATION_DURATION_EXTRA_LONG * 2);
//        mProgressFabAnimation.setInterpolator(new LinearInterpolator());
//        mProgressFabAnimation.setRepeatCount(Animation.INFINITE);
//        mProgressFabAnimation.setRepeatMode(-1);
//        mFabProgress.startAnimation(mProgressFabAnimation);

    }

    /**
     * animate the reset of the view
     */
    private void animateReset(boolean error) {
//        future.cancel(true);
        future = null;

        //animating everything back to default :D
//        Utils.hideViewByScaleXY(mFabProgress).setDuration(ANIMATION_DURATION_MEDIUM).start();
//        mProgressFabAnimation.cancel();
//        Utils.animateViewElevation(mFabPhoto, 0, mElavationPx);

        if (error) {
            mFabPhoto.setImageDrawable(mDrawableError);
        } else {
            mFabPhoto.setImageDrawable(mDrawablePhoto);
        }

        mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_MEDIUM).start();

        mFabFavorite.animate().translationX((-1) * Utils.pxFromDp(DetailActivity.this, 58)).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mFabSpecial.animate().translationX((-1) * Utils.pxFromDp(DetailActivity.this, 108)).setDuration(ANIMATION_DURATION_MEDIUM).start();
    }

    /**
     * animate the fab to green if place is favorite
     */
    private void animateFavoriteSuccess() {
        //some nice animations so the user knows the wallpaper was set properly
    	
		mFabPhoto.animate().rotation(720).setDuration(ANIMATION_DURATION_EXTRA_LONG).start();
        mFabPhoto.setImageDrawable(mDrawablePhoto);
        mFabFavorite.animate().rotation(720).setDuration(ANIMATION_DURATION_EXTRA_LONG).start();
        mFabFavorite.setImageDrawable(new IconicsDrawable(this,
				FontAwesomeFont.Icon.faw_heart).color(
				getResources().getColor(R.color.white)).sizeDp(20));
        
    	
        //animate the button to green. just do it the first time
        if (mFabPhoto.getTag() == null) {
            TransitionDrawable transition = (TransitionDrawable) mFabPhoto.getBackground();
            transition.startTransition(ANIMATION_DURATION_LONG);
            mFabPhoto.setTag("");
        }

        if (mFabFavorite.getTag() == null) {
            TransitionDrawable transition = (TransitionDrawable) mFabFavorite.getBackground();
            transition.startTransition(ANIMATION_DURATION_LONG);
            mFabFavorite.setTag("");
        }

        if (mFabSpecial.getTag() == null) {
            TransitionDrawable transition = (TransitionDrawable) mFabSpecial.getBackground();
            transition.startTransition(ANIMATION_DURATION_LONG);
            mFabSpecial.setTag("");
        }
    }
    
    
    /**
     * animate the fab to red if place was removed from favorites
     */
    private void animateFavoriteRemoved() {
    	mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_MEDIUM).start();
		mFabPhoto.setImageDrawable(mDrawablePhoto);
		mFabFavorite.animate().rotation(720).setDuration(ANIMATION_DURATION_EXTRA_LONG).start();
        mFabFavorite.setImageDrawable(new IconicsDrawable(this,
				FontAwesomeFont.Icon.faw_heart_o).color(
				getResources().getColor(R.color.white)).sizeDp(16));
	
		//animate the button back to red. just do it the first time
        if (mFabPhoto.getTag() != null) {
            TransitionDrawable transition = (TransitionDrawable) mFabPhoto.getBackground();
            transition.reverseTransition(ANIMATION_DURATION_LONG);
            mFabPhoto.setTag(null);
        }

        if (mFabFavorite.getTag() != null) {
            TransitionDrawable transition = (TransitionDrawable) mFabFavorite.getBackground();
            transition.reverseTransition(ANIMATION_DURATION_LONG);
            mFabFavorite.setTag(null);
        }

        if (mFabSpecial.getTag() != null) {
            TransitionDrawable transition = (TransitionDrawable) mFabSpecial.getBackground();
            transition.reverseTransition(ANIMATION_DURATION_LONG);
            mFabSpecial.setTag(null);
        }
    }
    
    

    /**
     * finish the animations of the ui after it is complete. reset the button to the start
     *
     * @param success
     */
    private void animateComplete(boolean success) {
        //hide the progress again :D
//        Utils.hideViewByScaleXY(mFabProgress).setDuration(ANIMATION_DURATION_MEDIUM).start();
//        mProgressFabAnimation.cancel();

        //show the fab again ;)
        mFabFavorite.animate().translationX((-1) * Utils.pxFromDp(DetailActivity.this, 58)).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mFabSpecial.animate().translationX((-1) * Utils.pxFromDp(DetailActivity.this, 108)).setDuration(ANIMATION_DURATION_MEDIUM).start();

        // if we were not successful remove the x again :D
        if (!success) {
            //Utils.animateViewElevation(mFabPhoto, 0, mElavationPx);
            mFabPhoto.setImageDrawable(mDrawablePhoto);
            mFabPhoto.animate().rotation(360).setDuration(ANIMATION_DURATION_MEDIUM).start();
        }
//        future = null;
    }

    /**
     * @param titleTextColor
     * @param rgb
     */
    private void setColors(int titleTextColor, int rgb) {
        mTitleContainer.setBackgroundColor(rgb);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(titleTextColor);
        }
        //getWindow().setNavigationBarColor(vibrantSwatch.getRgb());

        TextView titleTV = (TextView) mTitleContainer.findViewById(R.id.activity_detail_title);
        titleTV.setTextColor(titleTextColor);
        titleTV.setText(mSelectedImage.getName());

        TextView subtitleTV = (TextView) mTitleContainer.findViewById(R.id.activity_detail_subtitle);
        subtitleTV.setTextColor(titleTextColor);
        subtitleTV.setText(this.getResources().getString(R.string.points)+" "+mSelectedImage.getPoints());

        ((TextView) mTitleContainer.findViewById(R.id.activity_detail_subtitle))
                .setTextColor(titleTextColor);
    }


    @Override
    public void onBackPressed() {
        mFabSpecial.animate()
                .translationX(0)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .setListener(animationFinishListener1)
                .start();


        //move the share fab below the normal fab (58 because this is the margin top + the half
        mFabFavorite.animate()
                .translationX(0)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .setListener(animationFinishListener1)
                .start();
    }

    private CustomAnimatorListener animationFinishListener1 = new CustomAnimatorListener() {
        private int animateFinish1 = 0;

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            process();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            process();
        }

        private void process() {
            animateFinish1 = animateFinish1 + 1;
            if (animateFinish1 >= 2) {
                //create the fab animation and hide fabProgress animation, set an delay so those will hide after the shareFab is below the main fab
                Utils.hideViewByScaleXY(mFabSpecial)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .setListener(animationFinishListener2)
                        .start();
                Utils.hideViewByScaleXY(mFabFavorite)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .setListener(animationFinishListener2)
                        .start();
//                Utils.hideViewByScaleXY(mFabProgress)
//                        .setDuration(ANIMATION_DURATION_MEDIUM)
//                        .setListener(animationFinishListener2)
//                        .start();
                Utils.hideViewByScaleXY(mFabPhoto)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .setListener(animationFinishListener2)
                        .start();
            }
        }
    };

    private CustomAnimatorListener animationFinishListener2 = new CustomAnimatorListener() {
        private int animateFinish2 = 0;

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            process();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            process();
        }

        private void process() {
            animateFinish2 = animateFinish2 + 1;
            if (animateFinish2 >= 3) {   //INFO if 4 we need doubletap to close detailView. 3 closes it directly after animation
                ViewPropertyAnimator hideFabAnimator = Utils.hideViewByScaleY(mTitleContainer);
                hideFabAnimator.setListener(new CustomAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        coolBack();
                    }
                });
            }
        }
    };

    /**
     *
     */
    private void coolBack() {
        try {
            super.onBackPressed();
        } catch (Exception e) {
            // ew;
        }
    }
}
