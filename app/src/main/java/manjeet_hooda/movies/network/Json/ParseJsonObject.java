package manjeet_hooda.movies.network.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import manjeet_hooda.movies.global.GlobalDataContainer;
import manjeet_hooda.movies.global.Movie;

/**
 * Created by manjeet on 17/4/16.
 */
public class ParseJsonObject {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static ArrayList<Movie> parseJsonResponse(JSONObject response){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        if(response == null || response.length() == 0) {
            return movieArrayList;
        }
        try {
            if(response.has(GlobalDataContainer.KEY_MOVIES)) {
                JSONArray arrayMovies = response.getJSONArray(GlobalDataContainer.KEY_MOVIES);
                for(int i =0; i< arrayMovies.length(); i++){
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    long id = currentMovie.getLong(GlobalDataContainer.KEY_ID);
                    String title = currentMovie.getString(GlobalDataContainer.KEY_TITLE);
                    JSONObject releaseDate = currentMovie.getJSONObject(GlobalDataContainer.KEY_RELEASE_DATES);
                    String date = null;
                    if(releaseDate.has(GlobalDataContainer.KEY_THEATER)) {
                        date = releaseDate.getString(GlobalDataContainer.KEY_THEATER);
                    }
                    else
                    {
                        date = "NA";
                    }
                    JSONObject ratings = currentMovie.getJSONObject(GlobalDataContainer.KEY_RATINGS);
                    int audience_score = -1;
                    if(ratings.has(GlobalDataContainer.KEY_AUDIENCE_SCORE)){
                        audience_score = ratings.getInt(GlobalDataContainer.KEY_AUDIENCE_SCORE);
                    }
                    String synopsis = currentMovie.getString(GlobalDataContainer.KEY_SYNOPSIS);
                    JSONObject objectPosters = currentMovie.getJSONObject(GlobalDataContainer.KEY_POSTERS);
                    String URLThumbnail = null;
                    if(objectPosters.has(GlobalDataContainer.KEY_THUMBNAIL)){
                        URLThumbnail = objectPosters.getString(GlobalDataContainer.KEY_THUMBNAIL);
                    }

                    Movie movie_item = new Movie();
                    movie_item.setmTitle(title);
                    Date dateObject = dateFormat.parse(date);
                    movie_item.setmDate(dateObject);
                    movie_item.setmAudienceScore(audience_score);
                    movie_item.setmSynopsis(synopsis);
                    movie_item.setmUrlThumbnail(URLThumbnail);
                    movieArrayList.add(movie_item);
                }
            }
        }catch (JSONException e){

        }catch (ParseException e){

        }
        return movieArrayList;
    }

}
