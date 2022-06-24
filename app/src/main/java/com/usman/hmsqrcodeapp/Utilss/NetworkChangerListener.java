package com.usman.hmsqrcodeapp.Utilss;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.snackbar.Snackbar;
import com.usman.hmsqrcodeapp.R;

public class NetworkChangerListener extends BroadcastReceiver
{

    View main;

    public NetworkChangerListener(View main) {
        this.main = main;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!com.usman.hmsqrcodeapp.Utilss.Common.isConnectedToInternet( context )){
            Snackbar snackbar = Snackbar.make( main,"No internet Please Try Again!", Snackbar.LENGTH_INDEFINITE )
                    .setAction( "RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    } );
            snackbar.setActionTextColor( Color.RED ).show();

        }
    }
}
