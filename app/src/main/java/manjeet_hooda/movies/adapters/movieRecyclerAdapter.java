package manjeet_hooda.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import manjeet_hooda.movies.R;
import manjeet_hooda.movies.animations.AnimationUtils;
import manjeet_hooda.movies.global.Movie;
import manjeet_hooda.movies.network.VolleySingleton;

/**
 * Created by manjeet on 16/4/16.
 */
public class movieRecyclerAdapter extends RecyclerView.Adapter<movieRecyclerAdapter.movieRecyclerHolder> {
    private ArrayList<Movie> movies_list = new ArrayList<>();
    private Context mContext;
    private int mPreviousPosition = 10;

    public static class movieRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private ImageView mMovieThumbnail;
        private TextView mDate;
        private RatingBar mScore;
        private Context mContext;
        private Movie movie_item;
        private VolleySingleton volleySingleton;
        private SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy");
        private ImageLoader imageLoader;
        private String date;

        public movieRecyclerHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            mMovieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            mDate = (TextView)itemView.findViewById(R.id.movieReleaseDate);
            mScore = (RatingBar)itemView.findViewById(R.id.movieAudienceScore);
            mContext = context;
            volleySingleton = VolleySingleton.getsInstance();
            imageLoader = volleySingleton.getImageLoader();
        }

        public void bindMovie(Movie movie) {
            mTitle.setText(movie.getmTitle());
            date = format.format(movie.getmDate());
            mDate.setText(date);
            mScore.setRating(movie.getmAudienceScore() / 20.0f);
            String urlThumb = movie.getmUrlThumbnail();
            if(urlThumb != null){
                imageLoader.get(urlThumb, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        mMovieThumbnail.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
        }
    };

    public movieRecyclerAdapter(Context context){
        mContext = context;
    }

    public void setMovies_list(ArrayList<Movie> list){
        this.movies_list = list;
        notifyDataSetChanged();
    }

    @Override
    public movieRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(
                R.layout.movie_item, parent, false);
        return new movieRecyclerHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(movieRecyclerHolder holder, int position){
        Movie movie = movies_list.get(position);
        holder.bindMovie(movie);

        if (position > mPreviousPosition) {
            AnimationUtils.animateSunblind(holder, true);
        } else {
            AnimationUtils.animateSunblind(holder, false);
        }
        mPreviousPosition = position;
    }

    @Override
    public int getItemCount(){
        return movies_list.size();
    }
}