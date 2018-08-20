package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import gr.redpepper.footballrunningtime.customClasses.MyChronometer;
import gr.redpepper.footballrunningtime.customClasses.TheMatch;

public class MatchScreen extends Activity {

    private MyChronometer chronometer;

    private TheMatch match;

    private Context context;

    private int opponentsGoals;

    private ToggleButton shootButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView chronometerText = findViewById(R.id.chronometerMatchText);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/clockfont.otf");

        chronometerText.setTypeface(typeface);

        findTheViews();

        context = this;

        chronometer = new MyChronometer(context,1000, chronometerText, this);

        match = new TheMatch(chronometer,opponentsGoals);

        shootButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //enable
                    match.Shoot();
                }else{
                    //disable
                }
            }
        });
    }

    private void findTheViews(){

        shootButton = findViewById(R.id.shootButton);

    }

}


