package rbs.caryourday.androidlabs;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";

    final ArrayList<String> messageArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        final ListView listView = findViewById(R.id.ListView);
        final EditText edittext = findViewById(R.id.EditText);
        final Button button3 = findViewById(R.id.button3);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);


        /*Send button*/
        button3.setOnClickListener(new View.OnClickListener() {

            /*Saving EditText to the messagesList ArrayList*/
            @Override
            public void onClick(View view) {
                String s = edittext.getText().toString();
                messageArray.add(s);
                messageAdapter.notifyDataSetChanged();
                edittext.setText("");

            }

        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {


        public ChatAdapter(Context ctx) {

            super(ctx, 0);
        }

        /*Lab 4 Step 6: 3 methods for performing different tasks*/
        public int getCount() {

            return messageArray.size();
        }

        public String getItem(int position) {
            return messageArray.get(position);
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById((R.id.message_text));
            message.setText(getItem(position));
            return result;
        }
    }
}


