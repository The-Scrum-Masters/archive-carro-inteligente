package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by ryan on 19/09/16.
 */
public class SMSManager extends BroadcastReceiver
{
    private ISMSManager ismsManager;

    //ONLY USED SO THAT ANDROID CAN CREATE THE OBJECT (otherwise AndroidManifest.xml will have an error)
    public SMSManager(ISMSManager ismsManager, Context context)
    {
        this.ismsManager = ismsManager;

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        context.registerReceiver(this, mIntentFilter);
    }


    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("GOT A TEXT!!!!!!!!!");
        System.out.println("GOT A TEXT!!!!!!!!!");
        System.out.println("GOT A TEXT!!!!!!!!!");
        String messageBody = null;
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction()))
        {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent))
            {
                messageBody = smsMessage.getMessageBody();
            }
        }
        ismsManager.SMSReceived(messageBody);
    }


}
