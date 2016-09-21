package adapter;


import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.showbox.showbox.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import interfaces.ApiConstants;
import model.TV.TVDTO;
import ui.fragments.TVDetailsFragment;

/**
 * Created by Vlade Ilievski on 9/21/2016.
 */
public class RecyclerViewAdapterTV extends RecyclerView.Adapter<RecyclerViewAdapterTV.CustomViewHolder> {
    private List<TVDTO> items;
    private Context mContext;
    private int layoutResourceId;

    public RecyclerViewAdapterTV(Context context,int layoutResourceId, List<TVDTO> items) {
        this.items = items;
        this.layoutResourceId=layoutResourceId;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.library_layout_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        customViewHolder.movieTitle.setText((items.get(i).getName()));
        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + items.get(i).getPoster_path()).into(customViewHolder.movieImage);
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView movieImage;
        protected TextView movieTitle;

        public CustomViewHolder(View view) {
            super(view);
            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
            this.movieTitle = (TextView) view.findViewById(R.id.movieTitle);
        }
    }
}