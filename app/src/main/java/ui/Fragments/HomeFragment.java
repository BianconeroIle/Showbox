package ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showbox.showbox.R;

/**
 * Created by Vlade Ilievski on 9/13/2016.
 */
public class HomeFragment extends Fragment {


    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.library_layout, container, false);

        return rootView;
    }
}

