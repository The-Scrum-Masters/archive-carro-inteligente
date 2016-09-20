package com.TheScrumMasters.TrolleyReader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class NotificationServer extends AppCompatActivity
{

    Spinner bayChooser;

    ProgressBar bay0;
    ProgressBar bay1;
    ProgressBar bay2;

    String[] bays;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_server);

        bayChooser = ((Spinner) findViewById(R.id.Bay_Spinner));

        bay0 = ((ProgressBar) findViewById(R.id.Bay0_ProgressBar));
        bay1 = ((ProgressBar) findViewById(R.id.Bay1_ProgressBar));
        bay2 = ((ProgressBar) findViewById(R.id.Bay2_ProgressBar));

        bays = new String[] {"Bay 1", "Bay 2", "Bay 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, bays);
        bayChooser.setAdapter(adapter);

    }

    public void addTrolley_onClick(View v)
    {
        int chosenBay = bayChooser.getSelectedItemPosition();
        ProgressBar bay = getBayFromPosition(chosenBay);
        if (bay == null)
        {
            Toast.makeText(this, "Bay not selected",Toast.LENGTH_SHORT).show();
            return;
        }
        int newCapacity = bay.getProgress() + 1;
        if (newCapacity > bay.getMax())
        {
            Toast.makeText(this, "already at max",Toast.LENGTH_SHORT).show();
            return;
        }
        bay.setProgress(newCapacity);
        //checkBays();
    }

    public void removeTrolley_onClick(View v)
    {
        int chosenBay = bayChooser.getSelectedItemPosition();
        ProgressBar bay = getBayFromPosition(chosenBay);
        if (bay == null)
        {
            Toast.makeText(this, "Bay not selected",Toast.LENGTH_SHORT).show();
            return;
        }
        int newCapacity = bay.getProgress() - 1;
        if (newCapacity < 0)
        {
            Toast.makeText(this, "already at min",Toast.LENGTH_SHORT).show();
            return;
        }
        bay.setProgress(newCapacity);
        //checkBays();
    }

    private ProgressBar getBayFromPosition(int chosenBay)
    {
        switch(chosenBay)
        {
            case 0: return bay0;
            case 1: return bay1;
            case 2: return bay2;
        }
        return null;
    }
}
