package com.example.anatkononovych.diagnose;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by anatkononovych on 4/22/15.
 */
public class Level {
    private static Activity activity;
    private static RelativeLayout mainLayout;
    private int[][] staticPicsArray;
    private int[][][] dynamicPicsArray;
    private int levelNum;
    private Point[][] positionsArray;
    private static int maxLevelNum;
    private static LevelInfo levelInfo;
    public static DBAdapter db;
    private StaticPics pic = null;
    private DynamicPics[] dynamicArray = null;
    private int screenHeight;
    private int screenWidth;
    private MainActivity mainActivity;
    private static final int STANDARD_WIDTH = 1080;
    private static final int STANDARD_HEIGHT = 1776;

    public Level(Activity activity, RelativeLayout mainLayout, int[][] staticPicsArray, int[][][] dynamicPicsArray, Point[][] positionsArray, int levelNum, String userName, MainActivity mainActivity) {
        this.activity = activity;
        this.mainLayout = mainLayout;
        this.staticPicsArray = staticPicsArray;
        this.dynamicPicsArray = dynamicPicsArray;
        this.positionsArray = positionsArray;
        this.levelNum = levelNum;
        this.levelInfo = new LevelInfo();
        this.maxLevelNum = staticPicsArray.length;
        this.levelInfo.setUserName(userName);
        this.db = new DBAdapter(activity);
        this.mainActivity = mainActivity;
        db.open();
        db.deleteAllMotions();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        Level.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        ImageView backgroundImage = (ImageView)activity.findViewById(R.id.backgroundImage);
        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (levelInfo.getNumOfTouches() == 0) {
                    levelInfo.setResponseTime(System.currentTimeMillis());
                }
                levelInfo.setNumOfTouches(levelInfo.getNumOfTouches() + 1);
            }
        });


    }

    public static Activity getActivity() {
        return activity;
    }

    public static RelativeLayout getMainLayout() {
        return mainLayout;
    }

    public static int getMaxLevelNum() {
        return maxLevelNum;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void incLevelNum() {
        levelNum++;
    }

    public static LevelInfo getLevelInfo() {
        return levelInfo;
    }

    // add new level until the game ends
    public void addLevel() {
        levelInfo.setLevelNum(this.levelNum);

        if (this.levelNum == 0) {
            //create the static pics
            pic = new StaticPics(staticPicsArray[levelNum], this);
            pic.setOnScreen();

            mainLayout.setOnDragListener(new DragListener(this));

        }
        //update the pictures
        pic.updatePhotos(staticPicsArray[levelNum]);


        int max = maxDynamicPicsNum();

        if (this.levelNum == 0) {
            dynamicArray = new DynamicPics[max];
            for (int i = 0; i < max; i++) {
                dynamicArray[i] = new DynamicPics();
                if (i == 0) {
                    dynamicArray[i].addNewDynamicPic(R.id.target);
                } else {
                    dynamicArray[i].addNewDynamicPic(activity.getResources().getIdentifier("src" + i, "id", activity.getPackageName()));
                }

            }
        }

        for (int i = 0; i < dynamicPicsArray[levelNum].length; i++) {
            positionsArray[levelNum][i].x = Math.round((positionsArray[levelNum][i].x*screenWidth)/(float) STANDARD_WIDTH);
            positionsArray[levelNum][i].y = Math.round((positionsArray[levelNum][i].y*screenHeight)/(float) STANDARD_HEIGHT);
            dynamicArray[i].insertPhoto(positionsArray[levelNum][i].x, positionsArray[levelNum][i].y, dynamicPicsArray[levelNum][i][0]);

        }
        for (int i = dynamicPicsArray[levelNum].length; i < max; i++) {
            dynamicArray[i].setInvisible(activity.getResources().getIdentifier("src" + i, "id", activity.getPackageName()));
        }

        levelInfo.setScreenAppearTime(System.currentTimeMillis());
    }

    private int maxDynamicPicsNum() {
        int max = 0;
        for (int i = 0; i < dynamicPicsArray.length; i++) {
            if (dynamicPicsArray[i].length > max)
                max = dynamicPicsArray[i].length;
        }
        return max;
    }

    public void clearLevelInfo() {
        levelInfo.setLevelNum(-1);
        levelInfo.setScreenAppearTime(0);
        levelInfo.setDistractor(-1);
        levelInfo.setNumOfTouches(0);
        levelInfo.setPerformanceTime(0);
        //levelInfo.setTimeOfFirstTouch(0);
        levelInfo.setResponseTime(0);
    }

   /* public void clearLayout() {
        clearLevelInfo();

        View v1 = (View) activity.findViewById(R.id.target);
        mainLayout.removeView(v1);

        for (int i = 1; i < dynamicPicsArray[levelNum].length; i++) {
            int recID = activity.getResources().getIdentifier("src" + i, "id", activity.getPackageName());
            View v = (View) activity.findViewById(recID);
            mainLayout.removeView(v);
        }
    }*/

    public void closeAllItems(View dragView, View view) {
        dragView.setX(view.getX() + view.getWidth() / 2 - dragView.getWidth() / 2);
        dragView.setY(view.getY() + view.getHeight() / 2 - dragView.getHeight() / 2);
        dragView.setVisibility(View.VISIBLE);

        if(levelNum>1)
            mainActivity.closeAllItems();
    }

    public void finishLevel(final View dragView, View v) {
        if(isRightAnswer(dragView)==1){
            Level.getLevelInfo().setOnTarget(true);
        }else{
            Level.getLevelInfo().setOnTarget(false);
        }
        getLevelInfo().setDistractor(isRightAnswer(dragView));
        getLevelInfo().setPerformanceTime(System.currentTimeMillis());
        insertInfoToDB();

        final float prevX = dragView.getX();
        final float prevY = dragView.getY();
        dragView.setX(v.getX()+v.getWidth()/2-dragView.getWidth()/2);
        dragView.setY(v.getY()+v.getHeight()/2-dragView.getHeight()/2);
        dragView.setVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(dragView, "alpha", 0.0f);
        alpha1.setDuration(1000);


        set.play(alpha1);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //clearLayout();
                //clearLevelInfo();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(levelNum==0){
                    dragView.setX(prevX);
                    dragView.setY(prevY);


                }
                if(levelNum>=1)
                    mainActivity.finishLevel();
                dragView.setAlpha(1);
                //levelNum++;
                //addLevel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    public void viewBackToStartingPoint(View dragView, float xPos, float yPos) {
        dragView.setX(xPos);
        dragView.setY(yPos);
        dragView.setVisibility(View.VISIBLE);
        Animation animation = new TranslateAnimation(0, -xPos + dragView.getLeft() + getCurrX(dragView),
                0, -yPos + dragView.getTop() + getCurrY(dragView));
        animation.setDuration(200);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener(dragView));
        dragView.startAnimation(animation);
    }

    private class MyAnimationListener implements Animation.AnimationListener {

        private View img;

        public MyAnimationListener(View img) {
            this.img = img;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            img.clearAnimation();
            img.setX(getCurrX(img) + img.getLeft());
            img.setY(getCurrY(img) + img.getTop());
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    }

    private int getCurrX(View v) {
        int i;
        for (i = 0; i < dynamicPicsArray[levelNum].length; i++) {
            if (dynamicPicsArray[levelNum][i][0] == (int) v.getTag())
                break;
        }

        return positionsArray[levelNum][i].x;
    }


    private int getCurrY(View v) {
        int i;
        for (i = 0; i < dynamicPicsArray[levelNum].length; i++) {
            if (dynamicPicsArray[levelNum][i][0] == (int) v.getTag())
                break;
        }

        return positionsArray[levelNum][i].y;
    }

    private int isRightAnswer(View v) {
        int i;
        for (i = 0; i < dynamicPicsArray[levelNum].length; i++) {
            if (dynamicPicsArray[levelNum][i][0] == (int) v.getTag())
                break;
        }

        return dynamicPicsArray[levelNum][i][1];
    }

    public static void insertInfoToDB() {
        String distractor = "non";
        if(levelInfo.getDistractor()==2){
            distractor = "true";
        }
        long result = db.insertMotion(levelInfo.getUserName(), levelInfo.getLevelNum(), distractor, levelInfo.getPerformanceTime(),
                levelInfo.getResponseTime(), levelInfo.getNumOfTouches(), levelInfo.isOnTarget() ? 1 : 0);
        System.out.println("insert: "+result);
    }
}

