package ui.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.showbox.showbox.R;

import java.util.ArrayList;

import adapter.HomeRecyclerAdapter;
import model.NewsDTO;
import util.ReadRSS;

/**
 * Created by Vlade Ilievski on 9/13/2016.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    RecyclerView recycler_view;
    HomeRecyclerAdapter homeRecyclerAdapter;
    ArrayList<NewsDTO> newsItems;
    ProgressDialog progressDialog;

    public static final String TAG = HomeFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        initVariables(v);



        return v;
    }

    public void initVariables(View v){
        recycler_view=(RecyclerView)v.findViewById(R.id.recycler_view);
        homeRecyclerAdapter=new HomeRecyclerAdapter(newsItems,getContext());
        recycler_view.setAdapter(homeRecyclerAdapter);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


    }
    public void initListeners(){

    }

}

