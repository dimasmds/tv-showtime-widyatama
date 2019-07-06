package com.widyatama.favoriteshows.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.widyatama.favoriteshows.BuildConfig;
import com.widyatama.favoriteshows.R;
import com.widyatama.favoriteshows.model.FavoriteMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.MovieListViewHolder>{

    private Context context;
    private ArrayList<FavoriteMovie> movies;

    public FavoriteListAdapter(Context context, ArrayList<FavoriteMovie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public FavoriteListAdapter.MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MovieListViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_movie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteListAdapter.MovieListViewHolder movieListViewHolder, int i) {
        final int position = i;
        movieListViewHolder.BindItem(movies.get(i));
        movieListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, movies.get(position).getMovieTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class MovieListViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewThumb = itemView.findViewById(R.id.rimImageViewThumbnail);
        private TextView textViewTitle = itemView.findViewById(R.id.rimTextViewTitle);
        private TextView textViewLanguage = itemView.findViewById(R.id.rimTexTViewLanguage);
        private TextView textViewRating = itemView.findViewById(R.id.rimTextViewRating);
        private TextView textViewVote = itemView.findViewById(R.id.rimTextViewVoting);

        MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void BindItem(FavoriteMovie movie) {
            textViewTitle.setText(movie.getMovieTitle());
            textViewRating.setText(String.format(context.getResources().getString(R.string.dummy_rating), movie.getMovieRating()));
            textViewVote.setText(String.format(context.getResources().getString(R.string.voting), movie.getMovieVoter()));
            Picasso.get().load(BuildConfig.IMAGE_BASE_URL + movie.getMovieBackdrop()).fit().centerCrop().into(imageViewThumb);
            textViewLanguage.setText(movie.getMovieOverview());
        }

    }

}
