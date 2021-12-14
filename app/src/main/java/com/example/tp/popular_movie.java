package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tp.apiservice.Movie;
import com.example.tp.apiservice.MovieService;
import com.example.tp.apiservice.PopularMovieResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.language_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Call<PopularMovieResponse> call = null;
        MovieService movieService=new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
        ApplicationContext app=(ApplicationContext)getApplicationContext();
        if (item.getItemId()==R.id.fr){
            item.setChecked(true);
            app.setActiveLanguage("FR");
            if(app.getActiveFragment()=="UPCOMING")
                call=movieService.getUpcomingMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
            if(app.getActiveFragment()=="POPULAR")
                call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
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
        }

        if (item.getItemId()==R.id.eng){
            item.setChecked(true);
            app.setActiveLanguage("ENG");
            if(app.getActiveFragment()=="UPCOMING")
                call=movieService.getUpcomingMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"eng");
            if(app.getActiveFragment()=="POPULAR")
                call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"eng");
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        ApplicationContext app=(ApplicationContext)getApplicationContext();
        app.setActiveFragment("POPULAR");

        MovieService movieService=new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
        app.setActiveLanguage("FR");
        Call<PopularMovieResponse> call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
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

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Call<PopularMovieResponse> call = null;
                ApplicationContext app=(ApplicationContext)getApplicationContext();
                switch(id){
                    //check id
                    case R.id.upcoming:
                        // The toggle is enabled
                        app.setActiveFragment("UPCOMING");
                        if(app.getActiveLanguage()=="FR")
                            call=movieService.getUpcomingMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
                        if(app.getActiveLanguage()=="ENG")
                            call=movieService.getUpcomingMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"eng");
                        mRecyclerView=findViewById(R.id.rvPopularMovies);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
                        setTitle("Upcoming movies");
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
                        return true;
                    case R.id.popular:
                        app.setActiveFragment("POPULAR");
                        if(app.getActiveLanguage()=="FR")
                            call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
                        if(app.getActiveLanguage()=="ENG")
                            call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"eng");
                        mRecyclerView=findViewById(R.id.rvPopularMovies);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
                        setTitle("Popular movies");
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
                        return true;
                }

                return true;
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