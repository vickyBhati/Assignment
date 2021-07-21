package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.assignment.databinding.DetailsPageBinding;
import com.example.assignment.model.ModelDetails;
import com.example.assignment.presnter.BaseActivity;
import com.example.assignment.presnter.DetailsPresenter;
import com.example.assignment.view.IDetailsView;

public class DetailsPage extends BaseActivity implements IDetailsView {


    DetailsPageBinding binding;
    DetailsPresenter presenter;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.details_page);
        presenter = new DetailsPresenter();
        presenter.setView(this);


        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
        }
        if (Utility.hasConnection(this)) {
            String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=55957fcf3ba81b137f8fc01ac5a31fb5";
            presenter.getUsers(this,url);
        }
        else {
            Utility.no_internet(this);
        }

    }


    @Override
    public void onSuccess(ModelDetails body) {
        if (body!=null){
            Glide.with(this).load("https://image.tmdb.org/t/p/w780"+body.getPoster_path()).into(binding.image);
            binding.name.setText("Title : "+body.getOriginal_title());
            binding.tagline.setText(body.getTagline());
            binding.desc.setText(body.getOverview());
            binding.releaseDate.setText("Release Date :  "+body.getRelease_date());
            binding.status.setText("Status :  "+body.getStatus());
            binding.popularity.setText("Popularity : "+body.getPopularity());
        }

    }

    @Override
    public Context getContext() {
        return this;
    }
}
