package com.example.lii.chatshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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

public class BrowseitemActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    LinearLayout mainlayout;

    ArrayList<Item> itemlist;
    ArrayList<ItemLayout> itemlayoutlist;
    Item tempitem;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browseitem);

        //get all items
        try {

            result = new MakeNetworkCall().execute("BrowseItem").get();

        } catch (Exception e)
        {
            Log.d("BROWSEITEMACTIVITY", "error");
        }

        if (!result.contains("Success")) //return to home
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to get items.", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(BrowseitemActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        itemlist = new ArrayList<Item>();
        mainlayout = (LinearLayout) findViewById(R.id.mainlayout);
        //parse hasil select query
        StringBuilder sbuilder = new StringBuilder(result);
        sbuilder.delete(0,7);
        StringBuilder temp = new StringBuilder("");

        for (int i=0, j=0; i<sbuilder.length(); i++)
        {
            if (sbuilder.charAt(i)=='{'){
                itemlist.add(new Item());
                j=0;
            }
            else if (sbuilder.charAt(i)=='}')
            {
                itemlist.get(itemlist.size()-1).setStatus(temp.toString());
                temp = new StringBuilder("");
            }
            else if (sbuilder.charAt(i)=='|'){
                if (j==0) itemlist.get(itemlist.size()-1).setItemID(temp.toString());
                else if (j==1) itemlist.get(itemlist.size()-1).setItemName(temp.toString());
                else if (j==2) itemlist.get(itemlist.size()-1).setItemDescription(temp.toString());
                else if (j==3) itemlist.get(itemlist.size()-1).setImage(temp.toString());
                temp = new StringBuilder("");
                j++;
            }
            else
                temp.append(sbuilder.charAt(i));
        }

        for (int i = 0; i < itemlist.size(); i++) {
            mainlayout.addView(makeItemList(itemlist.get(i), i));

        }

        /*
        itemlayoutlist = new ArrayList<>();
        for (int i = 0; i < itemlist.size(); i++) {
            itemlayoutlist.add(new ItemLayout(this, itemlist.get(i)));
        }
        for (int i = 0; i < itemlist.size(); i++) {
            new AsyncTask<Integer, Void, Void>(){
                @Override
                protected Void doInBackground(Integer... params) {
                    // **Code**
                    itemlayoutlist.get(params[0]).testfunc();
                    return null;
                }
            }.execute(i);
        }*/




    }
    LinearLayout makeItemList(Item item, final int itemlistid) {

        LinearLayout resLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dp24 = (int) (24 * scale + 0.5f);
        int dp100 = (int) (100 * scale + 0.5f);

        tempitem = item;

        //layout besarnya
        resLayout.setOrientation(LinearLayout.VERTICAL);
        resLayout.setPadding(dp24, dp24, dp24, dp24);

        LinearLayout horizontalLayout1 = new LinearLayout(this);
        horizontalLayout1.setOrientation(LinearLayout.HORIZONTAL);

        //gambar
        ImageView newimage = new ImageView(this);
        //newimage.setImageResource(R.drawable.item1);
        if (item.getImageURL().equals("no image"))
            newimage.setImageResource(R.drawable.noimage);
        else
            new DownloadImageTask(newimage).execute(item.getImageURL());
        newimage.setLayoutParams(new LinearLayout.LayoutParams(dp100, dp100));
        horizontalLayout1.addView(newimage);

        //textlayout
        LinearLayout textlayout = new LinearLayout(this);
        textlayout.setOrientation(LinearLayout.VERTICAL);

        TextView text1 = new TextView(this);
        text1.setTypeface(Typeface.DEFAULT_BOLD);
        text1.setText(item.getItemName());
        TextView text2 = new TextView(this);
        text2.setText(item.getItemDescription());

        textlayout.addView(text1);
        textlayout.addView(text2);

        horizontalLayout1.addView(textlayout);
        resLayout.addView(horizontalLayout1);

        //buttons
        LinearLayout buttonlayout = new LinearLayout(this);
        buttonlayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonlayout.setGravity(Gravity.CENTER);

        Button button1 = new Button(this);
        button1.setText("View Comments");
        Button button2 = new Button(this);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {

                Intent i = new Intent(BrowseitemActivity.this, ViewcommentActivity.class);
                i.putExtra("itemname", itemlist.get(itemlistid).getItemName());
                i.putExtra("itemimg", itemlist.get(itemlistid).getImageURL());
                i.putExtra("itemid", itemlist.get(itemlistid).getItemId());
                i.putExtra("itemdesc", itemlist.get(itemlistid).getItemDescription());
                startActivity(i);
            }
        });
        button2.setText("Check Stock");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {

                Intent i = new Intent(BrowseitemActivity.this, CheckstockActivity.class);
                i.putExtra("itemname", itemlist.get(itemlistid).getItemName());
                i.putExtra("itemimg", itemlist.get(itemlistid).getImageURL());
                i.putExtra("itemid", itemlist.get(itemlistid).getItemId());
                i.putExtra("itemdesc", itemlist.get(itemlistid).getItemDescription());
                startActivity(i);
            }
        });

        buttonlayout.addView(button1);
        buttonlayout.addView(button2);

        resLayout.addView(buttonlayout);

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
            }catch(MalformedURLException m)
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

    //blm dipake
    public class ItemLayout {
        LinearLayout resLayout, horizontalLayout1, textlayout, buttonlayout;
        Button bComment, bStock;
        ImageView newimage;
        TextView text1, text2;
        Item item;

        Context ctx;

        ItemLayout(Context cont, Item itm)
        {
            item = itm;
            ctx = cont;
            float scale = getResources().getDisplayMetrics().density;
            int dp24 = (int) (24 * scale + 0.5f);
            int dp100 = (int) (100 * scale + 0.5f);

            resLayout = new LinearLayout(cont);
            resLayout.setOrientation(LinearLayout.VERTICAL);
            resLayout.setPadding(dp24, dp24, dp24, dp24);
            horizontalLayout1 = new LinearLayout(cont);
            horizontalLayout1.setOrientation(LinearLayout.HORIZONTAL);

            newimage = new ImageView(cont);
            newimage.setLayoutParams(new LinearLayout.LayoutParams(dp100, dp100));
            horizontalLayout1.addView(newimage);
            textlayout = new LinearLayout(cont);

            textlayout.setOrientation(LinearLayout.VERTICAL);
            text1 = new TextView(cont);
            text1.setTypeface(Typeface.DEFAULT_BOLD);
            text2 = new TextView(cont);
            textlayout.addView(text1);
            textlayout.addView(text2);

            horizontalLayout1.addView(textlayout);
            resLayout.addView(horizontalLayout1);

            buttonlayout = new LinearLayout(cont);
            buttonlayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonlayout.setGravity(Gravity.CENTER);

            bComment = new Button(cont);
            bStock = new Button(cont);

            buttonlayout.addView(bComment);
            buttonlayout.addView(bStock);

            resLayout.addView(buttonlayout);

        }

        void testfunc()
        {
            tempitem = item;

            //newimage.setImageResource(R.drawable.item1);
            if (item.getImageURL().equals("no image"))
                newimage.setImageResource(R.drawable.noimage);
            else
                new DownloadImageTask(newimage).execute(item.getImageURL());

            //textlayout
            text1.setText(item.getItemName());
            text2.setText(item.getItemDescription());

            bComment.setText("View Comments");
            bStock.setText("Check Stock");
            bStock.setOnClickListener(new View.OnClickListener() {
                @Override
                //open additem activity
                public void onClick(View v) {

                    Intent i = new Intent(ctx, CheckstockActivity.class);
                    i.putExtra("itemname", item.getItemName());
                    i.putExtra("itemimg", item.getImageURL());
                    i.putExtra("itemid", item.getItemId());
                    i.putExtra("itemdesc", item.getItemDescription());
                    startActivity(i);
                }
            });

        }

        LinearLayout getResLayout(){ return resLayout; }
    }

}
class Item {
    String itemID;
    String itemName;
    String itemDescription;
    String image; //URL
    String status;

    public Item()
    {

    }

    public Item(String id, String name, String desc, String img, String stat)
    {
        itemID=id;
        itemName=name;
        itemDescription=desc;
        image=img;
        status=stat;
    }

    public void setItemID(String in)
    {
        itemID= in;
    }

    public String getItemId()
    {
        return itemID;
    }

    public void setItemName(String in)
    {
        itemName = in;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemDescription(String in)
    {
        itemDescription = in;
    }

    public String getItemDescription()
    {
        return itemDescription;
    }

    public void setImage(String in)
    {
        image = in;
    }

    public String getImageURL()
    {
        return image;
    }

    public void setStatus(String in)
    {
        status= in;
    }

    public String getStatus()
    {
        return status;
    }
}