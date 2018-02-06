package rbs.caryourday.androidlabs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME="StartActivity";
    Button  startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /*Storing reference to StartActivity button*/
        startButton = findViewById(R.id.button);

        /*Giving the button some function*/
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
                /*Method call to switch to the ListItems Activity*/
                launchListItemsActivity();
            }
        });
    }
    /* The method for the onclickview view - launchListItemsActivity*/
    protected void launchListItemsActivity() {
        Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);

        startActivityForResult(intent, 50);
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}
