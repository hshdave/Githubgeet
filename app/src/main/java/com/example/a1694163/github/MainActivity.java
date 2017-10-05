package com.example.a1694163.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackMe  {

    ListView mylistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylistview=(ListView)findViewById(R.id.listview);

        JsonRetriever.RetrieveFromURL(this, "https://api.github.com/users/MadReza/followers", this);
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
    public void clicked(View v)
    {
        Toast.makeText(MainActivity.this, "RAWR", Toast.LENGTH_SHORT).show();
    }
}
