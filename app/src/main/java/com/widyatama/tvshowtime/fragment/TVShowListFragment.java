package com.widyatama.tvshowtime.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.adapter.recyler.FavoriteListAdapter;
import com.widyatama.tvshowtime.adapter.recyler.TVShowListAdapter;
import com.widyatama.tvshowtime.core.db.model.FavoriteTVShows;
import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.model.tvshow.TVShowResponse;
import com.widyatama.tvshowtime.core.model.tvshow.Result;
import com.widyatama.tvshowtime.core.presenter.FavoritePresenter;
import com.widyatama.tvshowtime.core.presenter.TVShowPresenter;
import com.widyatama.tvshowtime.core.view.FavoriteView;
import com.widyatama.tvshowtime.core.view.TVShowView;

import java.util.ArrayList;
import java.util.List;

public class TVShowListFragment extends Fragment implements TVShowView, FavoriteView {

    public static final String POSITION = "position";
    public static final String LIST_STATE_KEY = "list_state";

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyFavorite;

    private List<Result> movies;
    private ArrayList<FavoriteTVShows> favoriteTVShows;

    int position;
    TVShowPresenter tvShowPresenter;
    FavoritePresenter favoritePresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.movieFragmentRecyclerView);
        progressBar = view.findViewById(R.id.movieFragmentProgressBar);
        swipeRefreshLayout = view.findViewById(R.id.movieFragmentSwipeRefreshLayout);
        emptyFavorite = view.findViewById(R.id.empty_favorite);
        position = getArguments() != null ? getArguments().getInt(POSITION, 0) : 0;
        tvShowPresenter = new TVShowPresenter(this);
        favoritePresenter = new FavoritePresenter(getContext(),this);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        movies = new ArrayList<>();
        favoriteTVShows = new ArrayList<>();

        if(savedInstanceState != null){
            movies = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            TVShowListAdapter movieListAdapter = new TVShowListAdapter(getContext(), movies);
            recyclerView.setLayoutManager(layoutManager);
            if(position != 2)
                recyclerView.setAdapter(movieListAdapter);
            else
                favoritePresenter.showFavoriteData();
        } else {
            if (position == 0) {
                tvShowPresenter.getAiringToday(1);
            } else if(position == 1) {
                tvShowPresenter.getOnTheAir(1);
            } else {
                favoritePresenter.showFavoriteData();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (position == 0) {
                    tvShowPresenter.getAiringToday(1);
                } else if(position == 1) {
                    tvShowPresenter.getOnTheAir(1);
                } else {
                    favoritePresenter.showFavoriteData();
                }
            }
        });
    }

    @Override
    public void showMovieLoading() {
        if(!swipeRefreshLayout.isRefreshing()){
            progressBar.setVisibility(View.VISIBLE);
        }
        recyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void hideMovieLoading() {
        if(!swipeRefreshLayout.isRefreshing()){
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovieError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showMovies(TVShowResponse data) {
        movies.clear();
        movies.addAll(data.getResults());
        TVShowListAdapter movieListAdapter = new TVShowListAdapter(getContext(), movies);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieListAdapter);
    }

    @Override
    public void showMovie(TVShow data) {}
    @Override
    public void onAdded(String message) {}
    @Override
    public void onDeleted(String message) {}

    @Override
    public void showFavoriteData(ArrayList<FavoriteTVShows> data) {
        if(emptyFavorite.getVisibility() == View.VISIBLE)
            emptyFavorite.setVisibility(View.GONE);

        favoriteTVShows.clear();
        favoriteTVShows.addAll(data);
        FavoriteListAdapter favoriteAdapter = new FavoriteListAdapter(getContext(), favoriteTVShows);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteAdapter);
        swipeRefreshLayout.setRefreshing(false);

        if(favoriteTVShows.size() == 0)
            emptyFavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(LIST_STATE_KEY, (ArrayList<? extends Parcelable>) movies);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(position == 2) {
            favoritePresenter.showFavoriteData();
        }
    }
}
