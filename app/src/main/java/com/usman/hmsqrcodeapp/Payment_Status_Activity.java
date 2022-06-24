package com.usman.hmsqrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Payment_Status_Activity extends AppCompatActivity {

    LottieAnimationView animationView;
    TextView payment_status;
    Button okay_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_payment_status );
        getSupportActionBar().hide();
        animationView= findViewById( R.id.animationView2 );
        payment_status = findViewById( R.id.payment_status );
        okay_btn = findViewById( R.id.okay_btn );


        Intent intent = getIntent();
        String status = intent.getStringExtra( "status" );
        Log.e("me",status);

        if (status.equals("true")){
            animationView.setAnimation( "payment-successfull.json" );
            payment_status.setText( "Payment Successfull" );
        }
        else{
            animationView.setAnimation( "payment-unsuccessfull.json" );
            payment_status.setText( "Payment UnSuccessfull" );
        }
        okay_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Payment_Status_Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        } );




    }
}