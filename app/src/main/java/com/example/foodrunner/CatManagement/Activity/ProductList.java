package com.example.foodrunner.CatManagement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.foodrunner.CatManagement.Model.Products;
import com.example.foodrunner.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProductList extends ArrayAdapter<Products> {

    private Activity context;
    private List<Products> productsList;


    public ProductList(Activity context , List<Products> productsList){

        super(context ,R.layout.single_view_layout,productsList);
        this.context=context;
        this.productsList=productsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.single_view_layout,null,true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewCategory = listViewItem.findViewById(R.id.textViewCategory);

        Button btnAddtoCart = listViewItem.findViewById(R.id.btnDel);



        //belowistest
        ImageView imageViewDb = listViewItem.findViewById(R.id.imageViewdb);



        Products products = productsList.get(position);
        //imageView.setImageBitmap();




        // imageView.setImageBitmap();
        textViewName.setText(products.getName());
        textViewPrice.setText("Rs. "+products.getPrice().toString());
//        textViewCategory.setText(products.getCategory());


        // Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/menulist-1e47e.appspot.com/o?uploadType=resumable&name=Products%2F1601089235182.jpg&upload_id=ABg5-Uw6Rq8ibE_7IZKEnp16AFbGneaMUxaM8IxK-JULKyTYvdhPWitwsbUYmS6LehBjz9s-iqN7_f4nfVmxUZD1LCwwWRe1tQ&upload_protocol=resumable").into(imageViewDb);
        //.load(products.getImageURL()).into(imageViewDb);

        Picasso.with(context).load(products.getImageURL()).into(imageViewDb);

     //AddTOCART IMPLEMENTATION
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Dinner.class);
                context.startActivity(intent);
            }
        });

        return listViewItem;
    }


}
