package com.example.project167.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project167.databinding.ViewholderInfoBinding;
import com.example.project167.domain.CategoryDomain;

import java.util.ArrayList;

public class ManageCategoryAdapter extends RecyclerView.Adapter<ManageCategoryAdapter.ViewHolder> {
    private ArrayList<CategoryDomain> categoryList;
    private Context context;

    public ManageCategoryAdapter(ArrayList<CategoryDomain> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item view using ViewBinding
        ViewholderInfoBinding binding = ViewholderInfoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDomain category = categoryList.get(position);

        // Set the category name
        holder.binding.txtTenKhoaHoc.setText(category.getName());

        // Load image using Glide
        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(category.getPicPath(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderInfoBinding binding;

        public ViewHolder(ViewholderInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
