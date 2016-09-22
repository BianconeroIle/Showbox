package adapter;


import android.content.Context;
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
import model.Movie.MovieDTO;
import model.TV.TVDTO;

/**
 * Created by Vlade Ilievski on 9/21/2016.
 */
public class SimilarRecyclerViewAdapter<T> extends RecyclerView.Adapter<SimilarRecyclerViewAdapter.CustomViewHolder> {
    private List<T> items;
    private Context mContext;
    private int layoutResourceId;

    private OnSimilarItemClickListener<T> listener;

    public interface OnSimilarItemClickListener<T> {
        void onSimilarClick(T object);
    }

    public SimilarRecyclerViewAdapter(Context context, int layoutResourceId, List<T> items, OnSimilarItemClickListener listener) {
        this.items = items;
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public SimilarRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.similar_layout_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimilarRecyclerViewAdapter.CustomViewHolder customViewHolder, int i) {
        final T obj = items.get(i);
        if (obj instanceof MovieDTO) {
            MovieDTO movie = (MovieDTO) obj;
            customViewHolder.movieTitle.setText(movie.getTitle());
            Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + movie.getPosterPath()).into(customViewHolder.movieImage);
        } else if (obj instanceof TVDTO) {
            TVDTO movie = (TVDTO) obj;
            customViewHolder.movieTitle.setText(movie.getName());
            Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + movie.getPoster_path()).into(customViewHolder.movieImage);
        }
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSimilarClick(obj);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView movieImage;
        protected TextView movieTitle;

        public CustomViewHolder(View view) {
            super(view);
            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
            this.movieTitle = (TextView) view.findViewById(R.id.movieTitle);
        }
    }
}