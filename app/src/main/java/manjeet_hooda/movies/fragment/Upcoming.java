package manjeet_hooda.movies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
public class Upcoming extends Fragment implements MoviesLoadedListener, ListEndListener {
    private ArrayList<Movie> upcomingList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View view;
    private movieRecyclerAdapter upcomingAdapter;
    private LinearLayoutManager mLayoutManager;
    private JsonRequest mUpcomingJsonRequest;
    private RelativeLayout UprogressBar;
    private Context mContext;
    private Bundle savedInstanceBundle;
    private boolean dataConnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming,container, false);
        mContext = getActivity();
        savedInstanceBundle = savedInstanceState;
        dataConnection = ConnectionUtil.hasDataConnection(getActivity());
        UprogressBar = (RelativeLayout)view.findViewById(R.id.upcomingLoadingPanel);
        setupRecyclerView();
        getList(savedInstanceState);
        setupFloatingRefresh();
        return view;
    }

    private void setupFloatingRefresh(){
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.upcoming_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ConnectionUtil.hasDataConnection(mContext))
                    NoConnectionDialog.showDialog(mContext);
                else {
                    getList(savedInstanceBundle);
                    upcomingAdapter.setMovies_list(upcomingList);
                    Toast.makeText(mContext, "Fetching Data...", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void setupRecyclerView(){
        mRecyclerView = (RecyclerView)view.findViewById(R.id.upcoming_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        upcomingAdapter = new movieRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(upcomingAdapter);

    }

    public void getList(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            upcomingList = savedInstanceState.getParcelableArrayList(GlobalDataContainer.UPCOMING_MOVIES_ARRAY_LIST_EXTRA);
            upcomingAdapter.setMovies_list(upcomingList);
            UprogressBar.setVisibility(View.GONE);
        } else {
            upcomingList = MyApplication.getadapter().readMovies(1);
            if (upcomingList.isEmpty()) {
                mUpcomingJsonRequest  = new JsonRequest(upcomingList,
                        GlobalDataContainer.UPCOMING_MOVIES_URL,
                        false,this,
                        1);
                mUpcomingJsonRequest.execute();
            }else
            {
                upcomingAdapter.setMovies_list(upcomingList);
                UprogressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GlobalDataContainer.UPCOMING_MOVIES_ARRAY_LIST_EXTRA, upcomingList);
    }

    @Override
    public void onMoviesLoaded(ArrayList<Movie> listMovies) {
        upcomingAdapter.setMovies_list(listMovies);
        UprogressBar.setVisibility(View.GONE);
    }

    @Override
    public void onListEnding() {
        new JsonRequest(upcomingList,
                GlobalDataContainer.UPCOMING_MOVIES_URL,
                false,this,
                1).execute();
    }
}
