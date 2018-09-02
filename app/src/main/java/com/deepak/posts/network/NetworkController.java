package com.deepak.posts.network;

import com.deepak.posts.PostsApp;
import com.deepak.posts.listeners.IResponseListener;
import com.deepak.posts.models.ResponseModel;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkController {
    public void getPosts(IResponseListener iResponseListener, String key) {
        PostsApp.getRetrofitAPI().getVideoPosts(key).enqueue(new Callback<ResponseModel>() {
            @ParametersAreNonnullByDefault
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                iResponseListener.onResponse(response.body());
            }

            @ParametersAreNonnullByDefault
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iResponseListener.onFailure(t);
            }
        });
    }
}