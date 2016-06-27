package com.rahathaider.runwatch;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//activity class: allows interaction and display of UI elements
public class MainActivity extends Activity {
    //initialise variables
    Button buttonstart, buttonreset;
    TextView time;
    long starttime = 0L;
    long timeInMs = 0L;
    long timeSwap = 0L;
    long updatedtime = 0L;
    int t = 1;
    int sec = 0;
    int min = 0;
    int ms = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonstart = (Button) findViewById(R.id.start);
        buttonreset = (Button) findViewById(R.id.reset);
        time = (TextView) findViewById(R.id.timer);

        buttonstart.setOnClickListener(new OnClickListener() {

            //events to occur when start button is pressed
            @Override
            public void onClick(View v) {

                if (t == 1) { //start timer, change start text to "pause"
                    buttonstart.setText("Pause");
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    t = 0;
                } else { //pause timer, change pause text to "start"
                    buttonstart.setText("Start");
                    time.setTextColor(Color.BLUE);
                    timeSwap += timeInMs;
                    handler.removeCallbacks(updateTimer);
                    t = 1;
                }}
        });

        //events to occur when pressing reset button
        buttonreset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //set all variables to 0/default

                starttime = 0L;
                timeInMs = 0L;
                timeSwap = 0L;
                updatedtime = 0L;
                t = 1;
                sec = 0;
                min = 0;
                ms = 0;
                buttonstart.setText("Start");
                handler.removeCallbacks(updateTimer);
                time.setText("00:00:00");
            }});
    }

    //function that updates the timer in real time
    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMs = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwap + timeInMs;
            sec = (int) (updatedtime / 1000);
            min = sec / 60;
            sec = sec % 60;
            ms = (int) (updatedtime % 1000);
            //formats text displayed to user
            time.setText("" + min + ":" + String.format("%02d", sec) + ":"
                    + String.format("%03d", ms));
            time.setTextColor(Color.RED);
            handler.postDelayed(this, 0);
        }};
}