package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;

import gr.redpepper.footballrunningtime.database.AppDataBase;
import gr.redpepper.footballrunningtime.database.CupEntity;
import gr.redpepper.footballrunningtime.database.DatabaseDao;
import gr.redpepper.footballrunningtime.database.TeamEntity;
import gr.redpepper.footballrunningtime.menus.LoginMenu;

public class EntryScreen extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_screen);

        context = this;

        new PublicateDb().execute();

    }

    private class PublicateDb extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {

            AppDataBase db = AppDataBase.getInstance(context);

            DatabaseDao tdao = db.dbDao();

            List<TeamEntity> allTeams = tdao.getAll();

            Log.d("blepo","It runs one"+allTeams.get(0).getName());

            for (int i = 0; i< allTeams.size(); i++){

                Log.d("blepo","It runs one"+allTeams.get(i).getName());

            }

            List<CupEntity> allCups = tdao.getAllCups();

            for (int i = 0; i< allCups.size(); i++){

                Log.d("blepo","It runs two"+allCups.get(i).getName());

            }

            db.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(EntryScreen.this,LoginMenu.class);

                    startActivity(intent);

                    EntryScreen.this.finish();

                }
            }, 5000);
        }
    }
}
