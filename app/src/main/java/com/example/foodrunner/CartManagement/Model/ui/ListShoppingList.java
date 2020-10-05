package com.example.foodrunner.CartManagement.Model.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.example.foodrunner.CartMainActivity;
import com.example.foodrunner.CartManagement.Model.common.ShopItem;
import com.example.foodrunner.CartManagement.Model.firebase.AddShoppingModel;
import com.example.foodrunner.R;
import com.example.foodrunner.payment.OnlinePayment;
import com.example.foodrunner.payment.payondelivery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListShoppingList extends AppCompatActivity {

    ImageButton addShoppingButton;
    Button btnDelivery,btnOnline;
    private CreateAndFillValuesInShoppingRows createAndFillValuesInListShoppingRows;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shopping_list);

        addShoppingButton=findViewById(R.id.addButton);
        btnDelivery=findViewById(R.id.btnDelivery);
        btnOnline=findViewById(R.id.btnOnline);
        expandableListView=findViewById(R.id.Explist);
        fetchAndPopulateEntriesInExpandableListView();
        addShoppingButton.setOnClickListener(new View.OnClickListener() { //move to shopping list activity
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListShoppingList.this, CreateShoppingList.class);
                startActivity(intent);
            }
        });

        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListShoppingList.this, OnlinePayment.class);
                startActivity(intent);

            }
        });

        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListShoppingList.this, payondelivery.class);
                startActivity(intent);

            }
        });


    }

    private void fetchAndPopulateEntriesInExpandableListView() {

        FirebaseDatabase.getInstance().getReference().child("shopping")
                .child(CartMainActivity.getSubscriberId(ListShoppingList.this))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<ExpandShopGroup> groupList=new ArrayList<>();
                        for(DataSnapshot areaSnapsnot:dataSnapshot.getChildren())
                        {
                            AddShoppingModel value=areaSnapsnot.getValue(AddShoppingModel.class);
                            String orderId=areaSnapsnot.getKey();
                            ExpandShopGroup grul=new ExpandShopGroup();
                            grul.setShoppingId(orderId);
                            grul.setDate(value.getDate());
                            ArrayList<ShopItem> childList=new ArrayList<>();

                            for(ShopItem shopItem:value.getSales().values())
                            {
                                ShopItem rowChild=new ShopItem();
                                rowChild.setCategory(shopItem.getCategory());
                                rowChild.setProduct(shopItem.getProduct());
                                rowChild.setQuantity(shopItem.getQuantity());
                                rowChild.setUnit(shopItem.getUnit());
                                childList.add(rowChild);
                            }
                            grul.setItems(childList);
                            groupList.add(grul);
                        }
                        Collections.sort(groupList);
                        for(int k=1; k<=groupList.size(); k++)
                        {
                            groupList.get(k-1).setName("Shopping List -"+k);
                        }
                        createAndFillValuesInListShoppingRows=new CreateAndFillValuesInShoppingRows(ListShoppingList.this, groupList);
                        expandableListView.setAdapter(createAndFillValuesInListShoppingRows);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) //set the up and down button
    {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width=metrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT< Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            expandableListView.setIndicatorBounds(width - GetPixelFromDips(80),width - GetPixelFromDips(55));
        }
        else
        {
            expandableListView.setIndicatorBoundsRelative(width - GetPixelFromDips(85),width - GetPixelFromDips(55));
        }
    }
    private int GetPixelFromDips(float pixels)
    {
        final float scale=getResources().getDisplayMetrics().density;
        return (int)(pixels* scale + 0.5f);
    }

}