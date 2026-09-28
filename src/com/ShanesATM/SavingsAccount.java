package com.ShanesATM;

import java.time.LocalDate;

public class SavingsAccount extends BankAccount
{
    // Instance variable

    private double rate;

    // Static class variables

    private static double defaultRate = 0.04;

    private static double charge = 0.50;

    // Constructors

    public SavingsAccount(int number, LocalDate dateOpen)
    {
        super(number, dateOpen);

        this.rate = defaultRate;
    }

    public SavingsAccount(int number,
                          LocalDate dateOpen,
                          String first,
                          String last)
    {
        super(number, dateOpen, first, last);

        this.rate = defaultRate;
    }

    public SavingsAccount(int number,
                          LocalDate dateOpen,
                          String first,
                          String last,
                          double rate)
    {
        super(number, dateOpen, first, last);

        setRate(rate);
    }

    // Getter and Setter

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = (rate >= 0) ? rate : defaultRate;
    }

    // Static Getters and Setters

    public static double getDefaultRate()
    {
        return defaultRate;
    }

    public static void setDefaultRate(double value)
    {
        if(value > 1)
        {
            defaultRate = value / 100;
        }
        else if(value > 0)
        {
            defaultRate = value;
        }
    }

    public static double getCharge()
    {
        return charge;
    }

    public static void setCharge(double value)
    {
        if(value > 0)
        {
            charge = value;
        }
    }

    // Business Logic

    public double applyInterest()
    {
        double interest = balance * rate;

        balance += interest;

        return interest;
    }

    @Override
    public String toString()
    {
        return super.toString() +
                String.format("\n\tRate: %.2f%%",
                rate * 100);
    }

    @Override
    public double withdraw(double amount)
    {
        if(amount > 0 &&
           balance >= (amount + charge))
        {
            amount += charge;

            balance -= amount;

            return amount;
        }

        return 0;
    }
}
