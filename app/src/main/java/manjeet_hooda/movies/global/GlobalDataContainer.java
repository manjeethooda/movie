package manjeet_hooda.movies.global;

import manjeet_hooda.movies.fragment.Search;

/**
 * Created by manjeet on 16/4/16.
 */
public class GlobalDataContainer {

    //api key
    public static final String API_KEY_RT = "";

    //ViewPager
    public static int num_tabs = 3;
    public static String[] tabs = {"Search", "In Theatres", "Upcoming"};

    //webview
    public static final String MOVIE_URI = "movie_URL";
    public static final String MOVIE_TITLE = "movie_title";

    //Movies
    public static final String fetching = "Fetching Data... Please Wait";
    public static int limit = 30;
    public static String MOVIES_URL =
            "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey="+ API_KEY_RT +"&limit=" + limit;
    public static String MOVIES_ARRAY_LIST_EXTRA = "Movies_ArrayList";
    public static String UPCOMING_MOVIES_ARRAY_LIST_EXTRA = "Upcoming_Movies";
    public static String UPCOMING_MOVIES_URL =
            "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=" + API_KEY_RT +"&page_limit=" + limit;
    public static final String SEARCH_MOVIES_ARRAY_LIST_EXTRA = "search_movies_list";
    public static final String SEARCH_MOVIES_URL =
            "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey="+ API_KEY_RT +"&q=";
    public static final String SEARCH_MOVIES_NAME = "search_movie_name";

    //keys
    public static final String KEY_MOVIES="movies";
    public static final String KEY_ID="id";
    public static final String KEY_TITLE="title";
    public static final String KEY_RELEASE_DATES="release_dates";
    public static final String KEY_THEATER="theater";
    public static final String KEY_RATINGS="ratings";
    public static final String KEY_AUDIENCE_SCORE="audience_score";
    public static final String KEY_SYNOPSIS="synopsis";
    public static final String KEY_POSTERS="posters";
    public static final String KEY_THUMBNAIL="thumbnail";
    public static final String KEY_LINKS="links";
    public static final String KEY_SELF="self";
    public static final String KEY_CAST="cast";
    public static final String KEY_REVIEWS="reviews";
    public static final String KEY_SIMILAR="similar";
    public static final String KEY_ALTERNATE="alternate";

}
