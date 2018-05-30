package unit7.dev.favoritesmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbService {

    @GET("/3/movie/{id}")
    Call<MovieTMDb> getMovie(@Path("id") int id,
                             @Query("api_key") String api_key,
                             @Query("language") String lang);
}
