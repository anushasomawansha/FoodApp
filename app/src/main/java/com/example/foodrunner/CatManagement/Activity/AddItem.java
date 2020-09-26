package com.example.foodrunner.CatManagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodrunner.CatManagement.Model.Products;
import com.example.foodrunner.DeleteItem;
import com.example.foodrunner.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class AddItem extends AppCompatActivity {
    Button btnAdd , btnDel;
    EditText editTextName , editTextPrice;
    DatabaseReference dbRef;
    Spinner spinnerCategory;

    //test
    Button btnbrowse;
    ImageView imageView;
    Uri FilePathUri;
    StorageReference storageReference;
    List<Products> productsList;
    int Image_Request_Code = 7;


    public void clearControls(){
        editTextName.setText("");
        editTextPrice.setText("");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        btnDel = findViewById(R.id.btnDel);
        btnAdd = findViewById(R.id.btnAdd);
        editTextName = findViewById(R.id.etName);
        editTextPrice = findViewById(R.id.etPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnbrowse = findViewById(R.id.btnbrowse);
        imageView=findViewById(R.id.imageView);

        dbRef= FirebaseDatabase.getInstance().getReference().child("Products");
        storageReference = FirebaseStorage.getInstance().getReference("Products");//test

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addProduct();
                UploadImage(); //test
            }
        });

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });


       /* btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = etName.getText().toString();
                Float price = Float.parseFloat(etPrice.getText().toString());
                String category = etCategory.getText().toString();


                String key = dbRef.push().getKey();
                dbRef.child(key).child("name").setValue(name);
                dbRef.child(key).child("price").setValue(price);
                dbRef.child(key).child("category").setValue(category);

              products.setName(etName.getText().toString().trim());
                products.setDes(etDes.getText().toString().trim());
                products.setPrice(Float.parseFloat(etPrice.getText().toString().trim()));
               // dbRef.child("pro1").setValue(products);
                dbRef.push().setValue(products);*


                Intent intent = new Intent(AddItem.this , DeleteItem.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext() , "Saved Successfully" , Toast.LENGTH_SHORT).show();
                clearControls();

            }
        });*/

/*
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this,DeleteItem.class);
                startActivity(intent);
            }
        });*/
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
                imageView.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public void UploadImage() {

        if (FilePathUri != null) {


            final StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
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
                                            String name = editTextName.getText().toString().trim();
                                            Float price = Float.valueOf(editTextPrice.getText().toString().trim());
                                            String category = spinnerCategory.getSelectedItem().toString();

                                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(AddItem.this , DeleteItem.class);
                                            startActivity(intent);
                                            @SuppressWarnings("VisibleForTests")
                                            Products products = new Products(imageUrl,name,price,category );
                                            String ImageUploadId = dbRef.push().getKey();
                                            dbRef.child(ImageUploadId).setValue(products);
                                        }
                                    });
                                }
                            }

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
        }
        else {

            Toast.makeText(AddItem.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }
}