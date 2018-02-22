package rbs.caryourday.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME="ListItemsActivity";

    /*Used to take a snapshot*/
    ImageButton cameraButton;

    /*Used to turn the switch on and off*/
    Switch s;

    /*Referencing checkbox*/
    CheckBox ch;
    MenuItem check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        /*Making button reference*/
        cameraButton = findViewById(R.id.imageButton2);


        /*When the camera button is clicked...*/
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Takes photo*/
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    /*By calling this method, you are effectively calling onActivityResult()
                    which is starts on line 141*/
                    startActivityForResult(takePicture, 1);
                }
            }
        });
        /*Step 8*/
        /*This is the Switch object we declared at the top of the class,
         * and we're also connecting the ID of the Switch button to the object s
         * You can find the IDs of your buttons in their .xml files. So for ListItemsActivity.java,
         * you'd look in activity_login.xml*/
        s = findViewById(R.id.switch1);


        /*Here we are giving the Switch some function. It "listens" to a click */
        s.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Context context = getApplicationContext();
                CharSequence on = "Switch is On";
                CharSequence off = "Switch is Off";
                int durationOn = Toast.LENGTH_SHORT;
                int durationOff = Toast.LENGTH_LONG;

                /*If the switch is turned on, display "Switch is on"*/
                if(s.isChecked()){
                    Toast toastOn = Toast.makeText(context, on, durationOn);
                    toastOn.show();
                }

                /*If the switch is turned off, display "Switch is off*/
                else if (!(s.isChecked())){
                    Toast toastOff = Toast.makeText(context, off, durationOff);
                    toastOff.show();

                }//end of ifs

            }//end of onClickListener()
        });
        /*Making a Checkbox object, and storing the checkBox ID inside of it. Why?
         * We're doing this because we want to give the checkbox some sort of function,
         * so we need to connect it to an object, like ch, so we can manipulate the
         * checkbox however we want. In this case, what we want to do is: Whenever the user
         * clicks the checkbox, we want to give them the option of exiting the current
         * activity. If they select OK, they would be sent back to StartActivity. If they select
         * cancel, then the box will disappear and they'll remain in ListItemsActivity*/
        CheckBox ch = findViewById(R.id.checkBox);


        /*Step 10*/
        /*This is where all that function occurs which I mentioned above.*/
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                builder.setMessage(R.string.dialog_message)

                        .setTitle(R.string.dialog_title)

                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){

                            }
                        })
                        .show();
            }
        });
    }//end of onCreate()

    /*Whenever we take a picture with the button, the picture we took will replace the camera icon
     * that was initially there before we took any picture.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Replacing button's image with the picture we just took*/
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cameraButton.setImageBitmap(imageBitmap);
        }

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
