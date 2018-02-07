package rbs.caryourday.androidlabs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;




public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Saving the user's input*/
        /*Accessing the strings.xml string*/
        SharedPreferences s = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = s.edit();
        int numLaunches = s.getInt("NumRuns", 0);

        edit.putInt("NumRuns", numLaunches + 1); //ran one more time
        edit.commit();
       // edit.putString("Name", "Kayode");
        edit.commit();


        /*Making a reference to the button in the .xml file */
        Button loginbutton = findViewById(R.id.button2);

        /*Giving the button some function*/
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity();

            }
        });

   }

    protected void launchActivity(){
        /*Switching to the other Activity*/
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);

        /*This is what will take us to StartActivity*/
        startActivity(intent);
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
