package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 250;
    public static final String TAG = "ComposeActivity";

    ImageView ivUserProfileImage;
    Button btnCancel;
    Button btnTweet;
    EditText etComposeTweet;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ivUserProfileImage = findViewById(R.id.ivProfileImage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTweet);
        setSupportActionBar(toolbar);

        client = TwitterApplication.getRestClient(this);

        etComposeTweet = findViewById(R.id.etComposeTweet);
        // etComposeTweet.setTextIsSelectable(true);

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // close this activity and return to preview activity (if there is any)
            }
        });
        btnTweet = findViewById(R.id.btnTweet);
        // disable compose tweet button when the user hasn't input anything
        // btnTweet.setEnabled(!etComposeTweet.getText().toString().isEmpty());

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etComposeTweet.getText().toString();
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too long", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make an API to Twitter to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess when publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet: " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            // set result code and bundle data for response
                            setResult(RESULT_OK, intent);
                            // close the activity, pass data to parent
                            finish();
                        } catch (JSONException e) {
                            Log.e(TAG, "error when publish tweet");

                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure when publish tweet", throwable);
                    }
                });
            }
        });

    }
}