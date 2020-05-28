package com.example.project_zari;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;


public class buyerAccFragment extends Fragment {

    SharedPreferences sharedpreferences;
    private TextView name, email, dob, gender;
    Button btn;

    public buyerAccFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("buyersignin", Context.MODE_PRIVATE);

        /*System.out.println(preferences.getString("name", ""));*/
        String cust_name = preferences.getString("name", "Name: ");
        String cust_email = preferences.getString("email", "Email: ");
        String cust_dob = preferences.getString("dob", "DOB: ");
        String cust_gender = preferences.getString("gender", "Gender: ");

        name = (TextView) view.findViewById(R.id.username);
        email = (TextView) view.findViewById(R.id.useremail);
        dob = (TextView) view.findViewById(R.id.userdob);
        gender = (TextView) view.findViewById(R.id.usergender);

        name.setText(cust_name);
        email.setText(cust_email);
        dob.setText(cust_dob);
        gender.setText(cust_gender);

        btn = (Button) view.findViewById(R.id.logout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }




}