package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Δημήτρης on 18/1/2018.
 */

public class MyChronometer {

    private int speed;
    private TextView textview;

    private Timer mTimer = null;

    private int Milliseconds;
    private int Seconds;

    private Activity activity;

    private ToggleButton toggleButtonshoot;

    private Button tooglebuttonstart;

    public MyChronometer(int speed, TextView textview, Activity activity, ToggleButton shoot, ToggleButton start) {
        this.speed = speed;
        this.textview = textview;
        this.activity = activity;
        this.toggleButtonshoot = shoot;
        this.tooglebuttonstart = start;
    }

    public void Start(){

        Milliseconds = 0;

        Seconds = 0;

        if(mTimer == null){

            mTimer = new Timer("new1",true);

            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    calculate();

                }
            }, 0, speed);

        }

    }

    public void Pause(){

        if(mTimer != null) {

            mTimer.cancel();

            mTimer.purge();

            mTimer = null;
        }


    }

    public void Resume(){

        if( mTimer == null){

            mTimer = new Timer("new",true);

            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    calculate();

                }
            }, 0, speed);


        }


    }

    public void Reset(){

        if( mTimer != null){

            mTimer.cancel();

            mTimer.purge();

            mTimer = null;
        }

        Milliseconds = 0;

        Seconds = 0;

        textview.setText("00:00:00");
    }

    private void calculate()
    {
        final String Millis;

        if(Seconds == 45){

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toggleButtonshoot.setVisibility(View.GONE);
                }
            });

            mTimer.cancel();

            mTimer.purge();

            mTimer = null;

            Milliseconds = 0;

            Seconds = 0;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            textview.setText("00:45:00");

                            tooglebuttonstart.setVisibility(View.VISIBLE);
                        }
                    },70);

                }
            });

        }else {


            if (Milliseconds == 1000) {

                Milliseconds = 0;

                Seconds++;
            }

            if ((Milliseconds % 1000 / 10) < 10) {
                Millis = "0" + Milliseconds % 1000 / 10;
            } else {
                Millis = Integer.toString(Milliseconds % 1000 / 10);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    textview.setText("00:" + String.format("%02d", Seconds) + ":" + Millis);

                }

            });

            Milliseconds++;

        }
    }

    public void FixedZero(){

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textview.setText("00:" +String.format("%02d",Seconds) + ":00");
            }
        },70);


        Milliseconds  = 0;
    }

    public int getMilliseconds() {
        return Milliseconds;
    }
}
