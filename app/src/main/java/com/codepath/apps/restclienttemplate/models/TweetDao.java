package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
    // make a query on the Tweet and User table, select fields with specific columns name
    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt As tweet_createdAt, Tweet.favoriteCount AS tweet_favoriteCount, Tweet.retweetCount AS tweet_retweetCount, Tweet.id AS tweet_id, User.* " +
            "FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets); // can take in any number of tweets as an array

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}

