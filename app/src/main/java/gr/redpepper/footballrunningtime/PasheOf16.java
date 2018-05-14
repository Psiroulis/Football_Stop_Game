package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasheOf16 extends Activity {

    private int selectedTeamId;

    private Context context;

    private ArrayList<Team> allCupsTeams;

    private ArrayList<ArrayList<Team>> matches;

    private int Cup;

    private RecyclerView list;

    private MatchesAdapter mAdapter;

    private Button startMatchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pashe_of16);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        list = findViewById(R.id.pa16recview);

        startMatchButton = findViewById(R.id.start16button);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        selectedTeamId = intent.getIntExtra("selected_team_id",0);

        Cup = intent.getIntExtra("choosenCup",0);

        allCupsTeams = new ArrayList<>();

        matches = new ArrayList<>();

        mAdapter = new MatchesAdapter(context,matches,selectedTeamId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setAdapter(mAdapter);

        new GetAllTeamsOfCup().execute();

        startMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int oponentID = 0;

                for (int i = 0; i<matches.size(); i++){

                    Team one = matches.get(i).get(0);

                    Team two = matches.get(i).get(1);

                    if(one.getId() == selectedTeamId || two.getId() == selectedTeamId){

                        if(one.getId() == selectedTeamId){

                            oponentID = two.getId();

                        }else{

                            oponentID = one.getId();
                        }

                    }
                }

                Intent intent = new Intent(PasheOf16.this,MatchActivity.class);
                intent.putExtra("playerteamid",selectedTeamId);
                intent.putExtra("opponentteamid",oponentID);
                intent.putExtra("phase",1);
                intent.putExtra("choosenCup",Cup);
                intent.putExtra("matches",matches);

                startActivity(intent);

                PasheOf16.this.finish();
            }
        });

    }


    private class GetAllTeamsOfCup extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            TeamsDatabase db = TeamsDatabase.getInstance(context);

            TeamsDao tdao = db.teamsDao();

            List<TeamsEntity> teams;

            if(Cup == 4){

                teams = tdao.getAllWorldCupTeams();

            }else{

                teams = tdao.getCupsTeams(Cup);

            }



            for (int i = 0; i< teams.size(); i++){

                TeamsEntity entity = teams.get(i);

                Team oneteam = new Team(entity.getUid(),entity.getName(),entity.getLocked(),entity.getCup(),entity.getOverall());

                allCupsTeams.add(oneteam);
            }

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Collections.shuffle(allCupsTeams);

            for (int i = 0; i< allCupsTeams.size(); i++){

                if( i == 0 || ( i % 2 ) == 0){

                    Team one = allCupsTeams.get(i);

                    Team two = allCupsTeams.get(i + 1);

                    ArrayList<Team> twoTeamsArray = new ArrayList<>();

                    twoTeamsArray.add(one);

                    twoTeamsArray.add(two);

                    matches.add(twoTeamsArray);

                }

            }

            mAdapter.notifyDataSetChanged();

            startMatchButton.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(PasheOf16.this, TeamSelection.class);

        intent.putExtra("choosenCup",Cup);

        PasheOf16.this.finish();

        startActivity(intent);



    }
}
