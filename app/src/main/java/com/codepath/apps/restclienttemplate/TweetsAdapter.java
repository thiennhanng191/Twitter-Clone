package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    private  OnTweetListener mOnTweetListner;

    // Pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets, OnTweetListener onTweetListener) {
        this.context = context;
        this.tweets = tweets;
        this.mOnTweetListner = onTweetListener;
    }
    // For each row, inflate the layout for a tweet
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view, mOnTweetListner);
    }

    // Bind values based on the position of the elements
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);

        // Bind the tweet with the view holder at the specified position
        holder.bind(tweet);
    }
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clear all elements of the recycler
    public void clear() {
        tweets.clear(); // modified the existing tweets list - should not make a new ArrayList() to keep the same reference so as not to mess up the recycler view
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> tweetsList) {
        tweets.addAll(tweetsList);
    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivProfileImage;
        TextView tvTweetContent;
        TextView tvTwitterHandle;
        TextView tvTimestamp;
        TextView tvName;
        TextView tvFavoriteCount;
        TextView tvRetweetCount;

        OnTweetListener onTweetListner;

        public ViewHolder(@NonNull View itemView, OnTweetListener onTweetListner) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvTweetContent = itemView.findViewById(R.id.tvTweetContent);
            tvTwitterHandle = itemView.findViewById(R.id.tvTwitterHandle);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvName = itemView.findViewById(R.id.tvName);
            tvFavoriteCount = itemView.findViewById(R.id.tvFavoriteCount);
            tvRetweetCount = itemView.findViewById(R.id.tvRetweetCount);

            this.onTweetListner = onTweetListner;
            itemView.setOnClickListener(this);

        }


        public void bind(Tweet tweet) {
            tvTweetContent.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvTwitterHandle.setText(tweet.user.screenName);
            tvTimestamp.setText(tweet.getFormattedTimestamp());
            tvFavoriteCount.setText(tweet.favoriteCount);
            tvRetweetCount.setText(tweet.retweetCount);
            Glide.with(context).load(tweet.user.profileImageUrl).transform(new RoundedCornersTransformation(100, 0)).into(ivProfileImage);
        }

        @Override
        public void onClick(View v) {
            onTweetListner.onTweetClick(getAdapterPosition());
        }
    }

    public interface OnTweetListener {
        void onTweetClick(int position);
    }
}
