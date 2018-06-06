package unit7.dev.favoritesmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import unit7.dev.favoritesmovies.model.MovieResponse;
import unit7.dev.favoritesmovies.model.TrailerResponse;

public interface TMDbService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey,
                                         @Query("language") String language);
										 
	@GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
