package gr.redpepper.footballrunningtime;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<Team> teams;

    public PagerAdapter(Context context,  ArrayList<Team> teams) {

        this.context = context;

        this.teams = teams;

    }

    @Override
    public int getCount() {

        return teams.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_flag_layout,null);

        TextView teamName = view.findViewById(R.id.teamNametxt);

        ImageView teamFlag = view.findViewById(R.id.flagImage);

        ImageView lock = view.findViewById(R.id.lock);

        Button button = view.findViewById(R.id.SelectTeamButton);

        Team team = teams.get(position);

        teamName.setText(team.getName());

        teamFlag.setBackgroundResource(GetTeamDrawable(team.getName()));

        if(team.getLocked() == 0){

            button.setText("Select");

            lock.setVisibility(View.GONE);

        }else{

            button.setText("Unlock");

            teamFlag.setAlpha(0.6f);

            lock.setVisibility(View.VISIBLE);

        }

        ViewPager vp = (ViewPager) container;

        vp.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;

        View view = (View) object;

        vp.removeView(view);
    }

    private Integer GetTeamDrawable(String teamName){

        return context.getResources().getIdentifier( teamName.toLowerCase() , "drawable", context.getPackageName());

    }
}

