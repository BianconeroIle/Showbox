package ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showbox.showbox.R;

import adapter.FavoritesFragmentPagerAdapter;

/**
 * Created by Vlade Ilievski on 10/14/2016.
 */

public class FavouriteFragment extends Fragment {
    public static final String TAG = FavouriteFragment.class.getName();
    FavoritesFragmentPagerAdapter favoritesFragmentPagerAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourite_fragment,container,false);
        initVariables(v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initVariables(View v){
        ViewPager vpPager = (ViewPager)v.findViewById(R.id.vpPager);
        favoritesFragmentPagerAdapter = new FavoritesFragmentPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(favoritesFragmentPagerAdapter);
    }
}
