package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.showbox.showbox.R;

import java.util.List;

import model.Category;

/**
 * Created by Vlade Ilievski on 9/5/2016.
 */


public class CategorySpinnerAdapter extends ArrayAdapter<Category> implements View.OnClickListener {
    int resource;
    public CategorySpinnerAdapter(Context context, int resource, List<Category> categoryList) {
        super(context, resource, categoryList);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            TextView categories=(TextView)convertView.findViewById(R.id.category);
            categories.setText(category.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Category category=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            TextView categories=(TextView)convertView.findViewById(R.id.category);
            categories.setText(category.getName());
        }
        return convertView;
    }

    @Override
    public void onClick(View view) {

    }

}
