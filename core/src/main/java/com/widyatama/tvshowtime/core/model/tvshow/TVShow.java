package com.widyatama.tvshowtime.core.model.tvshow;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.widyatama.tvshowtime.core.model.generic.Genre;
import com.widyatama.tvshowtime.core.model.generic.ProductionCompany;

import java.util.List;

public class TVShow implements Parcelable {

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies;
    @SerializedName("last_air_date")
    @Expose
    private String releaseDate;
    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> runtime;
    @SerializedName("languages")
    @Expose
    private List<String> spokenLanguages;
    @SerializedName("name")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Integer getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getRuntime() {
        return runtime.get(0);
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdropPath);
        dest.writeTypedList(this.genres);
        dest.writeValue(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeTypedList(this.productionCompanies);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.runtime);
        dest.writeValue(this.spokenLanguages);
        dest.writeString(this.title);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
    }

    private TVShow(Parcel in) {
        this.backdropPath = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.productionCompanies = in.createTypedArrayList(ProductionCompany.CREATOR);
        this.releaseDate = in.readString();
        this.runtime = in.readArrayList(Integer.class.getClassLoader());
        this.spokenLanguages = in.createStringArrayList();
        this.title = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TVShow> CREATOR = new Parcelable.Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
