package com.septems.avinash.ngrid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by sucharithanalla on 03-09-2017.
 */

public class FeedbackActivity extends AppCompatActivity {
    RatingBar ratingbar1;
    Button button;
    TextView tv;
    ImageView iv;
    LayerDrawable stars;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        RatingBar ratingBar1 = findViewById(R.id.ratingBar1);
        tv = findViewById(R.id.text);
        iv = findViewById(R.id.star);

        stars = (LayerDrawable) ratingBar1.getProgressDrawable();

        stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        tv.setTextSize(20);
        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                tv.setText(String.format("%2.1f", rating));
                int value = (int) (rating) - 1;
                tv.setText(String.valueOf(value));

                tv.setText(String.format("Simply Amazing   " + rating));
                stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                iv.setImageResource(R.drawable.fun);

                if (value < 2) {
                    tv.setText(String.format("Not so Good    " + rating));
                    stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    iv.setImageResource(R.drawable.sad);
                } else if (value == 3) {
                    tv.setText(String.format("Avarage    " + rating));
                    stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    iv.setImageResource(R.drawable.fun);
                }
            }
        });

        ((ImageView)findViewById(R.id.back)).setImageBitmap(blur(BitmapFactory.decodeResource(getResources(),R.drawable.fdback)));
    }

    //Set the radius of the Blur. Supported range 0 < radius <= 25
    private static final float BLUR_RADIUS = 25f;

    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}
