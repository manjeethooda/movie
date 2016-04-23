package manjeet_hooda.movies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import manjeet_hooda.movies.Callbacks.ListEndListener;
import manjeet_hooda.movies.Callbacks.MoviesLoadedListener;
import manjeet_hooda.movies.R;
import manjeet_hooda.movies.adapters.movieRecyclerAdapter;
import manjeet_hooda.movies.global.GlobalDataContainer;
import manjeet_hooda.movies.global.Movie;
import manjeet_hooda.movies.global.MyApplication;
import manjeet_hooda.movies.global.NoConnectionDialog;
import manjeet_hooda.movies.network.ConnectionUtil;
import manjeet_hooda.movies.network.Json.JsonRequest;
/**
 * Created by manjeet on 16/4/16.
 */
public class Search extends Fragment implements MoviesLoadedListener, ListEndListener {

    private ArrayList<Movie> searchList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View view;
    private movieRecyclerAdapter searchAdapter;
    private LinearLayoutManager mLayoutManager;
    private JsonRequest mSearchJsonRequest;
    private String movieName;
    private RelativeLayout mRelativeLayout;
    private EditText mEdittext;
    private Button mButton;
    private int count;
    private boolean onMoviesLoadedCalled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container, false);
        setupProgress();
        getList(savedInstanceState);
        setupButton();
        setupRecyclerView();
        return view;
    }

    private void setupProgress(){
        mRelativeLayout = (RelativeLayout)view.findViewById(R.id.loadingPanel);
        mRelativeLayout.setVisibility(View.INVISIBLE);
    }

    private void setupButton(){
        count = 1;
        mEdittext = (EditText) view.findViewById(R.id.search_movie);
        mButton = (Button) view.findViewById(R.id.search_movie_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieName = mEdittext.getText().toString();
                movieName = String.format(movieName.replaceAll("\\s+","+"));
                mRelativeLayout.setVisibility(View.VISIBLE);
                if (movieName.trim().length() == 0) {
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }else {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdittext.getWindowToken(), 0);
                    count++;
                    searchMovie(movieName);
                }
            }
        });

    }

    public void setupRecyclerView(){
        mRecyclerView = (RecyclerView)view.findViewById(R.id.search_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        searchAdapter = new movieRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(searchAdapter);
    }

    public void getList(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            searchList = savedInstanceState.getParcelableArrayList(GlobalDataContainer.SEARCH_MOVIES_ARRAY_LIST_EXTRA);
            if(searchList.isEmpty()) {
                movieName = savedInstanceState.getString(GlobalDataContainer.SEARCH_MOVIES_NAME);
               if(movieName!=null)
               {
                   if(movieName.length() != 0) {
                       mRelativeLayout.setVisibility(View.VISIBLE);
                       searchMovie(movieName);
                   }
                   return;
               }
            }
            searchAdapter.setMovies_list(searchList);
            mRelativeLayout.setVisibility(View.INVISIBLE);
            //searchProgress.setVisibility(View.GONE);
        }
    }

    public void searchMovie(String movName){
        if(ConnectionUtil.hasDataConnection(getActivity())) {
            //Toast.makeText(getActivity(), "Fetching Data...", Toast.LENGTH_SHORT).show();
            onMoviesLoadedCalled = false;
            mSearchJsonRequest = new JsonRequest(searchList,
                    String.format("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=&q=" + movName),
                    false, this,
                    2);
            mSearchJsonRequest.execute();
            if(count %2 == 0){
               mButton.performClick();
            }
        }else
        {
            NoConnectionDialog.showDialog(getActivity());
            mRelativeLayout.setVisibility(View.INVISIBLE);
        }
        //searchProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GlobalDataContainer.SEARCH_MOVIES_ARRAY_LIST_EXTRA, searchList);
        outState.putString(GlobalDataContainer.SEARCH_MOVIES_NAME, movieName);
        }

    @Override
    public void onMoviesLoaded(ArrayList<Movie> listMovies) {
        if(!onMoviesLoadedCalled) {
            if(listMovies.isEmpty()){
                Toast.makeText(getActivity(),"No Movie Found",Toast.LENGTH_LONG).show();
            }
            searchAdapter.setMovies_list(listMovies);
            mRelativeLayout.setVisibility(View.INVISIBLE);
            onMoviesLoadedCalled = true;
            mRecyclerView.scrollToPosition(0);
        }
        //searchProgress.setVisibility(View.GONE);
    }

    @Override
    public void onListEnding() {
    }
}
