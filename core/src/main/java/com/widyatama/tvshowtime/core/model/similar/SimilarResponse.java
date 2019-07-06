package com.widyatama.tvshowtime.core.model.similar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarResponse {

    @SerializedName("results")
    @Expose
    private List<Similar> results = null;

    public List<Similar> getResults() {
        return results;
    }

}
