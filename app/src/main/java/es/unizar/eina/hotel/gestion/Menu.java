package es.unizar.eina.hotel.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.unizar.eina.hotel.R;
import es.unizar.eina.hotel.habitaciones.Hab;
import es.unizar.eina.hotel.habitaciones.RoomEdit;

import es.unizar.eina.hotel.reservas.Res;
import es.unizar.eina.hotel.reservas.ReservationEdit;

public class Menu extends AppCompatActivity {

    private static final int ACTIVITY_CREATE_ROOM = 0;
    private static final int ACTIVITY_CREATE_RES = 1;
    private static final int ACTIVITY_LIST_ROOMS = 2;
    private static final int ACTIVITY_LIST_RES = 3;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Button sRoom = (Button) findViewById(R.id.showRoom);
        Button sRes = (Button) findViewById(R.id.showRes);
        Button cRoom = (Button) findViewById(R.id.createRoom);
        Button cRes = (Button) findViewById(R.id.createRes);

        sRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listRooms();
            }
        });


         sRes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        listReservations();
        }
        });


        cRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRoom();
            }
        });


        cRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            createRes();
        }
    });

    }

    private void listRooms() {
        Intent i = new Intent(this, Hab.class);
        startActivityForResult(i, ACTIVITY_LIST_ROOMS);
    }



     private void listReservations() {
     Intent i = new Intent(this, Res.class);
     startActivityForResult(i, ACTIVITY_LIST_RES);
     }

    private void createRoom() {
        Intent i = new Intent(this, RoomEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE_ROOM);
    }



     private void createRes() {
     Intent i = new Intent(this, ReservationEdit.class);
     startActivityForResult(i, ACTIVITY_CREATE_RES);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }
}