package com.example.anatkononovych.diagnose;

import android.content.SyncStatusObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by anatkononovych on 4/22/15.
 */
public class StaticPics {
    private ImageView dest1, dest2, dest3, dest4, frame;
    private Level level;
    private int[] picArray;
    private RelativeLayout.LayoutParams layoutParams1;
    private RelativeLayout.LayoutParams layoutParams2;
    private RelativeLayout.LayoutParams layoutParams3;
    private RelativeLayout.LayoutParams layoutParams4;
    private RelativeLayout.LayoutParams layoutParamsFrame;
    private static final int MAX_PIC_SIZE_WIDTH = 250;
    private static final int MAX_PIC_SIZE_HEIGHT = 200;
    private Animation animation;

    public StaticPics(int[] picArray, Level level) {
        this.picArray = picArray;
        this.level = level;
    }

    public void setOnScreen(){
        //get screen width and height
        DisplayMetrics displaymetrics = new DisplayMetrics();
        Level.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        //insert the photos to the imageViews
        insertPhotos(height, width);

        //set dragListeners to the pictures
        dest1.setOnDragListener(new DragListener(level));
        dest2.setOnDragListener(new DragListener(level));
        dest3.setOnDragListener(new DragListener(level));
        dest4.setOnDragListener(new DragListener(level));

        Level.getMainLayout().addView(dest1);
        Level.getMainLayout().addView(dest2);
        Level.getMainLayout().addView(dest3);
        Level.getMainLayout().addView(dest4);
        Level.getMainLayout().addView(frame);
    }



    //get random array
    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    //insert photos into the imageViews randomly
    public void insertPhotos(int height, int width){
        //int[] imageArray = {pics[0], pics[1], pics[2]};
        //shuffleArray(imageArray);

        int space = 20;

        frame = new ImageView(Level.getActivity());
        frame.setImageResource(R.drawable.frame);
        int frameWidth = MAX_PIC_SIZE_WIDTH*2+space*3;
        int frameHeight = MAX_PIC_SIZE_HEIGHT*2+space*3;
        int frameX = width/2-frameWidth/2-space*3/2;
        int frameY = height/2-frameHeight/2-MAX_PIC_SIZE_HEIGHT-space*3/2;

        layoutParamsFrame = new RelativeLayout.LayoutParams(frameWidth, frameHeight);
        frame.setLayoutParams(layoutParamsFrame);
        frame.setX(frameX+frame.getLeft());
        frame.setY(frameY+frame.getTop());
        frame.setId(R.id.frame);

        dest1 = new ImageView(Level.getActivity());
        dest1.setX(frameX+MAX_PIC_SIZE_WIDTH+space*3+frame.getLeft()+dest1.getLeft());
        dest1.setY(frameY+space*2+frame.getTop()+dest1.getTop());

        dest1.setId(R.id.dest1);

        dest2 = new ImageView(Level.getActivity());
        layoutParams2 = new RelativeLayout.LayoutParams(MAX_PIC_SIZE_WIDTH, MAX_PIC_SIZE_HEIGHT);
        dest2.setLayoutParams(layoutParams2);
        dest2.setX(frameX+space*2+dest2.getLeft()+frame.getLeft());
        dest2.setY(frameY+space*2+dest2.getTop()+frame.getTop());
        dest2.setId(R.id.dest2);

        dest3 = new ImageView(Level.getActivity());
        layoutParams3 = new RelativeLayout.LayoutParams(MAX_PIC_SIZE_WIDTH, MAX_PIC_SIZE_HEIGHT);
        dest3.setLayoutParams(layoutParams3);
        dest3.setX(frameX+MAX_PIC_SIZE_WIDTH+space*3+frame.getLeft()+dest3.getLeft());
        dest3.setY(frameY+MAX_PIC_SIZE_HEIGHT+space*2+frame.getTop()+dest3.getTop());
        dest3.setId(R.id.dest3);

        dest4 = new ImageView(Level.getActivity());
        layoutParams4 = new RelativeLayout.LayoutParams(MAX_PIC_SIZE_WIDTH, MAX_PIC_SIZE_HEIGHT);
        dest4.setLayoutParams(layoutParams4);
        dest4.setImageResource(R.drawable.rect);
        //layoutParams4 = new RelativeLayout.LayoutParams(MAX_PIC_SIZE_WIDTH, MAX_PIC_SIZE_HEIGHT);
        //dest4.setLayoutParams(layoutParams4);
        dest4.setX(frameX+space*2+dest4.getLeft()+frame.getLeft());
        dest4.setY(frameY+MAX_PIC_SIZE_HEIGHT+space*2+frame.getTop()+dest4.getTop());
        dest4.setId(R.id.dest4);

        // blinking rectangle
        animation = new AlphaAnimation((float)0.5, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        dest4.startAnimation(animation);
    }

    //insert photos into the imageViews randomly
    public void updatePhotos(int[] imageArray){
        //int[] imageArray = {pics[0], pics[1], pics[2]};
        //shuffleArray(imageArray);

        dest1.setImageResource(imageArray[0]);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(dest1.getResources(), imageArray[0], o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        layoutParams1 = new RelativeLayout.LayoutParams(w, h);
        dest1.setLayoutParams(layoutParams1);
        if(imageArray.length>=2){
            bmp = BitmapFactory.decodeResource(dest2.getResources(), imageArray[1], o);
            w = bmp.getWidth();
            h = bmp.getHeight();
            layoutParams2 = new RelativeLayout.LayoutParams(w, h);
            dest2.setLayoutParams(layoutParams2);
            dest2.setVisibility(View.VISIBLE);
            dest2.setImageResource(imageArray[1]);

        }else{
            dest2.setVisibility(View.INVISIBLE);
        }
        if(imageArray.length>=3){
            bmp = BitmapFactory.decodeResource(dest1.getResources(), imageArray[2], o);
            w = bmp.getWidth();
            h = bmp.getHeight();
            System.out.println(w);
            layoutParams3 = new RelativeLayout.LayoutParams(w, h);
            dest3.setLayoutParams(layoutParams3);
            dest3.setVisibility(View.VISIBLE);
            dest3.setImageResource(imageArray[2]);

            bmp = BitmapFactory.decodeResource(dest1.getResources(), imageArray[3], o);
            w = bmp.getWidth();
            h = bmp.getHeight();
            System.out.println(w);
            layoutParams4 = new RelativeLayout.LayoutParams(w, h);
            dest4.setLayoutParams(layoutParams4);
            dest4.setImageResource(imageArray[3]);

            if(imageArray[2]==R.drawable.rect){
                dest4.setId(R.id.dest3);
                dest3.setId(R.id.dest4);
                dest4.clearAnimation();
                dest3.startAnimation(animation);
            }else{
                dest4.setId(R.id.dest4);
                dest3.setId(R.id.dest3);
                dest4.startAnimation(animation);
                dest3.clearAnimation();
            }
        }else{
            dest3.setVisibility(View.INVISIBLE);
        }
    }
}


