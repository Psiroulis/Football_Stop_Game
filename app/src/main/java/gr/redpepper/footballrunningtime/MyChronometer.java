package gr.redpepper.footballrunningtime;

import android.app.Activity;
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

    private Activity activity;

    public MyChronometer(int speed, TextView textview, Activity activity) {
        this.speed = speed;
        this.textview = textview;
        this.activity = activity;
    }

    public void Start(){

        this.Milliseconds = 0;

        this.Seconds = 0;

        if(this.mTimer == null){

            this.mTimer = new Timer(true);

            this.mTimer.scheduleAtFixedRate(timerTask,0,this.speed);

        }

    }

    public void Pause(){

        if(this.mTimer != null) {

            this.mTimer.cancel();

            this.mTimer.purge();

            this.mTimer = null;
        }

    }

    public void Resume(){

        if( this.mTimer == null){

            this.mTimer = new Timer(true);

            this.mTimer.scheduleAtFixedRate(timerTask,0,this.speed);

        }


    }

    public void Reset(){

        if( this.mTimer != null){

            this.mTimer.cancel();

            this.mTimer.purge();

            this.mTimer = null;
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

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    textview.setText("00:" +String.format("%02d",Seconds) + ":" + Millis);

                }

            });

            Milliseconds++;

        }
    };

    public int getMilliseconds() {
        return Milliseconds;
    }
}
