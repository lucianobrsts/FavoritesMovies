package unit7.dev.favoritesmovies.viewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import unit7.dev.favoritesmovies.R;
import unit7.dev.favoritesmovies.activity.DetailActivity;
import unit7.dev.favoritesmovies.adapter.MoviesAdapter;
import unit7.dev.favoritesmovies.model.Movie;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title, userRating;
    public ImageView thumbnail;

    MoviesAdapter adapter = new MoviesAdapter();

    public MyViewHolder(View view) {
        super(view);

        title = (TextView) view.findViewById(R.id.title);
        userRating = (TextView) view.findViewById(R.id.userRating);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                adapter = new MoviesAdapter();
                if(position != RecyclerView.NO_POSITION) {
                    Movie clickedDataItem = adapter.getMovieList().get(position);
                    Intent intent = new Intent(adapter.getmContext(), DetailActivity.class);
                    intent.putExtra("original_title", adapter.getMovieList().get(position).getOriginal_title());
                    intent.putExtra("poster_path", adapter.getMovieList().get(position).getPoster_path());
                    intent.putExtra("overview", adapter.getMovieList().get(position).getOverview());
                    intent.putExtra("vote_average", Double.toString(adapter.getMovieList().get(position).getVote_average()));
                    intent.putExtra("release", adapter.getMovieList().get(position).getRelease_date());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    adapter.getmContext().startActivity(intent);
                    Toast.makeText(view.getContext(), "Você clicou " + clickedDataItem.getOriginal_title(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}