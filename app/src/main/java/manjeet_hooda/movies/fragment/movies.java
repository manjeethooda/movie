package manjeet_hooda.movies.fragment;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class movies extends Fragment implements MoviesLoadedListener, ListEndListener {

    private ArrayList<Movie> theatreList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View view;
    private movieRecyclerAdapter theatreAdapter;
    private LinearLayoutManager mLayoutManager;
    private JsonRequest mTheatreJsonRequest;
    private RelativeLayout progressBar;
    private boolean dataConnection;
    private Context mContext;
    private Bundle savedInstanceBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theatre,container, false);
        mContext = getActivity();
        savedInstanceBundle = savedInstanceState;
        progressBar = (RelativeLayout)view.findViewById(R.id.loadingPanel);
        dataConnection = ConnectionUtil.hasDataConnection(getActivity());
        setupRecyclerView();
        getList(savedInstanceState);
        setupFloatingRefresh();
        return view;
    }

    public void setupRecyclerView(){
        mRecyclerView = (RecyclerView)view.findViewById(R.id.movie_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        theatreAdapter = new movieRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(theatreAdapter);
    }

    private void setupFloatingRefresh(){
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!ConnectionUtil.hasDataConnection(mContext))
                        NoConnectionDialog.showDialog(mContext);
                    else {
                        getList(savedInstanceBundle);
                        theatreAdapter.setMovies_list(theatreList);
                        Toast.makeText(mContext, "Fetching Data...", Toast.LENGTH_LONG).show();
                    }
            }
        });

    }

    public void getList(Bundle savedInstanceState){

            theatreList = MyApplication.getadapter().readMovies(0);
            if (theatreList.isEmpty()) {
                mTheatreJsonRequest  = new JsonRequest(theatreList,
                        GlobalDataContainer.MOVIES_URL,
                        false,this,
                        0);
                mTheatreJsonRequest.execute();
            }else
            {
                theatreAdapter.setMovies_list(theatreList);
                progressBar.setVisibility(View.GONE);
            }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GlobalDataContainer.MOVIES_ARRAY_LIST_EXTRA, theatreList);
    }

    @Override
    public void onMoviesLoaded(ArrayList<Movie> listMovies) {
        theatreAdapter.setMovies_list(listMovies);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onListEnding() {
        new JsonRequest(theatreList,
                GlobalDataContainer.MOVIES_URL,
                false,this,
                0).execute();
    }
}
