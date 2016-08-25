package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.showbox.showbox.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Movie;

/**
 * Created by Vlade Ilievski on 8/18/2016.
 */
public class FavouriteAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private List<Movie> items;
    ImageView itemImage;
    TextView itemMovieTitle;
    private int layoutResource;

    public FavouriteAdapter(Context context,int layoutResource, List<Movie> items) {
        super(context ,layoutResource);
        this.context = context;
        this.layoutResource=layoutResource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(context);
            convertView = li.inflate(layoutResource, parent, false);
            itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
            itemMovieTitle = (TextView) convertView.findViewById(R.id.itemMovieTitle);


            Picasso.with(context).load(items.get(position).getMoviePicture()).into(itemImage);
            itemMovieTitle.setText(items.get(position).getTitle());

        }

        return convertView;
    }

}
