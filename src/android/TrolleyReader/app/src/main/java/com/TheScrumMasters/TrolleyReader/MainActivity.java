package com.TheScrumMasters.TrolleyReader;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.TheScrumMasters.TrolleyReader.UtilityClasses.NFCHandler;

public class MainActivity extends AppCompatActivity
{

    private NFCHandler nfcHandler;
    private boolean hasNFCReader;

    //textviews, set in getTextViews()
    TextView nfcType;
    TextView nfcID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTextViews();
        nfcHandler = new NFCHandler(this, false);
        hasNFCReader = nfcHandler.doesDeviceHaveReader();

        if (!hasNFCReader)
            disableNFC();



        Intent intent = getIntent();
        if (intent != null && hasNFCReader)
        {
            nfcHandler.handleNewRead(intent);
        }

    }

    @Override
    public void onPause()
    {
        if (hasNFCReader)
        {
            nfcHandler.disableForegroundDispatch();
        }
        super.onPause();
    }

    @Override
    public void onResume()
    {
        //nfcHandler.enableForegroundDispatch(); IDK why it doesn't work, but the below does
        if (hasNFCReader)
        {
            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[] {};
            String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }

        super.onResume();
    }


    @Override
    protected void onNewIntent(Intent intent)
    {
        //called when a new tag is read.
        //Tag tag = intent.getParcelableExtra(NfcAdapter.class.getName());
        //Toast.makeText(this, NfcAdapter.class.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Read a new Tag", Toast.LENGTH_SHORT).show();
        nfcHandler.handleNewRead(intent);
        nfcID.setText(nfcHandler.getTrolleyID());
        nfcType.setText(nfcHandler.getCentreID());
    }

    public void WriteTagMode_onClick(View v)
    {
        nfcHandler.disableForegroundDispatch();
        startActivity(new Intent(this, TrolleyWriter.class));
    }

    public void ReadTagMode_onClick(View v)
    {
        nfcHandler.disableForegroundDispatch();
        startActivity(new Intent(this, TrolleyReader.class));
    }

    public void NotificationMode_onClick(View v)
    {
        startActivity(new Intent(this, NotificationManager.class));
    }

    public void NotificationClient_onClick(View v)
    {
        startActivity(new Intent(this, NotificationClient.class));
    }

    public void NotificationServer_onClick(View v)
    {
        startActivity(new Intent(this, NotificationServer.class));
    }

    private void getTextViews()
    {
        nfcType = (TextView) findViewById(R.id.NFC_Type_Out);
        nfcID = (TextView) findViewById(R.id.NFC_ID_Out);
    }

    private void disableNFC()
    {
        Toast.makeText(this, "No Nfc reader detected disabling relevant features", Toast.LENGTH_LONG).show();
        findViewById(R.id.writeTagMode).setEnabled(false);
        findViewById(R.id.readTagMode).setEnabled(false);
    }

}
