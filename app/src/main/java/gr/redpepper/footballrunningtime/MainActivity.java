package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private ToggleButton shoot_btn;

    private TextView goalsText,timeText;

    private Button startReset_btn;

    private MyChronometer chronometer;

    private int speed = 1;

    private int halftime = 1;

    private int goals = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        goalsText = findViewById(R.id.goals);

        timeText = findViewById(R.id.chrono);

        shoot_btn = findViewById(R.id.shoot_button);

        startReset_btn = findViewById(R.id.startmatch_button);

        chronometer = new MyChronometer(speed,timeText,this,shoot_btn,startReset_btn);

        startReset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shoot_btn.setVisibility(View.VISIBLE);

                chronometer.Start();

                startReset_btn.setVisibility(View.GONE);

                if(halftime == 1){

                    goalsText.setText("Goals:0");

                    halftime =2;

                    startReset_btn.setText("Start Second Half");


                }else if (halftime == 2){

                    halftime = 1;

                    startReset_btn.setText("Start New Game");

                    goals = 0;

                }

            }
        });

        shoot_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is On

                    chronometer.Pause();

                    Log.d("blepo",""+chronometer.getMilliseconds());

                    if( (chronometer.getMilliseconds() >=0 && chronometer.getMilliseconds()<=9) ||

                            (chronometer.getMilliseconds() >=997 && chronometer.getMilliseconds()<=999) ||

                            chronometer.getMilliseconds() == 1000 ){

                        //Toast.makeText(MainActivity.this,"Goal The milli is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                        goals ++;

                        goalsText.setText("Goals:"+goals);

                        chronometer.FixedZero();
                    }else if((chronometer.getMilliseconds() >= 990 && chronometer.getMilliseconds() < 997) ||
                                (chronometer.getMilliseconds() >9 && chronometer.getMilliseconds()<12)){

                        Toast.makeText(MainActivity.this,"OUCH the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                    }else if(chronometer.getMilliseconds() >= 480 && chronometer.getMilliseconds() <= 520){

                        Toast.makeText(MainActivity.this,"OnwGoal the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                    }



                } else {
                    // The toggle is Off

                    chronometer.Resume();
                }
            }
        });

        shoot_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    float x = (float) 0.9;

                    float y = (float) 0.9;

                    shoot_btn.setScaleX(x);
                    shoot_btn.setScaleY(y);
                    shoot_btn.setBackgroundResource(R.drawable.ball);

                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    float x = (float) 1;

                    float y = (float) 1;

                    shoot_btn.setScaleX(x);
                    shoot_btn.setScaleY(y);
                    shoot_btn.setBackgroundResource(R.drawable.ball);

                }

                return false;
            }
        });


    }
}
