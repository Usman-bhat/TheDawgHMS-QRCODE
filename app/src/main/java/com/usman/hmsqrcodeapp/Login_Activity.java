package com.usman.hmsqrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {


    EditText m_username,m_password;
    Button btnLogin;
    ProgressBar progressbar;
    TextView error1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getSupportActionBar().hide();
        SharedPreferences sp = getSharedPreferences( "data",MODE_PRIVATE );
        if (sp.contains( "key" )){
            startActivity( new Intent(getApplicationContext(), MainActivity.class) );
            finish();
        }

        m_username = findViewById( R.id.m_username );
        m_password = findViewById( R.id.m_password );
        btnLogin= findViewById( R.id.btnLogin );
        error1= findViewById( R.id.error1 );
        progressbar = findViewById( R.id.progressbar );
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setVisibility( View.INVISIBLE );
                progressbar.setVisibility( View.VISIBLE );
                Log.e("me",m_username.getText().toString().trim());
                Log.e("me",m_password.getText().toString().trim());
                loginProcess(m_username.getText().toString().trim(),m_password.getText().toString().trim());
            }
        } );

    }

    public void loginProcess(String name, String Password){
        if(name.equals( "Cook123") & Password.equals( "123456" )){
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            SharedPreferences sp = getSharedPreferences( "data",MODE_PRIVATE );
            SharedPreferences.Editor editor = sp.edit();
            editor.putString( "key",name );
            editor.commit();
            startActivity( intent );
            finish();
            Toast.makeText( Login_Activity.this, "Logged Successfully", Toast.LENGTH_SHORT ).show();

        }else{
            btnLogin.setVisibility( View.VISIBLE );
            progressbar.setVisibility( View.INVISIBLE );
            error1.setText( "Wrong Username Or Password" );
        }
    }

}