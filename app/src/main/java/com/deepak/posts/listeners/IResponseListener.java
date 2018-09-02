package com.deepak.posts.listeners;

import com.deepak.posts.models.ResponseModel;

public interface IResponseListener {

    void onResponse(ResponseModel apiResponse);

    void onFailure(Throwable throwable);
}
