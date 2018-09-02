package com.deepak.posts.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.deepak.posts.R;
import com.deepak.posts.databinding.FragmentPostsBinding;
import com.deepak.posts.listeners.IResponseListener;
import com.deepak.posts.models.Post;
import com.deepak.posts.models.ResponseModel;
import com.deepak.posts.models.SortData;
import com.deepak.posts.network.NetworkController;
import com.deepak.posts.utils.Constants;
import com.deepak.posts.utils.NetworkUtils;
import com.deepak.posts.utils.PaginationScrollListener;
import com.deepak.posts.utils.PreferenceUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link VideoPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPostsFragment extends Fragment implements IResponseListener/*, IOnLoadMoreListener*/ {
    private MainActivity activity;
    private FragmentPostsBinding fragmentPostsBinding;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;
    private VideoPostsAdapter mAdapter;
    private List<Post> listPosts;

    public VideoPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VideoPostsFragment.
     */
    public static VideoPostsFragment newInstance() {
        return new VideoPostsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPostsBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false);
        activity.setUpActionBar(getString(R.string.posts));

        listPosts = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        fragmentPostsBinding.rvMovies.setLayoutManager(mLayoutManager);
        fragmentPostsBinding.rvMovies.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new VideoPostsAdapter(activity, listPosts);
        fragmentPostsBinding.rvMovies.setAdapter(mAdapter);

        fragmentPostsBinding.rvMovies.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (NetworkUtils.isNetworkConnected(activity)) {
                    if (currentPage < TOTAL_PAGES) {
                        mAdapter.addLoadingFooter();
                        isLoading = true;
                        currentPage += 1;

                        if (currentPage == 2) {
                            loadNextPage(Constants.KEY_2);
                        } else {
                            loadNextPage(Constants.KEY_3);
                        }
                    } else {
                        isLastPage = true;
                    }
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();

        return fragmentPostsBinding.getRoot();
    }

    private void displayOfflineData() {
        String json = PreferenceUtils.getAppStringPreference(activity, Constants.API_RESPONSE, Constants.BLANK);
        Gson gson = new Gson();
        ResponseModel responseModel = gson.fromJson(json, ResponseModel.class);
        if (responseModel != null) {
            if (responseModel.getPosts().size() != 0) {
                setVisibility(View.VISIBLE, View.GONE, View.GONE, Constants.BLANK);
                listPosts.addAll(responseModel.getPosts());
                mAdapter.notifyDataSetChanged();
            } else {
                setVisibility(View.GONE, View.GONE, View.VISIBLE, Constants.NO_DATA);
            }
        } else {
            setVisibility(View.GONE, View.GONE, View.VISIBLE, Constants.NO_INTERNET_CONNECTION);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    private void setVisibility(int recycler, int progress, int textView, String txt) {
        fragmentPostsBinding.rvMovies.setVisibility(recycler);
        fragmentPostsBinding.linProgress.setVisibility(progress);
        fragmentPostsBinding.linSearchTxt.setVisibility(textView);
        fragmentPostsBinding.tvMessage.setText(txt);
    }

    private void loadNextPage(String key) {
        if (NetworkUtils.isNetworkConnected(activity)) {
            NetworkController networkController = new NetworkController();
            networkController.getPosts(this, key);
        }
    }

    private void loadFirstPage() {
        if (NetworkUtils.isNetworkConnected(activity)) {
            NetworkController networkController = new NetworkController();
            networkController.getPosts(this, Constants.KEY_1);
        } else {
            displayOfflineData();
        }
    }

    @Override
    public void onResponse(ResponseModel responseModel) {
        if (responseModel != null) {
            if (responseModel.getPosts().size() != 0) {
                setVisibility(View.VISIBLE, View.GONE, View.GONE, Constants.BLANK);
                if (currentPage == PAGE_START) {
                    Gson gson = new Gson();
                    String json = gson.toJson(responseModel);
                    PreferenceUtils.setAppPreference(activity, Constants.API_RESPONSE, json);
                    listPosts.addAll(responseModel.getPosts());
                } else {
                    mAdapter.removeLoadingFooter();
                    isLoading = false;
                    listPosts.addAll(responseModel.getPosts());
                }
                mAdapter.notifyDataSetChanged();
            } else {
                setVisibility(View.GONE, View.GONE, View.VISIBLE, getString(R.string.no_data_found));
            }
        } else {
            setVisibility(View.GONE, View.GONE, View.VISIBLE, getString(R.string.server_error));
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sorted_by_date:
                Collections.sort(listPosts, new SortData(1));
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_sorted_by_likes:
                Collections.sort(listPosts, new SortData(2));
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_sorted_by_views:
                Collections.sort(listPosts, new SortData(3));
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_sorted_by_shares:
                Collections.sort(listPosts, new SortData(4));
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}