package com.TheScrumMasters.TrolleyReader.UtilityClasses;

/**
 * Created by ryan on 30/08/16.
 */
public class Trolley
{
    private String TrolleyID;
    private String ShoppingCentreID;

    public Trolley(String ID, String ShoppingID)
    {
        TrolleyID = ID;
        ShoppingCentreID = ShoppingID;
    }

    public String toJSON()
    {
        //String str = "{" + "TrolleyID:" + TrolleyID + ",CentreID:" + ShoppingCentreID + "}";
        String str = TrolleyID;
        return str;
    }
}
