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
    private String activeFragment;

    public String getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(String activeFragment) {
        this.activeFragment = activeFragment;
    }

    public String getActiveLanguage() {
        return activeLanguage;
    }

    public void setActiveLanguage(String activeLanguage) {
        this.activeLanguage = activeLanguage;
    }

    private String activeLanguage;


    @Override
    public void onCreate(){
        super.onCreate();
    }


}
