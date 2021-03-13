package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.utils.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {
    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    @PrimaryKey
    public long id;

    @Ignore
    public User user;

    @ColumnInfo
    public long userId;

    @ColumnInfo
    public String favoriteCount;

    @ColumnInfo
    public String retweetCount;

    // empty constructor needed by the Parceler library
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.favoriteCount = formatCount(jsonObject.getString("favorite_count"));
        tweet.retweetCount = formatCount(jsonObject.getString("retweet_count"));

        User user =  User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
        return tweet;
    }

    private static String formatCount(String count) {
        int val = Integer.parseInt(count);
        return val >= 10000 ? ""+ val/1000 + "K" : count;

    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getFormattedTimestamp() {
        return TimeFormatter.getTimeDifference(createdAt);
    }
}
