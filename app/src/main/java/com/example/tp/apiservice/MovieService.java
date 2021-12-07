package com.example.tp.apiservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    public static final String ENDPOINT="https://api.themoviedb.org";

    @GET("/3/movie/popular")
    Call<PopularMovieResponse> getPopularMovies(@Query("api_key") String key,@Query("page") int page);

    @GET("/3/movie/upcoming")
    Call<PopularMovieResponse> getUpcomingMovies(@Query("api_key") String key,@Query("page") int page);

    @GET("/3/movie/{movie_id}")
    Call<DetailsMovieResponse> getMovieDetail(@Path("movie_id") int movie_id, @Query("api_key") String key, @Query("page") int page);
}
