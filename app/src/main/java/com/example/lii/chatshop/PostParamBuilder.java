package com.example.lii.chatshop;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Class ini untuk membuat parameter untuk POST
 *
 */

class PostParamBuilder {

    private static String LOG_TAG = "PostParamBuilder";
    StringBuilder postparam;
    boolean empty;

    public PostParamBuilder(){
        postparam = new StringBuilder();
        empty = true;
    }

    public void addParam(String key, String value){

        try {
            if (!empty) postparam.append("&");
            postparam.append(URLEncoder.encode(key, "UTF-8"));
            postparam.append("=");
            postparam.append(URLEncoder.encode(value, "UTF-8"));

            empty = false;
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error in creating post parameter", e);
        }
    }


    public String getPostParam(){
        return postparam.toString();
    }
}