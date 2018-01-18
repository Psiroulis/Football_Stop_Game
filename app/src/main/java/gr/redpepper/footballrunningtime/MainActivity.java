package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private ToggleButton shoot_btn;

    private TextView goalsText,timeText;

    private Button startReset_btn;

    private MyChronometer chronometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        goalsText = findViewById(R.id.goals);

        timeText = findViewById(R.id.chrono);

        shoot_btn = findViewById(R.id.shoot_button);

        startReset_btn = findViewById(R.id.startmatch_button);

        chronometer = new MyChronometer(10,timeText,this);

        startReset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chronometer.Start();

            }
        });



    }
}
