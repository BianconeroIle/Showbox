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
    private int layoutResource;

    public FavouriteAdapter(Context context, int layoutResource, List<Movie> items) {
        super(context, layoutResource,items);
        this.context = context;
        this.layoutResource = layoutResource;
        this.items = items;
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
        holder.itemMovieTitle.setText(items.get(position).getTitle());
        Picasso.with(context).load(items.get(position).getMoviePicture()).into(holder.itemImage);
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
