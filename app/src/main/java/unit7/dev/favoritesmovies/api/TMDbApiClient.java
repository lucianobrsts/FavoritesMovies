package unit7.dev.favoritesmovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDbApiClient {

    /*private TMDbServiceAPI api;
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
    }*/

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static Retrofit retrofit = null;

    public static Retrofit getTMDbApiClient(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
