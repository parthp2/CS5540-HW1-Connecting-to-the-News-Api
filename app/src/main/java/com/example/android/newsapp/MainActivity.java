package com.example.android.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.newsapp.utilities.NetworkUtils;
import com.example.android.newsapp.utilities.openNewsJsonUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsdisplay;

    private  TextView errorMessage;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsdisplay =(TextView) findViewById(R.id.news_id) ;

        mNewsdisplay.setText("Click On Search Button for news !");


        progressBar =(ProgressBar) findViewById(R.id.pb_loader);

        errorMessage =(TextView) findViewById(R.id.error_message);
    }

    private void search()
    {

        String key= "80e52a38d8704706a5fae794366e4acd";

        new fetchNews().execute(key);

    }


    private void  showJsondata()
    {
        errorMessage.setVisibility(View.INVISIBLE);
        mNewsdisplay.setVisibility(View.VISIBLE);
    }

    private void showerror()
    {
        errorMessage.setVisibility(View.VISIBLE);
        mNewsdisplay.setVisibility(View.INVISIBLE);
    }


    public  class fetchNews extends AsyncTask<String ,Void ,String[]>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... param)
        {
            if(param.length == 0)
            {
                return null;
            }

            String key=param[0];

            URL newsurl= NetworkUtils.buildUrl(key);


            try{
                String JsonNewsData = NetworkUtils.getResponseFromHttpUrl(newsurl);

               String[] simpleNeWsJson= openNewsJsonUtil.getSimpleNewsJson(MainActivity.this,JsonNewsData);



                return simpleNeWsJson;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected  void onPostExecute(String[] newsdata)
        {
            progressBar.setVisibility(View.INVISIBLE);

            if(newsdata != null)
            {

                showJsondata();
                mNewsdisplay.setText("");
                for(String news: newsdata)
                {
                    mNewsdisplay.append((news)+ "\n-------------------------------------------------\n\n");
                }

            }
            else
            {
                showerror();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);

        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id =item.getItemId();

        if(id==R.id.action_serach)
        {
            search();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
