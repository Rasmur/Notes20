package com.example.igory.notes20;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements
        AddFragment.OnFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener,
        ChooseColorFragment.OnFragmentInteractionListener,
        AboutProgramFragment.OnFragmentInteractionListener{


    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    ItemFragment itemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            itemFragment = new ItemFragment();
            fragmentTransaction.add(R.id.main, itemFragment, ItemFragment.TAG);
            fragmentTransaction.commit();
        }
        else
        {
            itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentByTag(ItemFragment.TAG);
        }

        navigationView = (NavigationView) findViewById(R.id.navigation_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                if (drawerLayout != null)
                    drawerLayout.closeDrawers();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager
                        .beginTransaction();

                switch (menuItem.getItemId()){

                    case R.id.notesItem:

                        fragmentTransaction.replace(R.id.main, itemFragment, ItemFragment.TAG)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(ItemFragment.TAG)
                                .commit();

                        return true;

                    case R.id.settings:

                        SettingsFragment settingsFragment = new SettingsFragment();
                        fragmentTransaction.replace(R.id.main, settingsFragment, SettingsFragment.TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(SettingsFragment.TAG);
                        fragmentTransaction.commit();

                        return true;


                    case R.id.about:

                        AboutProgramFragment aboutProgramFragment = new AboutProgramFragment();
                        fragmentTransaction.replace(R.id.main, aboutProgramFragment, SettingsFragment.TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(SettingsFragment.TAG);
                        fragmentTransaction.commit();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

        if (itemFragment != null) {
            itemFragment.updateList(bundle);
        }
    }

    @Override
    public void onListFragmentInteraction(Bundle bundle) {

    }

    @Override
    public void onFragmentInteraction(int color) {
        AddFragment addFragment = (AddFragment) getSupportFragmentManager().findFragmentByTag(AddFragment.TAG);

        addFragment.GetArguments(color);

        //temFragment.GoToAddFragment(color);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
