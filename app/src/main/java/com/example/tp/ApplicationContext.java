package com.example.tp;

import android.app.Application;

public class ApplicationContext extends Application {

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    private int movie_id;

    @Override
    public void onCreate(){
        super.onCreate();
    }


}
