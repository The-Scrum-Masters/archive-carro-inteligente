package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

/**
 * Created by ryan on 19/09/16.
 */
public class SMSManager extends BroadcastReceiver
{
    private ISMSManager ismsManager;

    //ONLY USED SO THAT ANDROID CAN CREATE THE OBJECT (otherwise AndroidManifest.xml will have an error)
    public SMSManager()
    {

    }

    public SMSManager(ISMSManager ismsManager)
    {
        this.ismsManager = ismsManager;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
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
