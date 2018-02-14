package com.septems.avinash.ngrid.Assignment.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class AssignmentFragment extends AssignmentListFragment {

    public AssignmentFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("Assignments").limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
