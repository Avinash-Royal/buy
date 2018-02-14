package com.septems.avinash.ngrid.Assignment;

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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
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
import com.septems.avinash.ngrid.Assignment.models.Assignment;
import com.septems.avinash.ngrid.Assignment.models.User;
import com.septems.avinash.ngrid.Messaging.util.ImageUtils;
import com.septems.avinash.ngrid.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NewAssignmentPost extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    final static int PICK_PDF_CODE = 2342;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private static final int PICK_IMAGE = 1994;

    // [END declare_database_ref]

    private EditText mSubject, mStartDate, mEndDate, mDescription;
    //    private ImageView mimageView;
    public String pdf;
    //    private FloatingActionButton mSubmitButton;
    private Button mSubmitButton, image_pdf;

    //Animation Attributes
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;
    private int revealX;
    private int revealY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_new_post);

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

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mSubject = (EditText) findViewById(R.id.Edit_Subject);
        mStartDate = (EditText) findViewById(R.id.Edit_Startdate);
        mEndDate = (EditText) findViewById(R.id.Edit_Enddate);
        mDescription = (EditText) findViewById(R.id.Edit_Description);
//        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_new_post);
        mSubmitButton = (Button) findViewById(R.id.submit_post);
        image_pdf = (Button) findViewById(R.id.image_pdf);
//        mimageView = (ImageView) findViewById(R.id.image_pdf);
//        mimageView.setOnClickListener(onAvatarClick);
        image_pdf.setOnClickListener(onAvatarClick);

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
        final String Subject = mSubject.getText().toString();
        final String StartDate = mStartDate.getText().toString();
        final String EndDate = mEndDate.getText().toString();
        final String Description = mDescription.getText().toString();
        final String pdf = this.pdf;
//        final String authimage = myAccount.avata;

        // Title is required
        if (TextUtils.isEmpty(Subject)) {
            mSubject.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(StartDate)) {
            mStartDate.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(EndDate)) {
            mEndDate.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(Description)) {
            mDescription.setError(REQUIRED);
            return;
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
                            Toast.makeText(NewAssignmentPost.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, Subject, StartDate, EndDate, Description, pdf);
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
        mSubject.setEnabled(enabled);
        mStartDate.setEnabled(enabled);
        mEndDate.setEnabled(enabled);
        mDescription.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String subject, String startdate, String enddate, String description, String pdf) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("Assignments").push().getKey();
        Assignment assignment = new Assignment(userId, subject, startdate, enddate, description, pdf);
        Map<String, Object> postValues = assignment.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Assignments/" + key, postValues);
        childUpdates.put("/user-Assignments/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    private View.OnClickListener onAvatarClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            new AlertDialog.Builder(NewAssignmentPost.this)
                    .setTitle("Upload Image")
                    .setMessage("Are you sure want to Upload Image")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_PICK);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
//            getPDF();
        }
    };

    //
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(NewAssignmentPost.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                InputStream inputStream = NewAssignmentPost.this.getContentResolver().openInputStream(data.getData());

                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
//                imgBitmap = ImageUtils.cropToSquare(imgBitmap);
//                InputStream is = ImageUtils.convertBitmapToInputStream(imgBitmap);
//                final Bitmap liteImage = ImageUtils.makeImageLite(is,
//                        imgBitmap.getWidth(), imgBitmap.getHeight(),
//                        ImageUtils.AVATAR_WIDTH, ImageUtils.AVATAR_HEIGHT);

                String imageBase64 = ImageUtils.encodeBase64(imgBitmap);
                pdf = imageBase64;
                setImageAvatar(NewAssignmentPost.this,pdf);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(Uri data) {
        StorageReference sRef = mStorage.child("Assignments").child(mSubject.getText().toString() + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(NewAssignmentPost.this, "Uploaded Successfully", Toast.LENGTH_LONG);
                        pdf = taskSnapshot.getDownloadUrl().toString();
//                        mDatabase.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void setImageAvatar(Context context, String imgBase64){
        try {
            Resources res = getResources();
            Bitmap src;
            if (imgBase64.equals("default")) {
                src = BitmapFactory.decodeResource(res, R.drawable.default_avata);
            } else {
                byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
//            mimageView.setImageDrawable(ImageUtils.roundedImage(context, src));
        }catch (Exception e){
        }
    }
}
