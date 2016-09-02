package com.TheScrumMasters.TrolleyReader;

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
        String str = "{" + "TrolleyID:" + TrolleyID + ",CentreID:" + ShoppingCentreID + "}";
        return str;
    }
}
