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

public class LoginActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    EditText in_username, in_password;
    Button bLogIn, bRegister;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        setContentView(R.layout.activity_login);

        in_username = (EditText) findViewById(R.id.input_username);
        in_password = (EditText) findViewById(R.id.input_password);
        bLogIn = (Button) findViewById(R.id.btn_login);
        bRegister = (Button) findViewById(R.id.btn_request_register);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        bLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (in_username.getText().toString().equals("")||in_password.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "empty username/password", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    try {

                        result = new MakeNetworkCall().execute("Login",
                                in_username.getText().toString(),
                                in_password.getText().toString()).get();

                    } catch (Exception e)
                    {
                        Log.d("LOGINACTIVITY", "error");
                    }
                    if (result.contains("Success"))
                    {
                        updatePrefs(in_username.getText().toString(), in_password.getText().toString());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                    // if (result.equals("Failed")||result.equals("Something went wrong"))
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT);
                        toast.show();
                        }
                }

            }
        });

    }
    public void updatePrefs(String... arg) {

        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString("username", arg[0]);
        prefEditor.putString("password", arg[1]);
        prefEditor.apply();
    }
}
