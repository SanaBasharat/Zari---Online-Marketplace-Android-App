package com.example.project_zari;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class SellerAddProduct extends AppCompatActivity {

    String imgPath;
    String sizePath;
    String imgname;
    String sizename;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    Boolean imguploaded;
    Boolean sizeuploaded;
    private SharedPreferences sharedpreferences;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private Context mContext;
    //FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_add_product);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");
        this.mContext=getApplicationContext();

        imguploaded = false;
        sizeuploaded = false;

        imgPath = "";
        sizePath = "";


        mStorageRef = FirebaseStorage.getInstance().getReference();

        sharedpreferences = mContext.getSharedPreferences("sellersignin", Context.MODE_PRIVATE);
        final String email = sharedpreferences.getString("email","");

        ImageButton uploadBtn = findViewById(R.id.uploadimg);
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

        Button sizeBtn = findViewById(R.id.button4);
        sizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getApplicationContext())) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
//                    if (sizePath!=null){
//                        UploadToFirebase(sizePath);
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), "You must select an image first", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        Button addbtn = findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.nametext);
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your product name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    imgname = name.getText().toString();
                    sizename = name.getText().toString();
                    sizename = sizename.concat("sizechart");
                }
                String prodname = name.getText().toString();
                EditText desc = findViewById(R.id.desctext);
                if (desc.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your product description", Toast.LENGTH_SHORT).show();
                    return;
                }
                String proddesc = desc.getText().toString();
                EditText price = findViewById(R.id.pricetext);
                if (price.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your product price", Toast.LENGTH_SHORT).show();
                    return;
                }
                int prodprice = Integer.parseInt(price.getText().toString());

                RadioGroup radioGroup = findViewById(R.id.radioGroup2);
                if (radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "You must choose a product category", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imgPath!=null){
                    UploadToFirebase(imgPath);
                }
                else{
                    Toast.makeText(getApplicationContext(), "You must select an image first", Toast.LENGTH_SHORT).show();
                }

                if (sizePath!=null){
                    UploadToFirebase(sizePath);
                }
                else{
                    Toast.makeText(getApplicationContext(), "You must select an image first", Toast.LENGTH_SHORT).show();
                }

                String[] type = new String[1];

                int selectedId = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = findViewById(selectedId);
                type[0] =radioButton.getText().toString();

                if (imguploaded == false){
                    Toast.makeText(getApplicationContext(), "Please wait while the image uploads", Toast.LENGTH_SHORT).show();
                }
                else if (sizeuploaded == false){
                    Toast.makeText(getApplicationContext(), "Please wait while the image uploads", Toast.LENGTH_SHORT).show();
                }

                Item myitem = new Item();
                myitem.setName(prodname);
                myitem.setEmail(email);
                myitem.setDesc(proddesc);
                myitem.setPrice(prodprice);
                myitem.setCategory(type[0]);
                myitem.setImage(imgname);
                myitem.setSizechart(sizename);
                mDatabase.push().setValue(myitem);

                Toast.makeText(getApplicationContext(), "Your product has been added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

//    private void signInAnonymously() {
//        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                // do your stuff
//            }
//        })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        //Log.e(TAG, "signInAnonymously:FAILURE", exception);
//                    }
//                });
//    }


    public void UploadToFirebase(final String path){
        File filee = new File(path);
        String strFileName = filee.getName();

        String extension = "";

        int i = strFileName.lastIndexOf('.');
        if (i > 0) {
            extension = strFileName.substring(i+1);
        }
        //if (imgPath!=null){
        if (path.matches(imgPath)){
            imgname = imgname.concat(".");
            imgname = imgname.concat(extension);
        }
        else{
            sizename = sizename.concat(".");
            sizename = sizename.concat(extension);
        }
        Uri file = Uri.fromFile(new File(path));
        grantUriPermission("com.example.project_zari",file,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        StorageReference logoRef = mStorageRef.child(imgname);

        logoRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (path.matches(imgPath)){
                    imguploaded = true;
                }
                else{
                    sizeuploaded = true;
                }
                Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
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
                imgPath = getPath(getApplicationContext(),selectedImageUri);
                System.out.println("Image Path : " + imgPath);
                //img.setImageURI(selectedImageUri);
                //UploadToFirebase();
            }
            else{
                Uri selectedImageUri = data.getData();
                sizePath = getPath(getApplicationContext(),selectedImageUri);
                System.out.println("Image Path : " + sizePath);
                //img.setImageURI(selectedImageUri);
                //imageuploaded = true;
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
