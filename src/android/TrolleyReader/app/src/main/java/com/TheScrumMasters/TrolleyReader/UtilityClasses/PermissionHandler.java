package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

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
    private HashMap<Permissions, Boolean> grantedPermissions = new HashMap<>(Permissions.values().length);

    public PermissionHandler(Activity activity)
    {
        permissionMap.put(Permissions.SENDSMS, Manifest.permission.SEND_SMS);
        permissionMap.put(Permissions.RECEIVESMS, Manifest.permission.RECEIVE_SMS);

        grantedPermissions.put(Permissions.SENDSMS, false);
        grantedPermissions.put(Permissions.RECEIVESMS, false);
        getRequestResults(activity, permissionMap.keySet().toArray(new Permissions[permissionMap.size()])); //second arg to get all keys from permission map
    }

    /**
     *
     * @param activity the activity calling this
     * @param permissions Permissions to request, which are registered prior in {@link Permissions}
     */
    public void askPermission(Activity activity, Permissions[] permissions, int requestID)
    {
        ArrayList<String> permissionsToRequest = new ArrayList<>(permissions.length);
        for (Permissions permission : permissions)
        {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(activity, permissionMap.get(permission)) != PackageManager.PERMISSION_GRANTED)
            {
                permissionsToRequest.add(permissionMap.get(permission));
            }
        }
        if (permissionsToRequest.isEmpty())
        {
            //All the permissions we need have been granted, no need to request any
            return;
        }
        ActivityCompat.requestPermissions(activity, permissionsToRequest.toArray(new String[permissionsToRequest.size()]) , requestID); //sorry for that 2nd arg, all it does it get get all permission Strings
    }

    public void getRequestResults(Activity activity, Permissions[] permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null && permissions != null) {
            for (Permissions permission : permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permissionMap.get(permission)) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.put(permission, true);
                }
            }
        }
    }

    public boolean getIfPermissionGranted(Permissions permission)
    {
        return grantedPermissions.get(permission);
    }

    public Permissions[] getAllPermissions()
    {
        return permissionMap.keySet().toArray(new Permissions[permissionMap.size()]);
    }
}

