package com.TheScrumMasters.TrolleyReader.UtilityClasses;

import android.widget.ProgressBar;

/**
 * Created by ryan on 22/09/16.
 */
public class Bay
{
    private int capacity, value, id;
    private boolean isBelowThreshold;
    public Bay(int capacity, int value, int id)
    {
        this.capacity = capacity;
        this.value = value;
        this.id = id;

        isBelowThreshold = false;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public int getId()
    {
        return id;
    }

    public void setIsBelowThreshold(boolean belowThreshold)
    {
        isBelowThreshold = belowThreshold;
    }

    public boolean isBelowThreshold()
    {
        return isBelowThreshold;
    }
}
