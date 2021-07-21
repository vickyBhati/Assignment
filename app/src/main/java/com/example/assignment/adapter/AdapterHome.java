package com.example.assignment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.DetailsPage;
import com.example.assignment.R;
import com.example.assignment.databinding.LayoutHomeBinding;
import com.example.assignment.model.ModelMovieList;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder> {

    private Context context;
    ArrayList<ModelMovieList.ResultsBean> business_list;

    public AdapterHome(Context context, ArrayList<ModelMovieList.ResultsBean> _list) {
        this.context = context;
        this.business_list = _list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_home, parent, false);
        return new MyViewHolder(binding);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AdapterHome.MyViewHolder holder, final int position) {
        LayoutHomeBinding binding = (LayoutHomeBinding) holder.getBinding();
        binding.name.setText(business_list.get(position).getOriginal_title());
        binding.populertuy.setText("Popularity"+business_list.get(position).getPopularity());
        binding.releaseDate.setText("Release date"+business_list.get(position).getRelease_date());
        binding.desc.setText(""+business_list.get(position).getOverview());
        Glide.with(context).load("https://image.tmdb.org/t/p/w780"+business_list.get(position).getPoster_path()).into(binding.ivProfile);

        binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context, DetailsPage.class);
                intent.putExtra("id",""+business_list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return business_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;
        MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.executePendingBindings();
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }


}