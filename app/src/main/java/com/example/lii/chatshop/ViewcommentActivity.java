package com.example.lii.chatshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ViewcommentActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;

    String itemname, itemid, itemURL, itemdesc;
    ImageView itempicture;
    TextView tv_itemname, tv_itemdesc;
    EditText in_comment;
    String result, result2;
    Button bSubmit;


    LinearLayout mainlayout;
    ArrayList<Comment> commentlist;
    int tempint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomment);

        Intent intent = getIntent();

        itemname = intent.getStringExtra("itemname");
        itemURL = intent.getStringExtra("itemimg");
        itemdesc = intent.getStringExtra("itemdesc");
        itemid = intent.getStringExtra("itemid");

        mainlayout = (LinearLayout) findViewById(R.id.mainlayout);

        itempicture = (ImageView) findViewById(R.id.image);
        new DownloadImageTask(itempicture).execute(itemURL);

        tv_itemdesc = (TextView) findViewById(R.id.item_desc);
        tv_itemname = (TextView) findViewById(R.id.item_name);

        tv_itemname.setText(itemname);
        tv_itemdesc.setText(itemdesc);

        in_comment = (EditText) findViewById(R.id.et_comment);

        bSubmit = (Button) findViewById(R.id.btn_comment);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            //open additem activity
            public void onClick(View v) {
                if (in_comment.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "empty comment field", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    try {
                        result2 = new MakeNetworkCall().execute("AddComment", itemid, prefs.getString("userid", MyPREFERENCES),
                                in_comment.getText().toString()).get();
                    }catch (Exception e)
                    {

                    }
                    if (result2.contains("Success"))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Comment added!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });


        try {

            result = new MakeNetworkCall().execute("GetComment",
                    itemid).get();

        } catch (Exception e)
        {

        }
        if (result.contains("Success"))
        {
            Toast toast = Toast.makeText(getApplicationContext(), "theres a comment", Toast.LENGTH_SHORT);
            toast.show();
         }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            toast.show();
        }


        commentlist = new ArrayList<>();

        StringBuilder sbuilder = new StringBuilder(result);
        sbuilder.delete(0,7);
        StringBuilder temp = new StringBuilder("");

        //tv_itemdesc.setText();

        for (int i=0, j=0; i<sbuilder.length(); i++)
        {
            if (sbuilder.charAt(i)=='{'){
                commentlist.add(new Comment());
                j=0;
            }
            else if (sbuilder.charAt(i)=='}')
            {
                commentlist.get(commentlist.size()-1).setComment(temp.toString());
                temp = new StringBuilder("");
            }
            else if (sbuilder.charAt(i)=='|'){
                if (j==0) commentlist.get(commentlist.size()-1).setName(temp.toString());
                else if (j==1) commentlist.get(commentlist.size()-1).setDate(temp.toString());
                temp = new StringBuilder("");
                j++;
            }
            else
                temp.append(sbuilder.charAt(i));
        }

        for (int i = 0; i < commentlist.size(); i++) {
            mainlayout.addView(makeCommentList(commentlist.get(i)));

        }

    }

    LinearLayout makeCommentList(Comment cmnt) {

        LinearLayout resLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dp24 = (int) (24 * scale + 0.5f);
        int dp100 = (int) (100 * scale + 0.5f);

        //layout besarnya
        resLayout.setOrientation(LinearLayout.VERTICAL);
        resLayout.setPadding(dp24, dp24, dp24, dp24);

        TextView text1 = new TextView(this);
        text1.setText("User :" + cmnt.getName() + " ("+ cmnt.getDate()+")");

        TextView text2 = new TextView(this);
        text2.setText(cmnt.getComment());

        resLayout.addView(text1);
        resLayout.addView(text2);
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
 class Comment {

    String name;
    String commentid;
    String itemid;
    String userid;
    String date;
    String comment;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
