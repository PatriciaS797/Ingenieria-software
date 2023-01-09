package es.unizar.eina.hotel.habitaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import es.unizar.eina.hotel.gestion.database.DatabaseHelper;

/**
 * Simple rooms database access helper class. Defines the basic CRUD operations
 * for our hotel reservation managing app, and gives the ability to list all rooms as well as
 * retrieve or modify a specific room.
 */
public class HabDbAdapter {

    public static final String KEY_ID = "_id";
    public static final String KEY_MAX_OCCUPANTS = "maxOcup";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PRICE1PER = "price1Per";
    public static final String KEY_RECHARGE = "additionalCharge";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_TABLE = "rooms";

    private final Context mCtx;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public HabDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the rooms database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public HabDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @param id the title of the note
     * @param maxOcup the body of the note
     * @return rowId or -1 if failed
     */
    public long createRoom(long id, int maxOcup, String desc, double price, double addCharge) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, id);
        initialValues.put(KEY_MAX_OCCUPANTS, maxOcup);
        initialValues.put(KEY_DESCRIPTION, desc);
        initialValues.put(KEY_PRICE1PER, price);
        initialValues.put(KEY_RECHARGE, addCharge);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given roomId
     *
     * @param roomId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteRoom(long roomId) {

        return mDb.delete(DATABASE_TABLE, KEY_ID + "=" + roomId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all rooms in the database
     *
     * @return Cursor over all rooms
     */
    public Cursor fetchAllRooms() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_MAX_OCCUPANTS,
                        KEY_DESCRIPTION, KEY_PRICE1PER, KEY_RECHARGE}, null, null,
                null, null, null, null);
    }

    /**
     * Return a Cursor over the list of all rooms in the database, ordered by their ID
     *
     * @return Cursor over all rooms ordered as specified
     */
    public Cursor fetchAllRoomsByID() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_MAX_OCCUPANTS,
                        KEY_DESCRIPTION, KEY_PRICE1PER, KEY_RECHARGE}, null, null,
                null, null, KEY_ID, null);
    }

    /**
     * Return a Cursor over the list of all rooms in the database, ordered by their price
     *
     * @return Cursor over all rooms ordered as specified
     */
    public Cursor fetchAllRoomsByPrice() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_MAX_OCCUPANTS,
                        KEY_DESCRIPTION, KEY_PRICE1PER, KEY_RECHARGE}, null, null,
                null, null, KEY_PRICE1PER, null);
    }

    /**
     * Return a Cursor over the list of all rooms in the database, ordered by their price
     *
     * @return Cursor over all rooms ordered as specified
     */
    public Cursor fetchAllRoomsByOc() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_MAX_OCCUPANTS,
                        KEY_DESCRIPTION, KEY_PRICE1PER, KEY_RECHARGE}, null, null,
                null, null, KEY_MAX_OCCUPANTS, null);
    }


    /**
     * Return a Cursor positioned at the room that matches the given roomId
     *
     * @param roomId id of room to retrieve
     * @return Cursor positioned to matching room, if found
     * @throws SQLException if room could not be found/retrieved
     */
    public Cursor fetchRoom(long roomId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ID,
                                KEY_MAX_OCCUPANTS, KEY_DESCRIPTION, KEY_PRICE1PER, KEY_RECHARGE},
                        KEY_ID + "=" + roomId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update the room using the details provided. The room to be updated is
     * specified using the roomId, and it is altered to use the occupation, description,
     * price per one person and additional recharge values passed in
     * @param roomId id of note to update
     * @param maxOcup
     * @param desc
     * @param price
     * @param recharge
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateRoom(long roomId, int maxOcup, String desc, double price, double recharge) {
        ContentValues args = new ContentValues();
        args.put(KEY_MAX_OCCUPANTS, maxOcup);
        args.put(KEY_DESCRIPTION, desc);
        args.put(KEY_PRICE1PER, price);
        args.put(KEY_RECHARGE, recharge);

        return mDb.update(DATABASE_TABLE, args, KEY_ID + "=" + roomId, null) > 0;
    }
}
