package com.example.georgekimutai.muvileo.Api;

import com.example.georgekimutai.muvileo.Model.mResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("movie/popular")
    Call<mResponse> getpopularmovies(@Query("api_key")String apiKey);
    @GET("movie/top_rated")
    Call<mResponse> getTopRatedmovies(@Query("api_key")String apiKey);
}
