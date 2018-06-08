package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class EntryScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_screen);

        Intent intent = new Intent(EntryScreen.this,LoginActivity.class);

        startActivity(intent);
    }
}
