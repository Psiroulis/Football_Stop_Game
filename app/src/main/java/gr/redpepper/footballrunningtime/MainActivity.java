package gr.redpepper.footballrunningtime;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button singlePlayer_Button;

    private Button multyPlayer_Button;

    private Button exit_Button;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findTheViews();

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        singlePlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SinglePlayerMenu.class);

                startActivity(intent);

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

        exit_Button = findViewById(R.id.exitButton);

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

