package com.widyatama.tvshowtime.core.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_BACKDROP;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_ID;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_OVERVIEW;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_RATING;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_TITLE;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_VOTER;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s " +
                    "(%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT, " +
                    "%s REAL, %s INTEGER, %s TEXT, UNIQUE (%s) ON CONFLICT REPLACE)",
            TABLE_FAVORITE, _ID, COLUMN_MOVIE_ID, COLUMN_MOVIE_TITLE, COLUMN_MOVIE_OVERVIEW, COLUMN_MOVIE_RATING,
            COLUMN_MOVIE_VOTER, COLUMN_MOVIE_BACKDROP, COLUMN_MOVIE_ID);


    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }
}
