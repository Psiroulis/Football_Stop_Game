package gr.redpepper.footballrunningtime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

    private int timerSpeed = 1;

    private int playerGoals = 0;

    private int opponentsGoals = 0;

    private int postCounter = 3;

    //Penalty Variables
    private ImageView vertical_abll, horizontal_ball;

    private ToggleButton Penalty_Stop_But;

    private RelativeLayout penalty_Layout;

    private ValueAnimator ver_animator;

    private ValueAnimator hor_animator;

    private int penaltySpeed;

    private RelativeLayout verpenlay,horpenlay;

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

        penaltySpeed = intent.getIntExtra("penaltySpeed", 100);

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

                //postCounter = 0;


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

                    if(postCounter == 3){

                        //postCounter = 0;

                        posts.setText("" + postCounter);

                        clock.setVisibility(View.GONE);

                        shootBall.setVisibility(View.GONE);

                        penalty_Layout.setVisibility(View.VISIBLE);

                        shootBall.setChecked(true);

                        ver_animator.start();

                    }else{

                        if(!chronometer.ChangeHalf()){

                            chronometer.StartFirstHalf();

                        }else{

                            chronometer.StartSecondHalf();

                        }
                    }

                }
            }
        });

        
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
        vertical_abll = findViewById(R.id.ver_ball);
        horizontal_ball = findViewById(R.id.hor_ball);
        Penalty_Stop_But = findViewById(R.id.penalty_stop);
        penalty_Layout = findViewById(R.id.penaltyLayout);
        verpenlay = findViewById(R.id.vertrel);
        horpenlay = findViewById(R.id.horrel);
    }

    //Check if is goal or post when shoot happens
    private void CheckGoal() {


        Log.d("blepo", ""+chronometer.getMillis());
        if (chronometer.getMillis() >= 0 && chronometer.getMillis() <= 9) {

            //It's a Goal
            playerGoals ++;

            playerScore.setText(String.valueOf(playerGoals));

            messages.setText("GOALLLLL!!!");

            messages.setTextColor(Color.parseColor("#ff5544"));

            messagesLayout.setVisibility(View.VISIBLE);


        } else if ((chronometer.getMillis() >= 990 && chronometer.getMillis() <= 999) ||
                (chronometer.getMillis() > 9 && chronometer.getMillis() <= 18)) {

            //It's a post

            postCounter ++;

            posts.setText(String.valueOf(postCounter));

            messages.setText("HIT THE POST !!!");

            messages.setTextColor(Color.parseColor("#0000FF"));

            messagesLayout.setVisibility(View.VISIBLE);

        } else if (chronometer.getMillis() >= 480 && chronometer.getMillis() <= 520) {

            //It' a onw goal

            opponentsGoals++;

            opponentScore.setText(""+opponentsGoals);

            postCounter = 0;

            posts.setText(String.valueOf(postCounter));

            messages.setText("OWN GOAL :(");

            messages.setTextColor(Color.parseColor("#0000FF"));

            messagesLayout.setVisibility(View.VISIBLE);

        } else if (chronometer.getMillis() == 1000) {

            //It's a Goal
            playerGoals ++;

            playerScore.setText(String.valueOf(playerGoals));

            messages.setText("GOALLLLL!!!");

            messages.setTextColor(Color.parseColor("#FF0000"));

            messagesLayout.setVisibility(View.VISIBLE);
        }


    }

    //initialize Penalty animations
    private void InitializeAnimations() {


        vertical_abll.post(new Runnable() {
            @Override
            public void run() {

                //Log.d("blepo","Device Density: " + getResources().getDisplayMetrics().density);

                //Log.d("blepo","RelHeight: "+verpenlay.getHeight());

                //Log.d("blepo","BallHeight: "+vertical_abll.getHeight());

                float diff = Float.valueOf(verpenlay.getHeight() -  vertical_abll.getHeight());

                //Log.d("blepo","Distance to Run The ball: "+ diff);


                int diff2 = verpenlay.getHeight() -  vertical_abll.getHeight();

                ver_animator = ObjectAnimator.ofFloat(vertical_abll,"translationY",diff);


                ver_animator.setDuration((diff2 * 1000) / penaltySpeed);

                ver_animator.setRepeatMode(ValueAnimator.REVERSE);

                ver_animator.setRepeatCount(ValueAnimator.INFINITE);

                ver_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        //Log.d("blepo","The Animated Values"+valueAnimator.getAnimatedValue("translationY"));



                    }
                });

                ver_animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        Log.d("blepo","To teliko Y: "+ (vertical_abll.getY()/getResources().getDisplayMetrics().density) );
                    }


                });
            }
        });

        horizontal_ball.post(new Runnable() {
            @Override
            public void run() {

                //Log.d("blepo","Device Density: " + getResources().getDisplayMetrics().density);

                //Log.d("blepo","RelHeight: "+horpenlay.getHeight());

                //Log.d("blepo","BallHeight: "+horizontal_ball.getHeight());

                float diff = Float.valueOf(horpenlay.getWidth() -  horizontal_ball.getWidth());

               //Log.d("blepo","Distance to Run The ball: "+ diff);


                int diff2 = horpenlay.getWidth() -  horizontal_ball.getWidth();

                hor_animator = ObjectAnimator.ofFloat(horizontal_ball,"translationX",diff);


                hor_animator.setDuration((diff2 * 1000) / penaltySpeed);

                hor_animator.setRepeatMode(ValueAnimator.REVERSE);

                hor_animator.setRepeatCount(ValueAnimator.INFINITE);

                hor_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        //Log.d("blepo","The Animated Values"+valueAnimator.getAnimatedValue("translationX"));



                    }
                });

                hor_animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        Log.d("blepo","To teliko Y: "+ (horizontal_ball.getX()/getResources().getDisplayMetrics().density) );
                    }


                });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        InitializeAnimations();

        Penalty_Stop_But.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    //on

                    ver_animator.cancel();

                    hor_animator.start();

                } else {
                    //off

                    hor_animator.cancel();

                    penalty_Layout.setVisibility(View.INVISIBLE);


                    /*if( (verTime >= 1295 && verTime <= 1395) && (horTime >= 1295 && horTime <= 1395) ){

                        messages.setText("Goall!!!");

                        messagesLayout.setVisibility(View.VISIBLE);

                    }else{

                        messages.setText("Keeper Saves");

                        messagesLayout.setVisibility(View.VISIBLE);

                    }*/

                    clock.setVisibility(View.VISIBLE);

                    shootBall.setVisibility(View.VISIBLE);

                    vertical_abll.setTranslationY(0);

                    horizontal_ball.setTranslationX(0);
                }

            }
        });
    }


}


