package unit7.dev.favoritesmovies.api;

import android.os.AsyncTask;

import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDbAsync extends AsyncTask<Void, Void, Void> {

    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static final String API_KEY = "836f97fe1b57e238994bf977652185ee";;

    @Override
    protected Void doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDbService service = retrofit.create(TMDbService.class);
        try {
            //Response<List<MovieTMDb>> response = service.getMovie().execute().body();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
