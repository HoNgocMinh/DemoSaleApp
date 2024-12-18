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

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    private ManagmentCart managmentCart;
    ActivityCartBinding binding;

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
    //format định dạng tiền
    public static String formatCurrency(double amount) {
        double scaledAmount = amount * 1000; // Nhân với 1000
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(scaledAmount) + "đ";
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

        // Khởi tạo adapter với callback khi thay đổi số lượng hoặc xóa
        CartAdapter cartAdapter = new CartAdapter(managmentCart.getListCart(), this, () -> {
            calculateCart(); // Tính toán lại tổng số tiền khi có thay đổi
            // Cập nhật trạng thái hiển thị nếu giỏ hàng trống
            if (managmentCart.getListCart().isEmpty()) {
                binding.emptyTxt.setVisibility(View.VISIBLE);
                binding.scroll.setVisibility(View.GONE);
            }
        });

        binding.cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(),this, () -> calculateCart()));
    }

    //làm phép toán
    private void calculateCart() {
        double percentTax = 0.08;
        double delivery = 10;

        double tamtinh = Math.round(managmentCart.getTotalFee());
        double thanhtien = Math.round(tamtinh + tamtinh*percentTax + delivery);

        binding.txtTamTinh.setText(formatCurrency(tamtinh));
        binding.txtPhiDichVu.setText(formatCurrency(delivery));
        binding.txtThue.setText(formatCurrency(tamtinh*percentTax));
        binding.txtThanhTien.setText(formatCurrency(thanhtien));

        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
        }
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
        TextView txtPayment = findViewById(R.id.txt_Payment);
        ImageView imgPayment = findViewById(R.id.img_Payment);

        momoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(CartActivity.this,"Momo được chọn",Toast.LENGTH_SHORT).show();

                txtPayment.setText("Momo");
                imgPayment.setImageResource(R.drawable.ic_momo_24);

            }
        });

        zalopayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Toast.makeText(CartActivity.this,"ZaloPay được chọn",Toast.LENGTH_SHORT).show();
                txtPayment.setText("ZaloPay");
                imgPayment.setImageResource(R.drawable.ic_zalopay);

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
