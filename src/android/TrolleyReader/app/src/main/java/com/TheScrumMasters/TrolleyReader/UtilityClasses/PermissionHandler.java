package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.HashMap;

/**
 * Created by ryan on 19/09/16.
 */
public class PermissionHandler
{
    public enum Permissions
    {
        SENDSMS,
        RECEIVESMS
    }

    private HashMap<Permissions, String> permissionMap = new HashMap<>(Permissions.values().length);

    public PermissionHandler()
    {
        permissionMap.put(Permissions.SENDSMS, Manifest.permission.SEND_SMS);
        permissionMap.put(Permissions.RECEIVESMS, Manifest.permission.RECEIVE_SMS);
    }

    public void askPermission(Activity activity, Permissions permission)
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity, permissionMap.get(permission)) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionMap.get(permission)))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage("You need to enable SMS for this to work");
                builder1.setCancelable(true);

                builder1.setNeutralButton(
                        "OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, permission.ordinal());
        }
    }



}
