package com.example.android.testjson;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.testjson.model.Child;
import com.example.android.testjson.model.Data;
import com.example.android.testjson.model.Data_;
import com.example.android.testjson.model.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String limitTo10= "?limit=2";
    public static String someRedditPost = "https://www.reddit.com/r/soccer/comments/91ven3/.json" + limitTo10;
    TextView mTextView;
    //Model autogenerate by jsonschema2pojo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
       loadPost(getApplicationContext(), someRedditPost);
    }

    public void loadPost(final Context mContext, String url) {


        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Exclude fields (edited) without @Expose annotation
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

                List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));

                String title =  posts.get(0).getData().getChildren().get(0).getData().getTitle();
                mTextView.setText(title);
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
