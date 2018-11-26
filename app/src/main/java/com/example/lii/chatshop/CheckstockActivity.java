package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class CheckstockActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    String itemname, itemid, itemURL, itemdesc;
    ImageView itempicture;
    TextView tv_itemname, tv_itemdesc;


    LinearLayout mainlayout;
    ArrayList<ItemStock> stocklist;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        Intent intent = getIntent();

        itemname = intent.getStringExtra("itemname");
        itemURL = intent.getStringExtra("itemimg");
        itemdesc = intent.getStringExtra("itemdesc");
        itemid = intent.getStringExtra("itemid");

        setContentView(R.layout.activity_checkstock);


        mainlayout = (LinearLayout) findViewById(R.id.mainlayout);

        itempicture = (ImageView) findViewById(R.id.image);
        new DownloadImageTask(itempicture).execute(itemURL);

        tv_itemdesc = (TextView) findViewById(R.id.item_desc);
        tv_itemname = (TextView) findViewById(R.id.item_name);

        tv_itemname.setText(itemname);
        tv_itemdesc.setText(itemdesc);


        try {

            result = new MakeNetworkCall().execute("GetStock",
                    itemid).get();

        } catch (Exception e)
        {
            Log.d("LOGINACTIVITY", "error");
        }
        if (!result.contains("Success"))
        {
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            //Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            //toast.show();


        //tv_itemdesc.setText(result+itemid);
        stocklist = new ArrayList<>();

        StringBuilder sbuilder = new StringBuilder(result);
        sbuilder.delete(0,7);
        StringBuilder temp = new StringBuilder("");

        //tv_itemdesc.setText();

        for (int i=0, j=0; i<sbuilder.length(); i++)
        {
            if (sbuilder.charAt(i)=='{'){
                stocklist.add(new ItemStock());
                j=0;
            }
            else if (sbuilder.charAt(i)=='}')
            {
                stocklist.get(stocklist.size()-1).setDescription(temp.toString());
                temp = new StringBuilder("");
            }
            else if (sbuilder.charAt(i)=='|'){
                if (j==0) stocklist.get(stocklist.size()-1).setSellername(temp.toString());
                else if (j==1) stocklist.get(stocklist.size()-1).setStockid(temp.toString());
                else if (j==2) stocklist.get(stocklist.size()-1).setSellerid(temp.toString());
                else if (j==3) stocklist.get(stocklist.size()-1).setQuantity(temp.toString());
                else if (j==4) stocklist.get(stocklist.size()-1).setPrice(temp.toString());
                temp = new StringBuilder("");
                j++;
            }
            else
                temp.append(sbuilder.charAt(i));
        }

        for (int i = 0; i < stocklist.size(); i++) {
            mainlayout.addView(makeStockList(stocklist.get(i), i));

        }

        }

    }

    LinearLayout makeStockList(ItemStock stock, final int listid) {

        LinearLayout resLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dp24 = (int) (24 * scale + 0.5f);
        int dp100 = (int) (100 * scale + 0.5f);

        //layout besarnya
        resLayout.setOrientation(LinearLayout.VERTICAL);
        resLayout.setPadding(dp24, dp24, dp24, dp24);

        TextView text1 = new TextView(this);
        text1.setText("Seller :" + stock.getSellername());

        TextView text2 = new TextView(this);
        text2.setText(stock.getDescription());

        TextView text3 = new TextView(this);
        text3.setText("Harga : " + stock.getPrice());

        Button btn = new Button(this);
        btn.setText("Purchase");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {

                if (stocklist.get(listid).getSellerid().equals(prefs.getString("userid", MyPREFERENCES)))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "You are selling this item.", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Intent i = new Intent(CheckstockActivity.this, MakepurchaseActivity.class);
                    i.putExtra("stockid", stocklist.get(listid).getStockid());
                    i.putExtra("sellerid", stocklist.get(listid).getSellerid());
                    i.putExtra("sellername", stocklist.get(listid).getSellername());
                    i.putExtra("sellerdesc", stocklist.get(listid).getDescription());
                    i.putExtra("price", stocklist.get(listid).getPrice());
                    i.putExtra("itemname", itemname);
                    i.putExtra("itemdesc", itemdesc);
                    i.putExtra("stockqty", stocklist.get(listid).getQuantity());
                    startActivity(i);
                }
            }
        });

        resLayout.addView(text1);
        resLayout.addView(text2);
        resLayout.addView(text3);
        resLayout.addView(btn);
        return resLayout;
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch(MalformedURLException m)
            {
                mIcon11 = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
            }
            catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}


 class ItemStock {

    String sellername;
    String stockid;
    String description;
    String sellerid;
    String quantity;
    String price;

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) { this.sellername = sellername; }

    public String getStockid() { return stockid; }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    public String getSellerid() {
        return sellerid;
    }
    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }







}

