package com.widyatama.tvshowtime.core.view;

import com.widyatama.tvshowtime.core.model.review.Review;

import java.util.List;

public interface ReviewView {
    void showReviewLoading();
    void hideReviewLoading();
    void showReviewError();
    void showReviewData(List<Review> data);
}
