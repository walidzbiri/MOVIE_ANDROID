package com.example.tp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp.apiservice.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {

    private Context myActivity;
    private List<Movie> mMovieList;
    private Activity acti;
    private OnMovieListener mOnMovieListener;


    public PopularMovieAdapter(Context context, List<Movie> mMovieList,OnMovieListener onMovieListener) {
        this.myActivity = context;
        this.mMovieList = mMovieList;
        this.mOnMovieListener=onMovieListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView posterView;
        public OnMovieListener onMovieListener;

        public ViewHolder(@NonNull View itemView, Context context,OnMovieListener onMovieListener) {
            super(itemView);
            this.posterView = (ImageView) itemView.findViewById(R.id.poster_image);
            this.onMovieListener=onMovieListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PopularMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View repoView=inflater.inflate(R.layout.activity_item_movie,parent,false);
        return new ViewHolder(repoView,context,mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie repo =mMovieList.get(position);
        ImageView posterView=holder.posterView;
        Picasso.get().load("https://image.tmdb.org/t/p/original"+repo.getPoster_path()).into(posterView);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public interface OnMovieListener{
        void onMovieClick(int position);
    }
}
