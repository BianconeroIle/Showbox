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

import interfaces.ApiConstants;
import model.Movie.MovieDTO;
import model.TV.TVDTO;
import util.AppPreference;

/**
 * Created by Vlade Ilievski on 8/18/2016.
 */
public class FavouriteListViewAdapter<T> extends ArrayAdapter {
    private Context context;
    private List<T> items;
    private int layoutResource;
    AppPreference preference;

    public FavouriteListViewAdapter(Context context, int layoutResource, List<T> items) {
        super(context, layoutResource, items);
        this.context = context;
        this.layoutResource = layoutResource;
        this.items = items;
        preference = new AppPreference(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResource, parent, false);

            holder = new ViewHolder();

            holder.itemImage = (ImageView) row.findViewById(R.id.itemImage);
            holder.itemMovieTitle = (TextView) row.findViewById(R.id.itemMovieTitle);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        T object = items.get(position);

        String title = "";
        String imageUrl = "";

        if (object instanceof MovieDTO) {

            MovieDTO movie = (MovieDTO) object;
            title = movie.getTitle();
            imageUrl = movie.getPosterPath();

        } else if (object instanceof TVDTO) {

            TVDTO tvShow = (TVDTO) object;
            title = tvShow.getName();
            imageUrl = tvShow.getPoster_path();

        }
        holder.itemMovieTitle.setText(title);
        Picasso.with(context).load(ApiConstants.IMAGE_BASE_URL + imageUrl).into(holder.itemImage);
        return row;


    }


    static class ViewHolder {
        TextView itemMovieTitle;
        ImageView itemImage;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
