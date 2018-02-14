package com.septems.avinash.ngrid.Assignment.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.septems.avinash.ngrid.Assignment.AssignmentPost;
import com.septems.avinash.ngrid.Assignment.AssignmentViewHolder;
import com.septems.avinash.ngrid.Assignment.models.Assignment;
import com.septems.avinash.ngrid.Messaging.data.StaticConfig;
import com.septems.avinash.ngrid.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public abstract class AssignmentListFragment extends Fragment {

    private static final String TAG = "OrderListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    private DatabaseReference mAssignmentReference;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Assignment, AssignmentViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    String image_string = null;
    Bitmap image = null;
    ImageView img = null;
    View rootView;

    public AssignmentListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.assignment_layout, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.Assignment_list);
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(null);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Assignment, AssignmentViewHolder>(Assignment.class, R.layout.item_assignment,
                AssignmentViewHolder.class, postsQuery) {

            @Override
            protected void populateViewHolder(final AssignmentViewHolder viewHolder, final Assignment model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                mAssignmentReference = FirebaseDatabase.getInstance().getReference().child("Assignments").child(postKey).child("pdf");

//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new AlertDialog.Builder(getActivity())
//                                .setTitle("Download")
//                                .setMessage("Are you sure want to Download ?")
//                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        if (postKey == null) {
//                                            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
//                                        }
//                                        // Initialize Database
//                                        mAssignmentReference = FirebaseDatabase.getInstance().getReference().child("Assignments").child(postKey);
//                                        mAssignmentReference.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                Assignment assignment = dataSnapshot.getValue(Assignment.class);
//                                                // [START_EXCLUDE]
////                                                if (assignment.pdf == null) {
////                                                } else {
////                                                    if (!assignment.pdf.equals(StaticConfig.STR_DEFAULT_BASE64)) {
////                                                        byte[] decodedString = Base64.decode(assignment.pdf, Base64.DEFAULT);
////                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
////                                                        String url = "url you want to download";
////                                                        request.setDescription("Some descrition");
////                                                        request.setTitle("Some title");
////// in order for this if to run, you must use the android 3.2 to compile your app
////                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////                                                            request.allowScanningByMediaScanner();
////                                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
////                                                        }
////                                                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, model.Subject + ".ext");
////
////// get download service and enqueue file
////                                                        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
////                                                        manager.enqueue(request);
////                                                    }
////                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//                                            }
//                                        });
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                }).show();
//                    }
//                });

//                mAssignmentReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        image_string = (String) snapshot.getValue();
//                        byte[] decodedString = Base64.decode(image_string, Base64.URL_SAFE);
//                        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//
                viewHolder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String decoded_string = viewHolder.string.getText().toString();
                        byte[] decodedString = Base64.decode(decoded_string, Base64.DEFAULT);
                        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        try {
                            saveImageToSDCard(image);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String decoded_string = viewHolder.string.getText().toString();
                        byte[] decodedString = Base64.decode(decoded_string, Base64.DEFAULT);
                        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        img = (ImageView) rootView.findViewById(R.id.view_image);
                        img.setVisibility(View.VISIBLE);
                        AssignmentPost.view.findViewById(R.id.fab_new_post).setVisibility(View.GONE);
                        img.setImageDrawable(new BitmapDrawable(image));
                    }
                });

                if (model.Subject != null) {
//                    Toast.makeText(getActivity(),"is not null",Toast.LENGTH_SHORT).show();
                    viewHolder.Assigment.setText(model.Subject);
                } else {
//                    Toast.makeText(getActivity(),"is null",Toast.LENGTH_SHORT).show();
                    viewHolder.Assigment.setText("textnull");
                }

                if (model.StartDate != null) {
                    viewHolder.StartDate.setText(model.StartDate);
                } else {
                    Toast.makeText(getActivity(), "is null", Toast.LENGTH_SHORT).show();
                    viewHolder.StartDate.setText("");
                }

                if (model.EndDate != null) {
                    viewHolder.EndDate.setText(model.EndDate);
                } else {
                    viewHolder.EndDate.setText("");
                }

                if (model.Description != null) {
                    viewHolder.Description.setText(model.Description);
                } else {
                    viewHolder.Description.setText("");
                }

                if (model.pdf == null) {
                    viewHolder.linearLayout.setVisibility(View.GONE);
                } else {
                    if (!model.pdf.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                        viewHolder.linearLayout.setVisibility(View.VISIBLE);
                        viewHolder.imagename.setText(model.Subject.concat(".file"));
                    }
                }
                viewHolder.bindToPost(model);
                // Determine if the current user has liked this post and set UI accordingly
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

    private void saveImageToSDCard(Bitmap bitmap) throws IOException {
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        sdCardDirectory = new File(sdCardDirectory.getAbsolutePath() + "/Septem/Assignment/");
        sdCardDirectory.mkdirs();

        File image = new File(sdCardDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
        if (image.exists()) {
            image.delete();
        }
        boolean success = false;

        FileOutputStream outStream;
        try {
        outStream = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        success = true;
        final File finalSdCardDirectory = sdCardDirectory;
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "Image Saved to "
                        + finalSdCardDirectory.getAbsolutePath() + "/Septem/Assignment/", Toast.LENGTH_LONG).show();
            }
        });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (success == false && getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), "Unable to Save the Image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    if(img!=null && img.getVisibility()==View.VISIBLE){
                        img.setVisibility(View.GONE);
                        AssignmentPost.view.findViewById(R.id.fab_new_post).setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });
    }

}

