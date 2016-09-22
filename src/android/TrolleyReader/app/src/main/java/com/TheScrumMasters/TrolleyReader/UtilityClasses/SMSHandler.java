package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

/**
 * Created by ryan on 19/09/16.
 */
public class SMSHandler extends BroadcastReceiver
{
    private ISMSHandler ismsHandler;
    private SmsManager smsManager;

    Context context;

    /* receiver is registered here rather than in the android manifest because
     * we can instantiate it properly with it's interface whereas android cannot
     */
    public SMSHandler(ISMSHandler ismsHandler, Context context)
    {
        this.ismsHandler = ismsHandler;
        smsManager = SmsManager.getDefault();
        this.context = context;


        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        context.registerReceiver(this, mIntentFilter);
    }

    public void sendMessage(String phoneNumber, String message) throws IllegalArgumentException
    {
        ArrayList<String> splitMessage = smsManager.divideMessage(message);
        smsManager.sendMultipartTextMessage(phoneNumber, null, splitMessage, null, null);
        System.out.println("----SMS Handler INFO-----");
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Sending Message");
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
        ismsHandler.SMSReceived(messageBody);
    }

    public void destroy()
    {
        context.unregisterReceiver(this);
    }


}
