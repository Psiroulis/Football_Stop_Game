package gr.redpepper.footballrunningtime;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button singlePlayer_Button;

    private Button multyPlayer_Button;

    private Button exit_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findTheViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

        singlePlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        multyPlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        exit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.this.finish();

            }
        });
    }



    private void findTheViews(){

        singlePlayer_Button = findViewById(R.id.singlePlayerButton);

        multyPlayer_Button = findViewById(R.id.multyPlayerButton);

        exit_Button = findViewById(R.id.exitButton);

    }
}

/*start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,MatchActivity.class);
               intent.putExtra("opponentScore",3);
               intent.putExtra("penaltySpeed",300);
               intent.putExtra("playerFlag",R.drawable.grflag);
               intent.putExtra("opponentFlag",R.drawable.gerflag);
               startActivity(intent);
               MainActivity.this.finish();

           }
       });*/

