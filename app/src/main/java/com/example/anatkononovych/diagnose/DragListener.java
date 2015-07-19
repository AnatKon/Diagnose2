package com.example.anatkononovych.diagnose;

import android.app.Activity;
import android.text.Layout;
import android.view.DragEvent;
import android.view.View;

/**
 * Created by anatkononovych on 4/22/15.
 */
public class DragListener implements View.OnDragListener {
    private float xPos = 0, yPos = 0;
    private int id;
    private Level level;

    public DragListener(Level level) {
        this.level = level;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final View dragView = (View) event.getLocalState();

        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        // Handles each of the expected events
        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                id = v.getId();
                xPos = event.getX() + v.getX() - dragView.getWidth() / 2;
                yPos = event.getY() + v.getY() - dragView.getHeight() / 2;
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                xPos = event.getX() + v.getX() - dragView.getWidth() / 2;
                yPos = event.getY() + v.getY() - dragView.getHeight() / 2;

                break;
            case DragEvent.ACTION_DROP:
                //Level.getLevelInfo().getReleaseTime().setToNow();
                // if the correct item is being dropped to the right place
                if ((id == R.id.dest4) /*&& (dragView.getId() == R.id.target)*/) {

                    //if we are not in the last level
                    if (level.getLevelNum() < Level.getMaxLevelNum() - 1) {
                        level.finishLevel(dragView, v);
                    } else {
                        //the game ends
                        level.closeAllItems(dragView, v);
                    }

                } else {
                    //wrong target - the item will go back to it's starting point
                    Level.getLevelInfo().setOnTarget(false);
                    Level.insertInfoToDB();
                    level.viewBackToStartingPoint(dragView, xPos, yPos);
                }
                break;

            case DragEvent.ACTION_DRAG_ENDED:

                break;
        }

        return true;
    }
}


