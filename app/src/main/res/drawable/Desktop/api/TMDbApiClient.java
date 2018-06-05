package unit7.dev.favoritesmovies.api;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import unit7.dev.favoritesmovies.api.entity.MovieListingDTO;

public class TMDbApiClient {

    public static final String API_KEY = "836f97fe1b57e238994bf977652185ee";
    public static final String BASE_URL = "http://api.themoviedb.org/";
    public static Retrofit retrofit = null;

    private TMDbServiceAPI api;
    private String language;

    public static Retrofit getTMDbApiClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
