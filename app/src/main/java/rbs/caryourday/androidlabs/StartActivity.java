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

        startActivityForResult(intent, 10);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        /*Step 11*/
        /*Checking to see if we've returned to the StartActivity from ListItemsActivity*/
        if (requestCode == 10) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            if (resultCode == RESULT_OK) {

                /*A Toast object is a simple message box which will appear at the bottom of the screen*/
                String messagePassed = data.getStringExtra("Response");
                Toast toast = Toast.makeText(StartActivity.this, messagePassed, Toast.LENGTH_LONG);
                toast.show();

            }//end of second if
        }//end of first if
    }//end of onActivityResult()

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
