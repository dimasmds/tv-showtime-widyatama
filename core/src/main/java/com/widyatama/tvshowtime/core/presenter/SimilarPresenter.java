package com.widyatama.tvshowtime.core.presenter;

import android.support.annotation.NonNull;

import com.widyatama.tvshowtime.core.BuildConfig;
import com.widyatama.tvshowtime.core.api.APIRepository;
import com.widyatama.tvshowtime.core.api.Client;
import com.widyatama.tvshowtime.core.model.similar.SimilarResponse;
import com.widyatama.tvshowtime.core.view.SimilarView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimilarPresenter {

    private SimilarView view;

    public SimilarPresenter(SimilarView view) {
        this.view = view;
    }

    public void getSimilar(int movieId, int page) {
        view.showSimilarLoading();
        APIRepository apiRepository = Client.getClient().create(APIRepository.class);
        Call<SimilarResponse> call = apiRepository.getSimilar(movieId, BuildConfig.API_KEY, page);
        call.enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimilarResponse> call, @NonNull Response<SimilarResponse> response) {
                if (response.isSuccessful()) {
                    view.hideSimilarLoading();
                    if (Objects.requireNonNull(response.body()).getResults().size() > 0) {
                        assert response.body() != null;
                        view.showSimilarData(response.body().getResults());
                    } else {
                        view.showSimilarNotFound();
                    }
                } else {
                    view.showSimilarError(response.message());
                    view.hideSimilarLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SimilarResponse> call, @NonNull Throwable t) {
                view.showSimilarError(t.getMessage());
                view.hideSimilarLoading();
            }
        });
    }
}
