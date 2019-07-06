package com.widyatama.tvshowtime.core.presenter;

import android.support.annotation.NonNull;

import com.widyatama.tvshowtime.core.BuildConfig;
import com.widyatama.tvshowtime.core.api.APIRepository;
import com.widyatama.tvshowtime.core.api.Client;
import com.widyatama.tvshowtime.core.model.review.ReviewResponse;
import com.widyatama.tvshowtime.core.view.ReviewView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewPresenter {

    private ReviewView view;

    public ReviewPresenter(ReviewView view) {
        this.view = view;
    }

    public void getReview(int id) {
        view.showReviewLoading();
        APIRepository apiRepository = Client.getClient().create(APIRepository.class);
        Call<ReviewResponse> call = apiRepository.getReviews(id, BuildConfig.API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getReviews() != null && response.body().getReviews().size() > 0) {
                            view.hideReviewLoading();
                            view.showReviewData(response.body().getReviews());
                        } else {
                            view.hideReviewLoading();
                            view.showReviewError();
                        }
                    } else {
                        view.hideReviewLoading();
                        view.showReviewError();
                    }

                } else {
                    view.hideReviewLoading();
                    view.showReviewError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                view.hideReviewLoading();
                view.showReviewError();
            }
        });
    }
}
