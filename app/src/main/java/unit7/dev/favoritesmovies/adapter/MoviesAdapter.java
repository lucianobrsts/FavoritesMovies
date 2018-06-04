package unit7.dev.favoritesmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import unit7.dev.favoritesmovies.R;
import unit7.dev.favoritesmovies.activity.DetailActivity;
import unit7.dev.favoritesmovies.model.Movie;
import unit7.dev.favoritesmovies.viewHolder.MyViewHolder;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private Context mContext;
    private List<Movie> movieList;

    public MoviesAdapter() { }

    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, userRating;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            userRating = (TextView) view.findViewById(R.id.userRating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Movie clickedDataItem = movieList.get(position);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title", movieList.get(position).getOriginal_title());
                        intent.putExtra("poster_path", movieList.get(position).getPoster_path());
                        intent.putExtra("overview", movieList.get(position).getOverview());
                        intent.putExtra("vote_average", Double.toString(movieList.get(position).getVote_average()));
                        intent.putExtra("release", movieList.get(position).getRelease_date());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        mContext.startActivity(intent);
                        Toast.makeText(view.getContext(), "Você clicou " + clickedDataItem.getOriginal_title(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
