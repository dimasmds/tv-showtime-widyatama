package com.widyatama.tvshowtime.core.db.helper;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVORITE = "table_favorite";
    static String AUTHORITY = "com.widyatama.tvshowtime.core";


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
