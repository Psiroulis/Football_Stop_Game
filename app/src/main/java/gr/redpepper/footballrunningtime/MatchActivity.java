package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView chronometerText = findViewById(R.id.chronometerMatchText);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/clockfont.otf");

        chronometerText.setTypeface(typeface);

    }


}


