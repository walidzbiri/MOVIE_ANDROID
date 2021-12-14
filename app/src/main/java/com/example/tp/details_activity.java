package com.example.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp.apiservice.DetailsMovieResponse;
import com.example.tp.apiservice.Genre;
import com.example.tp.apiservice.MovieService;
import com.example.tp.apiservice.PopularMovieResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class details_activity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ApplicationContext app=(ApplicationContext)getApplicationContext();

        ImageView image=(ImageView) findViewById(R.id.poster_image_details);
        TextView overview=(TextView) findViewById(R.id.desc);
        TextView release_date=(TextView) findViewById(R.id.release_date);
        TextView genres=(TextView) findViewById(R.id.genres);


        MovieService movieService=new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
        Call<DetailsMovieResponse> call=null;
        if(app.getActiveLanguage()=="FR")
             call=movieService.getMovieDetail(app.getMovie_id(),"550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
        if(app.getActiveLanguage()=="ENG")
            call=movieService.getMovieDetail(app.getMovie_id(),"550ebedfa3fe2247c5d7c21a9342b7c0",1,"eng");



        call.enqueue(new Callback<DetailsMovieResponse>() {
            @Override
            public void onResponse(Call<DetailsMovieResponse> call, Response<DetailsMovieResponse> response) {
                DetailsMovieResponse repo_response = (DetailsMovieResponse) response.body();
                Picasso.get().load("https://image.tmdb.org/t/p/original"+repo_response.getPoster_path()).into(image);
                overview.setText(repo_response.getOverview());
                if(app.getActiveLanguage()=="FR")
                    release_date.setText("Date de sortie: "+repo_response.getRelease_date());
                if(app.getActiveLanguage()=="ENG")
                    release_date.setText("Release Date: "+repo_response.getRelease_date());
                String textForGenre="Genres:\n";
                for (Genre genre : repo_response.getGenres()) {
                    textForGenre+="- "+genre.getName()+"\n";
                }
                genres.setText(textForGenre);
                setTitle(repo_response.getTitle());
            }

            @Override
            public void onFailure(Call<DetailsMovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage()
                        ,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}