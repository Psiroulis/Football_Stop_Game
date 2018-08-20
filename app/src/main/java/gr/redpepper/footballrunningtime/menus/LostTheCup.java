package gr.redpepper.footballrunningtime.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import gr.redpepper.footballrunningtime.R;

public class LostTheCup extends Activity {

    private Button retryBtn,mainBtn;

    private int cup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_the_cup);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findTheViews();

        Intent intent = getIntent();

        cup = intent.getIntExtra("choosenCup",0);


    }

    @Override
    protected void onResume() {
        super.onResume();

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LostTheCup.this , TeamSelection.class);

                intent.putExtra("choosenCup",cup);

                LostTheCup.this.finish();

                startActivity(intent);

            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LostTheCup.this , SinglePlayerMenu.class);

                LostTheCup.this.finish();

                startActivity(intent);

            }
        });
    }

    private void findTheViews(){

        retryBtn = findViewById(R.id.loseRetryBtn);

        mainBtn = findViewById(R.id.menuLoseBtn);

    }

    @Override
    public void onBackPressed() {}
}
