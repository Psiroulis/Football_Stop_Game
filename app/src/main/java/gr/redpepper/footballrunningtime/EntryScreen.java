package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class EntryScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_screen);

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
