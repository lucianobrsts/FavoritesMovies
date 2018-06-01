package unit7.dev.favoritesmovies.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unit7.dev.favoritesmovies.BuildConfig;
import unit7.dev.favoritesmovies.R;
import unit7.dev.favoritesmovies.adapter.MoviesAdapter;
import unit7.dev.favoritesmovies.api.TMDbApiClient;
import unit7.dev.favoritesmovies.api.TMDbService;
import unit7.dev.favoritesmovies.model.Movie;
import unit7.dev.favoritesmovies.model.MovieResponse;

public class HomeActivity extends AppCompatActivity {

    public static final String LOG_TAG = MoviesAdapter.class.getName();

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    private SwipeRefreshLayout swipeContainer;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.home_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                Toast.makeText(HomeActivity.this, "Filmes atualizados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Activity getAtivity() {
        Context context = this;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buscando filmes...");
        progressDialog.setCancelable(false);
        progressDialog.dismiss();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this, movieList);

        if(getAtivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void loadJSON() {
        try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor, obtenha a API Key primeiramente do themoviedb.org", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
            TMDbApiClient client = new TMDbApiClient();
            TMDbService apiService = TMDbApiClient.getTMDbApiClient().create(TMDbService.class);
            Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if(swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Erro: ", t.getMessage());
                    Toast.makeText(HomeActivity.this, "Erro ao buscar dados", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Erro: ", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
