package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.TimeFormatter;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetails extends AppCompatActivity {
    TextView tvName;
    TextView tvTwitterHandle;
    TextView tvTimestamp;
    TextView tvTweetContent;
    TextView tvRetweetCount;
    TextView tvFavoriteCount;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        tvName = findViewById(R.id.tvName);
        tvTwitterHandle = findViewById(R.id.tvTwitterHandle);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvTweetContent = findViewById(R.id.tvTweetContent);
        tvRetweetCount = findViewById(R.id.tvRetweetCount);
        tvFavoriteCount = findViewById(R.id.tvFavoriteCount);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvName.setText(tweet.user.name);
        tvTwitterHandle.setText(tweet.user.screenName);
        tvTimestamp.setText(TimeFormatter.getTimeStamp(tweet.createdAt));
        tvTweetContent.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvFavoriteCount.setText(tweet.favoriteCount);
        tvRetweetCount.setText(tweet.retweetCount);
        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCornersTransformation(100, 0)).into(ivProfileImage);


    }
}