package com.widyatama.tvshowtime.core.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.widyatama.tvshowtime.core.R;
import com.widyatama.tvshowtime.core.db.helper.MovieHelper;
import com.widyatama.tvshowtime.core.db.model.FavoriteTVShows;
import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.view.FavoriteView;

public class FavoritePresenter {

    private MovieHelper movieHelper;
    private FavoriteView view;
    private Context context;

    public FavoritePresenter(Context context, FavoriteView favoriteView) {
        movieHelper = new MovieHelper(context);
        this.context = context;
        view = favoriteView;
    }

    public boolean isFavorite(int movieId){
        movieHelper.open();
        Cursor cursor = movieHelper.queryById(movieId);
        boolean isExists = !(cursor.getCount() > 0);
        cursor.close();
        movieHelper.close();
        return !isExists;
    }


    public void showFavoriteData(){
        movieHelper.open();
        try{
            view.showFavoriteData(movieHelper.query());
        } catch (SQLException e){
            e.printStackTrace();
        }
        movieHelper.close();
    }

    public boolean addToFavorite(TVShow result){
        movieHelper.open();
        FavoriteTVShows movie = new FavoriteTVShows();

        // assigned result movie from server to local FavoriteTVShows model
        movie.setMovieId(result.getId());
        movie.setMovieTitle(result.getTitle());
        movie.setMovieBackdrop(result.getBackdropPath());
        movie.setMovieVoter(result.getVoteCount());
        movie.setMovieOverview(result.getOverview());
        movie.setMovieRating(result.getVoteAverage());

        long insert = movieHelper.insert(movie);
        movieHelper.close();
        Log.d("addToFavorite", String.valueOf(insert));
        if(insert != -1){
           view.onAdded(context.getString(R.string.message_favorite_added));
           return true;
        } else {
            return false;
        }
    }

    public boolean removeFromFavorite(int movieId){
        movieHelper.open();
        int deletedRow = movieHelper.delete(movieId);
        movieHelper.close();
        Log.d("removeFromFavorite", String.valueOf(movieId));
        if(deletedRow > 0){
            view.onDeleted(context.getString(R.string.message_delete_favorite));
            return true;
        } else {
            return false;
        }
    }
}
