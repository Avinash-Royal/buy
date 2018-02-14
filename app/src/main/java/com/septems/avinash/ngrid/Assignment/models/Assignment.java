package com.septems.avinash.ngrid.Assignment.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avinash on 9/30/2017.
 */

public class Assignment {
    public String Branch;
    public String Year;
    public String uid;
    public String Subject;
    public String StartDate;
    public String EndDate;
    public String Description;
    public String pdf;

    public Assignment() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Assignment(String uid,  String Subject, String StartDate, String EndDate, String Description, String pdf ) {
        this.uid = uid;
        this.Subject = Subject;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Description = Description;
        this.pdf = pdf;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("Subject", Subject);
        result.put("StartDate", StartDate);
        result.put("EndDate", EndDate);
        result.put("Description", Description);
        result.put("pdf", pdf);

        return result;
    }
    // [END post_to_map]
}
// [END post_class]
