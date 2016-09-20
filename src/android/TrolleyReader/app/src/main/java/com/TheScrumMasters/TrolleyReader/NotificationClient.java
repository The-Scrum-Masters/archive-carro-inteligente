package com.TheScrumMasters.TrolleyReader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.TheScrumMasters.TrolleyReader.UtilityClasses.ISMSManager;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.PermissionHandler;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.SMSManager;

public class NotificationClient extends AppCompatActivity implements ISMSManager
{
    SMSManager smsManager;

    TextView MessageText;
    TextView StatusText;

    PermissionHandler permissionHandler;
    final int INITIAL_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_client);

        smsManager = new SMSManager(this, this);

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
    }

}
