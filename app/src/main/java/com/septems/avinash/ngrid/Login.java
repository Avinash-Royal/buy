package com.septems.avinash.ngrid;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.septems.avinash.ngrid.Messaging.data.SharedPreferenceHelper;
import com.septems.avinash.ngrid.Messaging.data.StaticConfig;
import com.septems.avinash.ngrid.utils.BaseActivity;
import com.septems.avinash.ngrid.utils.ShimmerFrameLayout;
import com.septems.avinash.ngrid.utils.materialAnim.AllAnimation;
import com.septems.avinash.ngrid.utils.materialAnim.AnimUtils;

import java.util.HashMap;

/**
 * Created by GAvinash on 7/28/2017.
 */

public class Login extends BaseActivity {

    private static final String TAG = "Login using Firebase";
    private String mUsername, mPassword;
    Animation custom, animation1, custom1;
    private FirebaseAuth mAuth;
    public static FirebaseUser User;
    private DatabaseReference mDatabase;
    private ShimmerFrameLayout mShimmerViewContainer;
    EditText user, pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        user.setText("avinash.royal888@gmail.com");
        pass.setText("Avinash$123");
        user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                user.setTextColor(Color.parseColor("#99fefefe"));
                if ("Username is Required".equals(user.getText().toString())) {
                    user.setText("");
                } else ;
                return false;
            }
        });
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                pass.setTextColor(Color.parseColor("#99fefefe"));
                if ("Password is Required".equals(pass.getText().toString())) {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setText("");
                }
                return false;
            }
        });

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        custom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.custom);
        custom1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.custom);
        user.setAnimation(custom);
        custom1.setStartOffset(200);
        pass.setAnimation(custom1);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        animation1.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(this));
        animation1.setStartOffset(400);
        findViewById(R.id.sign_in).setAnimation(animation1);

        final int PERMISSION_ALL = 1;
        final String[] PERMISSIONS = {
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (!hasPermissions(Login.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(Login.this, PERMISSIONS, PERMISSION_ALL);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getKeyboardVisibility();
            }
        });

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsername = user.getText().toString().trim();
                mPassword = pass.getText().toString().trim();
                signIn(mUsername, mPassword);
            }
        });

        findViewById(R.id.create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
//                overridePendingTransition(R.anim.push_out_left, R.anim.pull_in_right);
            }
        });
    }

    public boolean isKeyboardVisible = false, isClicked = false;

    public void getKeyboardVisibility() {
        this.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                Log.e("keyboard", "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    if (!isKeyboardVisible) {
//                        findViewById(R.id.layout_logo).setVisibility(View.GONE);
                    }
                    isKeyboardVisible = true;
                } else {
//                    findViewById(R.id.layout_logo).setVisibility(View.VISIBLE);
                    isKeyboardVisible = false;
                }
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length == 0
                        || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "permission Denied",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        findViewById(R.id.sign_in).setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        mShimmerViewContainer.setDuration(3000);
        mShimmerViewContainer.setAngle(ShimmerFrameLayout.MaskAngle.CW_0);
        mShimmerViewContainer.setRepeatMode(ObjectAnimator.INFINITE);
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            findViewById(R.id.sign_in).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in).setEnabled(true);
            return;
        }else{
            findViewById(R.id.sign_in).setEnabled(false);
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            User = mAuth.getCurrentUser();
                            if (User.isEmailVerified()) {
                                updateUI(User);
                                Intent intent = new Intent(Login.this, AllAnimation.class);
                                intent.putExtra("value", "hangout");
                                startActivityForResult(intent,99);
                                onAuthSuccess(task.getResult().getUser());
                                StaticConfig.UID = mAuth.getCurrentUser().getUid();
                                StaticConfig.signin = "true";
                                saveUserInfo();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        startActivity(i);
                                        finishActivity(99);
                                        findViewById(R.id.sign_in).setEnabled(true);
                                    }
                                }, 2000);
                            } else {
                                Toast.makeText(Login.this, "Email is not verified please verify it",
                                        Toast.LENGTH_SHORT).show();
                                findViewById(R.id.sign_in).setVisibility(View.VISIBLE);
                                findViewById(R.id.sign_in).setEnabled(true);
                            }
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed. Please check your 'Internet'",
                                    Toast.LENGTH_LONG).show();
                            findViewById(R.id.sign_in).setVisibility(View.VISIBLE);
                            findViewById(R.id.sign_in).setEnabled(true);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mUsername;
        if (TextUtils.isEmpty(email)) {
            user.setText("Username is Required");
            user.setTextColor(Color.RED);
            valid = false;
        } else ;

        String password = mPassword;
        if (TextUtils.isEmpty(password)) {
            pass.setText("Password is Required");
            pass.setTextColor(Color.RED);
            pass.setInputType(InputType.TYPE_CLASS_TEXT);
            valid = false;
        } else ;
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//            user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
//            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        mShimmerViewContainer.useDefaults();
        super.onStop();
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), username, user.getEmail());
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    @Override
    protected void onDestroy() {
        signOut();
        super.onDestroy();
    }

    private void writeNewUser(String userId, String name, String email) {
        com.septems.avinash.ngrid.NewsFeed.models.User user = new com.septems.avinash.ngrid.NewsFeed.models.User(name, email);
        mDatabase.child("user").child(userId).child("username").setValue(user.username);
    }

    void saveUserInfo() {
        FirebaseDatabase.getInstance().getReference().child("user/" + StaticConfig.UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashUser = (HashMap) dataSnapshot.getValue();
                com.septems.avinash.ngrid.Messaging.model.User userInfo = new com.septems.avinash.ngrid.Messaging.model.User();
                userInfo.name = (String) hashUser.get("name");
                userInfo.email = (String) hashUser.get("email");
                userInfo.avata = (String) hashUser.get("avata");
                userInfo.uid = (String) hashUser.get("uid");
                SharedPreferenceHelper.getInstance(Login.this).saveUserInfo(userInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}



