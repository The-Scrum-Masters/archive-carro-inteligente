package com.TheScrumMasters.TrolleyReader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.TheScrumMasters.TrolleyReader.UtilityClasses.ISMSHandler;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.PermissionHandler;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.SMSHandler;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.SMSNotification;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationClient extends AppCompatActivity implements ISMSHandler
{
    SMSHandler smsHandler;

    TextView MessageText;
    TextView StatusText;

    PermissionHandler permissionHandler;
    final int INITIAL_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_client);

        smsHandler = new SMSHandler(this, this);

        MessageText = ((TextView) findViewById(R.id.Message_Out));
        StatusText = ((TextView) findViewById(R.id.Status_Out));

        permissionHandler = new PermissionHandler(this);
        permissionHandler.askPermission(this, permissionHandler.getAllPermissions(), INITIAL_REQUEST_CODE);
    }

    @Override
    public void SMSReceived(String message)
    {
        StatusText.setText("Read Message!");
        StatusText.setTextColor(Color.GREEN);
        MessageText.setText(message);

        JSONObject parsedJSONMessage;
        int messageCode;
        String messageRecv;
        String bay;
        try
        {
            parsedJSONMessage =  SMSNotification.parseJSONMessage(message);
            messageCode = parsedJSONMessage.getInt("messageCode");
            messageRecv = parsedJSONMessage.getString("message");
            bay = parsedJSONMessage.getString("bay");

            notify(messageCode, messageRecv, bay);
        }
        catch (JSONException e)
        {
            Toast.makeText(this, "JSON couldn't be parsed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    private void notify(int messageCode, String message, String bay)
    {
        switch(messageCode)
        {
            case 1:
                lowTrolleyDialog(message,  bay);
                break;
            default:
                genericDialog(message);
                break;
        }
    }

    private void lowTrolleyDialog(String message, String bay)
    {
        String alertMessage = message + "\n" + "Low Bay: " + bay;
        new AlertDialog.Builder(this)
                .setTitle("Low Trolley alert!")
                .setMessage(alertMessage)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Replying yes to server", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Replying no to server", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void genericDialog(String message)
    {
        new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Replying yes to server", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Replying no to server", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
