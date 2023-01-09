package es.unizar.eina.hotel.gestion.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reservation";
    private static final int DATABASE_VERSION = 2;

    private static final String TAG = "ResDbAdapter";

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
            "create table reservation (_id integer primary key autoincrement, "
                    + "nombreCliente text not null, movilCliente text not null,precio double not null ,entrada text not null  ,salida text not null );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        onCreate(db);
    }
}