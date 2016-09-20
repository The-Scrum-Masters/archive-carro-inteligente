package com.TheScrumMasters.TrolleyReader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.TheScrumMasters.TrolleyReader.UtilityClasses.ISMSManager;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.SMSManager;

public class NotificationClient extends AppCompatActivity implements ISMSManager
{
    SMSManager ismsManager;

    TextView MessageText;
    TextView StatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_client);

        ismsManager = new SMSManager(this);

        MessageText = ((TextView) findViewById(R.id.Message_Out));
        StatusText = ((TextView) findViewById(R.id.Status_Out));
    }

    @Override
    public void SMSReceived(String message)
    {;
        StatusText.setText("Read Message!");
        StatusText.setTextColor(Color.GREEN);
        MessageText.setText(message);
    }

}
