package com.example.assignment.presnter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.assignment.R;
import com.example.assignment.api.AppController;
import com.example.assignment.model.ModelMovieList;
import com.example.assignment.view.IgetMovieView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getMoviePresenter extends BasePresenter<IgetMovieView> {

    public void getMovie(final Context context ) {

        getView().enableLoadingBar(context, true, context.getString(R.string.msg_please_wait));

        AppController.getInstance().getApiInterface().getMovie().enqueue(new Callback<ModelMovieList>() {
            @Override
            public void onResponse(@NonNull Call<ModelMovieList> call, @NonNull Response<ModelMovieList> response) {
                getView().enableLoadingBar(context, false, "");
                if (!handleError(response)) {
                    if (response.isSuccessful() && response.code() == 200) {
                        getView().onSuccess(response.body());
                    }
                }else{
                    getView().onError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelMovieList> call, @NonNull Throwable t) {
                getView().enableLoadingBar(context, false, "");
                try {
                    t.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().onError(t.getMessage());
            }
        });
    }


}
