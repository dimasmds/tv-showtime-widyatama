package com.widyatama.tvshowtime.adapter.recyler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.widyatama.tvshowtime.BuildConfig;
import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.activity.DetailTVShowActivity;
import com.widyatama.tvshowtime.core.db.model.FavoriteTVShows;
import com.widyatama.tvshowtime.core.model.tvshow.Result;
import com.widyatama.tvshowtime.core.presenter.FavoritePresenter;
import com.widyatama.tvshowtime.core.view.FavoriteView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TVShowListAdapter extends RecyclerView.Adapter<TVShowListAdapter.TVShowListViewHolder> implements FavoriteView {

    private Context context;
    private List<Result> movies;

    public TVShowListAdapter(Context context, List<Result> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public TVShowListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TVShowListViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_tv_shows, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TVShowListViewHolder movieListViewHolder, int i) {
        final int position = i;
        movieListViewHolder.BindItem(movies.get(i));
        movieListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTVShowActivity.class);
                intent.putExtra(DetailTVShowActivity.TITLE_MOVIE, movies.get(position).getTitle());
                intent.putExtra(DetailTVShowActivity.ID_MOVIE, movies.get(position).getId());
                context.startActivity(intent);
            }
        });

        FavoritePresenter presenter = new FavoritePresenter(context, this);

        if (!presenter.isFavorite(movies.get(i).getId())) {
            movieListViewHolder.linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onAdded(String message) {
    }

    @Override
    public void onDeleted(String message) {
    }

    @Override
    public void showFavoriteData(ArrayList<FavoriteTVShows> data) {}

    class TVShowListViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewThumb = itemView.findViewById(R.id.rimImageViewThumbnail);
        private TextView textViewTitle = itemView.findViewById(R.id.rimTextViewTitle);
        private TextView textViewLanguage = itemView.findViewById(R.id.rimTexTViewLanguage);
        private TextView textViewRating = itemView.findViewById(R.id.rimTextViewRating);
        private TextView textViewVote = itemView.findViewById(R.id.rimTextViewVoting);
        private LinearLayout linearLayout = itemView.findViewById(R.id.rimLinearLayoutAddFavorite);

        TVShowListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void BindItem(Result movie) {
            textViewTitle.setText(movie.getTitle());
            textViewRating.setText(String.format(context.getResources().getString(R.string.dummy_rating), movie.getVoteAverage()));
            textViewVote.setText(String.format(context.getResources().getString(R.string.voting), movie.getVoteCount()));
            Picasso.get()
                    .load(BuildConfig.IMAGE_BASE_URL + movie.getBackdropPath())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_broken_image)
                    .into(imageViewThumb);
            textViewLanguage.setText(movie.getOverview());
        }

    }
}
