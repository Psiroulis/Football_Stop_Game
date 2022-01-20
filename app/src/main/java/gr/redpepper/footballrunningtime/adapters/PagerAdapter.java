package gr.redpepper.footballrunningtime.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gr.redpepper.footballrunningtime.R;
import gr.redpepper.footballrunningtime.customClasses.Team;
import gr.redpepper.footballrunningtime.menus.TeamSelection;
import gr.redpepper.footballrunningtime.rounds.RoundOf16;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<Team> teams;

    private int choosenCup;

    public PagerAdapter(Context context,  ArrayList<Team> teams, int choosenCup) {

        this.context = context;

        this.teams = teams;

        this.choosenCup = choosenCup;

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
    public Object instantiateItem(final ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_flag_layout,null);

        TextView teamName = view.findViewById(R.id.teamNametxt);

        ImageView teamFlag = view.findViewById(R.id.flagImage);

        ImageView lock = view.findViewById(R.id.lock);

        Button button = view.findViewById(R.id.SelectTeamButton);

        final Team team = teams.get(position);

        TextView teamOveral = view.findViewById(R.id.overalText);

        teamName.setText(team.getName());

        teamFlag.setBackgroundResource(GetTeamDrawable(team.getName()));

        teamOveral.setText("Overal: "+team.getOveral());

        if(team.getLocked() == 0){

            button.setText("Select");

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/digital_7.ttf");

            button.setTypeface(typeface);

            lock.setVisibility(View.GONE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context,RoundOf16.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    //intent.putExtra("cup",team.getCup());

                    intent.putExtra("choosenCup",choosenCup);

                    intent.putExtra("selected_team_id",team.getId());

                    context.startActivity(intent);

                    ((TeamSelection)context).finish();
                }
            });

        }else{

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/digital_7.ttf");

            button.setTypeface(typeface);

            button.setText("Unlock");

            //teamFlag.setAlpha(0.6f);

            lock.setVisibility(View.VISIBLE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context,"einai lock",Toast.LENGTH_SHORT).show();

                }
            });

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

        String name = teamName;

        if(name.contains(" ")){

            name = name.replace(" ","_");

        }

        return context.getResources().getIdentifier( name.toLowerCase() , "drawable", context.getPackageName());

    }


}

