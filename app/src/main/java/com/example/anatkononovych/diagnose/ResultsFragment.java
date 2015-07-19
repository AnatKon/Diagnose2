package com.example.anatkononovych.diagnose;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class ResultsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Bundle bundle = this.getArguments();
        int counter = bundle.getInt("counter");

        String userName1 = bundle.getString("userName1");

        //Integer levelNum = bundle.getInt("levelNum");
        //long screenAppearTime = bundle.getLong("screenAppearTime");
        //String pressedObject = bundle.getString("pressedObject");
        //long pressTime = bundle.getLong("pressTime");
        //long releaseTime = bundle.getLong("releaseTime");
        //int isOnTarget = bundle.getInt("isOnTarget");
        Date dScreenAppearTime = null;
        Date dPressTime = null;
        Date dReleaseTime = null;

        //dScreenAppearTime = new Date(screenAppearTime);
        //dPressTime = new Date(pressTime);
        //dReleaseTime = new Date(releaseTime);

        RelativeLayout fragmentLayout = (RelativeLayout)view.findViewById(R.id.fragmentLayout);
        TextView txtUserName1 = (TextView)view.findViewById(R.id.txtUserName1);
        //TextView txtLevelNum  = (TextView)view.findViewById(R.id.txtLevelNum);
        //TextView txtScreenAppearTime  = (TextView)view.findViewById(R.id.txtScreenAppearTime);
        //TextView txtPressedObject  = (TextView)view.findViewById(R.id.txtPressedObject);
        //TextView txtPressTime = (TextView)view.findViewById(R.id.txtPressTime);
        //TextView txtReleaseTime = (TextView)view.findViewById(R.id.txtReleaseTime);
        //TextView txtIsOnTarget = (TextView)view.findViewById(R.id.txtIsOnTarget);

        txtUserName1.setText(userName1);

        for(int i = 1; i < counter; i++) {
            TextView txtLevelNum = new TextView(getActivity());
            fragmentLayout.addView(txtLevelNum);
            Integer levelNum = bundle.getInt("levelNum"+i);
            txtLevelNum.setText(levelNum.toString());

            TextView txtScreenAppearTime = new TextView(getActivity());
            fragmentLayout.addView(txtScreenAppearTime);
            long screenAppearTime = bundle.getLong("screenAppearTime"+i);
            dScreenAppearTime = new Date(screenAppearTime);
            txtScreenAppearTime.setText(dScreenAppearTime.toString());

            TextView txtPressedObject = new TextView(getActivity());
            fragmentLayout.addView(txtPressedObject);
            String pressedObject = bundle.getString("pressedObject"+i);
            txtPressedObject.setText(pressedObject);

            TextView txtPressTime = new TextView(getActivity());
            fragmentLayout.addView(txtPressTime);
            long pressTime = bundle.getLong("pressTime"+i);
            dPressTime = new Date(pressTime);
            txtPressTime.setText(dPressTime.toString());

            TextView txtReleaseTime = new TextView(getActivity());
            fragmentLayout.addView(txtReleaseTime);
            long releaseTime = bundle.getLong("releaseTime"+i);
            dReleaseTime = new Date(releaseTime);
            txtReleaseTime.setText(dReleaseTime.toString());

            TextView txtIsOnTarget = new TextView(getActivity());
            fragmentLayout.addView(txtIsOnTarget);
            int isOnTarget = bundle.getInt("isOnTarget"+i);
            txtIsOnTarget.setText(isOnTarget == 1 ? "true" : "false");
        }*/
        Bundle bundle = this.getArguments();
        int counter = bundle.getInt("counter");

        int sumOfMistakes = 0;
        int sumOfTouches = 0;
        int sumOfResponseTime = 0;
        int sumOfPerformanceTime=0;

        TableLayout tv=(TableLayout) getActivity().findViewById(R.id.table);
        int flag=1;

        // when i=-1, loop will display heading of each column
        // then usually data will be display from i=0 to jArray.length()
        int i=-1;
        System.out.println("counter "+ counter);
        for(;i<counter;i++){

            TableRow tr=new TableRow(getActivity());

            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));

            // this will be executed once
            if(flag==1){

                TextView b6=new TextView(getActivity());
                b6.setText("נגיעות");
                //b3.setTextColor(Color.BLUE);
                //b3.setTextSize(15);
                tr.addView(b6);

                TextView b7=new TextView(getActivity());
                b7.setPadding(10, 0, 0, 0);
                //b4.setTextSize(15);
                b7.setText("זמן תגובה");
                //b4.setTextColor(Color.BLUE);
                tr.addView(b7);

                TextView b8=new TextView(getActivity());
                b8.setPadding(10, 0, 0, 0);
                b8.setText("זמן ביצוע");
                //b5.setTextColor(Color.BLUE);
                //b5.setTextSize(15);
                tr.addView(b8);

                TextView b9=new TextView(getActivity());
                b9.setPadding(10, 0, 0, 0);
                b9.setText("מסיח");
                tr.addView(b9);

                TextView b10=new TextView(getActivity());
                b10.setPadding(10, 0, 0, 0);
                b10.setText("תשובה");
                tr.addView(b10);

                TextView b11=new TextView(getActivity());
                b11.setPadding(10, 0, 0, 0);
                b11.setText("מספר מסך");
                tr.addView(b11);



                tv.addView(tr);

                final View vline = new View(getActivity());
                vline.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                //vline.setBackgroundColor(Color.BLUE);
                tv.addView(vline); // add line below heading
                flag=0;
            } else {

                TextView b=new TextView(getActivity());
                String str=String.valueOf(bundle.getInt("numOfTouches" + i));
                b.setText(str);
                tr.addView(b);
                sumOfTouches+=bundle.getInt("numOfTouches"+i);

                TextView b1=new TextView(getActivity());
                b1.setPadding(10, 0, 0, 0);
                long milliResponse = bundle.getLong("responseTime" + i);
                String str1=String.valueOf((milliResponse / 60000 * 60) % 24 + ":" + (milliResponse / 60000) % 60 + ":" + (milliResponse / 1000) % 60 + ":" + (milliResponse / 100)%10);
                b1.setText(str1);
                tr.addView(b1);
                sumOfResponseTime+=milliResponse;

                TextView b2=new TextView(getActivity());
                b2.setPadding(10, 0, 0, 0);
                long milliPerform = bundle.getLong("performanceTime"+i);
                String str2=String.valueOf((milliPerform/60000*60)%24 + ":" + (milliPerform/60000)%60 + ":" + (milliPerform/1000)%60 + ":"+(milliPerform/100)%10);
                b2.setText(str2);
                tr.addView(b2);
                sumOfPerformanceTime+=milliPerform;

                TextView b3=new TextView(getActivity());
                b3.setPadding(10, 0, 0, 0);
                String str3=bundle.getString("distractor"+i);
                b3.setText(str3);
                tr.addView(b3);

                TextView b4=new TextView(getActivity());
                b4.setPadding(10, 0, 0, 0);
                String str4=bundle.getInt("isOnTarget"+i)==1? "true" : "false";
                b4.setText(str4);
                tr.addView(b4);
                if (bundle.getInt("isOnTarget"+i)==0)
                    sumOfMistakes++;

                TextView b5=new TextView(getActivity());
                b5.setPadding(10, 0, 0, 0);
                String str5=String.valueOf(bundle.getInt("levelNum" + i));
                b5.setText(str5);
                tr.addView(b5);


                tv.addView(tr);
                final View vline1 = new View(getActivity());
                vline1.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                //vline1.setBackgroundColor(Color.WHITE);
                tv.addView(vline1);  // add line below each row
            }
        }
        TextView txtErrorNum = (TextView)getActivity().findViewById(R.id.txtErrorNum);
        txtErrorNum.setText(sumOfMistakes+"");

        TextView txtTouchesNum = (TextView)getActivity().findViewById(R.id.txtTouchesNum);
        txtTouchesNum.setText(sumOfTouches+"");

        if(i!=0) {
            TextView txtPerformTime = (TextView)getActivity().findViewById(R.id.txtPerformTime);
            long avgPerform = sumOfPerformanceTime / i;
            txtPerformTime.setText((avgPerform/60000*60)%24 + ":" + (avgPerform/60000)%60 + ":" + (avgPerform/1000)%60 +":"+(avgPerform/100)%10);

            TextView txtResponseTime = (TextView) getActivity().findViewById(R.id.txtReactionTime);
            long avgResponse = sumOfResponseTime / i;
            txtResponseTime.setText((avgResponse/60000*60)%24 + ":" + (avgResponse/60000)%60 + ":" + (avgResponse/1000)%60 +":"+(avgResponse/100)%10);
        }
    }
}
