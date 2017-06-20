package com.example.android.newsapp.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.jar.JarException;

/**
 * Created by ppatel87 on 6/19/2017.
 */

public class openNewsJsonUtil
{

    public static String[] getSimpleNewsJson(Context context, String NewsJsonString)
            throws JSONException
    {

        String[] parsenewsdata=null;

        JSONObject newsjason=new JSONObject(NewsJsonString);


        if (newsjason.has("status"))
        {

                String status = newsjason.getString("status");

                switch (status) {
                    case "ok":
                        break;

                    default:
                    /* Server probably down */
                        return null;
                }



        }


         final String TAG_ARTICLES = "articles";
         final String TAG_AUTHER = "author";
         final String TAG_TITLE= "title";
         final String TAG_DESC = "description";
         final String TAG_URL = "url";
         final String TAG_IMAGE = "urlToImage";
         final String TAG_PUBLISH = "publishedAt";

        JSONArray news=newsjason.getJSONArray(TAG_ARTICLES);

        parsenewsdata =new String[news.length()];

        for(int i=0; i<news.length();i++)
        {

            JSONObject n=news.getJSONObject(i);


            String auther,title,desc,url,image,publish;

            auther=n.getString(TAG_AUTHER);
            title=n.getString(TAG_TITLE);
            desc=n.getString(TAG_DESC);
            url=n.getString(TAG_URL);
            image=n.getString(TAG_IMAGE);
            publish=n.getString(TAG_PUBLISH);


            parsenewsdata[i]= auther +"\n\n "+ title + "\n\n" +desc+ "\n\n" +url+ "\n\n" +image+ "\n\n" + publish;

        }

        return parsenewsdata;

    }
}
