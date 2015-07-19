package com.example.anatkononovych.diagnose;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * Created by anatkononovych on 4/22/15.
 */
public class DynamicPics {
    private ImageView img;
    private int pic;

    public void addNewDynamicPic(int id) {
        img = new ImageView(Level.getActivity());
        img.setId(id);
        //img.setTag(getResourceNameFromClassByID(R.drawable.class, pic));

        // drag the object
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Level.getLevelInfo().getPressTime().setToNow();
                //Level.getLevelInfo().setPressedObject(getResourceNameFromClassByID(R.drawable.class, pic));
                //img.setTag(getResourceNameFromClassByID(R.drawable.class, pic));
                // Starts the drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        img.setVisibility(View.INVISIBLE);
        Level.getMainLayout().addView(img);

    }

    public void insertPhoto(int x, int y, int pic) {
        this.pic = pic;
        img.setVisibility(View.VISIBLE);

        //insert the photos to the imageViews
        img.setImageResource(pic);

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(img.getResources(),pic, o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
        img.setLayoutParams(layoutParams);

        img.setX(x+img.getLeft());
        img.setY(y+img.getTop());
        img.setTag(pic);


    }

    public void setInvisible(int id) {
        img = (ImageView) Level.getActivity().findViewById(id);
        img.setVisibility(View.INVISIBLE);
    }

    private String getResourceNameFromClassByID(Class<?> aClass, int resourceID)
            throws IllegalArgumentException {
    /* Get all Fields from the class passed. */
        Field[] drawableFields = aClass.getFields();

    /* Loop through all Fields. */
        for (Field f : drawableFields) {
            try {
            /* All fields within the subclasses of R
             * are Integers, so we need no type-check here. */

            /* Compare to the resourceID we are searching. */
                if (resourceID == f.getInt(null))
                    return f.getName(); // Return the name.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    /* Throw Exception if nothing was found*/
        throw new IllegalArgumentException();
    }
}


