package unit7.dev.favoritesmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import unit7.dev.favoritesmovies.api.entity.MovieListingDTO;

public interface TMDbServiceAPI {

    String API_KEY = "3b4c65c3780fc1ef44ec5500b186d833";
    String BASE_URL = "http://api.themoviedb.org";
    String BASE_IMAGES_URL = "http://image.tmdb.org/t/p/";
    String POSTER_SIZE = "w185";
    String BACKDROP_SIZE = "w780";

    @GET("/3/movie/popular")
    Call<MovieListingDTO> getMostPopular(@Query("api_key") String API_KEY,
                                         @Query("page") int page,
                                         @Query("language") String language);
}
