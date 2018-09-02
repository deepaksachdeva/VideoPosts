package com.deepak.posts.ui.main;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.deepak.posts.R;
import com.deepak.posts.databinding.ActivityMainBinding;

/**
 * MainActivity to search movie from search option
 * Created by deepak sachdeva on 14/08/17.
 * <p>
 * version 1.0
 */

public class MainActivity extends AppCompatActivity implements Runnable {
    private Fragment fragment;
    private Handler mHandler;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this,
                R.layout.activity_main);
        mHandler = new Handler();
        if (savedInstanceState == null) {
            replaceFragment(VideoPostsFragment.newInstance());
        }
    }

    public void setUpActionBar(String title){
        activityMainBinding.toolbar.setTitle(title);
        setSupportActionBar(activityMainBinding.toolbar);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        // Inflate menu to add items to action bar if it is present.
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }

    public void replaceFragment(Fragment fragment) {
        this.fragment = fragment;
        mHandler.post(this);
    }

    @Override
    public void run() {
        // update the main content by replacing fragments

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        String backStateFragmentName = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate (backStateFragmentName, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateFragmentName) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame_main, fragment, backStateFragmentName);
            fragmentTransaction.addToBackStack(backStateFragmentName);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        } else {
            super.onBackPressed();
        }
    }
}