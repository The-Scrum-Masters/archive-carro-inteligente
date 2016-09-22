package com.TheScrumMasters.TrolleyReader.UtilityClasses;

/**
 * Created by ryan on 22/09/16.
 */
public class Bay
{
    private int capacity, value, id;
    private double lowerThreshold;
    private boolean reportedLow;

    public Bay(int capacity, int value, double lowerThreshold, int id)
    {
        this.capacity = capacity;
        this.value = value;
        this.id = id;
        this.lowerThreshold = lowerThreshold;
        reportedLow = false;
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

    public double getLowerThreshold()
    {
        return lowerThreshold;
    }

    public boolean isLow()
    {
        int bayThreshold = (int)Math.floor(lowerThreshold * capacity);
        if (value <= bayThreshold && !reportedLow)
        {
            reportedLow = true;
            return true;
        }
        else
        {
            reportedLow = false;
        }
        return false;
    }
}
