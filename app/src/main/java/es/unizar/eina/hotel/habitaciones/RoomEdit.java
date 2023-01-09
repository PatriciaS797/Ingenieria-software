package es.unizar.eina.hotel.habitaciones;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.hotel.R;

public class RoomEdit extends AppCompatActivity {

    private Long _ID;
    private EditText mRoomId;
    private EditText mOccupation;
    private EditText mPrice;
    private EditText mRecharge;
    private EditText mDescription;
    private HabDbAdapter mDbHelper;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(HabDbAdapter.KEY_ID, _ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        Long aux = (_ID != null) ? _ID : null;
        if(aux == null) {
            if(!mRoomId.getText().toString().isEmpty()) {
                aux = Long.parseLong(mRoomId.getText().toString());
            }
        }
        String description = mDescription.getText().toString();
        double price = (mPrice.getText().toString().isEmpty()) ? 0 :
                Double.parseDouble(mPrice.getText().toString());
        double recharge = (mRecharge.getText().toString().isEmpty()) ? 0 :
                Double.parseDouble(mRecharge.getText().toString());
        int maxOc = (mOccupation.getText().toString().isEmpty()) ? 0 :
                Integer.parseInt(mOccupation.getText().toString());

        if(_ID == null && aux != null) {
            mDbHelper.createRoom(aux, maxOc, description, price, recharge);
        } else if (_ID != null && aux != null) {
            mDbHelper.updateRoom(_ID, maxOc, description, price, recharge);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new HabDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.edit_room);
        setTitle("Edit Room");

        mRoomId = (EditText) findViewById(R.id.roomId);
        mOccupation = (EditText) findViewById(R.id.roomOccupation);
        mPrice = (EditText) findViewById(R.id.roomPrice);
        mRecharge = (EditText) findViewById(R.id.roomRecharge);
        mDescription = (EditText) findViewById(R.id.roomDesc);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        _ID = (savedInstanceState == null) ? null:
                (Long)savedInstanceState.getSerializable(HabDbAdapter.KEY_ID);
        if(_ID == null) {
            Bundle extras = getIntent().getExtras();
            _ID = (extras != null) ? extras.getLong(HabDbAdapter.KEY_ID) : null;
        }
        populateFields();
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }

        });
    }

    private void populateFields() {
        if(_ID != null) {
            Cursor room = mDbHelper.fetchRoom(_ID);
            startManagingCursor(room);
            mRoomId.setText(room.getString(room.getColumnIndexOrThrow(HabDbAdapter.KEY_ID)));
            mDescription.setText(room.getString(room.
                    getColumnIndexOrThrow(HabDbAdapter.KEY_DESCRIPTION)));
            mOccupation.setText(room.getString(room.getColumnIndexOrThrow
                    (HabDbAdapter.KEY_MAX_OCCUPANTS)));
            mPrice.setText(room.getString(room.getColumnIndexOrThrow
                    (HabDbAdapter.KEY_PRICE1PER)));
            mRecharge.setText(room.getString(room.getColumnIndexOrThrow
                    (HabDbAdapter.KEY_RECHARGE)));
        }
    }
}
