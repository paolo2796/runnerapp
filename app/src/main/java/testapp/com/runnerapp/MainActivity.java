package testapp.com.runnerapp;

import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unisa.runnerapp.adapters.LiveRequestsAdapter;
import it.unisa.runnerapp.beans.LiveRequest;
import it.unisa.runnerapp.beans.Runner;
import it.unisa.runnerapp.fragments.MapFragment;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle aBarToggle;

    public static Runner user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        setContentView(R.layout.live_run_panel);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout=(DrawerLayout) findViewById(R.id.sideNavRequest);
        aBarToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        aBarToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(aBarToggle);

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Fragment mf=new MapFragment();
        ft.add(R.id.container,mf);
        ft.commit();

        ListView lw=(ListView)findViewById(R.id.receivedRequestsList);
        lw.addHeaderView(getLayoutInflater().inflate(R.layout.nv_liverequests_header,lw,false));
        user=new Runner("mavit","pass","Mauro","Vitale",getResources().getDrawable(R.mipmap.ic_launcher),new Date(),70,200,(short)1);
        List<LiveRequest> l=new ArrayList<>();
        l.add(new LiveRequest(user,new Date()));
        lw.setAdapter(new LiveRequestsAdapter(this,R.layout.nv_liverequests_requestitem,l));
        //user=new Runner("kite321","pass","Kite","Del Kite",null,null,72,210,(short)2);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        aBarToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(aBarToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
