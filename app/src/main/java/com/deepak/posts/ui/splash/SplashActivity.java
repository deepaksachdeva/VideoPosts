package com.deepak.posts.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import com.deepak.posts.BR;
import com.deepak.posts.R;
import com.deepak.posts.databinding.ActivitySplashBinding;
import com.deepak.posts.ui.base.BaseActivity;
import com.deepak.posts.ui.base.BaseViewModel;
import com.deepak.posts.ui.main.MainActivity;
import javax.inject.Inject;

/**
 * This is splash screen it comes only for few seconds
 *
 * Created by deepak sachdeva on 13/08/17.
 *
 * version 1.0
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, BaseViewModel> implements SplashNavigator {

    @Inject
    SplashViewModel mSplashViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public BaseViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.setNavigator(this);
        mSplashViewModel.startSeeding();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}