
package es.unizar.eina.hotel.reservas;

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
public class ResDbAdapter {

    public static final String KEY_ID = "_id";
    public static final String KEY_NOMBRE_CLIENTE = "nombreCliente";
    public static final String KEY_MOVIL_CLIENTE = "movilCliente";
    public static final String KEY_FECHAENT = "entrada";
    public static final String KEY_FECHASAL = "salida";
    public static final String KEY_PRECIO= "precio";
    public static final String KEY_ID_HABS= "hab";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_TABLE = "reservation";
    private static final String DATABASE_TABLE2 = "HabInReservation";
    private final Context mCtx;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public ResDbAdapter(Context ctx) {
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
    public ResDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getReadableDatabase();
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
     *
     */
    public long createRes(long id, String movil, String nombre, double price,String entrada,String salida) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, id);
        initialValues.put(KEY_NOMBRE_CLIENTE, nombre);
        initialValues.put(KEY_MOVIL_CLIENTE, movil);
        initialValues.put(KEY_PRECIO, price);
        initialValues.put(KEY_FECHAENT, entrada);
        initialValues.put(KEY_FECHAENT, salida);



        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given roomId
     *
     * @param resId id of reservation to delet
     * @return true if deleted, false otherwise
     */
    public boolean deleteRes(long resId) {

        return mDb.delete(DATABASE_TABLE, KEY_ID + "=" + resId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all rooms in the database
     *
     * @return Cursor over all rooms
     */
    public Cursor fetchAllRes() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NOMBRE_CLIENTE,
                        KEY_MOVIL_CLIENTE, KEY_PRECIO, KEY_FECHAENT,KEY_FECHASAL}, null, null,
                null, null, null);
    }

    /**
     * Return a Cursor positioned at the room that matches the given roomId
     *
     *
     * @return Cursor positioned to matching room, if found
     * @throws SQLException if room could not be found/retrieved
     */
    public Cursor fetchAllResbyId(Long resId) throws SQLException {

        Cursor resCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ID,
                                KEY_NOMBRE_CLIENTE, KEY_MOVIL_CLIENTE, KEY_PRECIO, KEY_FECHAENT,KEY_FECHASAL},
                        KEY_ID + "=" + resId, null,
                        null, null, null, null);
        if (resCursor != null) {
            resCursor.moveToFirst();
        }
        return resCursor;
    }


    /**
     * Return a Cursor positioned at the room that matches the given roomId
     *
     *
     * @return Cursor positioned to matching room, if found
     * @throws SQLException if room could not be found/retrieved
     */
    public Cursor fetchAllResByName() throws SQLException {

        Cursor resCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ID,
                                KEY_NOMBRE_CLIENTE, KEY_MOVIL_CLIENTE, KEY_PRECIO, KEY_FECHAENT,KEY_FECHASAL},
                        null, null,
                        null, null, KEY_NOMBRE_CLIENTE, null);
        if (resCursor != null) {
            resCursor.moveToFirst();
        }
        return resCursor;
    }


    /**
     * Return a Cursor positioned at the room that matches the given roomId
     *
     *
     * @return Cursor positioned to matching room, if found
     * @throws SQLException if room could not be found/retrieved
     */
    public Cursor fetchAllResByPhone() throws SQLException {

        Cursor resCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ID,
                                KEY_NOMBRE_CLIENTE, KEY_MOVIL_CLIENTE, KEY_PRECIO, KEY_FECHAENT,KEY_FECHASAL},
                        null, null,
                        null, null, KEY_MOVIL_CLIENTE, null);
        if (resCursor != null) {
            resCursor.moveToFirst();
        }
        return resCursor;
    }

    /**
     * Update the room using the details provided. The room to be updated is
     * specified using the roomId, and it is altered to use the occupation, description,
     * price per one person and additional recharge values passed in
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateRes(long resId, String movil, String nombre, double price,String entrada,String salida) {
        ContentValues args = new ContentValues();
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_NOMBRE_CLIENTE, nombre);
        initialValues.put(KEY_MOVIL_CLIENTE, movil);
        initialValues.put(KEY_PRECIO, price);
        initialValues.put(KEY_FECHAENT, entrada);
        initialValues.put(KEY_FECHAENT, salida);


        return mDb.update(DATABASE_TABLE, args, KEY_ID + "=" + resId, null) > 0;
    }
}