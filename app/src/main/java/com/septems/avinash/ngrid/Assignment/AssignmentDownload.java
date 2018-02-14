package com.septems.avinash.ngrid.Assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.septems.avinash.ngrid.Assignment.models.Assignment;
import com.septems.avinash.ngrid.Messaging.data.StaticConfig;

public class AssignmentDownload extends BaseActivity {

    private static final String TAG = "AssignmentDownload";

    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mAssignmentReference;
    private ValueEventListener mAssignmentListner;
    private String mPostKey;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        // Initialize Database
        mAssignmentReference = FirebaseDatabase.getInstance().getReference()
                .child("Assignments").child(mPostKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener AssignmentListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Assignment assignment = dataSnapshot.getValue(Assignment.class);
                // [START_EXCLUDE]
                if (assignment.pdf == null) {
                } else {
                    if (!assignment.pdf.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                        byte[] decodedString = Base64.decode(assignment.pdf, Base64.DEFAULT);
                        bitmap =BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(AssignmentDownload.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mAssignmentReference.addValueEventListener(AssignmentListener);
        mAssignmentListner = AssignmentListener;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAssignmentListner != null) {
            mAssignmentReference.removeEventListener(mAssignmentListner);
        }
    }

}
