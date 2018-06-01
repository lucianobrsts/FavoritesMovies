package unit7.dev.favoritesmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import unit7.dev.favoritesmovies.model.Movie;

public interface TMDbService {

    @GET("movie/popular")
    Call<Movie> getPopularMovies(@Query("api_key") String apiKey);
}
