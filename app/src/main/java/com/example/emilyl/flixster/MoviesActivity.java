package com.example.emilyl.flixster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MoviesAdapter movieAdapter;
    ListView lvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movies = new ArrayList<>();
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movieAdapter = new MoviesAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);

        // 1. Get the actual movies
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "failure");
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

//        // 2. Get the ListView that we want to populate
//        ListView lvMovies = (ListView) findViewById(R.id.lvMovies);
//
//        // 3. Create an ArrayAdapter
//        MoviesAdapter adapter = new MoviesAdapter(this, movies);
//
//        // 4. Associate the ArrayAdapter with the ListView
//        if (lvMovies != null)
//            lvMovies.setAdapter(adapter);


    }
}
