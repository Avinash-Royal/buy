package com.septems.avinash.ngrid.utils.materialAnim;

/**
 * Created by GAvinash on 8/4/2017.
 */

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.septems.avinash.ngrid.R;


public class AllAnimation extends Activity {

    private TextView leftRightTvOne;
    private TextView leftRightTvTwo;
    private TextView leftRightTvThree;
    private int screenWidth;

    private AnimatorSet animatorSet;
    private AnimatorSet animatorSet1;
    private ObjectAnimator waveOneAnimator;
    private ObjectAnimator waveTwoAnimator;
    private ObjectAnimator waveThreeAnimator;

    private TextView hangoutTvOne;
    private TextView hangoutTvTwo;
    private TextView hangoutTvThree;
    private TextView hangoutTvfour;
    private TextView hangoutTvfive;
    private TextView windowsTvOne;
    private TextView windowsTvThree;
    private TextView windowsTvTwo;
    private AnimatorSet windowsAnimatorSet;

    private AnimationDrawable myAnimationDrawable1;
    private ImageView fourSquare;
    private ImageView incrementingBoxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_animation_layout);

        //********* hangout ************
        hangoutTvOne = findViewById(R.id.hangoutTvOne);
        hangoutTvTwo = findViewById(R.id.hangoutTvTwo);
        hangoutTvThree = findViewById(R.id.hangoutTvThree);
        hangoutTvfour = findViewById(R.id.hangoutTvfour);
        hangoutTvfive = findViewById(R.id.hangoutTvfive);

        hangoutTvOne.setVisibility(View.GONE);
        hangoutTvTwo.setVisibility(View.GONE);
        hangoutTvThree.setVisibility(View.GONE);
        hangoutTvfour.setVisibility(View.GONE);
        hangoutTvfive.setVisibility(View.GONE);

        //********* left-right **********
//        leftRightTvOne = (TextView) findViewById(R.id.leftRightTvOne);
//        leftRightTvTwo = (TextView) findViewById(R.id.leftRightTvTwo);
//        leftRightTvThree = (TextView) findViewById(R.id.leftRightTvThree);

//        leftRightTvOne.setVisibility(View.GONE);
//        leftRightTvTwo.setVisibility(View.GONE);
//        leftRightTvThree.setVisibility(View.GONE);

        //********* windows *************
//        windowsTvOne = (TextView) findViewById(R.id.windowsTvOne);
//        windowsTvTwo = (TextView) findViewById(R.id.windowsTvTwo);
//        windowsTvThree = (TextView) findViewById(R.id.windowsTvThree);

//        windowsTvOne.setVisibility(View.GONE);
//        windowsTvTwo.setVisibility(View.GONE);
//        windowsTvThree.setVisibility(View.GONE);

        //********* four square ****************

//        fourSquare = (ImageView) findViewById(R.id.fourSquare);
//        fourSquare.setVisibility(View.GONE);

        //********* box incrementing ***********

