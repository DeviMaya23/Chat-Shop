package com.example.lii.chatshop;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChecktransactionActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences prefs;
    String resultS, resultB;

    ArrayList<TransactionInfo> buyinglist, sellinglist;

    LinearLayout sell_layout, buy_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs =this.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checktransaction);

        sell_layout = (LinearLayout) findViewById(R.id.sell_layout);
        buy_layout = (LinearLayout) findViewById(R.id.buy_layout);

        buyinglist = new ArrayList<>();
        sellinglist = new ArrayList<>();

        try{
            resultB = new MakeNetworkCall().execute("AsBuyer", prefs.getString("userid",MyPREFERENCES)).get();
            resultS = new MakeNetworkCall().execute("AsSeller", prefs.getString("userid",MyPREFERENCES)).get();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //layout for selling history
        if (resultS.contains("Success")){
            StringBuilder sbuilder = new StringBuilder(resultS);
            sbuilder.delete(0,7);
            StringBuilder temp = new StringBuilder("");

            for (int i=0, j=0; i<sbuilder.length(); i++)
            {
                if (sbuilder.charAt(i)=='{'){
                    sellinglist.add(new TransactionInfo());
                    j=0;
                }
                else if (sbuilder.charAt(i)=='}')
                {
                    sellinglist.get(sellinglist.size()-1).setPrice(temp.toString());
                    sellinglist.get(sellinglist.size()-1).setType(0);
                    temp = new StringBuilder("");
                }
                else if (sbuilder.charAt(i)=='|'){
                    if (j==0) sellinglist.get(sellinglist.size()-1).setTransactionid(temp.toString());
                    else if (j==1) sellinglist.get(sellinglist.size()-1).setUsername(temp.toString());
                    else if (j==2) sellinglist.get(sellinglist.size()-1).setItemname(temp.toString());
                    else if (j==3) sellinglist.get(sellinglist.size()-1).setDescription(temp.toString());
                    else if (j==4) sellinglist.get(sellinglist.size()-1).setStatus(temp.toString());
                    else if (j==5) sellinglist.get(sellinglist.size()-1).setQuantity(temp.toString());
                    temp = new StringBuilder("");
                    j++;
                }
                else
                    temp.append(sbuilder.charAt(i));

            }
            for (int i = 0; i < sellinglist.size(); i++) {
                sell_layout.addView(makeTransactionList(sellinglist.get(i)));

            }
        }
        else {
            TextView txtv1 = new TextView(this);
            txtv1.setText("No selling history.");
            txtv1.setGravity(Gravity.CENTER);
            sell_layout.addView(txtv1);
        }

        //add layout for buying history
        if (resultB.contains("Success")){
            StringBuilder sbuilder = new StringBuilder(resultB);
            sbuilder.delete(0,7);
            StringBuilder temp = new StringBuilder("");

            for (int i=0, j=0; i<sbuilder.length(); i++)
            {
                if (sbuilder.charAt(i)=='{'){
                    buyinglist.add(new TransactionInfo());
                    j=0;
                }
                else if (sbuilder.charAt(i)=='}')
                {
                    buyinglist.get(buyinglist.size()-1).setPrice(temp.toString());
                    buyinglist.get(buyinglist.size()-1).setType(1);
                    temp = new StringBuilder("");
                }
                else if (sbuilder.charAt(i)=='|'){
                    if (j==0) buyinglist.get(buyinglist.size()-1).setTransactionid(temp.toString());
                    else if (j==1) buyinglist.get(buyinglist.size()-1).setUsername(temp.toString());
                    else if (j==2) buyinglist.get(buyinglist.size()-1).setItemname(temp.toString());
                    else if (j==3) buyinglist.get(buyinglist.size()-1).setDescription(temp.toString());
                    else if (j==4) buyinglist.get(buyinglist.size()-1).setStatus(temp.toString());
                    else if (j==5) buyinglist.get(buyinglist.size()-1).setQuantity(temp.toString());
                    temp = new StringBuilder("");
                    j++;
                }
                else
                    temp.append(sbuilder.charAt(i));
            }
            for (int i = 0; i < buyinglist.size(); i++) {
                buy_layout.addView(makeTransactionList(buyinglist.get(i)));

            }
        }
        else {
            TextView txtv2 = new TextView(this);
            txtv2.setText("No purchase history.");
            txtv2.setGravity(Gravity.CENTER);
            buy_layout.addView(txtv2);
        }

    }

    LinearLayout makeTransactionList(TransactionInfo trfinfo) {

        LinearLayout resLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dp24 = (int) (24 * scale + 0.5f);
        String type;

        if (trfinfo.getType()==0)
            type = "Buyer";
        else
            type = "Seller";

        //layout besarnya
        resLayout.setOrientation(LinearLayout.VERTICAL);
        resLayout.setPadding(dp24, dp24, dp24, dp24);

        TextView text1 = new TextView(this);
        text1.setText("Transaction ID : "+trfinfo.getTransactionid()
                + "\n" + type + " : "+ trfinfo.getUsername()
                + "\n" + trfinfo.getItemname()
                + "\n" + trfinfo.getDescription()
                + "\n" + "Jumlah : "+ trfinfo.getQuantity()
                + "\n" + "Harga : "+ trfinfo.getPrice()
                + "\n" + "Total harga : "+ Integer.toString(trfinfo.getPriceTotal())
                + "\n" + "\n" + "Status : " + trfinfo.getTransactionStatus()
        );

        /*
        TextView text2 = new TextView(this); text2.setText(type + " : "+ trfinfo.getUsername());
        TextView text3 = new TextView(this); text3.setText(trfinfo.getItemname());
        TextView text4 = new TextView(this); text4.setText(trfinfo.getDescription());
        TextView text5 = new TextView(this); text5.setText("Jumlah : "+ trfinfo.getQuantity());
        TextView text6 = new TextView(this); text6.setText("Harga : "+ trfinfo.getPrice());
        TextView text7 = new TextView(this); text6.setText("Harga : "+ trfinfo.getPrice());*/

        resLayout.addView(text1);
        return resLayout;
    }



    class TransactionInfo{

        int type; //0 -> selling, 1->buying
        String transactionid;
        String username;
        String status;
        String quantity;
        String itemname;
        String description;
        String price;

        public String getTransactionStatus(){

            int stat = Integer.parseInt(status);

            if (stat==1) return "Menunggu konfirmasi dari seller.";
            else if (stat==2) return "Pembelian telah dikonfirmasi, menunggu nomor tracking.";
            else if (stat==3) return "Nomor tracking telah masuk, menunggu baraang.";
            else if (stat==4) return "Transaksi selesai.";
            else return "Transaksi dibatalkan.";

        }

        public int getPriceTotal(){
            int a = Integer.parseInt(quantity);
            int b = Integer.parseInt(price);
            return a*b;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(String transactionid) {
            this.transactionid = transactionid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String userid) {
            this.username = userid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
