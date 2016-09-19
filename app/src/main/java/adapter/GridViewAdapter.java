package adapter;

import android.app.Activity;
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

/**
 * Created by Vlade Ilievski on 8/15/2016.
 */
public class GridViewAdapter extends ArrayAdapter<MovieDTO> {

    private Context mContext;
    private int layoutResourceId;
    private List<MovieDTO> items;

    public GridViewAdapter(Context mContext, int layoutResourceId, List<MovieDTO> items) {
        super(mContext, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.items = items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.library_layout_item, parent, false);
            holder = new ViewHolder();
            holder.titleMovie = (TextView) row.findViewById(R.id.movieTitle);
            holder.movieImage = (ImageView) row.findViewById(R.id.movieImage);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.titleMovie.setText((items.get(position).getTitle()));
        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + items.get(position).getPosterPath()).into(holder.movieImage);
        return row;
    }


    static class ViewHolder {
        TextView titleMovie;
        ImageView movieImage;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}