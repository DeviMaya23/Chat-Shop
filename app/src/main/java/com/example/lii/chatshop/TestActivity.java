package com.example.lii.chatshop;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
    Kalo belom -> LoginActivity, kalau udah -> HomeActivity
 */
public class TestActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;

    String test ="";
    Button btn, btn2;
    TextView txtv;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        txtv = (TextView) findViewById(R.id.tview);

        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        initPrefs();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MakeNetworkCall(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        test+=output;

                        txtv.setText(test);

                    }
                }).execute("http://192.168.0.8/chatshop/session.php", "test", "test2", "moi");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MakeNetworkCall(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        if (output.equals("NotLoggedIn")) {
                            finish();
                            startActivity(getIntent());
                        }
                    }
                }).execute("http://192.168.0.8/chatshop/session.php", "test", "test2", "moi");
            }
        });

    }

    public void initPrefs() {

        SharedPreferences.Editor prefEditor = prefs.edit();

        if (!prefs.contains("username")) {
            prefEditor.putString("username", "");
        }
        if (!prefs.contains("password")) {
            prefEditor.putString("password", "");
        }
        if (!prefs.contains("email")) {
            prefEditor.putString("email", "");
        }
        if (!prefs.contains("name")) {
            prefEditor.putString("name", "");
        }
        if (!prefs.contains("address")) {
            prefEditor.putString("address", "");
        }

        prefEditor.apply();
    }
}
