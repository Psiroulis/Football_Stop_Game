package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class TeamSelection extends Activity {

    private int Cup;

    private ViewPager viewpager;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        FindTheViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        Cup = intent.getIntExtra("choosenCup",0);

        new GetAllTeamsOfCup().execute(Cup);

    }

    private void FindTheViews(){

        viewpager = findViewById(R.id.viewpager);

    }

    private class GetAllTeamsOfCup extends AsyncTask<Integer,String,String>{

        ArrayList<Team> allteams = new ArrayList<>();

        @Override
        protected String doInBackground(Integer... integers) {

            TeamsDatabase db = TeamsDatabase.getInstance(context);

            TeamsDao tdao = db.teamsDao();

            List<TeamsEntity> allTeams;

            if(Cup == 4){

                allTeams = tdao.getAllWorldCupTeams();

            }else{

                allTeams = tdao.getCupsTeams(integers[0]);

            }

            for (int i = 0; i< allTeams.size(); i++){

                TeamsEntity entity = allTeams.get(i);

                Team oneteam = new Team(entity.getUid(),entity.getName(),entity.getLocked(),entity.getCup(),entity.getOverall());

                allteams.add(oneteam);
            }

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            PagerAdapter adapter = new PagerAdapter(context,allteams,Cup);

            viewpager.setAdapter(adapter);
        }
    }
}
