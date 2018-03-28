package rbs.caryourday.androidlabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
    }

    private class MessageFragment {

        protected void onCreateView() {


            LayoutInflater inflater = MessageDetails.this.getLayoutInflater();

            View view = null;

            view = inflater.inflate(R.layout.activity_message_details, null);
        }
    }
}