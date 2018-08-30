package gr.redpepper.footballrunningtime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import gr.redpepper.footballrunningtime.customClasses.MyChronometer;
import gr.redpepper.footballrunningtime.customClasses.Team;
import gr.redpepper.footballrunningtime.customClasses.TheMatch;
import gr.redpepper.footballrunningtime.database.AppDataBase;
import gr.redpepper.footballrunningtime.database.DatabaseDao;
import gr.redpepper.footballrunningtime.database.TeamEntity;
import gr.redpepper.footballrunningtime.menus.LostTheCup;
import gr.redpepper.footballrunningtime.menus.MainMenu;
import gr.redpepper.footballrunningtime.menus.SinglePlayerMenu;
import gr.redpepper.footballrunningtime.rounds.Final;
import gr.redpepper.footballrunningtime.rounds.RoundOf4;
import gr.redpepper.footballrunningtime.rounds.RoundOf8;

public class MatchScreen extends Activity {

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

    private RelativeLayout messagesLayout;

    private TextView messagesText;

    //Pause Menu Variables

    private RelativeLayout pauseLayout;

    private Button resumeGameButton;

    private Button exitGameButton;

    private boolean pauseMEnuChecker;

    //Penalty Variables and views
    private ImageView vertical_abll, horizontal_ball;

    private ToggleButton Penalty_Stop_But;

    private RelativeLayout penalty_Layout;

    private ValueAnimator ver_animator;

    private ValueAnimator hor_animator;

    private int penaltySpeed = 100;

    private RelativeLayout verpenlay, horpenlay;



    private Context context;

    private int playerTeamId;

    private int opponentTeamId;

    private TheMatch match;

    ArrayList<ArrayList<Team>> matches;

    private float yval, xval;

    private int phase;

    private int cup;

    private boolean tiePenalty;

    //Play sounds Variables
    private MediaPlayer mediaPlayer;

    private Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        FindTheViews();

        //Set Timer Font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/clockfont.otf");

        clock.setTypeface(typeface);

        //Get Match Info From Intent

        Intent intent = getIntent();

        playerTeamId = intent.getIntExtra("playerteamid", 0);

        opponentTeamId = intent.getIntExtra("opponentteamid", 0);

        phase = intent.getIntExtra("phase", 0);

        cup = intent.getIntExtra("choosenCup", 0);

        matches = new ArrayList<>();

        matches = (ArrayList<ArrayList<Team>>) intent.getSerializableExtra("matches");

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, new IntentFilter("gr.redpepper.footballrunningtime.Timenotification"));

        tiePenalty = false;

        pauseMEnuChecker = false;

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetTeamsInfo().execute();

        InitializeAnimations();

        //Play Button functionality

        startPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){

                    mediaPlayer = MediaPlayer.create(context, R.raw.startofgame);

                    mediaPlayer.start();

                    clockLayout.setVisibility(View.VISIBLE);

                    match.RunTheTimer();

                    pauseMEnuChecker = false;

                }else{

                    if(mediaPlayer!= null){

                        mediaPlayer.release();

                        mediaPlayer = null;
                    }

                    match.PauseTheTimer();

                    if(!pauseMEnuChecker) {

                        pauseLayout.setVisibility(View.VISIBLE);

                    }


                }
            }
        });

        //Shoot Button Functionality
        shootBall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                //shooting
                if (isChecked) {

                    int goalChecker = match.Shoot();

                    if (goalChecker == 0) {

                        posts.setText(String.valueOf(match.getPosts()));

                        showMessage("Hit The Post :(");

                    } else if (goalChecker == 1) {

                        playerScore.setText(String.valueOf(match.getPlayerGoals()));

                        long[] pattern = {0,1000,500,1000,500,1000};

                        vibrator.vibrate(pattern,-1);

                        showMessage("Gooooaall !!!");

                        if(mediaPlayer!=null){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }

                        mediaPlayer = MediaPlayer.create(context, R.raw.goal1);

                        mediaPlayer.start();

//                    } else if (goalChecker == 2) {
//
////                        showMessage("Missed");
//
                    } else if (goalChecker == 3) {

                        opponentScore.setText(String.valueOf(match.getOpponentGoals()));

                        //Toast.makeText(context,"Own Goal :(",Toast.LENGTH_SHORT).show();
                        showMessage("Own Goal :(");

                        if(mediaPlayer!=null){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }

                        mediaPlayer = MediaPlayer.create(context, R.raw.owngoal);

                        mediaPlayer.start();
                    }

                }
                //Restart the chronometer
                else {

                    if (match.getPosts() == 3) {

                        if(mediaPlayer!=null){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }

                        mediaPlayer = MediaPlayer.create(context, R.raw.penalty);

                        mediaPlayer.start();

                        match.setPosts(0);

                        posts.setText("0");

                        clockLayout.setVisibility(View.GONE);

                        penalty_Layout.setVisibility(View.VISIBLE);

                        shootBall.setChecked(true);

                        ver_animator.start();

                    } else {

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

                            clockLayout.setVisibility(View.VISIBLE);

                            vertical_abll.setTranslationY(0);

                            horizontal_ball.setTranslationX(0);

                            if(tiePenalty){

                                finishTheMatch(phase);

                                tiePenalty = false;

                            }else{

                                if (yval >= 80 && yval <= 100 && xval >= 80 && xval <= 100) {

                                    showMessage("Gooooaall");

                                    match.setPlayerGoals(match.getPlayerGoals() + 1);

                                    playerScore.setText(String.valueOf(match.getPlayerGoals()));

                                    long[] pattern = {0,1000,500,1000,500,1000};

                                    vibrator.vibrate(pattern,-1);


                                } else {

                                    showMessage("Keeper Saves :(");

                                }

                            }

                        }
                    }, 1400);

                }

            }
        });

