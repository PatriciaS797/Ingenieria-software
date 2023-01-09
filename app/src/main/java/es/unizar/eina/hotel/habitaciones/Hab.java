package es.unizar.eina.hotel.habitaciones;

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

public class Hab extends AppCompatActivity {
    private static final int ACTIVITY_EDIT=0;

    private static final int DELETE_ID = Menu.FIRST;
    private static final int EDIT_ID = Menu.FIRST + 1;
    private static final int ORDER_BY_ID = Menu.FIRST + 2;
    private static final int ORDER_BY_OC = Menu.FIRST + 3;
    private static final int ORDER_BY_PRICE = Menu.FIRST + 4;


    private HabDbAdapter mDbHelper;
    private ListView mList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_rooms);

        mDbHelper = new HabDbAdapter(this);
        mDbHelper.open();
        mList = (ListView)findViewById(R.id.list);
        fillData();

        registerForContextMenu(mList);
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor roomsCursor = mDbHelper.fetchAllRooms();

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] {HabDbAdapter.KEY_ID, HabDbAdapter.KEY_MAX_OCCUPANTS,
                HabDbAdapter.KEY_PRICE1PER, HabDbAdapter.KEY_RECHARGE,
                HabDbAdapter.KEY_DESCRIPTION};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] {R.id.roomId, R.id.roomOccupation, R.id.roomPrice, R.id.roomRecharge,
                R.id.roomDesc};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.rooms_row, roomsCursor, from, to);
        mList.setAdapter(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, ORDER_BY_ID, Menu.NONE, R.string.order_by_room_id);
        menu.add(Menu.NONE, ORDER_BY_OC, Menu.NONE, R.string.order_by_maxOc);
        menu.add(Menu.NONE, ORDER_BY_PRICE, Menu.NONE, R.string.order_by_price);
        return result;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ORDER_BY_ID:
                order_by_id();
                return true;
            case ORDER_BY_OC:
                order_by_oc();
                return true;
            case ORDER_BY_PRICE:
                order_by_price();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.delete_room);
        menu.add(Menu.NONE, EDIT_ID, Menu.NONE, R.string.edit_room);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                        item.getMenuInfo();
                mDbHelper.deleteRoom(info.id);
                fillData();
                return true;
            case EDIT_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                editRoom(info.position, info.id);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void editRoom(int position, long id) {
        Intent i = new Intent(this, RoomEdit.class);
        i.putExtra(HabDbAdapter.KEY_ID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    private void order_by_id() {
        Cursor roomsCursor = mDbHelper.fetchAllRoomsByID();

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[] {HabDbAdapter.KEY_ID, HabDbAdapter.KEY_MAX_OCCUPANTS,
                HabDbAdapter.KEY_PRICE1PER, HabDbAdapter.KEY_RECHARGE,
                HabDbAdapter.KEY_DESCRIPTION};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[] { R.id.roomId, R.id.roomOccupation, R.id.roomPrice, R.id.roomRecharge,
                R.id.roomDesc};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.rooms_row, roomsCursor, from, to);
        mList.setAdapter(notes);

    }

    private void order_by_price() {
        Cursor roomsCursor = mDbHelper.fetchAllRoomsByPrice();

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[] {HabDbAdapter.KEY_ID, HabDbAdapter.KEY_MAX_OCCUPANTS,
                HabDbAdapter.KEY_PRICE1PER, HabDbAdapter.KEY_RECHARGE,
                HabDbAdapter.KEY_DESCRIPTION};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[] { R.id.roomId, R.id.roomOccupation, R.id.roomPrice, R.id.roomRecharge,
                R.id.roomDesc};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.rooms_row, roomsCursor, from, to);
        mList.setAdapter(notes);
    }

    private void order_by_oc() {
        Cursor roomsCursor = mDbHelper.fetchAllRoomsByOc();

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[] {HabDbAdapter.KEY_ID, HabDbAdapter.KEY_MAX_OCCUPANTS,
                HabDbAdapter.KEY_PRICE1PER, HabDbAdapter.KEY_RECHARGE,
                HabDbAdapter.KEY_DESCRIPTION};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[] { R.id.roomId, R.id.roomOccupation, R.id.roomPrice, R.id.roomRecharge,
                R.id.roomDesc};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.rooms_row, roomsCursor, from, to);
        mList.setAdapter(notes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

}