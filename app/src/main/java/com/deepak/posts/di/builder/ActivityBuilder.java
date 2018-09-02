package com.deepak.posts.di.builder;

import com.deepak.posts.ui.splash.SplashActivity;
import com.deepak.posts.ui.splash.SplashActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();
}
