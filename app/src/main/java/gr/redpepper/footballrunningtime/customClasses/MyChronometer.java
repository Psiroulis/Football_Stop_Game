package gr.redpepper.footballrunningtime.customClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by psirogiannisdimitris on 25/02/2018.
 */

public class MyChronometer {

    private int milliseconds = 0;

    private int seconds = 0;

    private Timer timer = null;

    private int speed;

    private TextView textView;

    private Activity activity;

    private boolean changeHalfe;

    private Context context;

    public MyChronometer(Context context,int speed, TextView textView, Activity activity) {
        this.context = context;
        this.speed = speed;
        this.textView = textView;
        this.activity = activity;
        this.changeHalfe = false;
    }


    public void StartFirstHalf() {

        if (timer == null) {

            timer = new Timer("new1",true);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    if (getSeconds() == 45) {

                        Pause();

                        changeHalfe = true;

                        Intent intent = new Intent();
                        intent.setAction("gr.redpepper.footballrunningtime.Timenotification");
                        intent.putExtra("data","halftime");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    } else {

                        CountingTime();

                    }


                }
            }, 0, speed);

        }

    }


    public void StartSecondHalf() {

        if (timer == null) {

            timer = new Timer("new1",true);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    if (getSeconds() == 90) {

                        Reset();

                        changeHalfe = false;

                        Intent intent = new Intent();
                        intent.setAction("gr.redpepper.footballrunningtime.Timenotification");
                        intent.putExtra("data","end");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    } else {

                        CountingTime();

                    }


                }
            }, 0, speed);

        }

    }

    public void Pause() {

        if (timer != null) {

            timer.cancel();

            timer.purge();

            timer = null;

        }

    }

    public void Reset() {

        Pause();

        milliseconds = 0;

        seconds = 0;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("00:00:00");
            }
        });
    }


    public int getSeconds() {
        return seconds;
    }

    public int getMillis() {
        return milliseconds;
    }

    public boolean ChangeHalf() {
        return changeHalfe;
    }

    private void CountingTime(){

        if (milliseconds == 1000) {

            seconds++;

            milliseconds = 0;

        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String millis;

                String secondis;

                if ((getMillis() % 1000 / 10) < 10) {

                    millis = "0" + (getMillis() % 1000 / 10);

                } else {

                    millis = "" + (getMillis() % 1000 / 10);

                }

                if (getSeconds() >= 0 && getSeconds() < 10) {

                    secondis = "0" + seconds;
                } else {
                    secondis = "" + seconds;
                }

                textView.setText("00:" + secondis + ":" + millis);

            }
        });

        milliseconds++;

    }
}
