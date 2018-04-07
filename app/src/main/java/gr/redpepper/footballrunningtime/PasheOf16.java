package gr.redpepper.footballrunningtime;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasheOf16 extends AppCompatActivity {

    private int selectedTeamId;

    private Context context;

    ArrayList<Team> allOtherTeams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_pashe_of16);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        context = this;

        Intent intent = getIntent();

        selectedTeamId = intent.getIntExtra("selected_team_id",0);










    }

    @Override
    protected void onResume() {
        super.onResume();

        allOtherTeams = new ArrayList<>();

        new GetAllTeamsOfCup().execute();
    }

    private class GetAllTeamsOfCup extends AsyncTask<Integer,String,String> {

        @Override
        protected String doInBackground(Integer... integers) {

            TeamsDatabase db = TeamsDatabase.getInstance(context);

            TeamsDao tdao = db.teamsDao();

            List<TeamsEntity> allTeams = tdao.getAllExceptSelected(selectedTeamId);

            for (int i = 0; i< allTeams.size(); i++){

                TeamsEntity entity = allTeams.get(i);

                Team oneteam = new Team(entity.getUid(),entity.getName(),entity.getLocked(),entity.getCup(),entity.getOverall());

                allOtherTeams.add(oneteam);
            }

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for( int i = 0; i < allOtherTeams.size(); i++ ){

                Log.d("blepo","team id: "+allOtherTeams.get(i).getId()+"team name: " + allOtherTeams.get(i).getName());

            }

            ArrayList<Integer> check = new ArrayList<>();

            for (int i = 0; i<allOtherTeams.size(); i++){

                int min = 1;

                int max = 15;

                Random r = new Random();

                int ra;

                ra = r.nextInt(max - min + 1) + min;

                while(check.contains(ra)){

                    ra = r.nextInt(max - min + 1) + min;

                }

                check.add(ra);

                Log.d("blepo","TO random: " +ra);
            }

        }
    }
}
