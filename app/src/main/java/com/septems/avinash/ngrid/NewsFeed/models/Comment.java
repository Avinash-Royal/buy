package com.septems.avinash.ngrid.NewsFeed.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START comment_class]
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String author;
    public String text;
    public String authorimage;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text ,String authorimage) {
        this.uid = uid;
        this.author = author;
        this.text = text;
        this.authorimage = authorimage;
    }

}
// [END comment_class]
