package com.widyatama.tvshowtime.core.presenter;

import android.support.annotation.NonNull;

import com.widyatama.tvshowtime.core.BuildConfig;
import com.widyatama.tvshowtime.core.api.APIRepository;
import com.widyatama.tvshowtime.core.api.Client;
import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.model.tvshow.TVShowResponse;
import com.widyatama.tvshowtime.core.utils.Constant;
import com.widyatama.tvshowtime.core.view.TVShowView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowPresenter {

    private TVShowView view;

    public TVShowPresenter(TVShowView view) {
        this.view = view;
    }

    public void searchTVShow(String query, Integer page){
        view.showMovieLoading();
        APIRepository apiRepository = Client.getClient().create(APIRepository.class);
        Call<TVShowResponse> call = apiRepository.searchTVShows(BuildConfig.API_KEY, Constant.DEFAULT_MOVIE_LANGUAGE, query, page);
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                if(response.isSuccessful()){
                    view.showMovies(response.body());
                    view.hideMovieLoading();
                } else {
                    view.showMovieError(response.message());
                    view.hideMovieLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                view.showMovieError(t.getMessage());
                view.hideMovieLoading();
            }
        });
    }

    public void getAiringToday(Integer page){
        view.showMovieLoading();
        APIRepository apiRepository = Client.getClient().create(APIRepository.class);
        Call<TVShowResponse> call = apiRepository.getAiringToday(BuildConfig.API_KEY, Constant.DEFAULT_MOVIE_LANGUAGE, page, Constant.DEFAULT_TIMEZONE);
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                if(response.isSuccessful()){
                    view.showMovies(response.body());
                    view.hideMovieLoading();
                } else {
                    view.showMovieError(response.message());
                    view.hideMovieLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                view.showMovieError(t.getMessage());
                view.hideMovieLoading();
            }
        });
    }

    public void getOnTheAir(Integer page){
        view.showMovieLoading();
        APIRepository apiRepository = Client.getClient().create(APIRepository.class);
        Call<TVShowResponse> call = apiRepository.getOnTheAir(BuildConfig.API_KEY, Constant.DEFAULT_MOVIE_LANGUAGE, page);
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                if(response.isSuccessful()){
                    view.showMovies(response.body());
                    view.hideMovieLoading();
                } else {
                    view.showMovieError(response.message());
                    view.hideMovieLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                view.showMovieError(t.getMessage());
                view.hideMovieLoading();
            }
        });
    }

    public void getMovie(Integer movieId){
        view.showMovieLoading();
        APIRepository apiRepository = Client.getClient().create(APIRepository.class);
        Call<TVShow> call = apiRepository.getDetails(movieId, BuildConfig.API_KEY, Constant.DEFAULT_MOVIE_LANGUAGE);
        call.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(@NonNull Call<TVShow> call, @NonNull Response<TVShow> response) {
                if(response.isSuccessful()){
                    view.showMovie(response.body());
                    view.hideMovieLoading();
                } else {
                    view.showMovieError(response.message());
                    view.hideMovieLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TVShow> call, @NonNull Throwable t) {
                view.showMovieError(t.getMessage());
                view.hideMovieLoading();
            }
        });
    }
}
