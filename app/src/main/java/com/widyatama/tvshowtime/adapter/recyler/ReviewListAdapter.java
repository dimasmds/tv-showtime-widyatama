package com.widyatama.tvshowtime.adapter.recyler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.core.model.review.Review;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private List<Review> reviews;
    private Context context;

    public ReviewListAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_review, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindItem(reviews.get(i));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor = itemView.findViewById(R.id.rirTextViewAuthor);
        private TextView textViewReview = itemView.findViewById(R.id.rirTextViewReview);

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindItem(Review review) {
            textViewAuthor.setText(review.getAuthor());
            textViewReview.setText(review.getContent());
        }
    }
}
