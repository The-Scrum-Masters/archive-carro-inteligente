package com.TheScrumMasters.TrolleyReader;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.TheScrumMasters.TrolleyReader.UtilityClasses.Bay;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.ISMSHandler;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.SMSNotification;
import com.TheScrumMasters.TrolleyReader.UtilityClasses.SMSHandler;

import java.util.HashMap;

public class NotificationServer extends AppCompatActivity implements ISMSHandler
{

    Spinner bayChooser;
    /**
     * This field is the percent of capacity the bays reach before alerting staff
     */
    private final int LOW_BAY = Color.YELLOW;
    private final int NORMAL_BAY = Color.MAGENTA;


    SMSHandler smsHandler;

    private HashMap<Bay, ProgressBar> bays;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_server);

        smsHandler = new SMSHandler(this,this);

        bayChooser = ((Spinner) findViewById(R.id.Bay_Spinner));

        Bay bay0 = new Bay(20,20,0.75,0);
        Bay bay1 = new Bay(20,20,0.50,1);
        Bay bay2 = new Bay(20,20,0.20,2);

        ProgressBar progressBar_bay0 = ((ProgressBar) findViewById(R.id.Bay0_ProgressBar));
        ProgressBar progressBar_bay1 = ((ProgressBar) findViewById(R.id.Bay1_ProgressBar));
        ProgressBar progressBar_bay2 = ((ProgressBar) findViewById(R.id.Bay2_ProgressBar));



        Bay[] bayObjects = new Bay[] {bay0, bay1, bay2};
        ProgressBar[] baysArray = new ProgressBar[] {progressBar_bay0,progressBar_bay1,progressBar_bay2};
        String[] baysStringArray = new String[] {"Bay 1", "Bay 2", "Bay 3"};

        bays = new HashMap<>(baysStringArray.length);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, baysStringArray);
        bayChooser.setAdapter(adapter);

        for(int i=0;i<baysArray.length;i++)
        {
            baysArray[i].setMax(bayObjects[i].getCapacity());
            baysArray[i].setProgress(bayObjects[i].getValue());
            bays.put(bayObjects[i], baysArray[i]);

            updateBayColours(bayObjects[i]);
        }

    }
    private void updateBayColours(Bay bay)
    {
        ProgressBar bar = bays.get(bay);
        int colour = bay.isLow() ? LOW_BAY : NORMAL_BAY;
        bar.setProgressTintList(ColorStateList.valueOf(colour));
    }

    public void addTrolley_onClick(View v)
    {
        int chosenBay = bayChooser.getSelectedItemPosition();
        Bay bay = getBayById(chosenBay);
        ProgressBar progressBar_bay = bays.get(bay);
        if (bay == null)
        {
            Toast.makeText(this, "Bay not selected",Toast.LENGTH_SHORT).show();
            return;
        }
        int newCapacity = bay.getValue() + 1;
        if (newCapacity > bay.getCapacity())
        {
            Toast.makeText(this, "already at max",Toast.LENGTH_SHORT).show();
            return;
        }
        bay.setValue(newCapacity);
        progressBar_bay.setProgress(newCapacity);
        checkForLowBays(bay, chosenBay);
    }

    public void removeTrolley_onClick(View v)
    {
        int chosenBay = bayChooser.getSelectedItemPosition();
        Bay bay = getBayById(chosenBay);
        ProgressBar progressBar_bay = bays.get(bay);
        if (bay == null)
        {
            Toast.makeText(this, "Bay not selected", Toast.LENGTH_SHORT).show();
            return;
        }
        int newCapacity = bay.getValue() - 1;
        if (newCapacity < 0)
        {
            Toast.makeText(this, "already at min", Toast.LENGTH_SHORT).show();
            return;
        }
        bay.setValue(newCapacity);
        progressBar_bay.setProgress(newCapacity);
        checkForLowBays(bay, chosenBay);
    }

    private Bay getBayById(int id)
    {
        for (Bay bay : bays.keySet())
        {
            if (bay.getId() == id)
            {
                return bay;
            }
        }
        return null;
    }


    private void checkForLowBays(Bay bay, int bayPosition)
    {
        if (bay.isLow())
        {
            final String message = SMSNotification.createJSONMessage("1","A Bay is running out of trolleys, would you like to accept a request to fix this?", "Bay" + bayPosition);
            Toast.makeText(this, "Low bay detected sending message", Toast.LENGTH_LONG).show();
            System.out.println(message);
            try
            {
                final String[] staffNumbers = getStaffNumbers();
                if (staffNumbers == null)
                {
                    return;
                }

            }
            catch (IllegalArgumentException e)
            {
                Toast.makeText(this, "Couldn't send message", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        updateBayColours(bay);
    }

    private String[] getStaffNumbers()
    {
        EditText phoneNumberText = ((EditText) findViewById(R.id.phoneNumber_EditText));
        String phoneNumbers = phoneNumberText.getText().toString();
        if (phoneNumbers.isEmpty())
        {
            Toast.makeText(this, "Phone numbers are empty, please enter atleast 1", Toast.LENGTH_LONG);
            return null;
        }
        //System.out.println("Phone numbers: ");
        //System.out.println(phoneNumbers);
        String[] phoneNumberArray = phoneNumbers.split("/\n/g");
        for (String str : phoneNumberArray)
            System.out.println(str);

        return phoneNumberArray;
    }

    @Override
    protected void onDestroy()
    {
        smsHandler.destroy();
        super.onDestroy();
    }

    @Override
    public void SMSReceived(String message)
    {
        Toast.makeText(this, "message received", Toast.LENGTH_LONG).show();
    }
}
