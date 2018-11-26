package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/*
    Splash screen
    Cek kalau user udah pernah login/belum, kalau belum ke login page, kalo udah ke home page
 */

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    String result;

    TextView testview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        initPrefs();

        //cek kalau username&password yg disimpen bisa dipakai buat login
        try {

            result = new MakeNetworkCall(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                }
            }).execute("Login",
                    prefs.getString("username", MyPREFERENCES),
                    prefs.getString("password", MyPREFERENCES)).get();

        } catch (Exception e)
        {
            Log.d("MAINACTIVITY", "error");
        }

        if (result.contains("Success")) //update sharedref, destroy activity ini, start activity home
        {
            //nanti bikin class resultstringprocessor
            StringBuilder sbuilder = new StringBuilder(result);
            StringBuilder name = new StringBuilder("");
            StringBuilder email = new StringBuilder("");
            StringBuilder address = new StringBuilder("");
            StringBuilder userid = new StringBuilder("");
            // remove Success, { and }
            sbuilder.delete(0,7);
            sbuilder.deleteCharAt(0);
            sbuilder.setLength(sbuilder.length()-1);

            for(int i=0; i<sbuilder.length(); i++)
            {
                if (sbuilder.charAt(i) != ',')
                    name.append(sbuilder.charAt(i));
                else
                {
                    sbuilder.delete(0,i+1);
                    break;
                }
            }
            for(int i=0; i<sbuilder.length(); i++)
            {
                if (sbuilder.charAt(i) != ',')
                    email.append(sbuilder.charAt(i));
                else
                {
                    sbuilder.delete(0,i+1);
                    break;
                }
            }
            for(int i=0; i<sbuilder.length(); i++)
            {
                if (sbuilder.charAt(i) != ',')
                    address.append(sbuilder.charAt(i));
                else
                {
                    sbuilder.delete(0,i+1);
                    break;
                }
            }
            for(int i=0; i<sbuilder.length(); i++)
            {
                if (sbuilder.charAt(i) != ',')
                    userid.append(sbuilder.charAt(i));
                else
                {
                    sbuilder.delete(0,i+1);
                    break;
                }
            }
            //end
            //update sharedpreferences
            updatePrefs(name.toString(), email.toString(), address.toString(), userid.toString());

            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        else //destroy activity ini, start login
        {
            Intent i = new Intent(this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        setContentView(R.layout.activity_main);

        testview = (TextView) findViewById(R.id.tview);
        result = prefs.getString("userid", MyPREFERENCES);
        testview.setText(result);
    }

    //update sharedpreferences dengan user data yg diambil dari DB
    public void updatePrefs(String... arg) {

        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString("name", arg[0]);
        prefEditor.putString("email", arg[1]);
        prefEditor.putString("address", arg[2]);
        prefEditor.putString("userid", arg[3]);
        //prefEditor.putInt("userid", Integer.parseInt(arg[3]));

        prefEditor.apply();
    }

    //initial preferences
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


            /*
            result = new MakeNetworkCall().execute("http://192.168.0.8/chatshop/login.php",
                    prefs.getString("username", MyPREFERENCES),
                    prefs.getString("password", MyPREFERENCES)).get();
            */
