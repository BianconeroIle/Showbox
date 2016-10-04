package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.showbox.showbox.R;

import adapter.FavouriteViewPagerAdapter;

/**
 * Created by Ilija Angeleski on 8/18/2016.
 */
public class FavoriteActivity extends FragmentActivity {
    public static final String TAG = FavoriteActivity.class.getName();
    ViewPager pager;
    FavouriteViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_activity);

        pager=(ViewPager)findViewById(R.id.pager);

        pagerAdapter=new FavouriteViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

    }

}