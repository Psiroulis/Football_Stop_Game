package gr.redpepper.footballrunningtime;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends Activity {

    private ToggleButton shoot_btn;

    private TextView goalsText,timeText,away_goals;

    private ToggleButton startReset_btn;

    private MyChronometer chronometer;

    private int speed = 1;

    private int player_goals = 0;

    private int rival_goals = 0;

    private TextView messages;

    private int posts = 0;

    private int keeperside = 0;

    private LinearLayout penalty_Layout;

    private Button Pena_Left,Pena_Center,Pena_Right;

    private TextView postText;

    ImageView vertical_abll,horizontal_ball;

    private ObjectAnimator ver_animator;

    private ObjectAnimator hor_animator;



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

        messages = findViewById(R.id.messagesText);

        away_goals = findViewById(R.id.away_goals);

        penalty_Layout = findViewById(R.id.penantly_layout);

        Pena_Left = findViewById(R.id.left_penantly);

        Pena_Center = findViewById(R.id.center_penantly);

        Pena_Right = findViewById(R.id.right_penantly);

        postText = findViewById(R.id.posts);

        vertical_abll = findViewById(R.id.ver_ball);

        horizontal_ball = findViewById(R.id.hor_ball);


        stopbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hor_animator.cancel();

                float hor_ball_position = (float) hor_animator.getAnimatedFraction();

                Log.d("blepo",""+hor_ball_position);


                int hor_ball_positions =  (Integer) hor_animator.getAnimatedValue();

                Log.d("blepo",""+hor_ball_positions);
                ver_animator.start();
            }
        });


        startReset_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //on Start New Game

                    messages.setVisibility(View.GONE);

                    player_goals = 0;

                    rival_goals = 0;

                    posts = 0;

                    postText.setText("Posts: "+ posts);

                    chronometer.Start();

                    shoot_btn.setVisibility(View.VISIBLE);

                    goalsText.setText("Home: " + player_goals);

                    away_goals.setText("Away: " + rival_goals);

                    startReset_btn.setVisibility(View.GONE);

                }else{
                    //off Start Seconf Half

                    posts = 0;

                    postText.setText("Posts: "+ posts);

                    messages.setVisibility(View.GONE);

                    chronometer.Start();

                    shoot_btn.setVisibility(View.VISIBLE);

                    startReset_btn.setVisibility(View.GONE);
                }
            }
        });

        shoot_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is On

                    chronometer.Pause();

                    checkGoal();


                    Log.d("blepo",""+chronometer.getMilliseconds());

                } else {
                    // The toggle is Off

                    messages.setVisibility(View.GONE);

                    if (posts == 3) {

                        posts = 0;

                        postText.setText("Posts: "+ posts);

                        Random rand = new Random();

                        keeperside = rand.nextInt(3);

                        Log.d("blepo","To Random Tou keeper-> "+keeperside);

                        timeText.setVisibility(View.GONE);

                        shoot_btn.setVisibility(View.GONE);

                        penalty_Layout.setVisibility(View.VISIBLE);

                        shoot_btn.setChecked(true);

                    }else{

                        chronometer.Resume();

                    }

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

        Pena_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int choosenSide = 0;

                shootPenalty(choosenSide);

            }
        });

        Pena_Center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int choosenSide = 1;

                shootPenalty(choosenSide);

            }
        });

        Pena_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int choosenSide = 2;

                shootPenalty(choosenSide);

            }
        });

    }

    private void checkGoal(){

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("blepo","timertext"+timeText.getText().toString());

                if(chronometer.getMilliseconds() >= 0 && chronometer.getMilliseconds() <= 9){

                    //Toast.makeText(MainActivity.this,"Goal The milli is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                    player_goals++;

                    goalsText.setText("Home: "+player_goals);

                    chronometer.FixedZero();

                    messages.setText("GOALLLLL!!!");

                    messages.setTextColor(Color.parseColor("#FF0000"));

                    messages.setVisibility(View.VISIBLE);


                }else if((chronometer.getMilliseconds() >= 990 && chronometer.getMilliseconds() <= 999) ||
                        (chronometer.getMilliseconds() >9 && chronometer.getMilliseconds()<=18)){

                    //Toast.makeText(MainActivity.this,"OUCH the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();

                    messages.setText("HIT THE POST !!!");

                    messages.setTextColor(Color.parseColor("#0000FF"));

                    messages.setVisibility(View.VISIBLE);

                    posts ++;

                    postText.setText("Posts: "+ posts);

                }else if(chronometer.getMilliseconds() >= 480 && chronometer.getMilliseconds() <= 520){

                    //Toast.makeText(MainActivity.this,"OnwGoal the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                    messages.setText("OWN GOAL :(");

                    messages.setTextColor(Color.parseColor("#0000FF"));

                    messages.setVisibility(View.VISIBLE);

                    rival_goals ++;

                    away_goals.setText("Away: " + rival_goals);

                    posts = 0;

                    postText.setText("Posts: "+ posts);

                }else if(chronometer.getMilliseconds() == 1000){

                    //Toast.makeText(MainActivity.this,"OUCH the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();

                    messages.setText("HIT THE POST !!!");

                    messages.setTextColor(Color.parseColor("#0000FF"));

                    messages.setVisibility(View.VISIBLE);

                    posts ++;

                    postText.setText("Posts: "+ posts);
                }


            }
        },20);


    }

    private void shootPenalty(int Side){

        if(keeperside != Side){

            player_goals ++;

            goalsText.setText("Goals: "+player_goals);

            messages.setText("Goallll!!!");

            messages.setTextColor(Color.parseColor("#FF0000"));

            messages.setVisibility(View.VISIBLE);

            penalty_Layout.setVisibility(View.GONE);

            timeText.setVisibility(View.VISIBLE);

            shoot_btn.setVisibility(View.VISIBLE);

        }else{

            messages.setText("Keeper Saves :(");

            messages.setTextColor(Color.parseColor("#FF0000"));

            messages.setVisibility(View.VISIBLE);

            penalty_Layout.setVisibility(View.GONE);

            timeText.setVisibility(View.VISIBLE);

            shoot_btn.setVisibility(View.VISIBLE);

        }

    }


}