//        incrementingBoxView = (ImageView) findViewById(R.id.incrementingBoxView);
//        incrementingBoxView.setVisibility(View.GONE);

        //--------------------------------------

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;

        if (getIntent().getExtras() != null) {
            String value = getIntent().getStringExtra("value");
            if (value.equalsIgnoreCase("windows")) {

                windowsAnimation();
            } else if (value.equalsIgnoreCase("hangout")) {



                waveAnimation();
            }
//            else if (value.equalsIgnoreCase("leftright")) {
//                leftRightTvOne.setVisibility(View.VISIBLE);
//                leftRightTvTwo.setVisibility(View.VISIBLE);
//                leftRightTvThree.setVisibility(View.VISIBLE);
//
//                leftToRightToLeftMove();
//            } else if (value.equalsIgnoreCase("foursquare")) {
//                fourSquare.setVisibility(View.VISIBLE);
//                fourSquareAnimation();
//            } else if (value.equalsIgnoreCase("incrementingloading")) {
//                incrementingBoxView.setVisibility(View.VISIBLE);
//                incrementalHorizontalLoading();
//            }
        }
		/*
		 * waveAnimation();
		 * leftToRightToLeftMove();
		 * windowsAnimation();
		 * fourSquareAnimation();
		 */
    }

    public void waveAnimation() {
        hangoutTvOne.setVisibility(View.VISIBLE);
        PropertyValuesHolder tvOne_Y = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvOne_X = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
        waveOneAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvOne, tvOne_X, tvOne_Y);
        waveOneAnimator.setRepeatCount(-1);
        waveOneAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveOneAnimator.setDuration(300);
        waveOneAnimator.start();

        hangoutTvTwo.setVisibility(View.VISIBLE);
        PropertyValuesHolder tvTwo_Y = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvTwo_X = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
        waveTwoAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvTwo, tvTwo_X, tvTwo_Y);
        waveTwoAnimator.setRepeatCount(-1);
        waveTwoAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveTwoAnimator.setDuration(300);
        waveTwoAnimator.setStartDelay(100);
        waveTwoAnimator.start();

        hangoutTvThree.setVisibility(View.VISIBLE);
        PropertyValuesHolder tvThree_Y = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvThree_X = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
        waveThreeAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvThree, tvThree_X, tvThree_Y);
        waveThreeAnimator.setRepeatCount(-1);
        waveThreeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveThreeAnimator.setDuration(300);
        waveThreeAnimator.setStartDelay(200);
        waveThreeAnimator.start();

        hangoutTvfour.setVisibility(View.VISIBLE);
        PropertyValuesHolder tvfour_Y = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvfour_X = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
        waveThreeAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvfour, tvfour_X, tvfour_Y);
        waveThreeAnimator.setRepeatCount(-1);
        waveThreeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveThreeAnimator.setDuration(300);
        waveThreeAnimator.setStartDelay(300);
        waveThreeAnimator.start();

        hangoutTvfive.setVisibility(View.VISIBLE);
        PropertyValuesHolder tvfive_Y = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvfive_X = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
        waveThreeAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvfive, tvfive_X, tvfive_Y);
        waveThreeAnimator.setRepeatCount(-1);
        waveThreeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveThreeAnimator.setDuration(300);
        waveThreeAnimator.setStartDelay(400);
        waveThreeAnimator.start();
    }

    public void leftToRightToLeftMove() {

        final ValueAnimator valueTvOne_x = ObjectAnimator.ofFloat(leftRightTvOne, "x", leftRightTvOne.getX(), screenWidth * .9f);

        valueTvOne_x.setDuration(1000);
        valueTvOne_x.setRepeatCount(0);
        valueTvOne_x.setRepeatMode(ValueAnimator.REVERSE);
        valueTvOne_x.setInterpolator(new DecelerateInterpolator());

        final ValueAnimator valueTvTwo_x = ObjectAnimator.ofFloat(leftRightTvTwo, "x", leftRightTvTwo.getX(), screenWidth * .93f);//- 35);

        valueTvTwo_x.setDuration(1000);
        valueTvTwo_x.setRepeatCount(0);
        valueTvTwo_x.setRepeatMode(ValueAnimator.REVERSE);
        valueTvTwo_x.setInterpolator(new DecelerateInterpolator());

        final ValueAnimator valueTvThree_x = ObjectAnimator.ofFloat(leftRightTvThree, "x", leftRightTvThree.getX(), screenWidth * .96f);// - 20);

        valueTvThree_x.setDuration(1000);
        valueTvThree_x.setRepeatCount(0);
        valueTvThree_x.setRepeatMode(ValueAnimator.REVERSE);
        valueTvThree_x.setInterpolator(new DecelerateInterpolator());

        animatorSet1 = new AnimatorSet();
        animatorSet1.playSequentially(valueTvOne_x, valueTvTwo_x, valueTvThree_x);

        animatorSet1.start();
        animatorSet1.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ValueAnimator valueTvOne_x = ObjectAnimator.ofFloat(leftRightTvOne, "x", leftRightTvOne.getX(), leftRightTvOne.getX() * .01f);

                valueTvOne_x.setDuration(1000);
                valueTvOne_x.setRepeatCount(0);
                valueTvOne_x.setRepeatMode(ValueAnimator.REVERSE);
                valueTvOne_x.setInterpolator(new DecelerateInterpolator());

                final ValueAnimator valueTvTwo_x = ObjectAnimator.ofFloat(leftRightTvTwo, "x", leftRightTvTwo.getX(), leftRightTvTwo.getX() * .03f);

                valueTvTwo_x.setDuration(1000);
                valueTvTwo_x.setRepeatCount(0);
                valueTvTwo_x.setRepeatMode(ValueAnimator.REVERSE);
                valueTvTwo_x.setInterpolator(new DecelerateInterpolator());

                final ValueAnimator valueTvThree_x = ObjectAnimator.ofFloat(leftRightTvThree, "x", leftRightTvThree.getX(), leftRightTvThree.getX() * .06f);

                valueTvThree_x.setDuration(1000);
                valueTvThree_x.setRepeatCount(0);
                valueTvThree_x.setRepeatMode(ValueAnimator.REVERSE);
                valueTvThree_x.setInterpolator(new DecelerateInterpolator());

                animatorSet = new AnimatorSet();
                animatorSet.playSequentially(valueTvThree_x, valueTvTwo_x, valueTvOne_x);

                animatorSet.start();
                animatorSet.addListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorSet1.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

    }

    public void windowsAnimation() {

        final ValueAnimator valueTvOne_x = ObjectAnimator.ofFloat(windowsTvOne, "x", windowsTvOne.getX() - 40, windowsTvOne.getX() - 50, (screenWidth / 2) + 10f, (screenWidth / 2) + 25f,
                (screenWidth / 2) + 50f//,(screenWidth / 2)+55f//,(screenWidth / 2)+80f//,(screenWidth/2 )+25f,(screenWidth / 2) +30f//, (screenWidth / 2)+35f,(screenWidth / 2)+40f,(screenWidth / 2)+45f //,(screenWidth / 2)+6.6f,(screenWidth / 2)+7.7f,(screenWidth / 2)+8.8f //, (screenWidth / 2)+9,(screenWidth / 2)+10,
                , screenWidth * .92f, screenWidth + 5);

        valueTvOne_x.setDuration(5200);
        valueTvOne_x.setRepeatCount(0);
        valueTvOne_x.setRepeatMode(ValueAnimator.REVERSE);

        valueTvOne_x.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                windowsTvOne.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        final ValueAnimator valueTvTwo_x = ObjectAnimator.ofFloat(windowsTvTwo, "x", windowsTvTwo.getX() - 50, (screenWidth / 2.1f) + 10f, (screenWidth / 2.1f) + 25f, (screenWidth / 2.1f) + 50f//, (screenWidth / 2.1f) +55f//, (screenWidth / 2.1f) +80f//, (screenWidth / 2.1f) +25f,(screenWidth / 2.1f) +30f
                , screenWidth * .94f, screenWidth + 5);

        valueTvTwo_x.setDuration(6000);
        valueTvTwo_x.setRepeatCount(0);
        valueTvTwo_x.setStartDelay(200);
        valueTvTwo_x.setRepeatMode(ValueAnimator.REVERSE);

        valueTvTwo_x.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                windowsTvTwo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        final ValueAnimator valueTvThree_x = ObjectAnimator.ofFloat(windowsTvThree, "x", windowsTvThree.getX() - 50, (screenWidth / 2.2f) + 10f, (screenWidth / 2.2f) + 25f, (screenWidth / 2.2f) + 50f//,(screenWidth / 2.2f) +55f//,(screenWidth / 2.2f) +80f//,(screenWidth / 2.2f) +25f,(screenWidth / 2.2f) +30f
                , screenWidth * .94f, screenWidth + 5);

        valueTvThree_x.setDuration(6500);
        valueTvThree_x.setRepeatCount(0);
        valueTvTwo_x.setStartDelay(500);
        valueTvThree_x.setRepeatMode(ValueAnimator.REVERSE);

        valueTvThree_x.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                windowsTvThree.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        windowsAnimatorSet = new AnimatorSet();
        windowsAnimatorSet.playTogether(valueTvTwo_x, valueTvThree_x, valueTvOne_x);

        windowsAnimatorSet.start();
        windowsAnimatorSet.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                windowsAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

    }

    public void fourSquareAnimation() {
        myAnimationDrawable1 = (AnimationDrawable) fourSquare.getDrawable();

        myAnimationDrawable1.start();
    }

    public void incrementalHorizontalLoading() {
        myAnimationDrawable1 = (AnimationDrawable) incrementingBoxView.getDrawable();

        myAnimationDrawable1.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (animatorSet1 != null) {
            animatorSet1.cancel();
            animatorSet1.removeAllListeners();
        }

        if (windowsAnimatorSet != null) {
            windowsAnimatorSet.cancel();
            windowsAnimatorSet.removeAllListeners();
        }

        if (waveOneAnimator != null && waveTwoAnimator != null && waveThreeAnimator != null) {
            waveOneAnimator.cancel();
            waveOneAnimator.removeAllListeners();

            waveTwoAnimator.cancel();
            waveTwoAnimator.removeAllListeners();

            waveThreeAnimator.cancel();
            waveThreeAnimator.removeAllListeners();
        }

        if (myAnimationDrawable1 != null) {
            myAnimationDrawable1.stop();
        }

    }

    @Override
    public void onBackPressed() {
        onDestroy();
        super.onBackPressed();
    }
}


