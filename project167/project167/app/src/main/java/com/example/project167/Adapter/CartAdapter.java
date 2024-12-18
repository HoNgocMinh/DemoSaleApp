package com.example.project167.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.project167.Activity.DetailActivity;
import com.example.project167.Helper.ChangeNumberItemsListener;
import com.example.project167.Helper.ManagmentCart;
import com.example.project167.databinding.ViewholderCartBinding;
import com.example.project167.databinding.ViewholderPupListBinding;
import com.example.project167.domain.PopularDomain;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<PopularDomain> items;
    Context context;
    ViewholderCartBinding binding;
    ChangeNumberItemsListener changeNumberItemsListener;
    ManagmentCart managmentCart;

    public CartAdapter(ArrayList<PopularDomain> items,Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();

        return new Viewholder(binding);
    }
    //format định dạng tiền
    public static String formatCurrency(double amount) {
        double scaledAmount = amount * 1000; // Nhân với 1000
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(scaledAmount) + "đ";
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        binding.txtTenKhoaHoc.setText(items.get(position).getTitle());
        binding.txtPrice.setText(formatCurrency(items.get(position).getPrice()));

        int drawableResourced = holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl()
                , "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.pic);

        // Xử lý sự kiện xóa sản phẩm
        binding.btnDelete.setOnClickListener(view -> {
            // Xóa item từ ManagmentCart
            managmentCart.removeItem(position);

            // Cập nhật lại danh sách hiển thị
            items.remove(position);
            notifyItemRemoved(position); // Cập nhật RecyclerView
            notifyItemRangeChanged(position, items.size()); // Cập nhật các item sau vị trí xóa

            // Gọi callback để cập nhật tổng giá trị
            if (changeNumberItemsListener != null) {
                changeNumberItemsListener.change();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;
        public Viewholder(ViewholderCartBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }
}
