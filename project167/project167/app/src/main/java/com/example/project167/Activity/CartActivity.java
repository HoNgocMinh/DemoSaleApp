package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.Adapter.CartAdapter;
import com.example.project167.Helper.ManagmentCart;
import com.example.project167.R;
import com.example.project167.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private ManagmentCart managmentCart;
    ActivityCartBinding binding;
    double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        initlist();
        calculateCart();
        statusBarColor();

        //Xử lí nút bấm phương thức thanh toán
        binding.btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi ConstraintLayout được bấm
                showBottomDialog();
            }
        });


    }
    //chỉnh màu thanh trạng thái
    private void statusBarColor() {
        Window window=CartActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CartActivity.this,R.color.white));
    }

    private void initlist() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroll.setVisibility(View.VISIBLE);
        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(),this, () -> calculateCart()));
    }


    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100.0;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

          binding.totalFeeTxt.setText("$" + itemTotal);
          binding.taxTxt.setText("$" + tax);
          binding.deliveryTxt.setText("$" + delivery);
          binding.totalTxt.setText("$" + total);
    }

    //nut back
    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }


    //Chon phuong thuc thanh toan

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout momoLayout = dialog.findViewById(R.id.layoutMomo);
        LinearLayout zalopayLayout = dialog.findViewById(R.id.layoutZaloPay);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);


        momoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(CartActivity.this,"Momo được chọn",Toast.LENGTH_SHORT).show();

                TextView txtPayment = findViewById(R.id.txt_Payment);
                txtPayment.setText("Momo");
            }
        });

        zalopayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(CartActivity.this,"ZaloPay được chọn",Toast.LENGTH_SHORT).show();

                TextView txtPayment = findViewById(R.id.txt_Payment);
                txtPayment.setText("ZaloPay");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
