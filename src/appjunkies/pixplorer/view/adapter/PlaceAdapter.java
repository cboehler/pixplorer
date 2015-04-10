package appjunkies.pixplorer.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import appjunkies.pixplorer.OnItemClickListener;
import appjunkies.pixplorer.R;
import appjunkies.pixplorer.model.Place;
import appjunkies.pixplorer.other.PaletteTransformation;
import appjunkies.pixplorer.other.Utils;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PlaceAdapter extends RecyclerView.Adapter<ImagesViewHolder> {

    private Context mContext;

    private ArrayList<Place> mPlace;

    private int mScreenWidth;

    private int mDefaultTextColor;
    private int mDefaultBackgroundColor;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PlaceAdapter() {
    }

    public PlaceAdapter(ArrayList<Place> places) {
        this.mPlace = places;
    }

    public void updateData(ArrayList<Place> places) {
        this.mPlace = places;
        notifyDataSetChanged();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_place, viewGroup, false);

        //set the mContext
        this.mContext = viewGroup.getContext();

        //get the colors
        mDefaultTextColor = mContext.getResources().getColor(R.color.text_without_palette);
        mDefaultBackgroundColor = mContext.getResources().getColor(R.color.image_without_palette);

        //get the screenWidth :D optimize everything :D
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

        return new ImagesViewHolder(rowView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(final ImagesViewHolder imagesViewHolder, final int position) {

        final Place currentImage = mPlace.get(position);
        imagesViewHolder.placeName.setText(currentImage.getName());
        imagesViewHolder.placePoints.setText(mContext.getResources().getString(R.string.points)+" "+currentImage.getPoints());
        //imagesViewHolder.imageView.setDrawingCacheEnabled(true);
        imagesViewHolder.imageView.setImageBitmap(null);
        
        //reset colors so we prevent crazy flashes :D
        imagesViewHolder.placeName.setTextColor(mDefaultTextColor);
        imagesViewHolder.placePoints.setTextColor(mDefaultTextColor);
        imagesViewHolder.imageTextContainer.setBackgroundColor(mDefaultBackgroundColor);

        //cancel any loading images on this view
        Picasso.with(mContext).cancelRequest(imagesViewHolder.imageView);
        //load the image
        Picasso.with(mContext).load(mPlace.get(position).getImageSrc(mScreenWidth)).transform(PaletteTransformation.instance()).resize(800, 800).centerInside().into(imagesViewHolder.imageView, new Callback.EmptyCallback() {
        @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) imagesViewHolder.imageView.getDrawable()).getBitmap(); // Ew!
                if (bitmap != null && !bitmap.isRecycled()) {
                    Palette palette = PaletteTransformation.getPalette(bitmap);

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

                        if (s != null && position >= 0 && position < mPlace.size()) {
                            if (mPlace.get(position) != null) {
                                mPlace.get(position).setSwatch(s);
                            }

                            imagesViewHolder.placeName.setTextColor(s.getTitleTextColor());
                            imagesViewHolder.placePoints.setTextColor(s.getTitleTextColor());
                            Utils.animateViewColor(imagesViewHolder.imageTextContainer, mDefaultBackgroundColor, s.getRgb());
                        }
                    }
                }

                // just delete the reference again.
                bitmap = null;

                if (Build.VERSION.SDK_INT >= 21) {
                    imagesViewHolder.imageView.setTransitionName("cover" + position);
                }
                imagesViewHolder.imageTextContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(v, position);
                    }
                });
            }
	        @Override
	        public void onError() {
	              Log.e("placeadapter","PICASSO ERROR");	  
	        }
        });


        //calculate height of the list-item so we don't have jumps in the view
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        //image.width .... image.height
        //device.width ... device
        int finalHeight = (int) (displaymetrics.widthPixels / currentImage.getRatio());
        imagesViewHolder.imageView.setMinimumHeight(finalHeight);
    }

    @Override
    public int getItemCount() {
        return mPlace.size();
    }
}

class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final FrameLayout imageTextContainer;
    protected final ImageView imageView;
    protected final TextView placeName;
    protected final TextView placePoints;
    private final OnItemClickListener onItemClickListener;

    public ImagesViewHolder(View itemView, OnItemClickListener onItemClickListener) {

        super(itemView);
        this.onItemClickListener = onItemClickListener;

        imageTextContainer = (FrameLayout) itemView.findViewById(R.id.item_image_text_container);
        imageView = (ImageView) itemView.findViewById(R.id.item_image_img);
        placeName = (TextView) itemView.findViewById(R.id.item_image_name);
        placePoints = (TextView) itemView.findViewById(R.id.item_image_points);

        imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onClick(v, getPosition());
    }
}

