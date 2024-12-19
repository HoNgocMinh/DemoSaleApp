package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.Adapter.CartAdapter;
import com.example.project167.Api.CreateOrder;
import com.example.project167.Helper.ManagmentCart;
import com.example.project167.PaymentNotification;
import com.example.project167.R;
import com.example.project167.databinding.ActivityCartBinding;
import com.google.android.gms.wallet.PaymentsClient;

import org.json.JSONObject;

import java.text.DecimalFormat;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CartActivity extends AppCompatActivity {
    private ManagmentCart managmentCart;
    ActivityCartBinding binding;
    String txt_outthanhtien;
    String selectedPaymentMethod = "";



    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ZaloPay
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        //GG Pay


        //aaaaaa
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

        //Xử lí bấm nút thanh toán
        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPaymentMethod.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                } else if (selectedPaymentMethod.equals("GooglePay")) {
                    //Thanh toán bằng momo
                    Toast.makeText(CartActivity.this, "Thanh toán bằng GooglePay không khả dụng.", Toast.LENGTH_SHORT).show();
                }
                else if(selectedPaymentMethod.equals("ZaloPay")){
                    //thanh tón zalopay
                    CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(txt_outthanhtien);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(CartActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent1 = new Intent(CartActivity.this, PaymentNotification.class);
                                intent1.putExtra("result","Thanh toán thành công.");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent1 = new Intent(CartActivity.this, PaymentNotification.class);
                                intent1.putExtra("result","Hủy thanh toán.");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent1 = new Intent(CartActivity.this, PaymentNotification.class);
                                intent1.putExtra("result","Lỗi thanh toán.");
                                startActivity(intent1);
                            }
                        });
                    }
                }
                catch(Exception e){
                        e.printStackTrace();
                }
            }
        }
        });


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
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

    //làm phép toán tính tiền
    public void calculateCart() {

        double percentTax = 0.08;
        double delivery = 10;

        double tamtinh = Math.round(managmentCart.getTotalFee());
        double thanhtien = Math.round(tamtinh + tamtinh*percentTax + delivery);

        binding.txtTamTinh.setText(formatCurrency(tamtinh));
        binding.txtPhiDichVu.setText(formatCurrency(delivery));
        binding.txtThue.setText(formatCurrency(tamtinh*percentTax));
        binding.txtThanhTien.setText(formatCurrency(thanhtien));

        txt_outthanhtien = String.valueOf((int) thanhtien*1000);
        Log.d("aa",txt_outthanhtien);

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

        LinearLayout ggPayLayout = dialog.findViewById(R.id.layoutGooglePay);
        LinearLayout zalopayLayout = dialog.findViewById(R.id.layoutZaloPay);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        TextView txtPayment = findViewById(R.id.txt_Payment);
        ImageView imgPayment = findViewById(R.id.img_Payment);

        ggPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(CartActivity.this,"Momo được chọn",Toast.LENGTH_SHORT).show();

                txtPayment.setText("GooglePay");
                imgPayment.setImageResource(R.drawable.ic_google_pay);
                selectedPaymentMethod = "GooglePay";
            }
        });

        zalopayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Toast.makeText(CartActivity.this,"ZaloPay được chọn",Toast.LENGTH_SHORT).show();
                txtPayment.setText("ZaloPay");
                imgPayment.setImageResource(R.drawable.ic_zalopay);
                selectedPaymentMethod = "ZaloPay";
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
