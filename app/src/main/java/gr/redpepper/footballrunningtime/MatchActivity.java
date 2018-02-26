package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MatchActivity extends Activity {

    private ImageView playerFlag;

    private ImageView opponentFlag;

    private TextView playerScore;

    private TextView opponentScore;

    private TextView currentHalf;

    private RelativeLayout clockLayout;

    private RelativeLayout messagesLayout;

    private TextView messages;

    private TextView clock;

    private ToggleButton shootBall;

    private ToggleButton startPlay;

    private MyChronometer chronometer;

    private TextView posts;

    private int timerSpeed = 2;

    private int playerGoals = 0;

    private int opponentsGoals = 0;

    private int postCounter = 0;

    private int penaltySpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FindTheViews();

        //Get Match Info From Intent

        Intent intent = getIntent();

        opponentsGoals = intent.getIntExtra("opponentScore", 0);

        penaltySpeed = intent.getIntExtra("penaltySpeed", 1000);

        playerFlag.setImageResource(intent.getIntExtra("playerFlag", 0));

        opponentFlag.setImageResource(intent.getIntExtra("opponentFlag", 0));

        chronometer = new MyChronometer(timerSpeed, clock, this);

        //Play Button functionality
        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messagesLayout.setVisibility(View.GONE);

                startPlay.setVisibility(View.GONE);

                clockLayout.setVisibility(View.VISIBLE);

                postCounter = 0;


                if (!chronometer.ChangeHalf()) {

                    chronometer.StartFirstHalf();

                } else {

                    chronometer.StartSecondHalf();

                }

                shootBall.setVisibility(View.VISIBLE);

            }
        });

        //Shoot Button Functionality
        shootBall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                //shooting
                if (isChecked) {

                    messagesLayout.setVisibility(View.GONE);

                    chronometer.Pause();

                    CheckGoal();

                }
                //Restart the chronometer
                else {

                    messagesLayout.setVisibility(View.GONE);

                    if(!chronometer.ChangeHalf()){

                        chronometer.StartFirstHalf();

                    }else{

                        chronometer.StartSecondHalf();

                    }

                }
            }
        });


        //Change Ball Button Size When Is Pressed
        /*shootBall.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    float x = (float) 0.9;

                    float y = (float) 0.9;

                    shootBall.setScaleX(x);
                    shootBall.setScaleY(y);
                    shootBall.setBackgroundResource(R.drawable.ball);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    float x = (float) 1;

                    float y = (float) 1;

                    shootBall.setScaleX(x);
                    shootBall.setScaleY(y);
                    shootBall.setBackgroundResource(R.drawable.ball);

                }

                return false;
            }
        });*/


    }

    private void FindTheViews() {
        playerFlag = findViewById(R.id.playerTeamFlag);
        opponentFlag = findViewById(R.id.opponentTeamFlag);
        playerScore = findViewById(R.id.playerScore);
        opponentScore = findViewById(R.id.opponentScore);
        currentHalf = findViewById(R.id.currentHalf);
        messagesLayout = findViewById(R.id.messagesLayout);
        messages = findViewById(R.id.messagesText);
        clock = findViewById(R.id.timerText);
        shootBall = findViewById(R.id.shootButton);
        startPlay = findViewById(R.id.startPlayingButton);
        clockLayout = findViewById(R.id.timerLayout);
        posts = findViewById(R.id.posts);
    }

    //Check if is goal or post when shoot happens
    private void CheckGoal() {

        if (chronometer.getMillis() >= 0 && chronometer.getMillis() <= 9) {

            //It's a Goal
            playerGoals ++;

            playerScore.setText(""+playerGoals);

            messages.setText("GOALLLLL!!!");

            messages.setTextColor(Color.parseColor("#ff5544"));

            messagesLayout.setVisibility(View.VISIBLE);


        } else if ((chronometer.getMillis() >= 990 && chronometer.getMillis() <= 999) ||
                (chronometer.getMillis() > 9 && chronometer.getMillis() <= 18)) {

            //It's a post

            postCounter ++;

            posts.setText(""+postCounter);

            messages.setText("HIT THE POST !!!");

            messages.setTextColor(Color.parseColor("#0000FF"));

            messagesLayout.setVisibility(View.VISIBLE);

        } else if (chronometer.getMillis() >= 480 && chronometer.getMillis() <= 520) {

            //It' a onw goal

            opponentsGoals++;

            opponentScore.setText(""+opponentsGoals);

            postCounter = 0;

            posts.setText(""+postCounter);

            messages.setText("OWN GOAL :(");

            messages.setTextColor(Color.parseColor("#0000FF"));

            messagesLayout.setVisibility(View.VISIBLE);

        } else if (chronometer.getMillis() == 1000) {

            //It's a Goal
            playerGoals ++;

            playerScore.setText(""+playerGoals);

            messages.setText("GOALLLLL!!!");

            messages.setTextColor(Color.parseColor("#FF0000"));

            messagesLayout.setVisibility(View.VISIBLE);
        }


    }




}


