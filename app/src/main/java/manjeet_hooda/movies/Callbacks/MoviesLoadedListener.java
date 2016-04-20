package manjeet_hooda.movies.Callbacks;

import java.util.ArrayList;

import manjeet_hooda.movies.global.Movie;

/**
 * Created by manjeet on 17/4/16.
 */
public interface MoviesLoadedListener {
        public void onMoviesLoaded(ArrayList<Movie> listMovies);

}
