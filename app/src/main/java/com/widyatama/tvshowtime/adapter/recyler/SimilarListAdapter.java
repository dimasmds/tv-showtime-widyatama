package com.widyatama.tvshowtime.adapter.recyler;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.widyatama.tvshowtime.BuildConfig;
import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.activity.DetailTVShowActivity;
import com.widyatama.tvshowtime.core.model.similar.Similar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarListAdapter extends RecyclerView.Adapter<SimilarListAdapter.SimilarListViewHolder> {

    private Activity activity;
    private List<Similar> similars;

    public SimilarListAdapter(Activity activity, List<Similar> similars) {
        this.activity = activity;
        this.similars = similars;
    }

    @NonNull
    @Override
    public SimilarListAdapter.SimilarListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new SimilarListViewHolder(LayoutInflater.from(activity).inflate(R.layout.recycler_item_similar, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarListAdapter.SimilarListViewHolder similarListViewHolder, int i) {
        final int position = i;
        similarListViewHolder.bindItem(similars.get(position));
        similarListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailTVShowActivity.class);
                intent.putExtra(DetailTVShowActivity.TITLE_MOVIE, similars.get(position).getTitle());
                intent.putExtra(DetailTVShowActivity.ID_MOVIE, similars.get(position).getId());
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return similars.size();
    }


    class SimilarListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView = itemView.findViewById(R.id.risImageViewPoster);
        TextView textViewTitle = itemView.findViewById(R.id.risTextViewTitle);
        TextView textViewRating = itemView.findViewById(R.id.risTextViewRating);

        SimilarListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindItem(Similar similar){
            textViewTitle.setText(similar.getTitle());
            textViewRating.setText(String.format(activity.getResources().getString(R.string.dummy_rating), similar.getVoteAverage()));
            Picasso.get()
                    .load(BuildConfig.IMAGE_BASE_URL + similar.getPosterPath())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_broken_image)
                    .into(imageView);
        }
    }
}
