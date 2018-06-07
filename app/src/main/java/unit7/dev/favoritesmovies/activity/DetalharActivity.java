package unit7.dev.favoritesmovies.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unit7.dev.favoritesmovies.BuildConfig;
import unit7.dev.favoritesmovies.R;
import unit7.dev.favoritesmovies.adapter.TrailerAdapter;
import unit7.dev.favoritesmovies.api.TMDbApiClient;
import unit7.dev.favoritesmovies.api.TMDbService;
import unit7.dev.favoritesmovies.data.FavoriteContract;
import unit7.dev.favoritesmovies.data.FavoriteDbHelper;
import unit7.dev.favoritesmovies.model.Movie;
import unit7.dev.favoritesmovies.model.Trailer;
import unit7.dev.favoritesmovies.model.TrailerResponse;

public class DetalharActivity extends AppCompatActivity {

    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;
    private RecyclerView recyclerView;
    private TrailerAdapter adapter;
    private List<Trailer> trailerList;
    private FavoriteDbHelper favoriteDbHelper;
    private Movie favorite;
    private final AppCompatActivity activity = DetalharActivity.this;
    private SQLiteDatabase mDb;

    Movie movie;
    String thumbnail, movieName, synopsis, rating, dateOfRelease;
    int movie_id;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
        mDb = dbHelper.getWritableDatabase();


        imageView = (ImageView) findViewById(R.id.thumbnail_image_header);
        plotSynopsis = (TextView) findViewById(R.id.plotsynopsis);
        userRating = (TextView) findViewById(R.id.userrating);
        releaseDate = (TextView) findViewById(R.id.releasedate);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("movies")){

            movie = getIntent().getParcelableExtra("movies");

            thumbnail = movie.getPoster_path();
            movieName = movie.getOriginal_title();
            synopsis = movie.getOverview();
            rating = Double.toString(movie.getVote_average());
            dateOfRelease = movie.getRelease_date();
            movie_id = movie.getId();

            String poster = "https://image.tmdb.org/t/p/w185" + thumbnail;

            Picasso.get().load(poster).placeholder(R.drawable.loading).into(imageView);

            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(dateOfRelease);

            ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle(movieName);
        }else{
            Toast.makeText(this, "Sem dados da API.", Toast.LENGTH_LONG).show();
        }

       MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_button);

        if (exists(movieName)){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite == true) {
                        saveFavorite();
                        Snackbar.make(buttonView, "Adicionado aos Favoritos",
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        favoriteDbHelper = new FavoriteDbHelper(DetalharActivity.this);
                        favoriteDbHelper.deleteFavorite(movie_id);
                        Snackbar.make(buttonView, "Removido dos Favoritos",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite == true) {
                        saveFavorite();
                        Snackbar.make(buttonView, "Adicionado aos Favoritos.",
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        int movie_id = getIntent().getExtras().getInt("id");
                        favoriteDbHelper = new FavoriteDbHelper(DetalharActivity.this);
                        favoriteDbHelper.deleteFavorite(movie_id);
                        Snackbar.make(buttonView, "Removido dos Favoritos.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
        initViews();
    }

    public boolean exists(String searchItem) {

        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS
        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =? ";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private void initViews(){
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(this, trailerList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();

    }

    private void loadJSON(){
        try{
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor, primeiramente obtenha a API Key do themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }

            TMDbApiClient client = new TMDbApiClient();
            TMDbService apiService = TMDbApiClient.getTMDbApiClient().create(TMDbService.class);
            Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    List<Trailer> trailer = response.body().getResults();
                    recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetalharActivity.this, "Erro ao buscar dados.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Log.d("Erro", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveFavorite(){
        favoriteDbHelper = new FavoriteDbHelper(activity);
        favorite = new Movie();

        Double rate = movie.getVote_average();

        favorite.setId(movie_id);
        favorite.setOriginal_title(movieName);
        favorite.setPoster_path(thumbnail);
        favorite.setVote_average(rate);
        favorite.setOverview(synopsis);

        favoriteDbHelper.addFavorite(favorite);
    }

}