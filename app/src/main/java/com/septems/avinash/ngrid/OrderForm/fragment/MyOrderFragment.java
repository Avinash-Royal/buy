package com.septems.avinash.ngrid.OrderForm.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.septems.avinash.ngrid.NewsFeed.fragment.PostListFragment;

public class MyOrderFragment extends PostListFragment {

    public MyOrderFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-Orders")
                .child(getUid());
    }
}
