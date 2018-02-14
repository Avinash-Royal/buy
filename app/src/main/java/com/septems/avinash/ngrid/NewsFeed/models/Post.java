package com.septems.avinash.ngrid.NewsFeed.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
    public String authorphoto;
    public String title;
    public String image;
    public String body;
    public int likeCount = 0;
    public Map<String, Boolean> Likes = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String image, String authorphoto) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.image = image;
        this.authorphoto = authorphoto;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("LikeCount", likeCount);
        result.put("Likes", Likes);
        result.put("image", image);
        result.put("authorphoto", authorphoto);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
