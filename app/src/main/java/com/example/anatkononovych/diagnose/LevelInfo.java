package com.example.anatkononovych.diagnose;

import android.text.format.Time;

/**
 * Created by anatkononovych on 4/22/15.
 */
public class LevelInfo {
    private String userName;
    private int levelNum;
    private long screenAppearTime;
    //private String pressedObject;
    //private long pressTime;
    //private long releaseTime;
    private boolean isOnTarget;
    //private long timeOfFirstTouch;
    private int distractor;
    private long responseTime;
    private long performanceTime;
    private int numOfTouches;

    public LevelInfo(){
        this.userName = "";
        this.levelNum = -1;
        this.screenAppearTime = 0;//new Time(Time.getCurrentTimezone());
        //this.pressedObject = "";
        //this.pressTime = 0;//new Time(Time.getCurrentTimezone());
        //this.releaseTime = 0;//new Time(Time.getCurrentTimezone());
        this.isOnTarget = false;
        this.numOfTouches = 0;
        //this.timeOfFirstTouch = 0;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }




    public boolean isOnTarget() {
        return isOnTarget;
    }

    public void setOnTarget(boolean isOnTarget) {
        this.isOnTarget = isOnTarget;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumOfTouches() {
        return numOfTouches;
    }

    public void setNumOfTouches(int numOfTouches) {
        this.numOfTouches = numOfTouches;
    }

    /*public long getTimeOfFirstTouch() {
        return timeOfFirstTouch;
    }

    public void setTimeOfFirstTouch(long timeOfFirstTouch) {
        this.timeOfFirstTouch = timeOfFirstTouch;
    }*/

    public int getDistractor() {
        return distractor;
    }

    public void setDistractor(int distractor) {
        this.distractor = distractor;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime - screenAppearTime;
    }

    public long getPerformanceTime() {
        return performanceTime;
    }

    public void setPerformanceTime(long performanceTime) {
        this.performanceTime = performanceTime - screenAppearTime;
    }

    public long getScreenAppearTime() {
        return screenAppearTime;
    }

    public void setScreenAppearTime(long screenAppearTime) {
        this.screenAppearTime = screenAppearTime;
    }
}

