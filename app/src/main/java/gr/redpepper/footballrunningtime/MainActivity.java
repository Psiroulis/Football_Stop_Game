package gr.redpepper.footballrunningtime;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView text = findViewById(R.id.testtext);

        Button start = findViewById(R.id.startbut);

        Button stop = findViewById(R.id.stopbut);

        Button reset = findViewById(R.id.resetbut);

        final MyChronometer chrono = new MyChronometer(1,text,this);


       /*start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!chrono.ChangeHalf()){

                    chrono.StartFirstHalf();

                }else{

                    chrono.StartSecondHalf();

                }




            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chrono.Pause();

                Log.d("blepo","millis=" + chrono.getMillis());

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chrono.Reset();
            }
        });*/

       start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,MatchActivity.class);
               intent.putExtra("opponentScore",3);
               intent.putExtra("penaltySpeed",5000);
               intent.putExtra("playerFlag",R.drawable.grflag);
               intent.putExtra("opponentFlag",R.drawable.gerflag);
               startActivity(intent);
               MainActivity.this.finish();

           }
       });
    }
}

