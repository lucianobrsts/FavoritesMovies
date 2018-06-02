package unit7.dev.favoritesmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import unit7.dev.favoritesmovies.model.Movie;
import unit7.dev.favoritesmovies.model.MovieResponse;

public interface TMDbService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);
}
