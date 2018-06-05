package unit7.dev.favoritesmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import unit7.dev.favoritesmovies.model.MoviesResponse;

public interface TMDbServiceAPI {

    @GET("/3/movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String API_KEY);
}
