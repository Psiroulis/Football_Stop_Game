package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class Final extends Activity {

    private Context context;

    private RecyclerView list;

    private Button startMatchButton;

    private ArrayList<ArrayList<Team>> matchPairs;

    private ArrayList<ArrayList<Team>> match_final_Pairs;

    private ArrayList<Team> teamsToMakeCouples;

    private MatchesAdapter mAdapter;

    private int selectedTeamId;

    private int Cup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        list = findViewById(R.id.pafinalrecview);

        startMatchButton = findViewById(R.id.startfinalMatchBut);
    }

    @Override
    protected void onResume() {
        super.onResume();

        matchPairs = new ArrayList<>();

        match_final_Pairs = new ArrayList<>();

        teamsToMakeCouples = new ArrayList<>();

        Intent intent = getIntent();

        matchPairs = (ArrayList<ArrayList<Team>>) intent.getSerializableExtra("matches");

        selectedTeamId = intent.getIntExtra("playerTeamId",0);

        Cup = intent.getIntExtra("choosenCup",0);

        mAdapter = new MatchesAdapter(context,match_final_Pairs,selectedTeamId);
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

                match_final_Pairs.add(twoTeamsArray);

            }

        }

        mAdapter.notifyDataSetChanged();

        startMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int oponentID = 0;

                for (int i = 0; i<match_final_Pairs.size(); i++){

                    Team one = match_final_Pairs.get(i).get(0);

                    Team two = match_final_Pairs.get(i).get(1);

                    if(one.getId() == selectedTeamId || two.getId() == selectedTeamId){

                        if(one.getId() == selectedTeamId){

                            oponentID = two.getId();

                        }else{

                            oponentID = one.getId();
                        }

                    }
                }

                Intent intent = new Intent(Final.this,MatchActivity.class);
                intent.putExtra("playerteamid",selectedTeamId);
                intent.putExtra("opponentteamid",oponentID);
                intent.putExtra("phase",4);
                intent.putExtra("choosenCup",Cup);
                intent.putExtra("matches",match_final_Pairs);

                startActivity(intent);

                Final.this.finish();

            }
        });

        startMatchButton.setVisibility(View.VISIBLE);
    }
}
