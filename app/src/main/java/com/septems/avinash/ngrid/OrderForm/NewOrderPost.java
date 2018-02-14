package com.septems.avinash.ngrid.OrderForm;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.septems.avinash.ngrid.Assignment.BaseActivity;
import com.septems.avinash.ngrid.Assignment.models.Assignment;
import com.septems.avinash.ngrid.Assignment.models.User;
import com.septems.avinash.ngrid.Messaging.util.ImageUtils;
import com.septems.avinash.ngrid.OrderForm.Model.Order;
import com.septems.avinash.ngrid.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NewOrderPost extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    final static int PICK_PDF_CODE = 2342;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private static final int PICK_IMAGE = 1994;

    // [END declare_database_ref]
    private Spinner mRice, mToorDal, mWheat, mRajama, mChoole, mMatar;
    //    private FloatingActionButton mSubmitButton;
    private Button mSubmitButton;

    public String Rice,
            ToorDal,
            Wheat,
            Rajama,
            Choole,
            Martar;

    //Animation Attributes
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;
    private int revealX;
    private int revealY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);

        //Animation Code
        final Intent intent = getIntent();
        rootLayout = findViewById(R.id.root_layout);
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

//        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        // [END initialize_database_ref]

        mRice = (Spinner) findViewById(R.id.Rice_spinner);
        mToorDal = (Spinner) findViewById(R.id.Toor_Dal_spinner);
        mWheat = (Spinner) findViewById(R.id.Wheat_spinner);
        mRajama = (Spinner) findViewById(R.id.Rajam_spinner);
        mChoole = (Spinner) findViewById(R.id.Choole_spinner);
        mMatar = (Spinner) findViewById(R.id.Matar_spinner);
//        mSubject = (EditText) findViewById(R.id.Edit_Subject);
//        mStartDate = (EditText) findViewById(R.id.Edit_Startdate);
//        mEndDate = (EditText) findViewById(R.id.Edit_Enddate);
//        mDescription = (EditText) findViewById(R.id.Edit_Description);
        mSubmitButton = (Button) findViewById(R.id.submit_post);
//        image_pdf = (Button) findViewById(R.id.image_pdf);
////        mimageView = (ImageView) findViewById(R.id.image_pdf);
////        mimageView.setOnClickListener(onAvatarClick);
//        image_pdf.setOnClickListener(onAvatarClick);
//
//        SharedPreferenceHelper prefHelper = SharedPreferenceHelper.getInstance(NewAssignmentPost.this);
//        myAccount = prefHelper.getUserInfo();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    private void submitPost() {

        // Title is required

        if (("Select Quantity").equals(Rice)) {
            Rice = "0 Ton";
        } else {
            Rice = mRice.getSelectedItem().toString();
        }
        if (("Select Quantity").equals(ToorDal)) {
            ToorDal = "0 Ton";
        } else {
            ToorDal = mToorDal.getSelectedItem().toString();
        }
        if (("Select Quantity").equals(Wheat)) {
            Wheat = "0 Ton";
        } else {
            Wheat = mWheat.getSelectedItem().toString();
        }
        if (("Select Quantity").equals(Rajama)) {
            Rajama = "0 Ton";
        } else {
            Rajama = mRajama.getSelectedItem().toString();
        }
        if (("Select Quantity").equals(Choole)) {
            Choole = "0 Ton";
        } else {
            Choole = mChoole.getSelectedItem().toString();
        }
        if (("Select Quantity").equals(Martar)) {
            Martar = "0 Ton";
        } else {
            Martar = mMatar.getSelectedItem().toString();
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("user").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewOrderPost.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, Rice, ToorDal, Wheat, Rajama, Choole, Martar);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mRice.setEnabled(enabled);
        mMatar.setEnabled(enabled);
        mChoole.setEnabled(enabled);
        mRajama.setEnabled(enabled);
        mWheat.setEnabled(enabled);
        mToorDal.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    //
//    // [START write_fan_out]
    private void writeNewPost(String userId,String rice, String toorDal, String wheat, String rajama, String choole, String matar) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("Orders").push().getKey();
        Order order = new Order(rice, toorDal, wheat, rajama, choole, matar);
        Map<String, Object> postValues = order.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Orders/" + key, postValues);
        childUpdates.put("/user-Orders/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
