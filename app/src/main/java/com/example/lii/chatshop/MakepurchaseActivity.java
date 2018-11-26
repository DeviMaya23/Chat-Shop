package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MakepurchaseActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;
    String itemname, sellerid, sellername, sellerdesc, price, stockid, itemdesc, stockqty;
    TextView tv_itemname, tv_itemdesc, tv_sellername, tv_sellerdesc, tv_price;
    EditText et_quantity;
    Button btn_purchase;

    String result;

    int temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepurchase);

        Intent intent = getIntent();
        itemname = intent.getStringExtra("itemname");
        itemdesc = intent.getStringExtra("itemdesc");
        stockid = intent.getStringExtra("stockid");
        sellername = intent.getStringExtra("sellername");
        sellerdesc = intent.getStringExtra("sellerdesc");
        price = intent.getStringExtra("price");
        sellerid = intent.getStringExtra("sellerid");
        stockqty = intent.getStringExtra("stockqty");

        temp = Integer.parseInt(stockqty);

        tv_itemdesc = (TextView) findViewById(R.id.tv_itemdesc);
        tv_itemname = (TextView) findViewById(R.id.tv_itemname);
        tv_sellername = (TextView) findViewById(R.id.tv_sellername);
        tv_sellerdesc = (TextView) findViewById(R.id.tv_sellerdesc);
        tv_price = (TextView) findViewById(R.id.tv_price);
        et_quantity = (EditText) findViewById(R.id.input_quantity);
        btn_purchase = (Button) findViewById(R.id.btn_purchase);

        tv_itemdesc.setText(itemdesc);
        tv_itemname.setText(itemname);
        tv_sellername.setText("Seller : "+sellername);
        tv_sellerdesc.setText(sellerdesc);
        tv_price.setText("Price : "+price);

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                if (et_quantity.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "empty quantity", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    String tes = et_quantity.getText().toString();
                    int in_qty = Integer.parseInt(tes);
                    if (in_qty>temp)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "quantity too high", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else
                    {
                        try{
                            result = new MakeNetworkCall().execute("MakePurchase", prefs.getString("userid", MyPREFERENCES),
                                    sellerid,stockid, tes).get();
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        if (result.contains("Success"))
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Purchase successful!", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent i = new Intent(MakepurchaseActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }

                }
            }
        });



    }
}
