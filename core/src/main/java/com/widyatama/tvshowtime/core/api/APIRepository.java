package com.widyatama.tvshowtime.core.api;

import com.widyatama.tvshowtime.core.model.review.ReviewResponse;
import com.widyatama.tvshowtime.core.model.tvshow.TVShow;
import com.widyatama.tvshowtime.core.model.tvshow.TVShowResponse;
import com.widyatama.tvshowtime.core.model.similar.SimilarResponse;
import com.widyatama.tvshowtime.core.model.video.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRepository {


    @GET("search/tv")
    Call<TVShowResponse> searchTVShows(@Query("api_key") String apiKey,
                                       @Query("language") String language,
                                       @Query("query") String query,
                                       @Query("page") Integer page);

    @GET("tv/airing_today")
    Call<TVShowResponse> getAiringToday(@Query("api_key") String apiKey,
                                        @Query("language") String language,
                                        @Query("page") Integer page,
                                        @Query("timezone") String timezone);

    @GET("tv/on_the_air")
    Call<TVShowResponse> getOnTheAir(@Query("api_key") String apiKey,
                                     @Query("language") String language,
                                     @Query("page") Integer page);

    @GET("tv/{tv_id}")
    Call<TVShow> getDetails(@Path("tv_id") Integer tvId,
                            @Query("api_key") String apiKey,
                            @Query("language") String language);

    @GET("tv/{tv_id}/recommendations")
    Call<SimilarResponse> getSimilar(@Path("tv_id") Integer tvId,
                                     @Query("api_key") String apiKey,
                                     @Query("page") Integer page);

    @GET("tv/{tv_id}/videos")
    Call<VideoResponse> getVideos(@Path("tv_id") Integer tvId,
                                  @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("tv_id") Integer tvId,
                                    @Query("api_key") String apiKey);
}
