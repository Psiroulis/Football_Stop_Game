package gr.redpepper.footballrunningtime;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button singlePlayer_Button;

    private Button multyPlayer_Button;

    private Button instruct_Button;

    private Button options_Button;


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

                Intent intent = new Intent(MainActivity.this, SinglePlayerMenu.class);

                startActivity(intent);

                MainActivity.this.finish();

            }
        });

        multyPlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        /*new Thread(new Runnable() {
            @Override
            public void run() {

               //TeamsDatabase db = Room.databaseBuilder(getApplicationContext(), TeamsDatabase.class,"teamsDatabase").build();

                TeamsDatabase db = TeamsDatabase.getInstance(context);

                TeamsDao tdao = db.teamsDao();

                List<TeamsEntity> allTeams = tdao.getAll();

                for (int i = 0; i< allTeams.size(); i++){

                    Log.d("blepo",""+allTeams.get(i).getName());

                }

                List<CupEntity> allCups = tdao.getAllCups();

                for (int i = 0; i< allCups.size(); i++){

                    Log.d("blepo",""+allCups.get(i).getName());

                }

                db.close();




            }
        }).start();*/
    }



    private void findTheViews(){

        singlePlayer_Button = findViewById(R.id.singlePlayerButton);

        multyPlayer_Button = findViewById(R.id.multyPlayerButton);

        instruct_Button = findViewById(R.id.instructBut);

        options_Button = findViewById(R.id.optionBut);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/digital_7.ttf");

        singlePlayer_Button.setTypeface(typeface);

        multyPlayer_Button.setTypeface(typeface);

        instruct_Button.setTypeface(typeface);

        options_Button.setTypeface(typeface);

    }
}

/*start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,MatchActivity.class);
               intent.putExtra("opponentScore",3);
               intent.putExtra("penaltySpeed",300);
               intent.putExtra("playerFlag",R.drawable.greece);
               intent.putExtra("opponentFlag",R.drawable.germany);
               startActivity(intent);
               MainActivity.this.finish();

           }
       });*/

