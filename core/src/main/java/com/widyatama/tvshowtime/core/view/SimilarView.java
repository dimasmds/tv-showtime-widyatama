package com.widyatama.tvshowtime.core.view;

import com.widyatama.tvshowtime.core.model.similar.Similar;

import java.util.List;

public interface SimilarView {
    void showSimilarLoading();
    void hideSimilarLoading();
    void showSimilarError(String message);
    void showSimilarNotFound();
    void showSimilarData(List<Similar> data);
}
