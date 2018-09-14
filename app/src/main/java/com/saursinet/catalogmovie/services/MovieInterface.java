package com.saursinet.catalogmovie.services;

import com.saursinet.catalogmovie.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {
    @GET("movie/popular?language=en-US&api_key=" + "486a653b017367025133181e2e5b161a")
    Call<MovieResponse> getMovies(@Query("page") int page);
//    @GET("search/movie?language=en-US&include_adult=false&api_key=" + "486a653b017367025133181e2e5b161a")
//    Call<MovieResponse> searchMovies(@Query("query") String query);
}
