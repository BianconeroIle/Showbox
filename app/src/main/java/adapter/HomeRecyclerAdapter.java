package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.showbox.showbox.R;

import java.util.ArrayList;

import model.NewsDTO;

/**
 * Created by Vlade Ilievski on 10/4/2016.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    ArrayList<NewsDTO>item;
    Context context;

    public HomeRecyclerAdapter(ArrayList<NewsDTO> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public HomeRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.stories,parent,false);
        MyViewHolder holder=new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.MyViewHolder holder, int position) {
        NewsDTO current= item.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
        holder.date.setText(current.getPubDate());



    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,date;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.cardviewTitle);
            description=(TextView)itemView.findViewById(R.id.cardviewDescription);
            date=(TextView)itemView.findViewById(R.id.cardviewDate);
        }
    }
}
