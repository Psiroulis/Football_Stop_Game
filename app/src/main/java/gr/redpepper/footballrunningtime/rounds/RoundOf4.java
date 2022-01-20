package gr.redpepper.footballrunningtime.rounds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

import gr.redpepper.footballrunningtime.MatchScreen;
import gr.redpepper.footballrunningtime.adapters.DrawAdapter;
import gr.redpepper.footballrunningtime.R;
import gr.redpepper.footballrunningtime.customClasses.Team;

public class RoundOf4 extends Activity {

    private Context context;

    private RecyclerView list;

    private Button startMatchButton;

    private ArrayList<ArrayList<Team>> matchPairs;

    private ArrayList<ArrayList<Team>> match_4_Pairs;

    private ArrayList<Team> teamsToMakeCouples;

    private DrawAdapter mAdapter;

    private int selectedTeamId;

    private int Cup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase_of4);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        list = findViewById(R.id.pa4recview);

        startMatchButton = findViewById(R.id.start4MatchBut);



    }

    @Override
    protected void onResume() {
        super.onResume();



        matchPairs = new ArrayList<>();

        match_4_Pairs = new ArrayList<>();

        teamsToMakeCouples = new ArrayList<>();

        Intent intent = getIntent();

        matchPairs = (ArrayList<ArrayList<Team>>) intent.getSerializableExtra("matches");

        selectedTeamId = intent.getIntExtra("playerTeamId",0);

        Cup = intent.getIntExtra("choosenCup",0);

        mAdapter = new DrawAdapter(context,match_4_Pairs,selectedTeamId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setAdapter(mAdapter);

        for (int i = 0; i < matchPairs.size(); i++){

            if( matchPairs.get(i).get(0).getId() == selectedTeamId || matchPairs.get(i).get(1).getId() == selectedTeamId ){

                if(matchPairs.get(i).get(0).getId() == selectedTeamId){

                    teamsToMakeCouples.add( matchPairs.get(i).get(0) );

                }else{

                    teamsToMakeCouples.add( matchPairs.get(i).get(1) );
                }

                matchPairs.remove(i);
            }
        }

        Collections.shuffle(matchPairs);

        for (int i = 0; i < matchPairs.size(); i++){

            teamsToMakeCouples.add(matchPairs.get(i).get(0));

        }

        Collections.shuffle(teamsToMakeCouples);

        for (int i = 0; i< teamsToMakeCouples.size(); i++){

            if( i == 0 || ( i % 2 ) == 0){

                Team one = teamsToMakeCouples.get(i);

                Team two = teamsToMakeCouples.get(i + 1);

                ArrayList<Team> twoTeamsArray = new ArrayList<>();

                twoTeamsArray.add(one);

                twoTeamsArray.add(two);

                match_4_Pairs.add(twoTeamsArray);

            }

        }

        mAdapter.notifyDataSetChanged();

        startMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oponentID = 0;

                for (int i = 0; i<match_4_Pairs.size(); i++){

                    Team one = match_4_Pairs.get(i).get(0);

                    Team two = match_4_Pairs.get(i).get(1);

                    if(one.getId() == selectedTeamId || two.getId() == selectedTeamId){

                        if(one.getId() == selectedTeamId){

                            oponentID = two.getId();

                        }else{

                            oponentID = one.getId();
                        }

                    }
                }

                Intent intent = new Intent(RoundOf4.this,MatchScreen.class);
                intent.putExtra("playerteamid",selectedTeamId);
                intent.putExtra("opponentteamid",oponentID);
                intent.putExtra("phase",3);
                intent.putExtra("choosenCup",Cup);
                intent.putExtra("matches",match_4_Pairs);

                startActivity(intent);

                RoundOf4.this.finish();
            }
        });

        startMatchButton.setVisibility(View.VISIBLE);



    }
}
