package com.TheScrumMasters.TrolleyReader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificationManager extends AppCompatActivity
{
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);

        smsManager = SmsManager.getDefault();
        askPermissionforSMS();
    }

    public void sendSMS_onClick(View view)
    {

        String Message = ((EditText) findViewById(R.id.Message_EditText)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.Phone_EditText)).getText().toString();
        TextView ErrorOutTextView = ((TextView) findViewById(R.id.Error_Out));
        ArrayList<String> splitMessage = smsManager.divideMessage(Message);


        String validMessage = checkIfMessageValid(Message);

        if (validMessage !=  null)
        {
            ErrorOutTextView.setTextColor(Color.RED);
            ErrorOutTextView.setText(validMessage);
            return;
        }

        try
        {
            smsManager.sendMultipartTextMessage(phoneNumber, null, splitMessage, null, null);
            //smsManager.sendTextMessage(phoneNumber, null, Message, null, null);
            ErrorOutTextView.setTextColor(Color.GREEN);
            ErrorOutTextView.setText("Looks like the message sent!");
        }
        catch (Exception e)
        {
            ErrorOutTextView.setTextColor(Color.RED);
            ErrorOutTextView.setText("Couldn't send message, check permissions");
        }
    }

    //Will return null if it passed all checks
    //otherwise it will return error message
    private String checkIfMessageValid(String message)
    {
        if (message.length() > 918)
        {
            return "Message length exceeds 918 character, it is not possible to send this message";
        }
        return null;
    }


    private void askPermissionforSMS()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("You need to enable SMS for this to work");
                builder1.setCancelable(true);

                builder1.setNeutralButton(
                    "OK",
                    new DialogInterface.OnClickListener()
                    {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                    });
                AlertDialog alert11 = builder1.create();
                alert11.show();

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    Toast.makeText(this,"Thanks Send away!", Toast.LENGTH_LONG).show();

                } else {

                    // permission denied, boo!
                    Toast.makeText(this,"Can't really send SMS's if denied, quitting mode.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
