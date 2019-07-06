package com.widyatama.favoriteshows.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static String TABLE_FAVORITE = "table_favorite";
    private static String AUTHORITY = "com.widyatama.tvshowtime";


    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static final class MovieColumn implements BaseColumns {

        public static String COLUMN_MOVIE_ID = "movieId";
        public static String COLUMN_MOVIE_TITLE = "movieTitle";
        public static String COLUMN_MOVIE_OVERVIEW = "movieOverview";
        public static String COLUMN_MOVIE_RATING = "movieRating";
        public static String COLUMN_MOVIE_VOTER = "movieVoter";
        public static String COLUMN_MOVIE_BACKDROP = "movieBackdrop";

    }

}
