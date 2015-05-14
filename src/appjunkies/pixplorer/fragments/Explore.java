//package appjunkies.pixplorer.fragments;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//
//import tr.xip.errorview.ErrorView;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.ActivityOptionsCompat;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import appjunkies.pixplorer.OnItemClickListener;
//import appjunkies.pixplorer.R;
//import appjunkies.pixplorer.model.Place;
//import appjunkies.pixplorer.view.adapter.PlaceAdapter;
//
//import com.getbase.floatingactionbutton.FloatingActionButton;
//
//public class Explore extends Fragment
////implements OnClickListener
//{
//	public static SparseArray<Bitmap> photoCache = new SparseArray<>(1);
//	private PlaceAdapter mPlaceAdapter;
//	private ArrayList<Place> mPlaces;
//	private ArrayList<Place> mCurrentPlaces;
//	private RecyclerView mPlaceRecycler;
//	private ProgressBar mPlacesProgress;
//	private ErrorView mPlacesErrorView;
//	
//	private ArrayList<Place> featured = null;
//	
//	Animation rotateIn, rotateOut;
//	FloatingActionButton shuffleFab;
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.explore_fragment, container,
//				false);
//		mPlaceRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_last_images_recycler);
//        mPlacesProgress = (ProgressBar) rootView.findViewById(R.id.fragment_images_progress);
//        mPlacesErrorView = (ErrorView) rootView.findViewById(R.id.fragment_images_error_view);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
//        mPlaceRecycler.setLayoutManager(gridLayoutManager);
//        mPlaceRecycler.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//
//        mPlaceAdapter = new PlaceAdapter();
//        mPlaceAdapter.setOnItemClickListener(recyclerRowClickListener);
//        mPlaceRecycler.setAdapter(mPlaceAdapter);
//
//        showAll();
//		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT));
//		
//		return rootView;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		
//		// INITS findViewById***********************************************
//		shuffleFab = (FloatingActionButton) getActivity().findViewById(R.id.shuffleFab);
//
//		// *****************************************************************
//
//		// Animation*************************************
//		rotateIn = AnimationUtils.loadAnimation(getActivity(),
//				R.anim.rotate_and_fade_in);
//		rotateOut = AnimationUtils.loadAnimation(getActivity(),
//				R.anim.rotate_and_fade_out);
//		// **********************************************
//
//		
//		shuffleFab.clearAnimation();
//		shuffleFab.startAnimation(rotateIn);
//		shuffleFab.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (mPlaces != null) {
//	                //we don't want to shuffle the original list
//	                ArrayList<Place> shuffled = new ArrayList<Place>(mPlaces);
//	                Collections.shuffle(shuffled);
//	                mPlaceAdapter.updateData(shuffled);
//	                updateAdapter(shuffled);
//	            }
//
//			}
//		});
//			
//
//	}//end on create
//	
//
//	@Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    private void showAll() {
//        if (mPlaces != null) {
//            updateAdapter(mPlaces);
//        } else {
//            mPlacesProgress.setVisibility(View.VISIBLE);
//            mPlaceRecycler.setVisibility(View.GONE);
//            mPlacesErrorView.setVisibility(View.GONE);
//
//            // Load images from API
//            mPlaces = new ArrayList<Place>();
//            Place i1 = new Place();
//            i1.setName("Inn");
//            i1.setPoints(5);
//            i1.setCategory(3);
//            i1.setID(1);
//            i1.setImageSrc("http://images.forwallpaper.com/files/thumbs/preview/18/189739__innsbruck-on-the-river_p.jpg");
//            Place i2 = new Place();
//            i2.setName("Pro Libertate");
//            i2.setPoints(10);
//            i2.setCategory(4);
//            i2.setID(2);
//            i2.setImageSrc("https://c2.staticflickr.com/6/5265/5669558527_709c8250c5_b.jpg");
//            Place i3 = new Place();
//            i3.setName("Mountain");
//            i3.setPoints(30);
//            i3.setCategory(4);
//            i3.setID(3);
//            i3.setImageSrc("http://ngm.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuorcp_uWqvnh2IoUTL5OIs7FLbLAs0V4Ci6dyow/");
//            Place i4 = new Place();
//            i4.setName("Jump Out");
//            i4.setPoints(15);
//            i4.setCategory(3);
//            i4.setID(4);
//            i4.setImageSrc("http://www.innsbruck.info/uploads/tx_seoferatel/094fc758-719a-4b24-9fd9-10a9c1af51d8.jpg");
//            Place i5 = new Place();
//            i5.setName("Center");
//            i5.setPoints(20);
//            i5.setCategory(3);
//            i5.setID(5);
//            i5.setImageSrc("https://backroadjournal.files.wordpress.com/2012/10/img_0625.jpg");
//            Place i6 = new Place();
//            i6.setName("Swarowski");
//            i6.setPoints(50);
//            i6.setCategory(3);
//            i6.setID(6);
//            i6.setImageSrc("http://www.bergland.com/uploads/pics/swarovski_1_09.jpg");
//            Place i7 = new Place();
//            i7.setName("Camaro 1");
//            i7.setPoints(1000);
//            i7.setCategory(3);
//            i7.setID(7);
//            i7.setImageSrc("http://www.hdwallpapers.in/walls/chevrolet_camaro_ss-wide.jpg");
//            Place i8 = new Place();
//            i8.setName("Camaro 2");
//            i8.setPoints(1000);
//            i8.setCategory(3);
//            i8.setID(8);
//            i8.setImageSrc("http://www.hdwallpapers.in/walls/chevrolet_camaro_ss-wide.jpg");
//            Place i9 = new Place();
//            i9.setName("Camaro 3");
//            i9.setPoints(1000);
//            i9.setCategory(3);
//            i9.setID(9);
//            i9.setImageSrc("http://www.hdwallpapers.in/walls/chevrolet_camaro_ss-wide.jpg");
//            Place i10 = new Place();
//            i10.setName("Camaro 4");
//            i10.setPoints(1000);
//            i10.setCategory(3);
//            i10.setID(10);
//            i10.setImageSrc("http://www.hdwallpapers.in/walls/chevrolet_camaro_ss-wide.jpg");
//            
//            mPlaces.add(i1);
//            mPlaces.add(i6);
//            mPlaces.add(i2);
//            mPlaces.add(i3);
//            mPlaces.add(i4);
//            mPlaces.add(i5);
//            mPlaces.add(i7);
//            mPlaces.add(i8);
//            mPlaces.add(i9);
//            mPlaces.add(i10);
//            
//            updateAdapter(mPlaces);
//            
//            mPlacesProgress.setVisibility(View.GONE);
//            mPlaceRecycler.setVisibility(View.VISIBLE);
//            mPlacesErrorView.setVisibility(View.GONE);
//            
////            mApi.fetchImages().cache().subscribeOn(Schedulers.newThread())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .subscribe(observer);
//            
//        }
//    }
//
////    private void showFeatured() {
////        updateAdapter(mApi.filterFeatured(mPlaces));
////    }
////
//    private void showCategory(int category) {
//    	ArrayList<Place> list = new ArrayList<Place>(mPlaces);
//        for (Iterator<Place> it = list.iterator(); it.hasNext(); ) {
//            if ((it.next().getCategory() & category) != category) {
//                it.remove();
//            }
//        }
//        updateAdapter(list);
//    }
//    
////TODO
////    featured = filterFeatured(mPlace)
////    updateAdapter(featured);
//    
//    private ArrayList<Place> filterFeatured(ArrayList<Place> images) {
//        if (featured == null) {
//            ArrayList<Place> list = new ArrayList<Place>(images);
//            for (Iterator<Place> it = list.iterator(); it.hasNext(); ) {
//                if (it.next().getFeatured() == false) {
//                    it.remove();
//                }
//            }
//            featured = list;
//        }
//        return featured;
//    }
//    
//    
////
////    private Observer<ImageList> observer = new Observer<ImageList>() {
////        @Override
////        public void onNext(final ImageList images) {
////            mPlaces = images.getData();
////            updateAdapter(mPlaces);
////
////            if (ImagesFragment.this.getActivity() instanceof MainActivity) {
////                ((MainActivity) ImagesFragment.this.getActivity()).setCategoryCount(images);
////            }
////        }
////
////        @Override
////        public void onCompleted() {
////            // Dismiss loading dialog
////            mPlacesProgress.setVisibility(View.GONE);
////            mPlaceRecycler.setVisibility(View.VISIBLE);
////            mPlacesErrorView.setVisibility(View.GONE);
////        }
////
////        @Override
////        public void onError(final Throwable error) {
////            if (error instanceof RetrofitError) {
////                RetrofitError e = (RetrofitError) error;
////                if (e.getKind() == RetrofitError.Kind.NETWORK) {
////                	Log.e("IMAGEFRAGMENT", "NETWORK ERROR");
////                    mPlacesErrorView.setErrorTitle(R.string.error_network);
////                    mPlacesErrorView.setErrorSubtitle(R.string.error_network_subtitle);
////                } else if (e.getKind() == RetrofitError.Kind.HTTP) {
////                	Log.e("IMAGEFRAGMENT", "HTTP ERROR");
////                    mPlacesErrorView.setErrorTitle(R.string.error_server);
////                    mPlacesErrorView.setErrorSubtitle(R.string.error_server_subtitle);
////                } else {
////                	Log.e("IMAGEFRAGMENT", "UNCOMMON ERROR");
////                    mPlacesErrorView.setErrorTitle(R.string.error_uncommon);
////                    mPlacesErrorView.setErrorSubtitle(R.string.error_uncommon_subtitle);
////                }
////            }
////
////            mPlacesProgress.setVisibility(View.GONE);
////            mPlaceRecycler.setVisibility(View.GONE);
////            mPlacesErrorView.setVisibility(View.VISIBLE);
////
////            mPlacesErrorView.setOnRetryListener(new RetryListener() {
////                @Override
////                public void onRetry() {
////                    showAll();
////                }
////            });
////        }
////    };
//
//    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {
//
//        @Override
//        public void onClick(View v, int position) {
//
//            Place selectedImage = mCurrentPlaces.get(position);
//
//            Intent detailIntent = new Intent(getActivity(), appjunkies.pixplorer.activities.DetailActivity.class);
//            detailIntent.putExtra("position", position);
//            detailIntent.putExtra("selected_image", selectedImage);
//
//            if (selectedImage.getSwatch() != null) {
//                detailIntent.putExtra("swatch_title_text_color", selectedImage.getSwatch().getTitleTextColor());
//                detailIntent.putExtra("swatch_rgb", selectedImage.getSwatch().getRgb());
//            }
//
//            ImageView coverImage = (ImageView) v.findViewById(R.id.item_image_img);
//            if (coverImage == null) {
//                coverImage = (ImageView) ((View) v.getParent()).findViewById(R.id.item_image_img);
//            }
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                if (coverImage.getParent() != null) {
//                    ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
//                }
//            }
//
//            if (coverImage != null && coverImage.getDrawable() != null) {
//                Bitmap bitmap = ((BitmapDrawable) coverImage.getDrawable()).getBitmap(); //ew
//                if (bitmap != null && !bitmap.isRecycled()) {
//                    photoCache.put(position, bitmap);
//
//                    // Setup the transition to the detail activity
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), coverImage, "cover");
//
//                    getActivity().startActivity(detailIntent, options.toBundle());
//                }
//            }
//        }
//    };
//
//
//    /**
//     * a small helper class to update the adapter
//     *
//     * @param places
//     */
//    private void updateAdapter(ArrayList<Place> places) {
//        mCurrentPlaces = places;
//        mPlaceAdapter.updateData(mCurrentPlaces);
//        mPlaceRecycler.scrollToPosition(0);
//    }
//	
//	
//	
//	
//
//}


