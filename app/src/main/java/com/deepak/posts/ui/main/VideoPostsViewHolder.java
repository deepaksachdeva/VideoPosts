package com.deepak.posts.ui.main;

import android.support.v7.widget.RecyclerView;
import com.deepak.posts.databinding.PostListRowBinding;

/**
 * View holder of searched data
 * Created by deepak sachdeva on 14/08/17.
 *
 * version 1.0
 */

public class VideoPostsViewHolder extends RecyclerView.ViewHolder {
    public PostListRowBinding postListRowBinding;
    VideoPostsViewHolder(PostListRowBinding postListRowBinding) {
        super(postListRowBinding.getRoot());
        this.postListRowBinding = postListRowBinding;
    }
}