package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddstockActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    EditText in_itemname, in_description, in_quantity, in_price;
    Button bAddStock;

    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstock);
        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        in_itemname = (EditText) findViewById(R.id.input_itemname);
        in_description = (EditText) findViewById(R.id.input_description);
        in_quantity = (EditText) findViewById(R.id.input_quantity);
        in_price = (EditText) findViewById(R.id.input_price);

        bAddStock = (Button) findViewById(R.id.btn_addstock);

        bAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (in_itemname.getText().toString().equals("")||in_description.getText().toString().equals("")||
                        in_quantity.getText().toString().equals("")||in_price.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "empty name/description", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{


                    try {

                        result = new MakeNetworkCall().execute("AddStock",
                                in_itemname.getText().toString(),
                                in_quantity.getText().toString(),
                                prefs.getString("userid", MyPREFERENCES),
                                in_price.getText().toString(),
                                in_description.getText().toString()).get();

                    } catch (Exception e)
                    {
                        Log.d("ADDSTOCKACTIVITY", "error");
                    }
                    if (result.contains("Success")){

                        Toast toast = Toast.makeText(getApplicationContext(), "Stock added", Toast.LENGTH_SHORT);
                        toast.show();
                        //go back to home menu
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }


        });

    }
}
