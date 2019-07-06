package com.widyatama.tvshowtime.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.widyatama.tvshowtime.BuildConfig;
import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.adapter.recyler.ReviewListAdapter;
import com.widyatama.tvshowtime.adapter.recyler.SimilarListAdapter;
import com.widyatama.tvshowtime.core.db.model.FavoriteTVShows;
import com.widyatama.tvshowtime.core.model.review.Review;
import com.widyatama.tvshowtime.core.model.similar.Similar;
import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.model.tvshow.TVShowResponse;
import com.widyatama.tvshowtime.core.model.video.Video;
import com.widyatama.tvshowtime.core.presenter.FavoritePresenter;
import com.widyatama.tvshowtime.core.presenter.ReviewPresenter;
import com.widyatama.tvshowtime.core.presenter.SimilarPresenter;
import com.widyatama.tvshowtime.core.presenter.TVShowPresenter;
import com.widyatama.tvshowtime.core.presenter.VideoPresenter;
import com.widyatama.tvshowtime.core.utils.DateUtils;
import com.widyatama.tvshowtime.core.view.FavoriteView;
import com.widyatama.tvshowtime.core.view.ReviewView;
import com.widyatama.tvshowtime.core.view.SimilarView;
import com.widyatama.tvshowtime.core.view.TVShowView;
import com.widyatama.tvshowtime.core.view.VideoView;
import com.widyatama.tvshowtime.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailTVShowActivity extends BaseActivity implements
        TVShowView, FavoriteView, VideoView, SimilarView, ReviewView {

    public static final String TITLE_MOVIE = "titleMovie";
    public static final String ID_MOVIE = "idMovie";
    public static final String MOVIE_STATE = "movieState";
    public static final String VIDEO_STATE = "videoState";
    public static final String SIMILAR_STATE = "similarState";

    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private ImageView favoriteButton;
    private ImageView playButton;
    private TextView titleFooter;
    private ProgressBar progressBarFooter;
    private RecyclerView recyclerView;

    private RecyclerView recyclerVieWReview;
    private ProgressBar progressBarReview;
    private RelativeLayout layoutReview;
    private TextView textViewReview;

    private Boolean isFavorite;
    private Integer movieId;
    private TVShow tvShow;
    private Video video;
    private List<Similar> similars;

    private FavoritePresenter favoritePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.themeDetailState(this);

        setContentView(R.layout.activity_detail_tv_show);

        movieId = getIntent().getIntExtra(ID_MOVIE, 0);

        progressBar = findViewById(R.id.detailActivityProgressBar);
        nestedScrollView = findViewById(R.id.detailActivityNestedScrollView);
        ImageView backButton = findViewById(R.id.detailActivityBackButton);
        favoriteButton = findViewById(R.id.detailActivityFavoriteButton);
        playButton = findViewById(R.id.detailActivityPlayVideo);
        titleFooter = findViewById(R.id.detailMovieActivityTextViewFooter);
        progressBarFooter = findViewById(R.id.detailMovieActivityProgressDialogFooter);
        recyclerView = findViewById(R.id.detailMovieActivityRecyclerViewFooter);

        recyclerVieWReview = findViewById(R.id.detailMovieActivityRecyclerViewReview);
        progressBarReview = findViewById(R.id.detailMovieActivityProgressDialogReview);
        layoutReview = findViewById(R.id.detailMovieActivityLayoutReview);
        textViewReview = findViewById(R.id.detailMovieActivityTextViewReview);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        if (savedInstanceState != null) {
            tvShow = savedInstanceState.getParcelable(MOVIE_STATE);
            assert tvShow != null;
            loadMovieData(tvShow);
            if (savedInstanceState.getParcelable(VIDEO_STATE) != null) {
                video = savedInstanceState.getParcelable(VIDEO_STATE);
                loadVideoData(video);
            }

            if (savedInstanceState.getParcelableArrayList(SIMILAR_STATE) != null) {
                similars = savedInstanceState.getParcelableArrayList(SIMILAR_STATE);
                loadSimilarData(similars);
                progressBarFooter.setVisibility(View.INVISIBLE);
            }

        } else {
            TVShowPresenter presenter = new TVShowPresenter(this);
            presenter.getMovie(movieId);
            VideoPresenter videoPresenter = new VideoPresenter(this, this);
            videoPresenter.getVideo(movieId);
            SimilarPresenter similarPresenter = new SimilarPresenter(this);
            similarPresenter.getSimilar(movieId, 1);
            ReviewPresenter reviewPresenter = new ReviewPresenter(this);
            reviewPresenter.getReview(movieId);
        }

        favoriteState();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    if (favoritePresenter.addToFavorite(tvShow) && tvShow != null) {
                        isFavorite = true;
                        setFavorite();
                    } else {
                        Toast.makeText(DetailTVShowActivity.this, getString(R.string.message_fail_favorite), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (favoritePresenter.removeFromFavorite(movieId)) {
                        isFavorite = false;
                        setFavorite();
                    } else {
                        Toast.makeText(DetailTVShowActivity.this, getString(R.string.message_fail_add_favorite), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void favoriteState() {
        favoritePresenter = new FavoritePresenter(this, this);
        isFavorite = favoritePresenter.isFavorite(movieId);
        setFavorite();
    }

    private void setFavorite() {
        if (isFavorite) {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }
    }

    @Override
    public void showMovieLoading() {
        progressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideMovieLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovieError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovies(TVShowResponse data) {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showMovie(TVShow data) {
        tvShow = data;
        loadMovieData(tvShow);
    }

    private void loadMovieData(TVShow data) {
        try {
            TextView textViewTitle = findViewById(R.id.detailActivityTextViewTitle);
            TextView textViewProduction = findViewById(R.id.detailActivityTextViewProduction);
            TextView textViewYearMovie = findViewById(R.id.detailActivityTextViewYear);
            TextView textViewRating = findViewById(R.id.detailActivityTextViewRating);
            TextView textViewDuration = findViewById(R.id.detailActivityTextViewDuration);
            TextView textViewDescription = findViewById(R.id.detailActivityTextViewOverview);
            TextView textViewLanguage = findViewById(R.id.detailActivityTextViewLanguage);
            LinearLayout linearLayoutCategory = findViewById(R.id.detailActivityLinearLayoutCategory);

            textViewTitle.setText(data.getTitle());
            if (data.getReleaseDate() != null) {
                textViewYearMovie.setText(getString(R.string.last_air_date, DateUtils.dateToString(data.getReleaseDate(),
                        "yyyy-MM-dd", "dd MMM yyyy")));
            } else {
                textViewYearMovie.setVisibility(View.GONE);
            }

            if (data.getProductionCompanies() != null && data.getProductionCompanies().size() != 0) {
                textViewProduction.setText(data.getProductionCompanies().get(0).getName());
            }
            if (data.getVoteAverage() != null) {
                textViewRating.setText(String.format(Locale.getDefault(), "%.1f", data.getVoteAverage()));
            }
            if (data.getOverview() != null) {
                textViewDescription.setText(data.getOverview());
            }
            if (data.getRuntime() != null) {
                textViewDuration.setText(String.format(Locale.getDefault(), getString(R.string.d_minutes), data.getRuntime()));
            }

            final ImageView imageToolbar = findViewById(R.id.detailActivityToolbarBackground);
            Picasso.get()
                    .load(BuildConfig.IMAGE_BASE_URL + data.getBackdropPath())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_broken_image)
                    .into(imageToolbar);
            if (data.getSpokenLanguages() != null) {
                for (int i = 0; i < data.getSpokenLanguages().size(); i++) {
                    if (i == 0)
                        textViewLanguage.setText((new Locale(data.getSpokenLanguages().get(i))).getDisplayLanguage());
                    else
                        textViewLanguage.setText(String.format("%s, %s", textViewLanguage.getText(), (new Locale(data.getSpokenLanguages().get(i))).getDisplayLanguage()));
                }
            }

            if (data.getGenres() != null) {
                for (int i = 0; i < data.getGenres().size(); i++) {
                    TextView category = new TextView(this);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    params.setMarginEnd(10);
                    category.setLayoutParams(params);
                    category.setTextColor(getResources().getColor(android.R.color.white));
                    category.setLines(1);
                    category.setBackgroundColor(getResources().getColor(R.color.alhpa));
                    category.setPadding(10, 5, 10, 5);
                    category.setText(data.getGenres().get(i).getName());
                    linearLayoutCategory.addView(category);
                }
            }

            favoriteButton.setVisibility(View.VISIBLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onAdded(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleted(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFavoriteData(ArrayList<FavoriteTVShows> data) {
    }

    @Override
    public void showVideoData(Video video) {
        this.video = video;
        loadVideoData(this.video);
    }

    private void loadVideoData(final Video video) {
        playButton.setVisibility(View.VISIBLE);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BuildConfig.VIDEO_BASE_URL + video.getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showVideoNotFound(String message) {
        playButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showVideoError(String message) {
        playButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSimilarLoading() {
        progressBarFooter.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSimilarLoading() {
        progressBarFooter.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSimilarError(String message) {
    }

    @Override
    public void showSimilarNotFound() {
    }

    @Override
    public void showSimilarData(List<Similar> data) {
        if (data.size() > 0) {
            titleFooter.setVisibility(View.VISIBLE);
            similars = data;
            loadSimilarData(similars);
        }
    }

    private void loadSimilarData(List<Similar> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        SimilarListAdapter adapter = new SimilarListAdapter(this, data);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_STATE, tvShow);
        outState.putParcelable(VIDEO_STATE, video);
        outState.putParcelableArrayList(SIMILAR_STATE, (ArrayList<? extends Parcelable>) similars);
    }

    // Polymorphism Approach
    @Override
    protected void onResume() {
        super.onResume();
        ThemeUtils.themeDetailState(this);
    }

    @Override
    public void showReviewLoading() {
        progressBarReview.setVisibility(View.VISIBLE);
        recyclerVieWReview.setVisibility(View.INVISIBLE);
        textViewReview.setVisibility(View.GONE);
    }

    @Override
    public void hideReviewLoading() {
        progressBarReview.setVisibility(View.INVISIBLE);
        recyclerVieWReview.setVisibility(View.VISIBLE);
        textViewReview.setVisibility(View.VISIBLE);
    }

    @Override
    public void showReviewError() {
        textViewReview.setVisibility(View.GONE);
    }

    @Override
    public void showReviewData(List<Review> data) {
        ReviewListAdapter adapterReview = new ReviewListAdapter(data, this);
        recyclerVieWReview.setAdapter(adapterReview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerVieWReview.setLayoutManager(layoutManager);
    }
}
