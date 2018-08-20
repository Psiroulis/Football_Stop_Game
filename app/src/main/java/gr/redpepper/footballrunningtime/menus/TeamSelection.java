package gr.redpepper.footballrunningtime.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gr.redpepper.footballrunningtime.R;
import gr.redpepper.footballrunningtime.adapters.PagerAdapter;
import gr.redpepper.footballrunningtime.customClasses.Team;
import gr.redpepper.footballrunningtime.database.AppDataBase;
import gr.redpepper.footballrunningtime.database.TeamEntity;
import gr.redpepper.footballrunningtime.database.DatabaseDao;

public class TeamSelection extends Activity {

    private int Cup;

    private ViewPager viewpager;

    private Context context;

    private Button pagerPreviousButton,pagerNextButton;

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

        Cup = intent.getIntExtra("choosenCup", 0);

        new GetAllTeamsOfCup().execute(Cup);

        pagerPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(getItem(-1), true);
            }
        });

        pagerNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(getItem(+1),true);
            }
        });

    }

    private int getItem(int i) {
        return viewpager.getCurrentItem() + i;
    }

    private void FindTheViews() {

        viewpager = findViewById(R.id.viewpager);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/digital_7.ttf");

        TextView title = findViewById(R.id.tssometext);

        title.setTypeface(typeface);

        pagerPreviousButton = findViewById(R.id.pagerPreBtn);

        pagerNextButton = findViewById(R.id.pagerNexBtn);
    }

    private class GetAllTeamsOfCup extends AsyncTask<Integer, String, String> {

        ArrayList<Team> allteams = new ArrayList<>();

        @Override
        protected String doInBackground(Integer... integers) {

            AppDataBase db = AppDataBase.getInstance(context);

            DatabaseDao tdao = db.dbDao();

            List<TeamEntity> allTeams;

            if (Cup == 4) {

                allTeams = tdao.getAllWorldCupTeams();

            } else {

                allTeams = tdao.getCupsTeams(integers[0]);

            }

            for (int i = 0; i < allTeams.size(); i++) {

                TeamEntity entity = allTeams.get(i);

                Team oneteam = new Team(entity.getUid(), entity.getName(), entity.getLocked(), entity.getCup(), entity.getOverall());

                allteams.add(oneteam);
            }

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            PagerAdapter adapter = new PagerAdapter(context, allteams, Cup);

            viewpager.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(TeamSelection.this,SinglePlayerMenu.class);

        startActivity(intent);

        TeamSelection.this.finish();
    }
}
