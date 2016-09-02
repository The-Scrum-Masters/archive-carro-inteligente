package com.TheScrumMasters.TrolleyReader;

import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;
import java.net.*;

/**
 * Created by EpicPC on 25/08/2016.
 */
public class UDPMessageSender
{

    public void runUdpClient(final String udpMsg, final String address, final int port)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //int UDP_SERVER_PORT = 5005;

                    //String udpMsg = "hello world from UDP client " + UDP_SERVER_PORT;
                    DatagramSocket ds = null;
                    try
                    {
                        ds = new DatagramSocket();
                        //InetAddress serverAddr = InetAddress.getByName("192.168.1.100");
                        InetAddress serverAddr = InetAddress.getByName(address);
                        DatagramPacket dp;
                        dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, port);
                        ds.send(dp);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (ds != null)
                        {
                            ds.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

}

