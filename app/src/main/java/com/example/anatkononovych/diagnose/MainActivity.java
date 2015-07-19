package com.example.anatkononovych.diagnose;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class MainActivity extends ActionBarActivity {
    String userName = "";
    RelativeLayout mainLayout;
    Level level;
    Button btnNext;
    private static final int DISTRACTOR = 2;
    private static final int RIGHT_ANSWER = 1;
    private static final int WRONG_ANSWER = 0;

    int[][] staticPicsArray = {{R.drawable.triangledowngraysmall, R.drawable.triangleupyellowmedium, R.drawable.triangleleftbluemedium, R.drawable.rect},
            {R.drawable.hexagonbluesmall, R.drawable.starpurplesmall, R.drawable.arcredsmall, R.drawable.rect},
            {R.drawable.circlegraybig, R.drawable.squaregraybig, R.drawable.parallelrightgraybig, R.drawable.rect},
            {R.drawable.arrowuppurplesmall, R.drawable.arrowleftorangesmall, R.drawable.arrowrightyellowsmall, R.drawable.rect},
            {R.drawable.heartgraymedium, R.drawable.squaredarkgraybig, R.drawable.rect, R.drawable.arrowleftgraymedium},
            {R.drawable.arrowleftyellowmedium, R.drawable.arrowleftbluebig, R.drawable.arrowleftredmedium, R.drawable.rect},
            {R.drawable.arcgreenmedium, R.drawable.arrowleftgreenbig, R.drawable.circlegreenmedium, R.drawable.rect},
            {R.drawable.triangledownorangesmall, R.drawable.triangle90rightgreensmall, R.drawable.rect, R.drawable.triangleupbluesmall}};

    int[][][] dynamicPicsArray = {{{R.drawable.triangledowngreenmedium, RIGHT_ANSWER}, {R.drawable.starpurplemedium, WRONG_ANSWER}, {R.drawable.rectanglegraymedium, WRONG_ANSWER}, {R.drawable.trapezeyellowmedium, WRONG_ANSWER}, {R.drawable.hexagonbluemedium, WRONG_ANSWER}},
            {{R.drawable.triangleupgreensmall, RIGHT_ANSWER}, {R.drawable.starpurplebig, WRONG_ANSWER}, {R.drawable.trapezeyellowbig, WRONG_ANSWER}, {R.drawable.rectanglegraybig, WRONG_ANSWER}, {R.drawable.hexagonbluebig, WRONG_ANSWER}},
            {{R.drawable.triangleupgraybig, RIGHT_ANSWER}, {R.drawable.circlegreenbig, WRONG_ANSWER}, {R.drawable.parallelrightredsmall, WRONG_ANSWER}, {R.drawable.triangleupbluesmall, WRONG_ANSWER}, {R.drawable.squaregraysmall, WRONG_ANSWER}, {R.drawable.parallelrightbluebig, WRONG_ANSWER}},
            {{R.drawable.arrowdownbluesmall, RIGHT_ANSWER}, {R.drawable.circleorangemedium, WRONG_ANSWER}, {R.drawable.rectanglegraysmall, WRONG_ANSWER}, {R.drawable.heartpurplebig, WRONG_ANSWER}, {R.drawable.squareredmedium, WRONG_ANSWER}, {R.drawable.arrowrightorangebig, WRONG_ANSWER}, {R.drawable.arcyellowsmall, WRONG_ANSWER}, {R.drawable.arrowupbluebig, WRONG_ANSWER}},
            {{R.drawable.circledarkgraymedium, RIGHT_ANSWER}, {R.drawable.arcyellowmedium, WRONG_ANSWER}, {R.drawable.heartpurplemedium, WRONG_ANSWER}, {R.drawable.squareredbig, WRONG_ANSWER}, {R.drawable.arrowleftgreenmedium, WRONG_ANSWER}, {R.drawable.arcbluemedium, WRONG_ANSWER}},
            {{R.drawable.arrowleftgreenmedium, RIGHT_ANSWER}, {R.drawable.arrowrightbluemedium, WRONG_ANSWER}, {R.drawable.arrowdownyellowmedium, WRONG_ANSWER}, {R.drawable.arrowupredbig, WRONG_ANSWER}, {R.drawable.arrowrightgraybig, WRONG_ANSWER}, {R.drawable.arrowdowngreenbig, WRONG_ANSWER}},
            {{R.drawable.heartgreenmedium, RIGHT_ANSWER}, {R.drawable.circledarkgraymedium, WRONG_ANSWER}, {R.drawable.arrowleftbluesmall, WRONG_ANSWER}, {R.drawable.squareredmedium, WRONG_ANSWER}, {R.drawable.arrowleftorangemedium, WRONG_ANSWER}, {R.drawable.circlegreensmall, WRONG_ANSWER}, {R.drawable.arcyellowmedium, WRONG_ANSWER}},
            {{R.drawable.triangle90leftgraysmall, RIGHT_ANSWER}, {R.drawable.triangleupyellowmedium, WRONG_ANSWER}, {R.drawable.circleredsmall, WRONG_ANSWER}, {R.drawable.triangledownpurplebig, WRONG_ANSWER}, {R.drawable.squarebluebig, WRONG_ANSWER}, {R.drawable.squaregreensmall, WRONG_ANSWER}, {R.drawable.circleorangemedium, WRONG_ANSWER}}};

    // the positions of the dynamic items
    Point[][] positionArray = {{new Point(0, 400), new Point(0, 800), new Point(200, 1000), new Point(600, 1000), new Point(800, 400)},
            {new Point(800, 800), new Point(0, 400), new Point(200, 1000), new Point(600, 1000), new Point(800, 400)},
            {new Point(600, 1000), new Point(0, 400), new Point(0, 800), new Point(200, 1000), new Point(800, 800), new Point(780, 400)},
            {new Point(0, 800), new Point(0, 400), new Point(100, 1000), new Point(200, 1000), new Point(400, 1000), new Point(600, 1000), new Point(800, 800), new Point(800, 400)},
            {new Point(0, 800), new Point(0, 400), new Point(200, 1000), new Point(600, 1000), new Point(800, 800), new Point(800, 400)},
            {new Point(800, 800), new Point(0, 400), new Point(0, 800), new Point(200, 1000), new Point(600, 1000), new Point(800, 400)},
            {new Point(200, 1000), new Point(0, 400), new Point(0, 800), new Point(400, 1000), new Point(600, 1000), new Point(800, 400), new Point(800, 800)},
            {new Point(800, 400), new Point(0, 400), new Point(0, 800), new Point(200, 1000), new Point(400, 1000), new Point(600, 1000), new Point(800, 800)}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        btnNext = (Button) findViewById(R.id.btnNext);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnEnterUserName(View view) {
        // get the username
        EditText editText = (EditText) findViewById(R.id.txtUserName);
        userName = editText.getText().toString();
        //close keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        // delete the views of the opening screen
        mainLayout.removeView(editText);
        mainLayout.removeView((TextView) findViewById(R.id.userName));
        mainLayout.removeView((Button) findViewById(R.id.btn));
        // start game
        level = new Level(this, mainLayout, staticPicsArray, dynamicPicsArray, positionArray, 0, userName, this);
        level.addLevel();
        btnNext.setVisibility(View.VISIBLE);
    }

    public void btnNextClick(View view) {

        finishLevel();

        btnNext.setVisibility(View.INVISIBLE);

    }

    public void closeAllItems(){
        AnimatorSet set = new AnimatorSet();

        for (int i = 1; i < 5; i++) {
            int recID = getResources().getIdentifier("dest" + i, "id", getPackageName());
            View v = (View) findViewById(recID);
            ValueAnimator scaleDown1 = scale(v);
            set.play(scaleDown1);
            set.start();
        }

        View v1 = (View) findViewById(R.id.target);
        ValueAnimator scaleDown2 = scale(v1);
        set.play(scaleDown2);
        set.start();

        View v2 = (View) findViewById(R.id.frame);
        ValueAnimator scaleDown3 = scale(v2);
        set.play(scaleDown3);
        set.start();

        for (int i = 1; i < dynamicPicsArray[level.getLevelNum()].length; i++) {
            int recID = getResources().getIdentifier("src" + i, "id", getPackageName());
            View v = (View) findViewById(recID);
            ValueAnimator scaleDown1 = scale(v);
            set.play(scaleDown1);
            set.start();
        }
        moveToFragment();
    }

    private ValueAnimator scale(final View v) {
        ValueAnimator scaleDown = ValueAnimator.ofFloat(1, 0);
        scaleDown.setDuration(200);
        scaleDown.setInterpolator(new BounceInterpolator());
        scaleDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float newValue = (Float) valueAnimator.getAnimatedValue();
                v.setScaleY(newValue);
                v.setScaleX(newValue);
            }
        });
        return scaleDown;
    }

    public void finishLevel(){
        AnimatorSet set = new AnimatorSet();
        ValueAnimator flip = ValueAnimator.ofFloat(1, (float) 1.2);
        flip.setDuration(200);
        flip.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                View l = findViewById(R.id.mainLayout);
                Animation a = AnimationUtils.loadAnimation(
                        getBaseContext(), R.anim.fade_out);
                a.setDuration(200);
                l.startAnimation(a);

            }
        });

        ValueAnimator finish = ValueAnimator.ofFloat((float) 1.2, 1);
        finish.setDuration(200);
        finish.setInterpolator(new BounceInterpolator());
        finish.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {


            }
        });



        set.play(flip);
        set.play(finish).after(flip);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //level.clearLayout();
                level.clearLevelInfo();
                level.incLevelNum();
                level.addLevel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void moveToFragment(){
        Cursor cursor = level.db.getAllMotions();
        String userName1 = null;
        int levelNum = 0;
        int isOnTarget = 0;
        String distractor = "";
        long responseTime = 0;
        long performanceTime = 0;
        int numOfTouches = 0;
        Bundle bundle = new Bundle();
        int i = 0;
        while (cursor.moveToNext()) {
            userName1 = cursor.getString(0);
            levelNum = cursor.getInt(1);
            distractor = cursor.getString(2);
            responseTime = cursor.getLong(3);
            performanceTime = cursor.getLong(4);
            numOfTouches = cursor.getInt(5);
            isOnTarget = cursor.getInt(6);
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            bundle.putString("userName1"+i, userName1);
            bundle.putInt("levelNum"+i, levelNum);
            bundle.putString("distractor"+i, distractor);
            bundle.putLong("responseTime"+i, responseTime);
            bundle.putLong("performanceTime"+i, performanceTime);
            bundle.putInt("numOfTouches"+i, numOfTouches);
            bundle.putInt("isOnTarget"+i, isOnTarget);
            i++;
            /*Log.d("************", "userName: " + userName1 + ", levelNum: " + levelNum + ", screenAppearTime: " + formatter.format(dScreenAppearTime) +
                    ", pressedObject: " + pressedObject + ", pressTime: " + formatter.format(dPressTime) + ", releaseTime: " +
                    formatter.format(dReleaseTime) + ", isOnTarget: " + isOnTarget);*/
        }

        level.db.close();

// set Fragmentclass Arguments

        bundle.putInt("counter", i);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultsFragment newFragment = new ResultsFragment();
        newFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragmentContainer, newFragment, "HELLO");
        fragmentTransaction.commit();

    }
}
