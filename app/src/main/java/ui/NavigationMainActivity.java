package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.showbox.showbox.R;

import adapter.NavDrawerListAdapter;
import ui.fragments.FavoriteFragment;
import ui.fragments.HomeFragment;
import ui.fragments.MovieLibraryFragment;
import ui.fragments.TVLibraryFragment;

/**
 * Created by Vlade Ilievski on 9/13/2016.
 */
public class NavigationMainActivity extends AppCompatActivity implements NavDrawerListAdapter.OnNavigationDrawerChooseListener {

    //First We Declare Titles And Icons For Our Navigation mDrawerLayout List View
    //This Icons And Titles Are holded in an Array as you can see

    String TITLES[] = {"Home", "Movie", "TV", "Favorites"};
    int ICONS[] = {R.drawable.ic_home_white_36dp, R.drawable.ic_movie, R.drawable.ic_tv, R.drawable.ic_favorite_white_36dp};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Akash Bangad";
    String EMAIL = "akash.bangad@android4devs.com";
    int PROFILE = R.drawable.ic_logo;

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    NavDrawerListAdapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout mDrawerLayout;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar mDrawerLayout Toggle


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);

    /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new NavDrawerListAdapter(TITLES, ICONS, getApplicationContext());       // Creating the Adapter of NavDrawerListAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        mAdapter.setOnNavigationDrawerChooseListener(this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);        // mDrawerLayout object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // mDrawerLayout Toggle Object Made
        mDrawerLayout.setDrawerListener(mDrawerToggle); // mDrawerLayout Listener set to the mDrawerLayout toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }


    @Override
    public void onItemClick(int position, String title) {
        Log.d("NavigationMainActivity", "onItemClick pos=" + position + ", title=" + title);
        switch (position) {
            case 1:
                openHomeFragment();
                break;
            case 2:
                openMovieLibraryFragment();
                break;
            case 3:
                openTVLibraryFragment();
                break;
            case 4:
                openFavoriteFragment();
                break;
        }
    }
    private void openHomeFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = new HomeFragment();
        transaction.replace(R.id.container, fragment, HomeFragment.TAG);
        transaction.commit();
        mDrawerLayout.closeDrawers();
    }

    private void openMovieLibraryFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Movies");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MovieLibraryFragment fragment = new MovieLibraryFragment();
        transaction.replace(R.id.container, fragment, MovieLibraryFragment.TAG);
        transaction.commit();
        mDrawerLayout.closeDrawers();
    }

    private void openFavoriteFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favorite");
        }
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        FavoriteFragment fragment=new FavoriteFragment();
        transaction.replace(R.id.container,fragment,FavoriteFragment.TAG);
        transaction.commit();
        mDrawerLayout.closeDrawers();
    }

    private void openTVLibraryFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("TV Shows");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TVLibraryFragment fragment = new TVLibraryFragment();
        transaction.replace(R.id.container, fragment, TVLibraryFragment.TAG);
        transaction.commit();
        mDrawerLayout.closeDrawers();
    }

}
