package com.deepak.posts.ui.main;

import android.support.v7.widget.RecyclerView;

import com.deepak.posts.databinding.ItemProgressBinding;
import com.deepak.posts.databinding.PostListRowBinding;


/**
 * View holder of searched data
 * Created by deepak sachdeva on 14/08/17.
 *
 * version 1.0
 */


class ProgressViewHolder extends RecyclerView.ViewHolder {

    ItemProgressBinding itemProgressBinding;

    ProgressViewHolder(ItemProgressBinding itemProgressBinding) {
        super(itemProgressBinding.getRoot());
        this.itemProgressBinding = itemProgressBinding;
    }
}
