package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ryan on 21/09/16.
 */
public class SMSNotification
{
    public static String createJSONMessage(String messageCode, String message, String bay)
    {
        return "{" +
            "messageCode:" + messageCode + "," +
            "message:\"" + message + "\"," +
            "bay:" + bay +
        "}";
    }

    public static JSONObject parseJSONMessage(String JSON) throws JSONException
    {
        return new JSONObject(JSON);
    }

    //Will return null if it passed all checks
    //otherwise it will return error message
    public static String checkIfMessageValid(String message)
    {
        if (message.length() > 918)
        {
            return "Message length exceeds 918 character, it is not possible to send this message";
        }
        return null;
    }
}
