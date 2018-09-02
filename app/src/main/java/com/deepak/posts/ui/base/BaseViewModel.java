package com.deepak.posts.ui.base;

import android.arch.lifecycle.ViewModel;
import java.lang.ref.WeakReference;

/**
 * Module of SplashActivity
 * Created by deepak sachdeva on 14/08/17.
 *
 * version 1.0
 */
public abstract class BaseViewModel<N> extends ViewModel {

    private WeakReference<N> mNavigator;

    public BaseViewModel() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    protected N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}
