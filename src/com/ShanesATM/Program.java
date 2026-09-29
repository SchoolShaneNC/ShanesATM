package com.ShanesATM;

import javax.swing.SwingUtilities;

/**
 * Starts the ATM application.
 */
public class Program
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            ATMFrame atmFrame = new ATMFrame();
            atmFrame.setVisible(true);
        });
    }
}
