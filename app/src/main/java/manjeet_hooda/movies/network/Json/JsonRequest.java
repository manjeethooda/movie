package manjeet_hooda.movies.network.Json;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import manjeet_hooda.movies.Callbacks.MoviesLoadedListener;
import manjeet_hooda.movies.global.Movie;
import manjeet_hooda.movies.global.MyApplication;
import manjeet_hooda.movies.network.VolleySingleton;

/**
 * Created by manjeet on 17/4/16.
 */
public class JsonRequest extends AsyncTask<Void, Void, ArrayList<Movie> >{

    private ArrayList<Movie> moviesList;
    private boolean clearList;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private String URL;
    private MoviesLoadedListener myListener;
    private int Table;

    public JsonRequest(ArrayList<Movie> list, String URL, boolean clearList, MoviesLoadedListener myComponent, int TABLE){
        this.moviesList = list;
        this.clearList = clearList;
        this.URL = URL;
        this.Table = TABLE;
        mVolleySingleton = VolleySingleton.getsInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        this.myListener = myComponent;
    }

    public void sendJsonRequest(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        moviesList = ParseJsonObject.parseJsonResponse(response);
                        MyApplication.getadapter().insertMovies(Table, moviesList, clearList);
                        myListener.onMoviesLoaded(moviesList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        sendJsonRequest();
        return moviesList;
    }
}
