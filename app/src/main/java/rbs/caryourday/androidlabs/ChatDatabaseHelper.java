package rbs.caryourday.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String TABLE_NAME = "Chats";
    protected static final String KEY_MESSAGE = "Message";
    protected static final String KEY_id = "id";
    /*Lab 5 Step 2: Creating static final variables, and a constructor*/
    private static final String DATABASE_NAME = "MyDB";
    private static final int VERSION_NUM = 3;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /*Lab 5 Step 3: creating an onCreate(SQLiteDatabase db)*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_id + " INTEGER PRIMARY KEY, " +
                KEY_MESSAGE + ");"
        );

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }//end of onCreate()

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        /*Drop table command...*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        /*...and then recreating the database, with one less table to worry
         * about*/
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, Old Version = " + oldVer + "New Version = " + newVer);

    }

}//end of class
