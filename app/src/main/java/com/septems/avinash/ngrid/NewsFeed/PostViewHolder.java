package com.septems.avinash.ngrid.NewsFeed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.septems.avinash.ngrid.NewsFeed.models.Post;
import com.septems.avinash.ngrid.R;


public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView, authorphoto, postedimage;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        starView = itemView.findViewById(R.id.star);
        authorphoto = itemView.findViewById(R.id.post_author_photo);
        postedimage = itemView.findViewById(R.id.post_image);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.likeCount));
        bodyView.setText(post.body);
        starView.setOnClickListener(starClickListener);
    }
}