package appjunkies.pixplorer.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import tr.xip.errorview.ErrorView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import appjunkies.pixplorer.OnItemClickListener;
import appjunkies.pixplorer.R;
import appjunkies.pixplorer.activities.HomeActivity;
import appjunkies.pixplorer.database.DataPlaces;
import appjunkies.pixplorer.model.Place;
import appjunkies.pixplorer.view.adapter.PlaceAdapter;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class Explore extends Fragment
//implements OnClickListener
{
	public static SparseArray<Bitmap> photoCache = new SparseArray<>(1);
	private PlaceAdapter mPlaceAdapter;
	private ArrayList<Place> mPlaces;
	private ArrayList<Place> mCurrentPlaces;
	private RecyclerView mPlaceRecycler;
	private ProgressBar mPlacesProgress;
	private ErrorView mPlacesErrorView;
	
	private ArrayList<Place> featured = null;
	private ArrayList<Place> favorite = null;
	Place i1,i2,i3,i4,i5,i6,i7,i8,i9,i10;
	
	Animation rotateIn, rotateOut;
	FloatingActionButton shuffleFab;
	String splace="";
		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.explore_fragment, container,
				false);
		mPlaceRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_last_images_recycler);
        mPlacesProgress = (ProgressBar) rootView.findViewById(R.id.fragment_images_progress);
        mPlacesErrorView = (ErrorView) rootView.findViewById(R.id.fragment_images_error_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mPlaceRecycler.setLayoutManager(gridLayoutManager);
        mPlaceRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mPlaceAdapter = new PlaceAdapter();
        mPlaceAdapter.setOnItemClickListener(recyclerRowClickListener);
        mPlaceRecycler.setAdapter(mPlaceAdapter);
             		
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
        
        showAll();
        System.out.println("Show ALL first time");
       
        
       
        
//        Toast.makeText(null, splace, Toast.LENGTH_SHORT).show();
        
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// INITS findViewById***********************************************
		shuffleFab = (FloatingActionButton) getActivity().findViewById(R.id.shuffleFab);

		// *****************************************************************

		// Animation*************************************
		rotateIn = AnimationUtils.loadAnimation(getActivity(),
				R.anim.rotate_and_fade_in);
		rotateOut = AnimationUtils.loadAnimation(getActivity(),
				R.anim.rotate_and_fade_out);
		// **********************************************

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//		
//			TEST PLACES
		
		// Load images from API
        
		mPlaces = new ArrayList<Place>();
        i1 = new Place();
        i1.setName("Inn");
        i1.setPoints(5);
//        i1.setCategory(new Category().setId(1));
        i1.setId(1);
        i1.setImageSrc("http://images.forwallpaper.com/files/thumbs/preview/18/189739__innsbruck-on-the-river_p.jpg");
        i2 = new Place();
        i2.setName("Pro Libertate");
        i2.setPoints(10);
//        i2.setCategory(4);
        i2.setId(2);
        i2.setImageSrc("https://c2.staticflickr.com/6/5265/5669558527_709c8250c5_b.jpg");
        i3 = new Place();
        i3.setName("Mountain");
        i3.setPoints(30);
//        i3.setCategory(4);
        i3.setId(3);
        i3.setImageSrc("http://ngm.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuorcp_uWqvnh2IoUTL5OIs7FLbLAs0V4Ci6dyow/");
        i4 = new Place();
        i4.setName("Jump Out");
        i4.setPoints(15);
//        i4.setCategory(3);
        i4.setId(4);
        i4.setImageSrc("http://www.innsbruck.info/uploads/tx_seoferatel/094fc758-719a-4b24-9fd9-10a9c1af51d8.jpg");
        i5 = new Place();
        i5.setName("Center");
        i5.setPoints(20);
//        i5.setCategory(3);
        i5.setId(5);
        i5.setImageSrc("https://backroadjournal.files.wordpress.com/2012/10/img_0625.jpg");
        i6 = new Place();
        i6.setName("Swarowski");
        i6.setPoints(50);
//        i6.setCategory(3);
        i6.setId(6);
        i6.setImageSrc("http://www.bergland.com/uploads/pics/swarovski_1_09.jpg");
         i7 = new Place();
        i7.setName("Pragser Wildsee");
        i7.setPoints(222);
//        i7.setCategory(3);
        i7.setId(7);
        i7.setImageSrc("http://i62.tinypic.com/qnsepw.jpg");
        
        i8 = new Place();
        i8.setName("Skiing");
        i8.setPoints(300);
//        i8.setCategory(3);
        i8.setId(8);
        i8.setImageSrc("http://scontent-b-fra.cdninstagram.com/hphotos-xfa1/t51.2885-15/s306x306/e15/10932410_1544715455813452_1107632347_n.jpg");
        i9 = new Place();
        i9.setName("Bridge");
        i9.setPoints(150);
//        i9.setCategory(3);
        i9.setId(9);
        i9.setImageSrc("http://scontent-b-fra.cdninstagram.com/hphotos-xaf1/t51.2885-15/s306x306/e15/10914370_1519297625009641_722250486_n.jpg");
        i10 = new Place();
        i10.setName("Camaro");
        i10.setPoints(1000);
//        i10.setCategory(3);
        i10.setId(10);
        i10.setFeatured(true);
        i10.setImageSrc("http://www.hdwallpapers.in/walls/chevrolet_camaro_ss-wide.jpg");
       
        mPlaces.add(i1);
        mPlaces.add(i6);
        mPlaces.add(i2);
        mPlaces.add(i3);
        mPlaces.add(i4);
        mPlaces.add(i5);
        mPlaces.add(i7);
        mPlaces.add(i8);
        mPlaces.add(i9);
        mPlaces.add(i10);
		
       
//		
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
        if (Explore.this.getActivity() instanceof HomeActivity) {
            ((HomeActivity) Explore.this.getActivity()).setOnFilterChangedListener(new HomeActivity.OnFilterChangedListener() {
                @Override
                public void onFilterChanged(int filter) {
                    if (mPlaces != null) {
                        if (filter == HomeActivity.Category.LAST.id) {
                            showAll();
                            shuffleFab.setVisibility(View.VISIBLE);
                            System.out.println("SHOWALL");
                        } else if (filter == HomeActivity.Category.FAVORITE.id) {
                        	shuffleFab.setVisibility(View.INVISIBLE);
                        	//FIXME update favorites if removed-> maybe not neccessary if we use other logic to download serverplaces
                        	DataPlaces favorites = new DataPlaces(getActivity());
                     		favorites.open();
                     		int favcount =favorites.getFavoriteCount();
                     		if (favcount>0) {
                     			List<Place> favplaces = favorites.getFavorites();
                     			for (int i = 0; i < mPlaces.size(); i++) {	
                     				for (int j = 0; j < favcount; j++) {	
                     					if (mPlaces.get(i).getId()==favplaces.get(j).getId()){
                     						mPlaces.get(i).setFavorite(true);
                     						break;
                     					}else{
                     						mPlaces.get(i).setFavorite(false);
                     					}
                     				}
                     			}
                     		}
                     		favorites.close();
                     		showFavorites(mPlaces);
                        	System.out.println("FAVORITES");
                        } else if (filter == HomeActivity.Category.QUEST.id) {
                        	shuffleFab.setVisibility(View.INVISIBLE);
                            showFeatured(mPlaces);
                            System.out.println("FEATURED_QUEST");
                        } else {
                        	shuffleFab.setVisibility(View.INVISIBLE);
                            showCategory(filter);
                            System.out.println("HERE IS ELSE");
                        }
                    } 
                }
            });
        }
		
			shuffleFab.clearAnimation();
			shuffleFab.startAnimation(rotateIn);
			shuffleFab.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mPlaces != null) {
		                //we don't want to shuffle the original list
		                ArrayList<Place> shuffled = new ArrayList<Place>(mPlaces);
		                Collections.shuffle(shuffled);
		                mPlaceAdapter.updateData(shuffled);
		                updateAdapter(shuffled);
		            }

				}
			});	
		
		
		
        

	}//end on create
	

	@Override
    public void onResume() {
        super.onResume();
    }

    private void showAll() {
        if (mPlaces != null) {
            updateAdapter(mPlaces);
        } else {
            mPlacesProgress.setVisibility(View.VISIBLE);
            mPlaceRecycler.setVisibility(View.GONE);
            mPlacesErrorView.setVisibility(View.GONE);

            mPlaces = new ArrayList<Place>();
            i1 = new Place();
            i1.setName("Inn");
            i1.setPoints(5);
//            i1.setCategory(new Category().setId(1));
            i1.setId(1);
            i1.setImageSrc("http://images.forwallpaper.com/files/thumbs/preview/18/189739__innsbruck-on-the-river_p.jpg");
            i2 = new Place();
            i2.setName("Pro Libertate");
            i2.setPoints(10);
//            i2.setCategory(4);
            i2.setId(2);
            i2.setImageSrc("https://c2.staticflickr.com/6/5265/5669558527_709c8250c5_b.jpg");
            i3 = new Place();
            i3.setName("Mountain");
            i3.setPoints(30);
//            i3.setCategory(4);
            i3.setId(3);
            i3.setImageSrc("http://ngm.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuorcp_uWqvnh2IoUTL5OIs7FLbLAs0V4Ci6dyow/");
            i4 = new Place();
            i4.setName("Jump Out");
            i4.setPoints(15);
//            i4.setCategory(3);
            i4.setId(4);
            i4.setImageSrc("http://www.innsbruck.info/uploads/tx_seoferatel/094fc758-719a-4b24-9fd9-10a9c1af51d8.jpg");
            i5 = new Place();
            i5.setName("Center");
            i5.setPoints(20);
//            i5.setCategory(3);
            i5.setId(5);
            i5.setImageSrc("https://backroadjournal.files.wordpress.com/2012/10/img_0625.jpg");
            i6 = new Place();
            i6.setName("Swarowski");
            i6.setPoints(50);
//            i6.setCategory(3);
            i6.setId(6);
            i6.setImageSrc("http://www.bergland.com/uploads/pics/swarovski_1_09.jpg");
             i7 = new Place();
            i7.setName("Pragser Wildsee");
            i7.setPoints(222);
//            i7.setCategory(3);
            i7.setId(7);
            i7.setImageSrc("http://i62.tinypic.com/qnsepw.jpg");
            
            i8 = new Place();
            i8.setName("Skiing");
            i8.setPoints(300);
//            i8.setCategory(3);
            i8.setId(8);
            i8.setImageSrc("http://scontent-b-fra.cdninstagram.com/hphotos-xfa1/t51.2885-15/s306x306/e15/10932410_1544715455813452_1107632347_n.jpg");
            i9 = new Place();
            i9.setName("Bridge");
            i9.setPoints(150);
//            i9.setCategory(3);
            i9.setId(9);
            i9.setImageSrc("http://scontent-b-fra.cdninstagram.com/hphotos-xaf1/t51.2885-15/s306x306/e15/10914370_1519297625009641_722250486_n.jpg");
            i10 = new Place();
            i10.setName("Camaro");
            i10.setPoints(1000);
//            i10.setCategory(3);
            i10.setId(10);
            i10.setFeatured(true);
            i10.setImageSrc("http://www.hdwallpapers.in/walls/chevrolet_camaro_ss-wide.jpg");
           
            mPlaces.add(i1);
            mPlaces.add(i6);
            mPlaces.add(i2);
            mPlaces.add(i3);
            mPlaces.add(i4);
            mPlaces.add(i5);
            mPlaces.add(i7);
            mPlaces.add(i8);
            mPlaces.add(i9);
            mPlaces.add(i10);
            updateAdapter(mPlaces);
            
            mPlacesProgress.setVisibility(View.GONE);
            mPlaceRecycler.setVisibility(View.VISIBLE);
            mPlacesErrorView.setVisibility(View.GONE);
            
            
        }
    }
    private void showCategory(int category) {
    	ArrayList<Place> list = new ArrayList<Place>(mPlaces);
        for (Iterator<Place> it = list.iterator(); it.hasNext(); ) {
            if ((it.next().getCategory().getId() & category) != category) {
                it.remove();
            }
        }
        updateAdapter(list);
    }
    
    
    private void showFeatured(ArrayList<Place> places) {
    	featured = filterFeatured(places);
        updateAdapter(featured);
    }
    
    
    private ArrayList<Place> filterFeatured(ArrayList<Place> arrplace) {
        if (featured == null) {
            ArrayList<Place> list = new ArrayList<Place>(arrplace);
            for (Iterator<Place> it = list.iterator(); it.hasNext(); ) {
                if (it.next().isFeatured() == false) {
                    it.remove();
                }
            }
            featured = list;
        }
        return featured;
    }
    
    
    private void showFavorites(ArrayList<Place> places) {
    	favorite = filterFavorites(places);
        updateAdapter(favorite);
    }
    
    
    private ArrayList<Place> filterFavorites(ArrayList<Place> favplace) {
        if (favorite == null) {
            ArrayList<Place> list = new ArrayList<Place>(favplace);
            for (Iterator<Place> it = list.iterator(); it.hasNext(); ) {
                if (it.next().isFavorite() == false) {
                    it.remove();
                }
            }
            favorite = list;
        }
        return favorite;
    }
    
    

    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {

        @Override
        public void onClick(View v, int position) {

            Place selectedImage = mCurrentPlaces.get(position);

            Intent detailIntent = new Intent(getActivity(), appjunkies.pixplorer.activities.DetailActivity.class);
            detailIntent.putExtra("position", position);
            detailIntent.putExtra("selected_image", selectedImage);

            if (selectedImage.getSwatch() != null) {
                detailIntent.putExtra("swatch_title_text_color", selectedImage.getSwatch().getTitleTextColor());
                detailIntent.putExtra("swatch_rgb", selectedImage.getSwatch().getRgb());
            }

            ImageView coverImage = (ImageView) v.findViewById(R.id.item_image_img);
            if (coverImage == null) {
                coverImage = (ImageView) ((View) v.getParent()).findViewById(R.id.item_image_img);
            }

            if (Build.VERSION.SDK_INT >= 21) {
                if (coverImage.getParent() != null) {
                    ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
                }
            }

            if (coverImage != null && coverImage.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) coverImage.getDrawable()).getBitmap(); //ew
                if (bitmap != null && !bitmap.isRecycled()) {
                    photoCache.put(position, bitmap);

                    // Setup the transition to the detail activity
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), coverImage, "cover");

                    getActivity().startActivity(detailIntent, options.toBundle());
                }
            }
        }
    };


    /**
     * a small helper class to update the adapter
     *
     * @param places
     */
    private void updateAdapter(ArrayList<Place> places) {
        mCurrentPlaces = places;
        mPlaceAdapter.updateData(mCurrentPlaces);
        mPlaceRecycler.scrollToPosition(0);
    }
    
    
    
    
	
	
	
	

}

