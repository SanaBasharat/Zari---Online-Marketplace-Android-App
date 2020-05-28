package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SProfileFragment extends Fragment {
    private SharedPreferences sharedpreferences;
    DatabaseReference reff;
    private StorageReference mStorageRef;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_profile,container,false);
        this.mContext=getContext();
        sharedpreferences = mContext.getSharedPreferences("sellersignin", Context.MODE_PRIVATE);
        String email = sharedpreferences.getString("email","");
        String phoneno = sharedpreferences.getString("phone","");
        String address = sharedpreferences.getString("address","");
        String bname = sharedpreferences.getString("name","");
        String logo = sharedpreferences.getString("logo","");

        EditText name = view.findViewById(R.id.nameeditText);
        name.setText(bname);
        EditText phone = view.findViewById(R.id.phoneeditText);
        phone.setText(phoneno);
        EditText add = view.findViewById(R.id.addedittext);
        add.setText(address);

        final ImageView logoimg = view.findViewById(R.id.logo_image);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference photoref = mStorageRef.child(logo);

        photoref.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageURL = uri.toString();
                        Glide.with(getContext()).load(imageURL).into(logoimg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

        Button btn = (Button) view.findViewById(R.id.logoutBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });





//        Button savebtn = view.findViewById(R.id.buttonBtn);
//        savebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                {
//                    EditText name = view.findViewById(R.id.nameeditText);
//                    if (name.getText().toString().isEmpty()){
//                        Toast.makeText(getContext(), "You must enter your brand name", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    EditText desc = view.findViewById(R.id.desceditText);
//                    if (desc.getText().toString().isEmpty()){
//                        Toast.makeText(getContext(), "You must enter your description", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    EditText phone = view.findViewById(R.id.phoneeditText);
//                    if (phone.getText().toString().isEmpty()){
//                        Toast.makeText(getContext(), "You must enter your phone number", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    EditText add = view.findViewById(R.id.addedittext);
//                    if (add.getText().toString().isEmpty()){
//                        Toast.makeText(getContext(), "You must enter your address", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    Toast.makeText(getContext(), "Your changes have been saved!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        return view;
    }
}
