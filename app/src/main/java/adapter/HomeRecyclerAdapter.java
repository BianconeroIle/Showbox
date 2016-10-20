package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.showbox.showbox.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import model.NewsDTO;

/**
 * Created by Vlade Ilievski on 10/4/2016.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    List<NewsDTO> item;
    Context context;

    public HomeRecyclerAdapter(List<NewsDTO> items, Context context) {
        this.item = items;
        this.context = context;
    }

    @Override
    public HomeRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stories, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.MyViewHolder holder, int position) {
        NewsDTO current = item.get(position);
        holder.title.setText(current.getTitle());


        Document document = Jsoup.parse(current.getDescription());
        Element image = document.select("img").first();
        String imageUrl = image.absUrl("src");
        if (imageUrl != null) {
            Picasso.with(context).load(imageUrl).into(holder.image);
        } else {
            Picasso.with(context).load(R.drawable.ic_logo).into(holder.image);
        }

        document.select("img").remove();
        document.select("br").remove();
        document.select("p").remove();

        Log.d("HomeRecyclerAdapter", "clearedHtml=" + document.outerHtml());

        //Tue, 18 Oct 2016 06:44:25 PDT
        String serverDate = "EEE, dd MMM yyyy HH:mm:ss z";
        String presentationDate = "EEE, dd MMM yyyy HH:mm";


        try {
            Date date = new SimpleDateFormat(serverDate).parse(current.getPubDate());
            SimpleDateFormat presentationSimpleDateFormater = new SimpleDateFormat(presentationDate);
            presentationSimpleDateFormater.setTimeZone(TimeZone.getDefault());
            holder.date.setText(presentationSimpleDateFormater.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.date.setText(current.getPubDate());
        }

        holder.description.setText(Html.fromHtml(document.outerHtml()));

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.cardviewImage);
            title = (TextView) itemView.findViewById(R.id.cardviewTitle);
            description = (TextView) itemView.findViewById(R.id.cardviewDescription);
            date = (TextView) itemView.findViewById(R.id.cardviewDate);
        }
    }

    public List<NewsDTO> getItems() {
        return item;
    }
}
