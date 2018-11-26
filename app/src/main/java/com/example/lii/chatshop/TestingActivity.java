package com.example.lii.chatshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class TestingActivity extends AppCompatActivity {

    String result;
    TextView tview, tvie2;
    ArrayList<Item> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        try {

            result = new MakeNetworkCall().execute("BrowseItem").get();

        } catch (Exception e)
        {
            Log.d("BROWSEITEMACTIVITY", "error");
        }

        StringBuilder sbuilder = new StringBuilder(result);
        sbuilder.delete(0,7);
        StringBuilder temp = new StringBuilder("");
        StringBuilder ree = new StringBuilder("");
        String testr = "";

        itemlist = new ArrayList<>();
        int x=0;
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
                testr=temp.toString();
                if (j==0) itemlist.get(itemlist.size()-1).setItemID(testr);
                else if (j==1) itemlist.get(itemlist.size()-1).setItemName(temp.toString());
                else if (j==2) itemlist.get(itemlist.size()-1).setItemDescription(temp.toString());
                else if (j==3) itemlist.get(itemlist.size()-1).setImage(temp.toString());

                if (x==0)
                {
                    testr = temp.toString();
                    x=1;
                }
                temp = new StringBuilder("");
                j++;
            }
            else
                temp.append(sbuilder.charAt(i));
        }

        tview = (TextView) findViewById(R.id.item_name);
        tview.setText(itemlist.get(1).getItemId());
        tvie2 = (TextView) findViewById(R.id.tv2);
        tvie2.setText(testr);
    }
}
