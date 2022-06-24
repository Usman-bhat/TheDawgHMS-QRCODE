package com.usman.hmsqrcodeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.usman.hmsqrcodeapp.Utilss.NetworkChangerListener;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ImageView logout;
    Dialog dialog1;
    View mainc;
    NetworkChangerListener networkChangerListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();

        mainc=findViewById( R.id.mainc );
        networkChangerListener = new NetworkChangerListener(mainc);

        fab= findViewById( R.id.fab );
        logout = findViewById( R.id.logout );
        SharedPreferences sp = getSharedPreferences( "data",MODE_PRIVATE );
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1=new Dialog( MainActivity.this );
                dialog1.setContentView( R.layout.custom_logout_dialog );
                dialog1.setCancelable( true );
                dialog1.getWindow().getAttributes().windowAnimations=R.style.animation1;
                Button logoutbtn = dialog1.findViewById( R.id.logoutbtn );
                Button canclebtn = dialog1.findViewById( R.id.canclebtn );
                logoutbtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //logout
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove( "key" );
                        editor.commit();
                        startActivity( new Intent(getApplicationContext(), Login_Activity.class) );
                        finish();
                        Toast.makeText( MainActivity.this, "Logged out Successfully", Toast.LENGTH_SHORT ).show();
                        dialog1.dismiss();
                    }
                } );
                canclebtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                } );
                dialog1.show();

            }
        } );

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(),QrScannerActivity.class) );
            }
        } );
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver( networkChangerListener,filter );
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver( networkChangerListener );
        super.onStop();
    }





}