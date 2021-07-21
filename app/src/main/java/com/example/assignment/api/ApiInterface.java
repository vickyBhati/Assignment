package com.example.assignment.api;


import com.example.assignment.model.ModelDetails;
import com.example.assignment.model.ModelMovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {


    String BASE_URL = "https://api.themoviedb.org/3/movie/";




    @POST("now_playing?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&page=undefined")
    Call<ModelMovieList> getMovie();

    @GET("")
    Call<ModelDetails> getUsers(@Url String url);

}


