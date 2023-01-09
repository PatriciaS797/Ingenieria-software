package es.unizar.eina.hotel.reservas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.pm.PackageInfo ;
import android.content.pm.PackageManager ;

import es.unizar.eina.hotel.habitaciones.Hab;
import es.unizar.eina.hotel.send.SendAbstractionImpl;
import es.unizar.eina.hotel.send.WhatsAppImplementor;

import es.unizar.eina.hotel.send.SendImplementor;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.unizar.eina.hotel.R;
import es.unizar.eina.hotel.reservas.ResDbAdapter;

public class ReservationEdit extends AppCompatActivity {

    private EditText mCustomerName;
    private EditText mCustomerPhone;
    private EditText mPrecio;
    private EditText mFechaSal;
    private EditText mFechaEnt;
    private EditText mResId;
    private EditText mHabs;
    private Long _ID;
    private static final int WH_ID =  1;
    private static final int SMS_ID = 2;
    private ResDbAdapter mDbHelper;
    private Long aux;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(ResDbAdapter.KEY_ID, _ID);
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
            if(!mResId.getText().toString().isEmpty()) {
                aux = Long.parseLong(mResId.getText().toString());
            }

        }
        String nombre = mCustomerName.getText().toString();
        String movil= mCustomerPhone.getText().toString();
       // double precio= (mPrecio.getText().toString().isEmpty()) ? 0 :
         //       Double.parseDouble(mPrecio.getText().toString());
        String entrada  =  mFechaEnt.getText().toString();
        String salida  =  mFechaSal.getText().toString();


        if(_ID == null && aux != null) {
            mDbHelper.createRes(aux, nombre, movil, Double.parseDouble("0"), entrada,salida);
        } else if (_ID != null && aux != null) {
            mDbHelper.updateRes(_ID,nombre, movil, Double.parseDouble("0"), entrada,salida);
        }

    }


private void sendConfirmation(int type){
    SendAbstractionImpl a;

    a = new SendAbstractionImpl(this, "SMS");

    a.send(String.valueOf(mCustomerPhone), "holaaa");

}


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new ResDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.create_reservation);

        mResId = (EditText) findViewById(R.id.resId);
        mCustomerName = (EditText) findViewById(R.id.customerName);
        mCustomerPhone = (EditText) findViewById(R.id.customerPhone);
        mFechaSal = (EditText) findViewById(R.id.checkOut);
        mFechaEnt = (EditText) findViewById(R.id.entryDate);
        //anyadir hab
      /*
        String habitaciones= String.valueOf((EditText) findViewById(R.id.Rooms_in_res));
        String[] tokens = habitaciones.split(",");
        EditText[] habs = new EditText[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
           habs[i].setText(tokens[i]) ;

        }
        mHabs=habs;
*/
        mHabs = (EditText) findViewById(R.id.Rooms_in_res);
        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        _ID = (savedInstanceState == null) ? null:
                (Long)savedInstanceState.getSerializable(ResDbAdapter.KEY_ID);
        if(_ID == null) {
            Bundle extras = getIntent().getExtras();
            _ID = (extras != null) ? extras.getLong(ResDbAdapter.KEY_ID) : null;
        }
        populateFields();
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent mIntent = new Intent();

                setResult(RESULT_OK, mIntent);
                finish();

            }

        });

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                sendConfirmation(2);
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
            Cursor res = mDbHelper.fetchAllRes();
            mCustomerName.setText(res.getString(res.
                    getColumnIndexOrThrow(ResDbAdapter.KEY_NOMBRE_CLIENTE)));
            mCustomerPhone.setText(res.getString(res.getColumnIndexOrThrow
                    (ResDbAdapter.KEY_MOVIL_CLIENTE)));
            mPrecio.setText(res.getString(res.getColumnIndexOrThrow
                    (ResDbAdapter.KEY_PRECIO)));
            mFechaEnt.setText(res.getString(res.getColumnIndexOrThrow
                    (ResDbAdapter.KEY_FECHAENT)));
            mFechaSal.setText(res.getString(res.getColumnIndexOrThrow
                    (ResDbAdapter.KEY_FECHASAL)));
            /*for (int i = 0; i < mHabs.length; i++) {
                mHabs[i].setText(res.getString(res.getColumnIndexOrThrow
                        (ResDbAdapter.KEY_ID_HABS)));
            }*/
            mHabs.setText(res.getString(res.getColumnIndexOrThrow
                    (ResDbAdapter.KEY_ID_HABS)));


        }
    }
}
