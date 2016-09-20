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

import com.TheScrumMasters.TrolleyReader.UtilityClasses.PermissionHandler;

import java.util.ArrayList;

public class NotificationManager extends AppCompatActivity
{
    SmsManager smsManager;
    PermissionHandler permissionHandler;
    final int INITIAL_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);

        smsManager = SmsManager.getDefault();
        permissionHandler = new PermissionHandler(this);
        permissionHandler.askPermission(this, permissionHandler.getAllPermissions(), INITIAL_REQUEST_CODE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        permissionHandler.getRequestResults(this, permissionHandler.getAllPermissions());
        boolean result = permissionHandler.getIfPermissionGranted(PermissionHandler.Permissions.SENDSMS);
        if (result)
        {
            Toast.makeText(this, "Thanks Send away!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Can't really send SMS's if denied, quitting mode.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

}
