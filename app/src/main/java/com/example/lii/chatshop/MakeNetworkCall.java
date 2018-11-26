package com.example.lii.chatshop;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class ini untuk mengirim request POST ke server
 * Edit String URL_... untuk ubah URL server
 * 10.0.2.2 KALAU EMULATOR LOCALHOST
 * LOCAL IP KALAU HP ANDROID LOCALHOST
 *
 * Class ini menerima string yang dikirim php
 * Yang di PHP pake echo
 *
 * Kalau mau jalan secara concurrent(sync) pakai get() di activity yg panggil, constructor tanpa
 * interface
 *
 * Kalau mau ada update secara async (update sharedpref, update UI)
 * constructor pake yg interface
 */

class MakeNetworkCall extends AsyncTask<String, Void, String> {

    private static String LOG_TAG = "MainActivity";

    //List .php
    private static String URL_Register = "http://192.168.0.8/chatshop/register.php";
    private static String URL_Login = "http://192.168.0.8/chatshop/login.php";
    private static String URL_Additem = "http://192.168.0.8/chatshop/additem.php";
    private static String URL_Addstock = "http://192.168.0.8/chatshop/addstock.php";
    private static String URL_Browseitem = "http://192.168.0.8/chatshop/selectallitems.php";
    private static String URL_Getstock = "http://192.168.0.8/chatshop/getstock2.php";
    private static String URL_Getcomments = "http://192.168.0.8/chatshop/getcomments2.php";
    private static String URL_Addcomment = "http://192.168.0.8/chatshop/addcomment.php";
    private static String URL_Makepurchase = "http://192.168.0.8/chatshop/makepurchase.php";
    private static String URL_Selling = "http://192.168.0.8/chatshop/transactionasseller.php";
    private static String URL_Buying = "http://192.168.0.8/chatshop/transactionasbuyer.php";
    private static String URL_Confirmpurchase = "http://192.168.0.8/chatshop/confirmpurchase.php";
    private static String URL_Inserttracking = "http://192.168.0.8/chatshop/inserttracking.php";
    private static String URL_Confirmarrival = "http://192.168.0.8/chatshop/confirmarrival.php";

    //kalau mau update async, blm kepake
    AsyncResponse delegate = null;
    public MakeNetworkCall(AsyncResponse delegate){
        super();
        this.delegate = delegate;
    }

    public MakeNetworkCall(){
        super();
    }

