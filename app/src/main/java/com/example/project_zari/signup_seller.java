package com.example.project_zari;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class signup_seller extends AppCompatActivity {


    DatabaseReference reff;
    Seller seller;

    private StorageReference mStorageRef;
    private String selectedImagePath;
    Boolean imageuploaded;
    String bname;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_seller);
        imageuploaded = false;

        reff = FirebaseDatabase.getInstance().getReference().child("Seller");
        seller = new Seller();


        Button btn = findViewById(R.id.sellersu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText brandname = findViewById(R.id.ssuname);
                bname = brandname.getText().toString();
                if (brandname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must enter your brand name", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText email = findViewById(R.id.ssuemail);
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must enter your email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.getText().toString().contains("@") != true) {
                    Toast.makeText(getApplicationContext(), "You must enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText pass = findViewById(R.id.ssupass);
                if (pass.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must enter a password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pass.getText().toString().length() < 8) {
                    Toast.makeText(getApplicationContext(), "You password must be greater than 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText cpass = findViewById(R.id.ssuconfirmpass);
                if (cpass.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must enter re-enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText phone = findViewById(R.id.ssuphone);
                EditText address = findViewById(R.id.ssuaddress);

                UploadToFirebase();

                seller.setName(brandname.getText().toString());
                seller.setEmail(email.getText().toString());
                seller.setPassword(pass.getText().toString());
                seller.setPhone(phone.getText().toString());
                seller.setAddress(address.getText().toString());
                seller.setLogo(bname);

                reff.push().setValue(seller);


                Intent intent = new Intent(signup_seller.this, Seller_Home.class);
                startActivity(intent);
                finish();
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Button uploadBtn = findViewById(R.id.sellerlogo);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getApplicationContext())) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                }

            }
        });



    }

    public void UploadToFirebase(){
        File filee = new File(selectedImagePath);
        String strFileName = filee.getName();

        String extension = "";

        int i = strFileName.lastIndexOf('.');
        if (i > 0) {
            extension = strFileName.substring(i+1);
        }
        bname = bname.concat(".");
        bname = bname.concat(extension);
        Uri file = Uri.fromFile(new File(selectedImagePath));
        grantUriPermission("com.example.project_zari",file,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        StorageReference logoRef = mStorageRef.child(bname);

        logoRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(getApplicationContext(), "Logo uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("Unsuccessful upload");
                    }
                });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(getApplicationContext(),selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                //img.setImageURI(selectedImageUri);
                imageuploaded = true;
                //UploadToFirebase();
            }
        }
    }

    public String getPath(Context context, Uri uri) {
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }



    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                else {
                    requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                } else {
                    //Toast.makeText(Login.this, "GET_ACCOUNTS Denied",
                            //Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

}
