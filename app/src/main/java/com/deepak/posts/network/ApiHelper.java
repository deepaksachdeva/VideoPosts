package com.deepak.posts.network;

import com.deepak.posts.models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiHelper {

    @GET("{key}")
    Call<ResponseModel> getVideoPosts(@Path("key") String key);
}