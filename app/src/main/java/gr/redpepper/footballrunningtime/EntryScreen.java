package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;

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
            TeamsDatabase db = TeamsDatabase.getInstance(context);

            TeamsDao tdao = db.teamsDao();


            List<TeamsEntity> allTeams = tdao.getAll();

            for (int i = 0; i< allTeams.size(); i++){

                Log.d("blepo",""+allTeams.get(i).getName());

            }

            List<CupEntity> allCups = tdao.getAllCups();

            for (int i = 0; i< allCups.size(); i++){

                Log.d("blepo",""+allCups.get(i).getName());

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
                    Intent intent = new Intent(EntryScreen.this,LoginActivity.class);

                    startActivity(intent);

                    EntryScreen.this.finish();

                }
            }, 5000);
        }
    }
}
