package com.example.a1694163.github;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackMe  {

    ListView mylistview;
    ImageView img_v;

    TextView txtnm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_v =  (ImageView) findViewById(R.id.avatar);
        txtnm = (TextView) findViewById(R.id.login);

        Intent i =  getIntent();

        String url = i.getStringExtra("prourl");

        String flw = url+"/followers";

        System.out.println("Check Followers Link "+ flw);

        mylistview=(ListView)findViewById(R.id.listview);

        new Userdata().execute(url);
        JsonRetriever.RetrieveFromURL(this, url+"/followers", this);
    }

    @Override
    public void CallThis(String jsonText) {


        try {
            JSONArray jArray = new JSONArray(jsonText);
            Follower[] followers = new Follower[jArray.length()];

            for(int position = 0; position < jArray.length(); position++)
            {
                JSONObject jObj = jArray.getJSONObject(position);
                String name = jObj.getString("login");
                String imageURL = jObj.getString("avatar_url");
                String followerurl = jObj.getString("followers_url");
                String url = jObj.getString("url");

                Follower current = new Follower(imageURL, name, followerurl,url);
                followers[position] = current;
            }

            CustomFollowerAdapater cfa = new CustomFollowerAdapater(MainActivity.this, followers);
            mylistview.setAdapter(cfa);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public class Userdata extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            System.out.println("Check progiel url "+s);

            if(s!=null)
            {
                try
                {
                    JSONObject mainObj = new JSONObject(s);

                    Picasso.with(MainActivity.this).load(mainObj.getString("avatar_url")).into(img_v);

                    txtnm.setText(mainObj.getString("name"));


                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }

        @Override
        protected String doInBackground(String... strings) {

            String url = strings[0];

            Httpresolver hr = new Httpresolver();

            String jsnpro = hr.makeServiceCall(url);

            return jsnpro;
        }
    }
}
