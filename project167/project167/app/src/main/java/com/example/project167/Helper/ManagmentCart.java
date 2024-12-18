package com.example.project167.Helper;

import android.content.Context;
import android.widget.Toast;


import com.example.project167.domain.PopularDomain;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB=new TinyDB(context);
    }

    public void insertFood(PopularDomain item) {
        ArrayList<PopularDomain> listpop = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready){
            listpop.get(n).setNumberInCart(item.getNumberInCart());
        }else{
            listpop.add(item);
        }
        tinyDB.putListObject("CartList",listpop);
        Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<PopularDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public Double getTotalFee(){
        ArrayList<PopularDomain> listItem=getListCart();
        double fee=0;
        for (int i = 0; i < listItem.size(); i++) {
            fee=fee+(listItem.get(i).getPrice());
        }
        return fee;
    }

    //Xóa item theo vị trí
    public void removeItem(int position) {
        ArrayList<PopularDomain> listpop = getListCart();
        if (position >= 0 && position < listpop.size()) {
            listpop.remove(position); // Xóa item theo vị trí
            tinyDB.putListObject("CartList", listpop); // Cập nhật lại giỏ hàng trong bộ nhớ
        }
    }

    // Đếm số lượng khóa học trong giỏ hàng
    public int getCartItemCount() {
        ArrayList<PopularDomain> listpop = getListCart();
        return listpop.size();
    }
}

