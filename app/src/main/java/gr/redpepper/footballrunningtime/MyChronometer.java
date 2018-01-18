package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

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

    private Context context;

    private Activity activity;

    public MyChronometer(int speed, TextView textview, Activity activity) {
        this.speed = speed;
        this.textview = textview;
        this.activity = activity;
    }

    public void Start(){

        this.Milliseconds = 0;

        this.Seconds = 0;

        if(mTimer == null){

            mTimer = new Timer();

            mTimer.scheduleAtFixedRate(timerTask,0,this.speed);

        }

    }

    public void Pause(){

        if(mTimer != null) {

            mTimer.cancel();

            mTimer = null;
        }

    }

    public void Resume(){

        if(mTimer == null){

            mTimer.scheduleAtFixedRate(timerTask,0,this.speed);

        }


    }

    public void Reset(){

        if(mTimer != null){

            mTimer.cancel();

            mTimer = null;
        }

        Milliseconds = 0;

        Seconds = 0;

        textview.setText("00:00:00");
    }

    private TimerTask timerTask = new TimerTask() {

        String Millis;

        @Override
        public void run() {

            if(Milliseconds == 1000){

                Milliseconds = 0;

                Seconds ++;
            }

            if ((Milliseconds % 1000 / 10) < 10) {
                Millis = "0" + Milliseconds % 1000 / 10;
            } else {
                Millis = Integer.toString(Milliseconds % 1000 / 10);
            }

            /*
            if (Seconds < 10) {
                Sec = "0" + Seconds;
            } else {
                Sec = Integer.toString(Seconds);
            }

            */
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    textview.setText("00:" +String.format("%02d",Seconds) + ":" + Millis);

                }

            });

            Milliseconds++;

        }
    };
}
