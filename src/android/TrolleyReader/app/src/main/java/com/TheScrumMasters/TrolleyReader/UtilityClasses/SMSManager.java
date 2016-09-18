package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

/**
 * Created by ryan on 19/09/16.
 */
public class SMSManager extends BroadcastReceiver implements PermissionInterface
{
    private Activity activity;



    public SMSManager(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction()))
        {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
            }
        }
    }

    @Override
    public void PermissionResultCallback(boolean result)
    {

    }
}
