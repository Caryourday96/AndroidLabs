package rbs.caryourday.androidlabs;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static rbs.caryourday.androidlabs.ChatDatabaseHelper.KEY_MESSAGE;
import static rbs.caryourday.androidlabs.ChatDatabaseHelper.KEY_id;
import static rbs.caryourday.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";

    final ArrayList<String> messageArray = new ArrayList<>();
    private static SQLiteDatabase db;
    private boolean frameLayoutExists = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        final ListView listView = findViewById(R.id.ListView);
        final EditText edittext = findViewById(R.id.EditText);
        final Button button3 = findViewById(R.id.button3);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);


        final Context context = getApplicationContext();
        final ChatDatabaseHelper chatDatabaseHelper = new ChatDatabaseHelper(context);
        db = chatDatabaseHelper.getWritableDatabase();
        final ContentValues cv = new ContentValues();

        final Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_id, KEY_MESSAGE},
                null, null, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                messageArray.add(message);
                Log.i(ACTIVITY_NAME, "SQL_MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        /*Lab 5 Step 5: Printing information about the cursor:*/
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());

        /*Printing out each column name*/
        for (int num = 0; num < cursor.getColumnCount(); num++) {
            Log.i(ACTIVITY_NAME, cursor.getColumnName(num));
        }


        /*Send button*/
        button3.setOnClickListener(new View.OnClickListener() {

            /*Saving EditText to the messagesList ArrayList*/
            @Override
            public void onClick(View view) {
                String s = edittext.getText().toString();
                messageArray.add(s);
                messageAdapter.notifyDataSetChanged();
                edittext.setText("");
                cv.put(KEY_MESSAGE, s);
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, cv);

            }

        });
    }

    protected void onDestroy() {
        super.onDestroy();
        /*Closing all super() versions*/
        db.close();

        /*Closing the database that you opened in onCreate()*/
        //cursor.close();
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
        //  public long getItemId(int position){
        //        return db
        //   }

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


