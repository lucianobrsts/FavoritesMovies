package unit7.dev.favoritesmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unit7.dev.favoritesmovies.R;
import unit7.dev.favoritesmovies.model.Movie;
import unit7.dev.favoritesmovies.viewHolder.MyViewHolder;

public class MoviesAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context mContext;
    private List<Movie> movieList;

    public MoviesAdapter() { }

    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(movieList.get(i).getOriginal_title());

        String vote = Double.toString(movieList.get(i).getVote_average());
        viewHolder.userRating.setText(vote);

        // implematation Glide:4.7.1
        Glide.with(mContext)
                .load(movieList.get(i).getPoster_path())
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
