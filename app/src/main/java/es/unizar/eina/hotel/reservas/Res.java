package es.unizar.eina.hotel.reservas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.hotel.R;

public class Res extends AppCompatActivity {
    private static final int ACTIVITY_EDIT=0;

    private static final int DELETE_ID = Menu.FIRST;
    private static final int EDIT_ID = Menu.FIRST + 1;
    private static final int ORDER_BY_ID = Menu.FIRST + 2;
    private static final int ORDER_BY_NAME = Menu.FIRST + 3;
    private static final int ORDER_BY_PHONE = Menu.FIRST + 4;


    private ResDbAdapter mDbHelper;
    private ListView mList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_res);

        mDbHelper = new ResDbAdapter(this);
        mDbHelper.open();
        mList = (ListView)findViewById(R.id.list);
        fillData();

        registerForContextMenu(mList);
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor resCursor = mDbHelper.fetchAllRes();

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] {ResDbAdapter.KEY_ID, ResDbAdapter. KEY_NOMBRE_CLIENTE ,
                ResDbAdapter. KEY_MOVIL_CLIENTE , ResDbAdapter. KEY_FECHAENT,
                ResDbAdapter. KEY_FECHASAL};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] {R.id.resId,  R.id.customerName, R.id.customerPhone, R.id.entryDate,
                R.id.outDate};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter res =
                new SimpleCursorAdapter(this, R.layout.res_row, resCursor, from, to);
        mList.setAdapter(res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, ORDER_BY_ID, Menu.NONE, R.string.order_by_res_id);
        menu.add(Menu.NONE, ORDER_BY_NAME, Menu.NONE, R.string.order_by_name);
        menu.add(Menu.NONE, ORDER_BY_PHONE, Menu.NONE, R.string.order_by_phone);
        return result;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ORDER_BY_ID:
                order_by_id();
                return true;
            case ORDER_BY_NAME:
                order_by_name();
                return true;
            case ORDER_BY_PHONE:
                order_by_phone();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.delete_res);
        menu.add(Menu.NONE, EDIT_ID, Menu.NONE, R.string.edit_res);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                        item.getMenuInfo();
                mDbHelper.deleteRes(info.id);
                fillData();
                return true;
            case EDIT_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                editRes(info.position, info.id);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void editRes(int position, long id) {
        Intent i = new Intent(this, ReservationEdit.class);
        i.putExtra(ResDbAdapter.KEY_ID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    private void order_by_id() {
        Cursor resCursor = mDbHelper.fetchAllRes();

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[] {ResDbAdapter.KEY_ID, ResDbAdapter. KEY_NOMBRE_CLIENTE ,
                ResDbAdapter. KEY_MOVIL_CLIENTE , ResDbAdapter. KEY_FECHAENT,
                ResDbAdapter. KEY_FECHASAL};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[] { R.id.resId, R.id.customerName, R.id.customerPhone, R.id.entryDate,
                R.id.outDate};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter res =
                new SimpleCursorAdapter(this, R.layout.res_row, resCursor, from, to);
        mList.setAdapter(res);

    }

    private void order_by_phone() {
        Cursor resCursor = mDbHelper.fetchAllResByPhone();

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[] {ResDbAdapter.KEY_ID, ResDbAdapter. KEY_NOMBRE_CLIENTE ,
                ResDbAdapter. KEY_MOVIL_CLIENTE , ResDbAdapter. KEY_FECHAENT,
                ResDbAdapter. KEY_FECHASAL};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[] { R.id.resId, R.id.customerName, R.id.customerPhone, R.id.entryDate,
                R.id.outDate};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter res =
                new SimpleCursorAdapter(this, R.layout.res_row, resCursor, from, to);
        mList.setAdapter(res);
    }

    private void order_by_name() {
        Cursor resCursor = mDbHelper.fetchAllResByName();

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[] {ResDbAdapter.KEY_ID, ResDbAdapter. KEY_NOMBRE_CLIENTE ,
                ResDbAdapter. KEY_MOVIL_CLIENTE , ResDbAdapter. KEY_FECHAENT,
                ResDbAdapter. KEY_FECHASAL};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[] { R.id.resId, R.id.customerName, R.id.customerPhone, R.id.entryDate,
                R.id.outDate};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter res =
                new SimpleCursorAdapter(this, R.layout.res_row, resCursor, from, to);
        mList.setAdapter(res);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

}