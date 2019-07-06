package com.widyatama.tvshowtime.core.view;

import com.widyatama.tvshowtime.core.db.model.FavoriteTVShows;

import java.util.ArrayList;

public interface FavoriteView {
    void onAdded(String message);
    void onDeleted(String message);
    void showFavoriteData(ArrayList<FavoriteTVShows> data);
}
