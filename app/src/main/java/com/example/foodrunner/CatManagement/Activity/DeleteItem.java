package com.example.foodrunner.CatManagement.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrunner.CatManagement.Model.Products;
import com.example.foodrunner.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteItem extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference dbRef ,dbRefUP;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private List<Products> productsList;

    int Image_Request_Code = 78;
    Uri FilePathUri;
    ImageView imageViewGET;


    StorageReference storageReference,storageReferenceUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle=findViewById(R.id.progressCircle);
        productsList = new ArrayList<>();

        mAdapter = new ImageAdapter(DeleteItem.this,productsList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DeleteItem.this);

        mStorage = FirebaseStorage.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Products");


        mDBListener = dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                productsList.clear();
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Products products = postSnapshot.getValue(Products.class);
                    products.setProductId(postSnapshot.getKey());
                    productsList.add(products);
                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteItem.this, error.getMessage() , Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

   @Override
    public void onItemClick(int position) {
        // Toast.makeText(this,"Normal click at position "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(int position) {
        Products selectedItem = productsList.get(position);
        final String selectedKey = selectedItem.getProductId();
        final String name = selectedItem.getName();
        final Float price =selectedItem.getPrice();
        final String category = selectedItem.getCategory();
        final String imageURL = selectedItem.getImageURL();
         dbRefUP= FirebaseDatabase.getInstance().getReference("Products").child(selectedKey);
         storageReferenceUP =FirebaseStorage.getInstance().getReference("Products").child(selectedKey);
        showUpdateDialog(selectedKey ,imageURL,name ,price,category);
    }

    @Override
    public void onDeleteClick(int position) {
        Products selectedItem = productsList.get(position);
        final String selectedKey = selectedItem.getProductId();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dbRef.child(selectedKey).removeValue();
                Toast.makeText(DeleteItem.this, "Product Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbRef.removeEventListener(mDBListener);
    }


    private void showUpdateDialog(final String productId , final String imageURL, String productName , Float price, final String category ){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final  View dialogView = inflater.inflate(R.layout.delete_dialog , null);

        dialogBuilder.setView(dialogView);


        imageViewGET =dialogView.findViewById(R.id.imageViewGET);
        final TextView textViewName=dialogView.findViewById(R.id.textViewName);
        final EditText editTextPrice=dialogView.findViewById(R.id.editTextPrice);
        final TextView textViewCategory=dialogView.findViewById(R.id.textViewCategory);
        final Button btnUpdate=dialogView.findViewById(R.id.btnUpdate);
        final Button btnBrowse = dialogView.findViewById(R.id.updateBrowse);


        textViewName.setText(productName);
        editTextPrice.setText(price.toString());
        textViewCategory.setText(category);
        Picasso.with(this).load(imageURL).into(imageViewGET);

        dialogBuilder.setTitle("Update "+productName);


        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });

        final AlertDialog alertDialog =dialogBuilder.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FilePathUri != null) {



                    storageReferenceUP.putFile(FilePathUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    if (taskSnapshot.getMetadata() !=null){
                                        if(taskSnapshot.getMetadata().getReference() != null){
                                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imageUrl= uri.toString();
                                                    String name = textViewName.getText().toString().trim();
                                                    Float price = Float.valueOf(editTextPrice.getText().toString().trim());
                                                    String category = textViewCategory.getText().toString();

                                                    Toast.makeText(getApplicationContext(), "Update Successful ", Toast.LENGTH_LONG).show();
                                                    @SuppressWarnings("VisibleForTests")
                                                    Products products = new Products(imageUrl,name,price,category );
                                                    dbRefUP.setValue(products);

                                                    //

                                                }
                                            });
                                        }
                                    } alertDialog.dismiss();

                                    //  String TempImageName = txtdata.getText().toString().trim();
/*
                            String name = editTextName.getText().toString().trim();
                            Float price = Float.valueOf(editTextPrice.getText().toString().trim());
                            String category = spinnerCategory.getSelectedItem().toString();

                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            Products products = new Products(,name,price,category );
                            String ImageUploadId = dbRef.push().getKey();
                            dbRef.child(ImageUploadId).setValue(products);*/
                                }

                            });

                }else {
                    Float price = Float.valueOf(String.valueOf(editTextPrice.getText()));
                    String name = textViewName.getText().toString().trim();
                    updateProduct(productId, imageURL, name, price, category);

                    alertDialog.dismiss();
                }

            }
        });
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
            //  Picasso..load(FilePathUri).into(imageView);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imageViewGET.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    private boolean updateProduct(String id , String imageURL,String name, Float price, String category){
        dbRef = FirebaseDatabase.getInstance().getReference("Products").child(id);
        storageReference = FirebaseStorage.getInstance().getReference("Products").child(id);


        Products products = new Products(id,imageURL,name,price,category);
        dbRef.setValue(products);



        Toast.makeText(this,"Product Updated",Toast.LENGTH_LONG).show();
        return true;
    }

}