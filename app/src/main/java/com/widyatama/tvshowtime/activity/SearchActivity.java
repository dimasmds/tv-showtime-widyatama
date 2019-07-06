package com.widyatama.tvshowtime.activity;

import android.content.Context;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.adapter.recyler.TVShowListAdapter;
import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.model.tvshow.TVShowResponse;
import com.widyatama.tvshowtime.core.model.tvshow.Result;
import com.widyatama.tvshowtime.core.presenter.TVShowPresenter;
import com.widyatama.tvshowtime.core.view.TVShowView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements TVShowView {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private EditText editText;
    private TextView emptyMovie;

    private TVShowPresenter moviePresenter;
    private List<Result> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageView backButton = findViewById(R.id.searchActivityBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressBar = findViewById(R.id.searchActivityProgressBar);
        recyclerView = findViewById(R.id.searchActivityRecyclerView);
        editText = findViewById(R.id.searchActivityEditText);
        emptyMovie = findViewById(R.id.empty_movie);

        movies = new ArrayList<>();

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
            loadMovies(movies);
            editText.setText(savedInstanceState.getString("query"));
        } else {
            moviePresenter = new TVShowPresenter(this);
        }

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (editText.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.empty_query), Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    String query = editText.getText().toString();
                    if (emptyMovie.getVisibility() == View.VISIBLE)
                        emptyMovie.setVisibility(View.GONE);
                    moviePresenter.searchTVShow(query, 1);
                    dismissKeyboard();

                    return true;
                }
                return false;

            }
        });
    }

    @Override
    public void showMovieLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideMovieLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovieError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovies(TVShowResponse data) {
        movies = data.getResults();
        loadMovies(movies);
    }

    private void loadMovies(List<Result> movies) {
        TVShowListAdapter movieListAdapter = new TVShowListAdapter(this, movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(movieListAdapter);

        if (movies.size() == 0)
            emptyMovie.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovie(TVShow data) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movies);
        outState.putString("query", editText.getText().toString());
    }

    private void dismissKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
