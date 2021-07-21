package com.example.assignment.presnter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.assignment.R;
import com.example.assignment.api.AppController;
import com.example.assignment.model.ModelDetails;
import com.example.assignment.view.IDetailsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter extends BasePresenter<IDetailsView> {

    public void getUsers(final Context context , String url) {

        getView().enableLoadingBar(context, true, context.getString(R.string.msg_please_wait));

        AppController.getInstance().getApiInterface().getUsers(url).enqueue(new Callback<ModelDetails>() {
            @Override
            public void onResponse(@NonNull Call<ModelDetails> call, @NonNull Response<ModelDetails> response) {
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
            public void onFailure(@NonNull Call<ModelDetails> call, @NonNull Throwable t) {
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