    private InputStream PostMethod(String ServerURL, String PostParam) {

        InputStream DataInputStream = null;
        try {
            //Preparing
            URL url = new URL(ServerURL);
            HttpURLConnection cc = (HttpURLConnection)
                    url.openConnection();

            cc.setReadTimeout(5000); //timeout read inputstream
            cc.setConnectTimeout(5000); //timeout connection
            cc.setRequestMethod("POST");
            cc.setDoInput(true); //karena ada input, true
            cc.connect();

            //Writing data (bytes) to the data output stream
            DataOutputStream dos = new DataOutputStream(cc.getOutputStream());
            dos.writeBytes(PostParam);

            //flushes data output stream.
            dos.flush();
            dos.close();

            //Getting HTTP response code
            int response = cc.getResponseCode();

            //if response code is 200 / OK then read Inputstream
            //HttpURLConnection.HTTP_OK is equal to 200
            if(response == HttpURLConnection.HTTP_OK) {
                DataInputStream = cc.getInputStream();
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in GetData", e);
        }
        return DataInputStream;

    }



    private String ConvertStreamToString(InputStream stream) {

        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line = null;
        try {

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in ConvertStreamToString", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in ConvertStreamToString", e);
        } finally {

            try {
                stream.close();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in ConvertStreamToString", e);

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in ConvertStreamToString", e);
            }
        }
        return response.toString();


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg) {

        InputStream is = null;
        String URL = arg[0];
        String res = "";
        PostParamBuilder params = new PostParamBuilder();

        if (URL.equals("Register")){
            params.addParam("username", arg[1]);
            params.addParam("password", arg[2]);
            params.addParam("name", arg[3]);
            params.addParam("email", arg[4]);
            params.addParam("address", arg[5]);
            is = PostMethod(URL_Register, params.getPostParam());
        }
        else if (URL.equals("Login")){
            params.addParam("username", arg[1]);
            params.addParam("password", arg[2]);
            is = PostMethod(URL_Login, params.getPostParam());
        }
        else if (URL.equals("AddItem")){
            params.addParam("itemname", arg[1]);
            params.addParam("itemdescription", arg[2]);
            is = PostMethod(URL_Additem, params.getPostParam());
        }
        else if (URL.equals("AddStock")){
            params.addParam("itemname", arg[1]);
            params.addParam("itemstock", arg[2]);
            params.addParam("sellerid", arg[3]);
            params.addParam("price", arg[4]);
            params.addParam("description", arg[5]);
            is = PostMethod(URL_Addstock, params.getPostParam());
        }
        else if (URL.equals("GetStock")){
            params.addParam("itemid", arg[1]);
            is = PostMethod(URL_Getstock, params.getPostParam());
        }
        else if (URL.equals("GetComment")){
            params.addParam("itemid", arg[1]);
            is = PostMethod(URL_Getcomments, params.getPostParam());
        }
        else if (URL.equals("BrowseItem")){
            is = PostMethod(URL_Browseitem, "");
        }
        else if (URL.equals("AddComment"))
        {
            params.addParam("itemid", arg[1]);
            params.addParam("userid", arg[2]);
            params.addParam("comment", arg[3]);
            is = PostMethod(URL_Addcomment, params.getPostParam());
        }
        else if (URL.equals("MakePurchase")){
            params.addParam("buyerid", arg[1]);
            params.addParam("sellerid", arg[2]);
            params.addParam("stockid", arg[3]);
            params.addParam("quantity", arg[4]);
            is = PostMethod(URL_Makepurchase, params.getPostParam());
        }
        else if (URL.equals("AsBuyer")){
            params.addParam("userid", arg[1]);
            is = PostMethod(URL_Buying, params.getPostParam());
        }
        else if (URL.equals("AsSeller")){
            params.addParam("userid", arg[1]);
            is = PostMethod(URL_Selling, params.getPostParam());
        }
        else if (URL.equals("ConfirmPurchase")){
            params.addParam("trid", arg[1]);
            params.addParam("userid", arg[2]);
            is = PostMethod(URL_Confirmpurchase, params.getPostParam());
        }
        else if (URL.equals("InsertTracking")){
            params.addParam("trid", arg[1]);
            params.addParam("tracknum", arg[2]);
            params.addParam("userid", arg[3]);
            is = PostMethod(URL_Inserttracking, params.getPostParam());
        }
        else if (URL.equals("ConfirmArrival")){
            params.addParam("trid", arg[1]);
            params.addParam("userid", arg[2]);
            is = PostMethod(URL_Confirmarrival, params.getPostParam());
        }

        //Log.d(LOG_TAG, "URL: " + URL);


        if (is != null){
            res = ConvertStreamToString(is);
        } else{
            res = "Error retrieving data from DB.";
        }
        return res;
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (delegate != null)
            delegate.processFinish(result);

        Log.d(LOG_TAG, "Result: " + result);
    }

    //fungsi original, nanti dihapus
    private InputStream ByPostMethod2(String ServerURL, String in_username, String in_password) {

        InputStream DataInputStream = null;
        try {

            PostParamBuilder params = new PostParamBuilder();
            params.addParam("username", in_username);
            params.addParam("password", in_password);

            //Post parameters
            String PostParam;
            PostParam = params.getPostParam();


            //Preparing
            URL url = new URL(ServerURL);

            HttpURLConnection cc = (HttpURLConnection)
                    url.openConnection();
            //set timeout for reading InputStream
            cc.setReadTimeout(5000);
            // set timeout for connection
            cc.setConnectTimeout(5000);
            //set HTTP method to POST
            cc.setRequestMethod("POST");
            //set it to true as we are connecting for input
            cc.setDoInput(true);
            //opens the communication link
            cc.connect();


            //Writing data (bytes) to the data output stream
            DataOutputStream dos = new DataOutputStream(cc.getOutputStream());
            dos.writeBytes(PostParam);

            //flushes data output stream.
            dos.flush();
            dos.close();

            //Getting HTTP response code
            int response = cc.getResponseCode();

            //if response code is 200 / OK then read Inputstream
            //HttpURLConnection.HTTP_OK is equal to 200
            if(response == HttpURLConnection.HTTP_OK) {
                DataInputStream = cc.getInputStream();
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in GetData", e);
        }
        return DataInputStream;

    }
}