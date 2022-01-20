package gr.redpepper.footballrunningtime.rounds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gr.redpepper.footballrunningtime.MatchScreen;
import gr.redpepper.footballrunningtime.adapters.DrawAdapter;
import gr.redpepper.footballrunningtime.R;
import gr.redpepper.footballrunningtime.customClasses.Team;
import gr.redpepper.footballrunningtime.menus.TeamSelection;
import gr.redpepper.footballrunningtime.database.AppDataBase;
import gr.redpepper.footballrunningtime.database.DatabaseDao;
import gr.redpepper.footballrunningtime.database.TeamEntity;

public class RoundOf16 extends Activity {

    private int selectedTeamId;

    private Context context;

    private ArrayList<Team> allCupsTeams;

    private ArrayList<ArrayList<Team>> matches;

    private int Cup;

    private RecyclerView list;

    private DrawAdapter mAdapter;

    private Button startMatchButton;

    private ImageView cupImage;

    private TextView cupNameTitle;



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

        mAdapter = new DrawAdapter(context,matches,selectedTeamId);
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

                Intent intent = new Intent(RoundOf16.this,MatchScreen.class);
                intent.putExtra("playerteamid",selectedTeamId);
                intent.putExtra("opponentteamid",oponentID);
                intent.putExtra("phase",1);
                intent.putExtra("choosenCup",Cup);
                intent.putExtra("matches",matches);

                startActivity(intent);

                RoundOf16.this.finish();
            }
        });

    }


    private class GetAllTeamsOfCup extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            AppDataBase db = AppDataBase.getInstance(context);

            DatabaseDao tdao = db.dbDao();

            List<TeamEntity> teams;

            if(Cup == 4){

                teams = tdao.getAllWorldCupTeams();

            }else{

                teams = tdao.getCupsTeams(Cup);

            }



            for (int i = 0; i< teams.size(); i++){

                TeamEntity entity = teams.get(i);

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

        Intent intent = new Intent(RoundOf16.this, TeamSelection.class);

        intent.putExtra("choosenCup",Cup);

        RoundOf16.this.finish();

        startActivity(intent);



    }
}
