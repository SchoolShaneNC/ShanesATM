package com.ShanesATM;

import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Program
{
    public static void main(String[] args)
    {
        // Test SavingsAccount

        SavingsAccount save =
                new SavingsAccount(3333, LocalDate.now());

        save.setRate(0.02);
        save.deposit(300.00);
        save.applyInterest();
        save.withdraw(25.00);

        JOptionPane.showMessageDialog(
                null,
                save.toString(),
                BankAccount.getBankName(),
                JOptionPane.INFORMATION_MESSAGE);

        // Test ChequingAccount

        ChequingAccount ch =
                new ChequingAccount(4444, LocalDate.now());

        ch.setFee(5.55);
        ch.deposit(100.00);
        ch.applyFee();
        ch.withdraw(50.00);

        JOptionPane.showMessageDialog(
                null,
                ch.toString(),
                BankAccount.getBankName(),
                JOptionPane.INFORMATION_MESSAGE);

        // POLYMORPHISM

        BankAccount b =
                new ChequingAccount(7777, LocalDate.now());

        b.deposit(100.00);
        b.withdraw(50.00);

        JOptionPane.showMessageDialog(
                null,
                b.toString(),
                BankAccount.getBankName(),
                JOptionPane.INFORMATION_MESSAGE);

        // Array of BankAccount references

        BankAccount[] accounts =
        {
            new ChequingAccount(1234, LocalDate.now()),
            new SavingsAccount(9876, LocalDate.now())
        };

        for(int x = 0; x < accounts.length; x++)
        {
            JOptionPane.showMessageDialog(
                    null,
                    accounts[x].toString(),
                    BankAccount.getBankName(),
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // Type check and cast

        if(accounts[0] instanceof ChequingAccount)
        {
            ChequingAccount temp =
                    (ChequingAccount) accounts[0];

            temp.applyFee();

            JOptionPane.showMessageDialog(
                    null,
                    temp.toString(),
                    BankAccount.getBankName(),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

