package gr.redpepper.footballrunningtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class TeamSelection extends AppCompatActivity {

    private int Cup;

    ViewPager viewpager;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();

        Cup = intent.getIntExtra("choosenCup",0);

        FindTheViews();

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        viewpager.setAdapter(new PagerAdapter(context));

    }

    private void FindTheViews(){

    }
}
