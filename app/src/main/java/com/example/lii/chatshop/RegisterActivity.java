package com.example.lii.chatshop;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    EditText in_username, in_password, in_name, in_email, in_address;
    Button bRegister;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        in_username = (EditText) findViewById(R.id.input_username);
        in_password = (EditText) findViewById(R.id.input_password);
        in_name = (EditText) findViewById(R.id.input_name);
        in_email = (EditText) findViewById(R.id.input_email);
        in_address = (EditText) findViewById(R.id.input_address);
        bRegister = (Button) findViewById(R.id.btn_register);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check empty fields
                if (in_username.getText().toString().equals("")||in_password.getText().toString().equals("")||
                        in_name.getText().toString().equals("")||in_email.getText().toString().equals("")||
                        in_address.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "empty field(s)", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    try {

                        result = new MakeNetworkCall().execute("Register",
                                in_username.getText().toString(),
                                in_password.getText().toString(),
                                in_name.getText().toString(),
                                in_email.getText().toString(),
                                in_address.getText().toString()
                                ).get();

                    } catch (Exception e)
                    {
                        Log.d("REGISTERACTIVITY", "error");
                    }
                    if (result.equals("Registered"))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Register succesfull; please log in.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        String errmsg = "Registration failed : " + result;
                        Toast toast = Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }


            }
        });

    }
}
