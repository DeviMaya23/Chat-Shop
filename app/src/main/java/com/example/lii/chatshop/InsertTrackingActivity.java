package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertTrackingActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;
    String result;

    EditText transid, in_tracknum;
    Button bConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserttracking);

        transid = (EditText) findViewById(R.id.input_transid);
        in_tracknum = (EditText) findViewById(R.id.input_tracking);
        bConfirm = (Button) findViewById(R.id.btn_confirm);
        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {

                if (transid.getText().toString().equals("")||in_tracknum.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "empty field(s)", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    try {

                        result = new MakeNetworkCall().execute("InsertTracking",
                                transid.getText().toString(),
                                in_tracknum.getText().toString(),
                                prefs.getString("userid",MyPREFERENCES)
                        ).get();

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    if (result.equals("Success"))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Insert tracking number successful.", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent i = new Intent(InsertTrackingActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });






    }
}
