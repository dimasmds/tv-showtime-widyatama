package com.widyatama.favoriteshows.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.widyatama.favoriteshows.R;
import com.widyatama.favoriteshows.adapter.FavoriteListAdapter;
import com.widyatama.favoriteshows.model.FavoriteMovie;

import java.util.ArrayList;

import static com.widyatama.favoriteshows.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;

    private ArrayList<FavoriteMovie> favoriteMovieArrayList;

    public final int LOAD_FAVORITE_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mainActivityRecyclerView);

        // why?
        getSupportLoaderManager().initLoader(LOAD_FAVORITE_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        favoriteMovieArrayList = new ArrayList<>();
        return new CursorLoader(this, CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        try {
            favoriteMovieArrayList = getItem(cursor);
            FavoriteListAdapter favoriteListAdapter = new FavoriteListAdapter(this, favoriteMovieArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
            recyclerView.setAdapter(favoriteListAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "You are not installed the main app", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    private ArrayList<FavoriteMovie> getItem(Cursor cursor) {
        ArrayList<FavoriteMovie> favoriteMovies = new ArrayList<>();
        cursor.moveToFirst();
        FavoriteMovie favoriteMovie;
        if (cursor.getCount() > 0) {
            do {
                favoriteMovie = new FavoriteMovie(cursor);
                favoriteMovies.add(favoriteMovie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return favoriteMovies;
    }
}
