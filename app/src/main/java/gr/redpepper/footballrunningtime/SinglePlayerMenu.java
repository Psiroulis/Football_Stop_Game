package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SinglePlayerMenu extends Activity {

    private Button euro_Button,copaAmerica_Button,copaAfrica_Button,worldCup_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_player_menu);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findTheViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        euro_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseCup("euro");

            }
        });

        copaAmerica_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseCup("america");

            }
        });

        copaAfrica_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseCup("africa");

            }
        });

        worldCup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseCup("world");

            }
        });
    }

    private void findTheViews(){

        euro_Button = findViewById(R.id.EuroCupButton);

        copaAfrica_Button = findViewById(R.id.CopaAfricaButton);

        copaAmerica_Button = findViewById(R.id.CopaAmericaButton);

        worldCup_Button = findViewById(R.id.WorldCupButton);
    }

    private void ChooseCup(String Cup){

        switch (Cup){
            case "euro":

                GoToTeamSelection(1);

                break;
            case "africa":

                GoToTeamSelection(2);

                break;
            case "america":

                GoToTeamSelection(3);

                break;
            case "world":

                GoToTeamSelection(4);

                break;
            default:

                Log.d("Football Stop","Activity: "+ SinglePlayerMenu.class.getSimpleName()+"Error: Switch ChooseCup()->default was Called");

                break;
        }



    }

    private void GoToTeamSelection(int ChoosenCup){

        Intent intent = new Intent(SinglePlayerMenu.this , TeamSelection.class);

        intent.putExtra("choosenCup",ChoosenCup);

        SinglePlayerMenu.this.finish();

        startActivity(intent);

    }
}
