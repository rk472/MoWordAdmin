package studio.smartters.mowordadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import studio.smartters.mowordadmin.Fragment.HomeFragment;
import studio.smartters.mowordadmin.Fragment.ImageFragment;
import studio.smartters.mowordadmin.Fragment.VideoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static MainActivity inst;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction f = fm.beginTransaction();
        f.replace(R.id.container_main,new HomeFragment(),"home");
        f.commit();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }
    public static MainActivity getInstance(){
        return inst;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().findFragmentById(R.id.container_main).getTag().equals("home")){
                new AlertDialog.Builder(this).setCancelable(true)
                        .setMessage("Do You Really want to exit ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.super.onBackPressed();
                            }
                        }).setNegativeButton("no",null).show();
            }else{
                Fragment ff = new HomeFragment();
                String tag = "home";
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction f = fm.beginTransaction();
                f.replace(R.id.container_main,ff,tag);
                f.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences s=getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor e=s.edit();
            e.putBoolean("login",false);
            e.apply();
            finishAffinity();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;
        }else if(id==R.id.action_change_password){
            startActivity(new Intent(this,ChangePasswordActivity.class));
        }else{
            startActivity(new Intent(this,AddDocumentActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment ff = null;
        String tag = "other";
        if (id == R.id.nav_home) {
            ff = new HomeFragment();
            tag = "home";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        } else if (id == R.id.nav_image) {
            ff = new ImageFragment();
            tag = "other";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        } else if (id == R.id.nav_video) {
            ff = new VideoFragment();
            tag = "other";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        }else if (id == R.id.nav_search_name) {
            startActivity(new Intent(this,SearchNameActivity.class));
        } else if (id == R.id.nav_search_no) {
            startActivity(new Intent(this,SearchNoActivity.class));
        }
        else if (id == R.id.nav_survey_man) {
            startActivity(new Intent(this,ViewMandalActivity.class));
        } else if (id == R.id.nav_no_voter) {
            startActivity(new Intent(this,NoVoterActivity.class));
        }else if (id == R.id.nav_no_adhar) {
            startActivity(new Intent(this,NoAdharActivity.class));
        }else if (id == R.id.nav_help) {
            startActivity(new Intent(this,HelpViewActivity.class));
        }else if (id == R.id.nav_labharthi) {
            startActivity(new Intent(this,LabharthiListActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void goImage(View v){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction f = fm.beginTransaction();
        f.replace(R.id.container_main,new ImageFragment(),"other");
        f.commit();
    }
    public void goVideo(View v){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction f = fm.beginTransaction();
        f.replace(R.id.container_main,new VideoFragment(),"other");
        f.commit();
    }
    public void goHelp(View v){
        startActivity(new Intent(this,HelpViewActivity.class));
    }
    public void goNameSr(View v){
        startActivity(new Intent(this,SearchNameActivity.class));
    }
    public void goNoSr(View v){
        startActivity(new Intent(this,SearchNoActivity.class));
    }
    public void goSurveyor(View v){
        startActivity(new Intent(this,ViewMandalActivity.class));
    }
    public void goNoAdhar(View v){
        startActivity(new Intent(this,NoAdharActivity.class));
    }
    public void goNoVoter(View v){
        startActivity(new Intent(this,NoVoterActivity.class));
    }
    public void goLabharthi(View v){
        startActivity(new Intent(this,LabharthiListActivity.class));
    }

}