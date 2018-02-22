package gr.redpepper.footballrunningtime;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    ImageView vertical_abll, horizontal_ball;
    private ToggleButton shoot_btn;
    private TextView goalsText, timeText, away_goals;
    private ToggleButton startReset_btn;
    private MyChronometer chronometer;
    private int speed = 1;
    private int player_goals = 0;
    private int rival_goals = 0;
    private TextView messages;
    private int posts = 3;
    private RelativeLayout penalty_Layout;
    private TextView postText;
    private ObjectAnimator ver_animator;

    private ObjectAnimator hor_animator;

    private ToggleButton Penalty_Stop_But;

    private int verticalValues,horizontalValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findtheviews();

        startReset_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //on Start New Game

                    messages.setVisibility(View.GONE);

                    player_goals = 0;

                    rival_goals = 0;

                    //posts = 0;

                    postText.setText("Posts: " + posts);

                    chronometer.Start();

                    shoot_btn.setVisibility(View.VISIBLE);

                    goalsText.setText("Home: " + player_goals);

                    away_goals.setText("Away: " + rival_goals);

                    startReset_btn.setVisibility(View.GONE);

                } else {
                    //off Start Seconf Half

                    //posts = 0;

                    postText.setText("Posts: " + posts);

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

                    Log.d("blepo", "" + chronometer.getMilliseconds());

                } else {
                    // The toggle is Off

                    messages.setVisibility(View.GONE);

                    if (posts == 3) {

                        //posts = 0;

                        postText.setText("Posts: " + posts);

                        timeText.setVisibility(View.GONE);

                        shoot_btn.setVisibility(View.GONE);

                        penalty_Layout.setVisibility(View.VISIBLE);

                        shoot_btn.setChecked(true);

                        InitializeAnimations();

                        ver_animator.start();

                    } else {

                        chronometer.Resume();

                    }

                }
            }
        });

        shoot_btn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    float x = (float) 0.9;

                    float y = (float) 0.9;

                    shoot_btn.setScaleX(x);
                    shoot_btn.setScaleY(y);
                    shoot_btn.setBackgroundResource(R.drawable.ball);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

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

    private void checkGoal() {

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("blepo", "timertext" + timeText.getText().toString());

                if (chronometer.getMilliseconds() >= 0 && chronometer.getMilliseconds() <= 9) {

                    //Toast.makeText(MainActivity.this,"Goal The milli is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                    player_goals++;

                    goalsText.setText("Home: " + player_goals);

                    chronometer.FixedZero();

                    messages.setText("GOALLLLL!!!");

                    messages.setTextColor(Color.parseColor("#FF0000"));

                    messages.setVisibility(View.VISIBLE);


                } else if ((chronometer.getMilliseconds() >= 990 && chronometer.getMilliseconds() <= 999) ||
                        (chronometer.getMilliseconds() > 9 && chronometer.getMilliseconds() <= 18)) {

                    //Toast.makeText(MainActivity.this,"OUCH the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();

                    messages.setText("HIT THE POST !!!");

                    messages.setTextColor(Color.parseColor("#0000FF"));

                    messages.setVisibility(View.VISIBLE);

                    posts++;

                    postText.setText("Posts: " + posts);

                } else if (chronometer.getMilliseconds() >= 480 && chronometer.getMilliseconds() <= 520) {

                    //Toast.makeText(MainActivity.this,"OnwGoal the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();
                    messages.setText("OWN GOAL :(");

                    messages.setTextColor(Color.parseColor("#0000FF"));

                    messages.setVisibility(View.VISIBLE);

                    rival_goals++;

                    away_goals.setText("Away: " + rival_goals);

                    posts = 0;

                    postText.setText("Posts: " + posts);

                } else if (chronometer.getMilliseconds() == 1000) {

                    //Toast.makeText(MainActivity.this,"OUCH the millios is"+chronometer.getMilliseconds(),Toast.LENGTH_SHORT).show();

                    messages.setText("HIT THE POST !!!");

                    messages.setTextColor(Color.parseColor("#0000FF"));

                    messages.setVisibility(View.VISIBLE);

                    posts++;

                    postText.setText("Posts: " + posts);
                }


            }
        }, 20);


    }

    private void InitializeAnimations() {

        ver_animator = new ObjectAnimator();

        ver_animator.setIntValues(0, getResources().getDimensionPixelSize(R.dimen.animation_size));

        ver_animator.setDuration(5000);

        ver_animator.setRepeatMode(ValueAnimator.REVERSE);

        ver_animator.setRepeatCount(ValueAnimator.INFINITE);

        ver_animator.setEvaluator(new IntEvaluator());

        ver_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                vertical_abll.setTranslationY((Integer) ver_animator.getAnimatedValue());
            }
        });


        ver_animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                verticalValues = (Integer) ver_animator.getAnimatedValue();

                ver_animator.removeAllListeners();

                ver_animator.setDuration(0);

                ((ValueAnimator) ver_animator).reverse();

            }


        });

        ver_animator.setTarget(vertical_abll);


        hor_animator = new ObjectAnimator();

        hor_animator.setIntValues(0, getResources().getDimensionPixelSize(R.dimen.animation_size));

        hor_animator.setDuration(5000);

        hor_animator.setRepeatMode(ValueAnimator.REVERSE);

        hor_animator.setRepeatCount(ValueAnimator.INFINITE);

        hor_animator.setEvaluator(new IntEvaluator());

        hor_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                horizontal_ball.setTranslationX((Integer) hor_animator.getAnimatedValue());

            }
        });

        hor_animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                horizontalValues = (Integer) hor_animator.getAnimatedValue();

                hor_animator.removeAllListeners();

                hor_animator.setDuration(0);

                ((ValueAnimator) hor_animator).reverse();
            }
        });

        hor_animator.setTarget(horizontal_ball);


    }

    private void findtheviews() {
        goalsText = findViewById(R.id.goals);

        timeText = findViewById(R.id.chrono);

        shoot_btn = findViewById(R.id.shoot_button);

        startReset_btn = findViewById(R.id.startmatch_button);

        chronometer = new MyChronometer(speed, timeText, this, shoot_btn, startReset_btn);

        messages = findViewById(R.id.messagesText);

        away_goals = findViewById(R.id.away_goals);

        penalty_Layout = findViewById(R.id.penalty_layout);

        postText = findViewById(R.id.posts);

        vertical_abll = findViewById(R.id.ver_ball);

        horizontal_ball = findViewById(R.id.hor_ball);

        Penalty_Stop_But = findViewById(R.id.penalty_stop);
    }

    @Override
    protected void onResume() {
        super.onResume();

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

                    penalty_Layout.setVisibility(View.GONE);

                    if( (verticalValues >= 258 && verticalValues <= 284) && (horizontalValues >= 258 && horizontalValues <= 284) ){

                        messages.setText("Goall!!!");

                        messages.setVisibility(View.VISIBLE);

                    }else{

                        messages.setText("Keeper Saves");

                        messages.setVisibility(View.VISIBLE);

                    }

                    timeText.setVisibility(View.VISIBLE);

                    shoot_btn.setVisibility(View.VISIBLE);

                }

            }
        });
    }
}
