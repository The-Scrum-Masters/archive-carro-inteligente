package com.TheScrumMasters.TrolleyReader;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TrolleyReader extends AppCompatActivity
{
    private NFCHandler nfcHandler;
    private TextView DataOut;
    private TextView Status;
    private EditText IP;
    private EditText Port;
    private EditText Bay;

    private UDPMessageSender server;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trolley_reader);

        nfcHandler = new NFCHandler(this, false);
        DataOut = (TextView)findViewById(R.id.DataOut);
        Status = (TextView)findViewById(R.id.Status);
        IP = ((EditText) findViewById(R.id.IPDestEditText));
        Port = ((EditText) findViewById(R.id.PortDestEditText));
        Bay = ((EditText) findViewById(R.id.BayEditText));

        DataOut.setText("");

    }

    @Override
    public void onPause()
    {
        nfcHandler.disableForegroundDispatch();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        //nfcHandler.enableForegroundDispatch(); IDK why it doesn't work, but the below does

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
        IntentFilter[] intentFiltersArray = new IntentFilter[] {};
        String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };
        NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
        nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        //called when a new tag is read
        nfcHandler.handleNewRead(intent);
        Status.setText("Reading Tag");
        Status.setTextColor(Color.BLACK);
        System.out.println("read tag");
        try
        {
            DataOut.setText(nfcHandler.getTagText());
            Status.setText("Read Tag!");
            Status.setTextColor(Color.GREEN);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "read error", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendTxt_onClick(View view)
    {
        String message = DataOut.getText().toString();
        String BayString = Bay.getText().toString();
        if (message == "")
        {
            //if no nfc tag has been read
            Toast.makeText(this, "please scan an nfc tag first before sending", Toast.LENGTH_SHORT).show();
            return;
        }
        message = "TRIO>"+BayString + ":" + message;
        //get network info
        String address = IP.getText().toString();
        String portText= Port.getText().toString();
        int port = Integer.parseInt(portText);

        try
        {
            server = new UDPMessageSender();
            server.runUdpClient(message, address, port);
            Toast.makeText(this, "tried to send", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "couldn't send soz", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



}
