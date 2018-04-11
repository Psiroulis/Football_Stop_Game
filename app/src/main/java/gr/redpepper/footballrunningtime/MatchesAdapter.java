package gr.redpepper.footballrunningtime;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder>{

    private ArrayList<ArrayList<Team>> teams;

    private Context context;

    private int selectedTeamId;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView firstTeamName,secondTeamName;
        public ImageView firstTeamFlag,secondTeamFlag;

        public MyViewHolder(View view) {
            super(view);

            firstTeamFlag = view.findViewById(R.id.firstTeamFlag);
            firstTeamName = view.findViewById(R.id.firstTeamName);

            secondTeamFlag = view.findViewById(R.id.secondTeamFlag);
            secondTeamName = view.findViewById(R.id.secondTeamName);
        }
    }

    public MatchesAdapter(Context context, ArrayList<ArrayList<Team>> teams,int selectedTeamId) {
        this.context = context;
        this.teams = teams;
        this.selectedTeamId = selectedTeamId;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pashe16_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Team teamOne = teams.get(position).get(0);

        Team teamTwo = teams.get(position).get(1);

        holder.firstTeamName.setText(teamOne.getName());

        holder.firstTeamFlag.setBackgroundResource(GetTeamDrawable(teamOne.getName()));

        holder.secondTeamName.setText(teamTwo.getName());

        holder.secondTeamFlag.setBackgroundResource(GetTeamDrawable(teamTwo.getName()));

        if(teamOne.getId() == selectedTeamId){

            holder.firstTeamName.setTextColor(Color.parseColor("#FFD700"));

        }

        if(teamTwo.getId() == selectedTeamId){

            holder.secondTeamName.setTextColor(Color.parseColor("#FFD700"));

        }
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    private Integer GetTeamDrawable(String teamName){

        String name = teamName;

        if(name.contains(" ")){

            name = name.replace(" ","_");

        }

        return context.getResources().getIdentifier( name.toLowerCase() , "drawable", context.getPackageName());

    }

}
