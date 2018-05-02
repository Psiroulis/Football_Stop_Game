package gr.redpepper.footballrunningtime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MatchActivity extends Activity {

    //views

    private ImageView playerFlag;

    private ImageView opponentFlag;

    private TextView playerScore;

    private TextView opponentScore;

    private TextView currentHalf;

    private RelativeLayout clockLayout;

    private TextView clock;

    private ToggleButton shootBall;

    private ToggleButton startPlay;

    private TextView posts;

    //Penalty Variables and views
    private ImageView vertical_abll, horizontal_ball;

    private ToggleButton Penalty_Stop_But;

    private RelativeLayout penalty_Layout;

    private ValueAnimator ver_animator;

    private ValueAnimator hor_animator;

    private int penaltySpeed = 100;

    private RelativeLayout verpenlay,horpenlay;

    private int timerSpeed = 1;

    private Context context;

    private int playerTeamId;

    private int opponentTeamId;

    private TheMatch match;

    ArrayList<ArrayList<Team>> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        FindTheViews();

        //Get Match Info From Intent

        Intent intent = getIntent();

        playerTeamId = intent.getIntExtra("playerteamid",0);

        opponentTeamId = intent.getIntExtra("opponentteamid",0);

        matches = new ArrayList<>();

        matches = (ArrayList<ArrayList<Team>>) intent.getSerializableExtra("matches");

        if(matches != null){

            for (int i = 0; i<matches.size(); i++){

                Log.d("blepo",matches.get(i).get(0).getName()+" - "+matches.get(i).get(1).getName());

            }

        }else{

            Log.d("blepo","einai null");

        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        new GetTeamsInfo().execute();

        InitializeAnimations();

        //Play Button functionality
        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPlay.setVisibility(View.GONE);

                clockLayout.setVisibility(View.VISIBLE);

                shootBall.setVisibility(View.VISIBLE);

                match.RunTheTimer();

            }
        });

        //Shoot Button Functionality
        shootBall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                //shooting
                if (isChecked) {

                    int goalChecker = match.Shoot();

                    if(goalChecker == 0){

                        posts.setText(String.valueOf(match.getPosts()));

                        //Toast.makeText(context,"Hit The Post",Toast.LENGTH_SHORT).show();

                    }else if(goalChecker == 1){

                        playerScore.setText(String.valueOf(match.getPlayerGoals()));

                        //Toast.makeText(context,"Gooooaall",Toast.LENGTH_SHORT).show();

                    }else if(goalChecker == 2){

                        //Toast.makeText(context,"Missed",Toast.LENGTH_SHORT).show();

                    }else if(goalChecker == 3){

                        opponentScore.setText(String.valueOf(match.getOpponentGoals()));

                        //Toast.makeText(context,"Own Goal :(",Toast.LENGTH_SHORT).show();
                    }

                }
                //Restart the chronometer
                else {

                    if(match.getPosts() == 3){

                        match.setPosts(0);

                        posts.setText("0");

                        clock.setVisibility(View.GONE);

                        shootBall.setVisibility(View.GONE);

                        penalty_Layout.setVisibility(View.VISIBLE);

                        shootBall.setChecked(true);

                        ver_animator.start();

                    }else{

                        match.RunTheTimer();

                    }

                }
            }
        });

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


                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            penalty_Layout.setVisibility(View.INVISIBLE);

                            clock.setVisibility(View.VISIBLE);

                            shootBall.setVisibility(View.VISIBLE);

                            vertical_abll.setTranslationY(0);

                            horizontal_ball.setTranslationX(0);


                        }
                    },1400);

                    /*if( (verTime >= 1295 && verTime <= 1395) && (horTime >= 1295 && horTime <= 1395) ){

                        messages.setText("Goall!!!");

                        messagesLayout.setVisibility(View.VISIBLE);

                    }else{

                        messages.setText("Keeper Saves");

                        messagesLayout.setVisibility(View.VISIBLE);

                    }*/


                }

            }
        });



        //test Code
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MatchActivity.this,PasheOf8.class);
                intent.putExtra("playerteamid",playerTeamId);
                intent.putExtra("matches",matches);
                intent.putExtra("selected_team_id",playerTeamId);

                startActivity(intent);

            }
        });
    }


    private class GetTeamsInfo extends AsyncTask<String,String,String>{

        Team playerTeam , opponentTeam;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            TeamsDatabase db = TeamsDatabase.getInstance(context);

            TeamsDao tdao = db.teamsDao();

            TeamsEntity pt = tdao.findById(playerTeamId);

            TeamsEntity ot = tdao.findById(opponentTeamId);

            playerTeam = new Team(pt.getUid(),pt.getName(),pt.getLocked(),pt.getCup(),pt.getOverall());

            opponentTeam = new Team(ot.getUid(),ot.getName(),ot.getLocked(),ot.getCup(),ot.getOverall());

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            playerFlag.setBackgroundResource(GetTeamDrawable(playerTeam.getName()));

            opponentFlag.setBackgroundResource(GetTeamDrawable(opponentTeam.getName()));

            MyChronometer chronometer = new MyChronometer(timerSpeed,clock,MatchActivity.this);

            match = new TheMatch(chronometer,0);

        }
    }

    private Integer GetTeamDrawable(String teamName){

        String name = teamName;

        if(name.contains(" ")){

            name = name.replace(" ","_");

        }

        return context.getResources().getIdentifier( name.toLowerCase() , "drawable", context.getPackageName());

    }

    private void FindTheViews() {
        playerFlag = findViewById(R.id.playerTeamFlag);
        opponentFlag = findViewById(R.id.opponentTeamFlag);
        playerScore = findViewById(R.id.playerScore);
        opponentScore = findViewById(R.id.opponentScore);
        currentHalf = findViewById(R.id.currentHalf);
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

    //initialize Penalty animations
    private void InitializeAnimations() {


        vertical_abll.post(new Runnable() {
            @Override
            public void run() {

                //Log.d("blepo","Device Density: " + getResources().getDisplayMetrics().density);

                //Log.d("blepo","RelHeight: "+verpenlay.getHeight());

                //Log.d("blepo","BallHeight: "+vertical_abll.getHeight());

                float diff = (float) (verpenlay.getHeight() - vertical_abll.getHeight());

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
}


