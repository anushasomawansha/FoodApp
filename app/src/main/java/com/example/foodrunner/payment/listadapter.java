package com.example.foodrunner.payment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodrunner.R;

import java.util.List;

public class listadapter extends ArrayAdapter{

    private Activity mContext;
    List<Orders> ordersList;

    public listadapter(Activity mContext, List<Orders>orders){
        super(mContext, R.layout.activity_list_view_online,orders);
        this.mContext=mContext;
        this.ordersList = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_list_view_online,null,true);

        TextView tvName = listItemView.findViewById(R.id.tvName);
        TextView tvCarNum = listItemView.findViewById(R.id.tvCarNum);
        TextView tvEXP = listItemView.findViewById(R.id.tvEXP);
        TextView tvCVV = listItemView.findViewById(R.id.tvCVV);

        Orders orders = ordersList.get(position);

        tvName.setText(orders.getName());
        tvCarNum.setText(orders.getCardnumber());
        tvEXP.setText(orders.getExpdate());
        tvCVV.setText(orders.getCvv());

        return listItemView;
    }
}
