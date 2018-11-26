package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    TextView textview_name;

    Button bTransHistory, bDisplayItem, bAddItem, bAddStock, bLogout, bConfirmPurchase,
    bConfirmArrival, bInsertTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        setContentView(R.layout.activity_home);
        textview_name = (TextView) findViewById(R.id.tv_name);
        textview_name.setText(prefs.getString("name", MyPREFERENCES) + "id :"+ prefs.getString("userid",MyPREFERENCES));

        //transaction history
        bTransHistory = (Button) findViewById(R.id.btn_seetransaction);
        bTransHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ChecktransactionActivity.class);
                startActivity(i);
            }
        });

        //browse item
        bDisplayItem = (Button) findViewById(R.id.btn_displayitem);
        bDisplayItem.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BrowseitemActivity.class);
                startActivity(i);
            }
        });

        //add item
        bAddItem = (Button) findViewById(R.id.btn_additem);
        bAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AdditemActivity.class);
                startActivity(i);
            }
        });

        //confirm purchase
        bAddStock = (Button) findViewById(R.id.btn_addstock);
        bAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddstockActivity.class);
                startActivity(i);
            }
        });

        //confirm purchase
        bConfirmPurchase = (Button) findViewById(R.id.btn_confirmpurchase);
        bConfirmPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, Confirmpurchase.class);
                startActivity(i);
            }
        });

        //confirm purchase
        bInsertTracking = (Button) findViewById(R.id.btn_inserttracking);
        bInsertTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, InsertTrackingActivity.class);
                startActivity(i);
            }
        });

        bConfirmArrival = (Button) findViewById(R.id.btn_confirmarrival);
        bConfirmArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CfmArrival.class);
                startActivity(i);
            }
        });

        //add item
        bAddItem = (Button) findViewById(R.id.btn_additem);
        bAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AdditemActivity.class);
                startActivity(i);
            }
        });



        //logout
        bLogout = (Button) findViewById(R.id.btn_logout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

    }

    public void LogOut()
    {
        //delete login info from sharedpref
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString("username", "");
        prefEditor.putString("password", "");
        prefEditor.apply();

        Toast toast = Toast.makeText(getApplicationContext(), "You are logged out.", Toast.LENGTH_SHORT);
        toast.show();
        //run mainactivity
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
