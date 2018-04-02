package gr.redpepper.footballrunningtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;

public class TeamSelection extends AppCompatActivity {

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

        Intent intent = getIntent();

        Cup = intent.getIntExtra("choosenCup",0);

        FindTheViews();


        ArrayList<Integer> teamFlags = new ArrayList<>();

        teamFlags.add(R.drawable.gerflag);

        teamFlags.add(R.drawable.grflag);

        PagerAdapter adapter = new PagerAdapter(context,teamFlags);

        viewpager.setAdapter(adapter);

    }

    private void FindTheViews(){

        viewpager = findViewById(R.id.viewpager);

    }
}
