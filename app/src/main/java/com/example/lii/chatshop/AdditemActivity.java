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

public class AdditemActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    EditText in_itemname, in_itemdesc, in_itemurl;
    Button bAddItem;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        in_itemname = (EditText) findViewById(R.id.input_itemname);
        in_itemdesc = (EditText) findViewById(R.id.input_description);
        in_itemurl = (EditText) findViewById(R.id.input_itemurl);
        bAddItem = (Button) findViewById(R.id.btn_additem);

        bAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (in_itemname.getText().toString().equals("")||in_itemdesc.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "empty name/description", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    try {

                        result = new MakeNetworkCall().execute("AddItem",
                                in_itemname.getText().toString(),
                                in_itemdesc.getText().toString(),
                                in_itemurl.getText().toString()).get();

                        } catch (Exception e)
                        {
                                Log.d("ADDITEMACTIVITY", "error");
                        }
                        if (result.contains("Success")){

                            Toast toast = Toast.makeText(getApplicationContext(), "Item added succesfully", Toast.LENGTH_SHORT);
                            toast.show();
                            //go back to home menu
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }

                    }
                }


        });
    }
}
