package com.example.tp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tp.apiservice.Movie;
import com.example.tp.apiservice.MovieService;
import com.example.tp.apiservice.PopularMovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class popular_movie extends AppCompatActivity implements PopularMovieAdapter.OnMovieListener {

    private List<Movie> mMovies;
    private RecyclerView mRecyclerView;
    private PopularMovieAdapter mReposAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.switchbutton);

        MovieService movieService=new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);

        Call<PopularMovieResponse> call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1);
        mRecyclerView=findViewById(R.id.rvPopularMovies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,2));

        call.enqueue(new Callback<PopularMovieResponse>() {
            @Override
            public void onResponse(Call<PopularMovieResponse> call, Response<PopularMovieResponse> response) {
                PopularMovieResponse repo_response = (PopularMovieResponse) response.body();
                mMovies=repo_response.getMovies();
                mReposAdapter=new PopularMovieAdapter(popular_movie.this,mMovies,popular_movie.this);
                mRecyclerView.setAdapter(mReposAdapter);
            }

            @Override
            public void onFailure(Call<PopularMovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage()
                        ,
                        Toast.LENGTH_SHORT).show();
            }
        });

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Call<PopularMovieResponse> call;
                if (isChecked) {
                    // The toggle is enabled
                    call=movieService.getUpcomingMovies("550ebedfa3fe2247c5d7c21a9342b7c0",3);
                    mRecyclerView=findViewById(R.id.rvPopularMovies);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
                    setTitle("Upcoming movies");

                } else {
                    // The toggle is disabled
                    call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",3);
                    mRecyclerView=findViewById(R.id.rvPopularMovies);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
                    setTitle("Popular movies");
                }
                call.enqueue(new Callback<PopularMovieResponse>() {
                    @Override
                    public void onResponse(Call<PopularMovieResponse> call, Response<PopularMovieResponse> response) {
                        PopularMovieResponse repo_response = (PopularMovieResponse) response.body();
                        mMovies=repo_response.getMovies();
                        mReposAdapter=new PopularMovieAdapter(popular_movie.this,mMovies,popular_movie.this);
                        mRecyclerView.setAdapter(mReposAdapter);
                    }

                    @Override
                    public void onFailure(Call<PopularMovieResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage()
                                ,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    public void onMovieClick(int position) {
        Intent intent=new Intent(this,details_activity.class);
        ApplicationContext app=(ApplicationContext) getApplicationContext();
        app.setMovie_id(mMovies.get(position).getId());
        startActivity(intent);
    }
}