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
import model.TV.TVDTO;

/**
 * Created by Vlade Ilievski on 9/15/2016.
 */
public class GridViewTVLibraryAdapter extends ArrayAdapter<TVDTO> {
    private Context context;
    private int layoutResourceId;
    private List<TVDTO> items;

    public GridViewTVLibraryAdapter(Context context, int layoutResourceId, List<TVDTO> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.library_layout_item, parent, false);
            holder = new ViewHolder();
            holder.titleMovie = (TextView) row.findViewById(R.id.movieTitle);
            holder.movieImage = (ImageView) row.findViewById(R.id.movieImage);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.titleMovie.setText((items.get(position).getName()));
        Picasso.with(getContext()).load(ApiConstants.IMAGE_BASE_URL + items.get(position).getPoster_path()).into(holder.movieImage);
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

