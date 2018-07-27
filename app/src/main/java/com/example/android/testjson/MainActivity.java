package com.example.android.testjson;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String limitTo10= "?limit=2";
    public static String someRedditPost = "https://www.reddit.com/r/soccer/comments/91ven3/.json" + limitTo10;

    //Model autogenerate by jsonschema2pojo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPost(getApplicationContext(), someRedditPost);
    }

    public static void loadPost(final Context mContext, String url) {


        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                List<com.example.android.testjson.Post> posts = Arrays.asList(gson.fromJson(response, com.example.android.testjson.Post[].class));

               String title =  posts.get(0).getData().getChildren().get(0).getData().getTitle();

                Toast.makeText(mContext, "title: " + title, Toast.LENGTH_SHORT).show();



            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        ConnectionManager.getInstance(mContext).add(request);


    }
}
