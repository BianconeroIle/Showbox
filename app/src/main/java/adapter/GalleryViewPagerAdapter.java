package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.showbox.showbox.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import interfaces.MovieAPI;

/**
 * Created by Vlade Ilievski on 9/6/2016.
 */
public class GalleryViewPagerAdapter extends PagerAdapter {

    Context context;
    private LayoutInflater inflater;
    List<String> images = Collections.emptyList();

    public GalleryViewPagerAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        inflater = LayoutInflater.from(context);
        ImageView image = (ImageView) inflater.inflate(R.layout.slider_view_pager, collection, false);
        Picasso.with(context).load(MovieAPI.IMAGE_BASE_URL + images.get(position))/*placeholder(R.drawable.ic_logo)*/.into(image);

        collection.addView(image);
        Log.d("GalleryViewPagerAdapter", "image=" + MovieAPI.IMAGE_BASE_URL + images.get(position));
        return image;
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object object) {
        collection.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