//      Pause Buttons functionality

        resumeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pauseLayout.setVisibility(View.GONE);

                startPlay.setChecked(true);

                match.RunTheTimer();

            }
        });

        exitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MatchScreen.this , MainMenu.class);

                startActivity(intent);

                MatchScreen.this.finish();

            }
        });

    }


    private class GetTeamsInfo extends AsyncTask<String, String, String> {

        Team playerTeam, opponentTeam;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            AppDataBase db = AppDataBase.getInstance(context);

            DatabaseDao tdao = db.dbDao();

            TeamEntity pt = tdao.findById(playerTeamId);

            TeamEntity ot = tdao.findById(opponentTeamId);

            playerTeam = new Team(pt.getUid(), pt.getName(), pt.getLocked(), pt.getCup(), pt.getOverall());

            opponentTeam = new Team(ot.getUid(), ot.getName(), ot.getLocked(), ot.getCup(), ot.getOverall());

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            playerFlag.setBackgroundResource(GetTeamDrawable(playerTeam.getName()));

            opponentFlag.setBackgroundResource(GetTeamDrawable(opponentTeam.getName()));

            int timerSpeed = 1;

            MyChronometer chronometer = new MyChronometer(context, timerSpeed, clock);

            match = new TheMatch(chronometer, 0);

            if (opponentTeam.getOveral() >= 50 && opponentTeam.getOveral() <= 60) {

                match.setOpponentGoals(2);

            } else if (opponentTeam.getOveral() > 60 && opponentTeam.getOveral() <= 70) {

                match.setOpponentGoals(3);

            } else if (opponentTeam.getOveral() > 70 && opponentTeam.getOveral() <= 80) {

                match.setOpponentGoals(4);

            } else if (opponentTeam.getOveral() > 80 && opponentTeam.getOveral() <= 90) {

                match.setOpponentGoals(5);

            } else if (opponentTeam.getOveral() > 90 && opponentTeam.getOveral() <= 100) {

                match.setOpponentGoals(6);

            }

            opponentScore.setText(String.valueOf(match.getOpponentGoals()));

        }
    }

    private  Integer GetTeamDrawable(String teamName) {

        String name = teamName;

        if (name.contains(" ")) {

            name = name.replace(" ", "_");

        }

        return context.getResources().getIdentifier(name.toLowerCase(), "drawable", context.getPackageName());

    }

    private void FindTheViews() {
        TextView pauseMenuTitleText;

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
        messagesLayout = findViewById(R.id.messagesLayout);
        messagesText = findViewById(R.id.messagesTextView);
        pauseLayout = findViewById(R.id.pauseMenuLayout);
        resumeGameButton = findViewById(R.id.pauseMenuResumeButton);
        exitGameButton = findViewById(R.id.pauseMenuExitButton);
        pauseMenuTitleText = findViewById(R.id.pauseMenuTitleText);

        vertical_abll = findViewById(R.id.ver_ball);
        horizontal_ball = findViewById(R.id.hor_ball);
        Penalty_Stop_But = findViewById(R.id.penalty_stop);
        penalty_Layout = findViewById(R.id.penaltyLayout);
        verpenlay = findViewById(R.id.vertrel);
        horpenlay = findViewById(R.id.horrel);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/clockfont.otf");

        clock.setTypeface(typeface);

        Typeface buttypeface = Typeface.createFromAsset(getAssets(), "fonts/digital_7.ttf");

        pauseMenuTitleText.setTypeface(buttypeface);

        resumeGameButton.setTypeface(buttypeface);

        exitGameButton.setTypeface(buttypeface);
    }

    //initialize Penalty animations
    private void InitializeAnimations() {


        vertical_abll.post(new Runnable() {
            @Override
            public void run() {

                //Log.d("blepo","Device Density: " + getResources().getDisplayMetrics().density);

                //Log.d("blepo","RelHeight: "+verpenlay.getHeight());

                //Log.d("blepo","BallHeight: "+vertical_abll.getHeight());

                float diff = verpenlay.getHeight() - vertical_abll.getHeight();

                //Log.d("blepo","Distance to Run The ball: "+ diff);


                int diff2 = verpenlay.getHeight() - vertical_abll.getHeight();

                ver_animator = ObjectAnimator.ofFloat(vertical_abll, "translationY", diff);


                ver_animator.setDuration((diff2 * 1000) / penaltySpeed);

                ver_animator.setRepeatMode(ValueAnimator.REVERSE);

                ver_animator.setRepeatCount(ValueAnimator.INFINITE);

               /* ver_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        //Log.d("blepo","The Animated Values"+valueAnimator.getAnimatedValue("translationY"));

                    }
                });*/

                ver_animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        yval = vertical_abll.getY() / getResources().getDisplayMetrics().density;

                        //Log.d("blepo","To teliko Y: "+ (vertical_abll.getY()/getResources().getDisplayMetrics().density) );
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

                float diff = horpenlay.getWidth() - horizontal_ball.getWidth(); //->float.value

                //Log.d("blepo","Distance to Run The ball: "+ diff);


                int diff2 = horpenlay.getWidth() - horizontal_ball.getWidth();

                hor_animator = ObjectAnimator.ofFloat(horizontal_ball, "translationX", diff);
                hor_animator.setDuration((diff2 * 1000) / penaltySpeed);

                hor_animator.setRepeatMode(ValueAnimator.REVERSE);

                hor_animator.setRepeatCount(ValueAnimator.INFINITE);

                /*hor_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        //Log.d("blepo","The Animated Values"+valueAnimator.getAnimatedValue("translationX"));

                    }
                });*/

                hor_animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        xval = horizontal_ball.getX() / getResources().getDisplayMetrics().density;

                        //Log.d("blepo","To teliko X: "+ (horizontal_ball.getX()/getResources().getDisplayMetrics().density) );
                    }


                });

            }
        });

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if(mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(context, R.raw.halftime);

            mediaPlayer.start();

            shootBall.setChecked(false);

            pauseMEnuChecker = true;

            startPlay.setChecked(false);

            clockLayout.setVisibility(View.GONE);

            String message = intent.getStringExtra("data");

            if (message.equalsIgnoreCase("halftime")) {

                currentHalf.setText("2nd Half");

            } else if (message.equalsIgnoreCase("end")) {

                finishTheMatch(phase);

            }

        }
    };

    private void finishTheMatch(int phase){

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(context, R.raw.endofgame);

        mediaPlayer.start();

        Intent intentToProcced;

        if (match.getPlayerGoals() > match.getOpponentGoals()) {
            //win


            switch (phase) {
                case 1:
                    intentToProcced = new Intent(MatchScreen.this, RoundOf8.class);
                    break;

                case 2:
                    intentToProcced = new Intent(MatchScreen.this, RoundOf4.class);
                    break;

                case 3:
                    intentToProcced = new Intent(MatchScreen.this, Final.class);
                    break;

                case 4://intent to final win screen
                    intentToProcced = new Intent(MatchScreen.this,SinglePlayerMenu.class);
                    break;

                default:
                    intentToProcced = new Intent(MatchScreen.this, SinglePlayerMenu.class);
                    break;
            }

            intentToProcced.putExtra("choosenCup", cup);
            intentToProcced.putExtra("playerTeamId", playerTeamId);
            intentToProcced.putExtra("matches", matches);

            startActivity(intentToProcced);

            MatchScreen.this.finish();



        } else if (match.getPlayerGoals() == match.getOpponentGoals()) {
            //tie

            tiePenalty = true;

            clock.setVisibility(View.GONE);

            shootBall.setVisibility(View.GONE);

            penalty_Layout.setVisibility(View.VISIBLE);

            shootBall.setChecked(true);

            ver_animator.start();


        } else {
            //lose
            intentToProcced = new Intent(MatchScreen.this, LostTheCup.class);

            intentToProcced.putExtra("choosenCup",cup);

            startActivity(intentToProcced);

            MatchScreen.this.finish();
        }

    }

    private void showMessage(String message){

        messagesText.setText(message);

        messagesLayout.setVisibility(View.VISIBLE);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messagesLayout.setVisibility(View.GONE);
                    }
                });
            }
        },1100);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }

    @Override
    public void onBackPressed() {
    }
}


