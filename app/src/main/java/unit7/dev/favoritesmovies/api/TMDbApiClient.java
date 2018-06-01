package unit7.dev.favoritesmovies.api;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import unit7.dev.favoritesmovies.api.entity.MovieListingDTO;

public class TMDbApiClient {

    private TMDbServiceAPI api;
    private String language;

    public TMDbApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TMDbServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(TMDbServiceAPI.class);
        this.language = Locale.getDefault().getLanguage();
    }

    public MovieListingDTO getTopMovies(int page) throws IOException {
        return this.api.getMostPopular(TMDbServiceAPI.API_KEY, page, this.language).execute()
                .body();
    }
}
