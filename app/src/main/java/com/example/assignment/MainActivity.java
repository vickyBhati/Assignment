package com.example.assignment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment.adapter.AdapterHome;
import com.example.assignment.databinding.ActivityMainBinding;
import com.example.assignment.model.ModelMovieList;
import com.example.assignment.presnter.BaseActivity;
import com.example.assignment.presnter.getMoviePresenter;
import com.example.assignment.view.IgetMovieView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements IgetMovieView {


    getMoviePresenter presenter;
    ArrayList<ModelMovieList.ResultsBean> list = new ArrayList<>();
    ActivityMainBinding binding;

    AdapterHome adapterOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        presenter = new getMoviePresenter();
        presenter.setView(this);

        callApi();
    }

    private void callApi() {
        if (Utility.hasConnection(this)) {
            presenter.getMovie(MainActivity.this);
        }
        else {
            Utility.no_internet(this);
        }

    }

    @Override
    public void onSuccess(ModelMovieList body) {
            list.addAll(body.getResults());
            Log.e("fffffffff",""+list.size());
             adapterOrderStatus= new AdapterHome(getApplicationContext(),list);
            binding.rectangles.setLayoutManager(new LinearLayoutManager(this));
            binding.rectangles.setAdapter(adapterOrderStatus);
            adapterOrderStatus.notifyDataSetChanged();

    }

    @Override
    public Context getContext() {
        return this;
    }
}