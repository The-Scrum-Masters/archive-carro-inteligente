package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by ryan on 30/08/16.
 * Used to manage reading and writing of tags
 */
public class NFCHandler {
    private Activity activity;
    private NfcAdapter adapter;
    private Tag tag;

    boolean hasNFCReader;

    //tag properties
    String TrolleyID;
    String CentreID;

    boolean writeMode = false;//describes which mode NFC handler is in to better expect the values of TrolleyID/CentreID

    public NFCHandler(Activity activity, boolean mode)
    {

        this.activity = activity;
        hasNFCReader = doesDeviceHaveReader();
        writeMode = mode;
        adapter = NfcAdapter.getDefaultAdapter(activity);
    }

    public void handleNewRead(Intent intent)
    {
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null){
            Toast.makeText(activity, "TAG INFO WAS NULL", Toast.LENGTH_SHORT).show();
            return;
        }
        //tag type
        CentreID = tag.toString();
        byte[] TagID = tag.getId();
        StringBuilder HexID = new StringBuilder();
        for (byte b : TagID) {
            HexID.append(String.format("%02X ", b));
        }
        //tag id
        TrolleyID = HexID.toString();
    }

    public void enableForegroundDispatch()
    {
        Intent nfcIntent = new Intent(activity, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, nfcIntent, 0);
        IntentFilter[] intentFiltersArray = new IntentFilter[] {};
        String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };


        adapter.enableForegroundDispatch(activity, pendingIntent, intentFiltersArray, techList);
        Toast.makeText(activity, "enabled dispatch", Toast.LENGTH_SHORT).show();
    }

    public void disableForegroundDispatch()
    {
        adapter.disableForegroundDispatch(activity);
    }

    //Tag writing functions
    public NdefMessage getNdefMessageFromText(String messageText)
    {
        try
        {
            // Get UTF-8 byte
            byte[] lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
            byte[] text = messageText.getBytes("UTF-8"); // Content in UTF-8

            int langSize = lang.length;
            int textLength = text.length;

            ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + langSize + textLength);
            payload.write((byte) (langSize & 0x1F));
            payload.write(lang, 0, langSize);
            payload.write(text, 0, textLength);
            NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
            return new NdefMessage(new NdefRecord[]{record});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeTag(Tag tag, NdefMessage message)
    {
        if (tag != null) {
            try {
                Ndef ndefTag = Ndef.get(tag);

                if (ndefTag == null) {
                    // Let's try to format the Tag in NDEF
                    NdefFormatable nForm = NdefFormatable.get(tag);
                    if (nForm != null) {
                        nForm.connect();
                        nForm.format(message);
                        nForm.close();
                    }
                }
                else {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(message);
                    ndefTag.close();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getTagText() throws Exception
    {
        if (tag == null)
        {
            throw new Exception("Please call handle new read as tag is null");
        }
        Ndef ndef = Ndef.get(tag);
        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records)
        {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT))
            {
                try
                {
                    return readText(ndefRecord);
                }
                catch (UnsupportedEncodingException e)
                {
                    Toast.makeText(activity, "Unsupported encoding", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return null;
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException
    {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    //True if it does, false if it doesn't
    public boolean doesDeviceHaveReader() {return adapter != null;}

    public boolean isReaderEnabled() {return adapter.isEnabled();}

    public String getTrolleyID()
    {
        return TrolleyID;
    }

    public String getCentreID()
    {
        return CentreID;
    }
}
