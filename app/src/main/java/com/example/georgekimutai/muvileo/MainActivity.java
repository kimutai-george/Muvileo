package com.example.georgekimutai.muvileo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.georgekimutai.muvileo.Adapter.mAdapter;
import com.example.georgekimutai.muvileo.Api.ApiConfig;
import com.example.georgekimutai.muvileo.Api.Service;
import com.example.georgekimutai.muvileo.Model.Muvy;
import com.example.georgekimutai.muvileo.Model.mResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView movierecyclerview;
    private mAdapter movieAdapter;
    private List<Muvy> movielist;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String LOG=mAdapter.class.getName();
    private ProgressBar progressBar;
    private final String SCROLL_POSITION="recyclerview_position";
    private int mPosition=RecyclerView.NO_POSITION;
    private LinearLayoutManager linearLayoutManager;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.pb_loading_indicator);
        progressBar.setVisibility(View.VISIBLE);
        internet_connection_check();

        initViews();


        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.main_content);
        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressBar.setVisibility(View.INVISIBLE);
                internet_connection_check();
                initViews();
            }
        });


    }
    public Activity getActivity(){
        Context context=this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context=((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    private void initViews(){

        progressBar.setVisibility(View.VISIBLE);

        movierecyclerview=(RecyclerView)findViewById(R.id.maincontent_recyclerview);
        movielist=new ArrayList<>();
        movieAdapter=new mAdapter(this,movielist);
        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            movierecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            movierecyclerview.setLayoutManager(new GridLayoutManager(this, 4));

        }

        movierecyclerview.setItemAnimator(new DefaultItemAnimator());
        movierecyclerview.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);

        getSortOrder();
        internet_connection_check();

    }

    private void loadJSONData(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                String message="Please Obtain API From themoviedb.org";
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            ApiConfig apiConfig=new ApiConfig();
            Service apiservice=apiConfig.getClient().create(Service.class);
            retrofit2.Call<mResponse> call=apiservice.getpopularmovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<mResponse>() {
                @Override
                public void onResponse(retrofit2.Call<mResponse> call, Response<mResponse> response) {
                    List<Muvy> muvies=response.body().getResults();
                    movierecyclerview.setAdapter(new mAdapter(getApplicationContext(),muvies));
                    movierecyclerview.smoothScrollToPosition(0);
                    progressBar.setVisibility(View.INVISIBLE);

                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }

                @Override
                public void onFailure(retrofit2.Call<mResponse> call, Throwable t) {
                   /* Log.d("Error",t.getMessage());
                    String message="Error while Fetching Data";
                    Toast toast=Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT);
                    toast.show();*/

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.d("Error",e.getMessage());
            Toast toast=Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    private void loadJSONDatatoprated(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                String message="Please Obtain API From themoviedb.org";
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            ApiConfig apiConfig=new ApiConfig();
            Service apiservice=apiConfig.getClient().create(Service.class);
            retrofit2.Call<mResponse> call=apiservice.getTopRatedmovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<mResponse>() {
                @Override
                public void onResponse(retrofit2.Call<mResponse> call, Response<mResponse> response) {
                    List<Muvy> muvies=response.body().getResults();
                    movierecyclerview.setAdapter(new mAdapter(getApplicationContext(),muvies));
                    movierecyclerview.smoothScrollToPosition(0);
                    progressBar.setVisibility(View.INVISIBLE);

                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }

                @Override
                public void onFailure(retrofit2.Call<mResponse> call, Throwable t) {
                    /*Log.d("Error",t.getMessage());
                    String message="Error while Fetching Data";
                    Toast toast=Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT);
                    toast.show();*/

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.d("Error",e.getMessage());
            Toast toast=Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settingsmenu:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
        return super.onOptionsItemSelected(item);
    }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      //  Log.d(LOG,"Preference Successful");
        getSortOrder();
    }
    public void getSortOrder(){
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String sortorder=sharedPreferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular)
        );
        if (sortorder.equals(this.getString(R.string.pref_most_popular))){
           // Log.d(LOG,"Sort order is by most Popular");
            loadJSONData();
        }else {
           // Log.d(LOG,"Sorting by Average");
            loadJSONDatatoprated();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
       super.onResume();
        if (movielist.isEmpty()){
            getSortOrder();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (movierecyclerview != null) {
            outState.putParcelable(SCROLL_POSITION, movierecyclerview.getLayoutManager().onSaveInstanceState());

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SCROLL_POSITION)) {
            mPosition = savedInstanceState.getInt(SCROLL_POSITION);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            // Scroll the RecyclerView to mPosition
            movierecyclerview.smoothScrollToPosition(mPosition);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
    public void internet_connection_check(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
                  }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("You're not connected to any network");
            builder.setMessage("Please check your internet connection and try again.");
            builder.setIcon(android.R.drawable.ic_secure);
            builder.setCancelable(false);
            builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
            builder.create();
            builder.show();
        }
    }
}
