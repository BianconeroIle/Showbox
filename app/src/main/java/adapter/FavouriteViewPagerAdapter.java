package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ui.fragments.FavoriteFragment;

/**
 * Created by Vlade Ilievski on 9/23/2016.
 */
public class FavouriteViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;

    /**
     * Constructor of the class
     */
    public FavouriteViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FavoriteFragment myFragment = new FavoriteFragment();
        Bundle data = new Bundle();
        data.putInt("current_page", position + 1);
        myFragment.setArguments(data);
        return myFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
