package com.widyatama.tvshowtime.core.view;

import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.model.tvshow.TVShowResponse;

public interface TVShowView {
    void showMovieLoading();
    void hideMovieLoading();
    void showMovieError(String message);
    void showMovies(TVShowResponse data);
    void showMovie(TVShow data);
}
