package com.example.tp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class item_movie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_movie);
        ImageView image= (ImageView) findViewById(R.id.poster_image);
        ConstraintLayout cs=(ConstraintLayout) findViewById(R.id.item_movie_layout);
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hello");
                System.out.println("hellojhgjhgj");
                Intent intent=new Intent(view.getContext(),details_activity.class);
                ApplicationContext app=(ApplicationContext) getApplicationContext();
                app.setMovie_id(2222);
                app.startActivity(intent);
            }
        });

    }
}