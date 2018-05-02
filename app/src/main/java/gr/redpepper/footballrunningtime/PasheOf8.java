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

public class PasheOf8 extends Activity {

    private Context context;

    private RecyclerView list;

    private Button startMatchButton;

    private ArrayList<ArrayList<Team>> matchPairs;

    private ArrayList<ArrayList<Team>> match_8_Pairs;

    private ArrayList<Team> teamsToMakeCouples;

    private MatchesAdapter mAdapter;

    private int selectedTeamId;

    private int Cup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pashe_of8);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        list = findViewById(R.id.pa8recview);

        startMatchButton = findViewById(R.id.start8MatchBut);
    }

    @Override
    protected void onResume() {
        super.onResume();

        matchPairs = new ArrayList<>();

        match_8_Pairs = new ArrayList<>();

        teamsToMakeCouples = new ArrayList<>();

        Intent intent = getIntent();

        matchPairs = (ArrayList<ArrayList<Team>>) intent.getSerializableExtra("matches");

        selectedTeamId = intent.getIntExtra("selected_team_id",0);

        //Cup = intent.getIntExtra("cup",0);

        mAdapter = new MatchesAdapter(context,match_8_Pairs,selectedTeamId);
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

        for (int i = 0; i < 7; i++){

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

                match_8_Pairs.add(twoTeamsArray);

            }

        }

        mAdapter.notifyDataSetChanged();

        startMatchButton.setVisibility(View.VISIBLE);

    }

}
