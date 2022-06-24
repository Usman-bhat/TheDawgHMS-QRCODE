package com.usman.hmsqrcodeapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.lang.ref.ReferenceQueue;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        scannerView = new ZXingScannerView( this );
        setContentView(scannerView);
        Dexter.withContext( getApplicationContext() )
                .withPermission( Manifest.permission.CAMERA )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                } ).check();
    }

    @Override
    public void handleResult(Result result) {
        Log.e("me",result.getText());


        AlertDialog.Builder builder = new AlertDialog.Builder(QrScannerActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.payment_confirm_dialog, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        TextView name,rollno;
        name = dialogView.findViewById(R.id.student_name);
        rollno = dialogView.findViewById(R.id.student_rollno);
        String[] text1 = result.getText().split(":");
        Log.e("me",text1[0]);
        Log.e("me",text1[1]);

        String rollno1 = text1[0];
        name.setText("Name: "+ text1[1] );
        rollno.setText("Roll No: "+ rollno1 );

        Button cancle_btn = dialogView.findViewById( R.id.cancle_btn );
        Button submit_btn =dialogView.findViewById( R.id.submit_btn );
        cancle_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onBackPressed();
            }
        } );
        submit_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue = Volley.newRequestQueue(QrScannerActivity.this);
                rollno.setText("Roll No: "+ text1[0] );
                String url = "http://192.168.43.38/HMS/API-Android/QrCodeAPI.php?rollno="+rollno1;
                Log.e( "me",url );

                StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                Log.e( "me",response );
                                if (response.equals( "true" )){
                                    Toast.makeText( QrScannerActivity.this, "success", Toast.LENGTH_LONG ).show();
                                    Intent i=new Intent(QrScannerActivity.this,Payment_Status_Activity.class);
                                    i.putExtra( "status","true" );
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    Toast.makeText( QrScannerActivity.this, "UnSuccess", Toast.LENGTH_LONG ).show();
                                    Intent i=new Intent(QrScannerActivity.this,Payment_Status_Activity.class);
                                    i.putExtra( "status","false" );
                                    startActivity(i);
                                    finish();

                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText( QrScannerActivity.this, error.toString(), Toast.LENGTH_LONG ).show();
                            }
                        });
                requestQueue.add(stringRequest);

            }
        } );

        alertDialog.show();


//        MainActivity.student_details.setText( result.getText() );
//        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler( this );
        scannerView.startCamera();
    }
}