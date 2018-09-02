package com.deepak.posts.ui.main;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deepak.posts.R;
import com.deepak.posts.databinding.ItemProgressBinding;
import com.deepak.posts.databinding.PostListRowBinding;
import com.deepak.posts.models.Post;
import com.deepak.posts.utils.Constants;
import java.util.List;

/**
 * To display list of search data
 * Created by deepak sachdeva on 14/08/17.
 * <p>
 * version 1.0
 */

public class VideoPostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private List<Post> listPosts;
    private Context context;
    private LayoutInflater layoutInflater;

    VideoPostsAdapter(Context context, List<Post> listPosts) {
        this.context = context;
        this.listPosts = listPosts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        switch (viewType) {
            case ITEM:
                PostListRowBinding postListRowBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.post_list_row, parent, false);
                viewHolder = new VideoPostsViewHolder(postListRowBinding);
                break;
            case LOADING:
                ItemProgressBinding itemProgressBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.item_progress, parent, false);
                viewHolder = new ProgressViewHolder(itemProgressBinding);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                if (listPosts != null) {
                    VideoPostsViewHolder videoPostsViewHolder = (VideoPostsViewHolder) holder;
                    for (int i = 0; i < listPosts.size(); i++) {
                        videoPostsViewHolder.postListRowBinding.setPost(listPosts.get(position));
                    }

//                    videoPostsViewHolder.postListRowBinding.linParent.setTag(R.integer.selected_index, position);
                }
                break;
            case LOADING:
                // Do nothing
                break;
        }
    }

    /**
     * use to display image url coming from server using data-binding
     *
     * @param view     view
     * @param imageUrl url of image
     */
    @BindingAdapter({Constants.MOVIE_POSTER})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.product_placeholder))
                .into(view);
    }

    @Override
    public int getItemCount() {
        return listPosts == null ? 0 :listPosts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listPosts.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Post());
    }

    public void add(Post post) {
        listPosts.add(post);
        notifyItemInserted(listPosts.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = listPosts.size() - 1;
        Post item = getItem(position);
        if (item != null) {
            listPosts.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Post getItem(int position) {
        return listPosts.get(position);
    }
}