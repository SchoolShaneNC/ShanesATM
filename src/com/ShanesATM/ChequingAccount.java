package com.ShanesATM;
import java.time.LocalDate;

public class ChequingAccount extends BankAccount
{
    //instance variable
    private double fee;

    //static variable
    private static double defaultFee = 5.0;

    //constructors
    public ChequingAccount(int number, LocalDate dateOpen)
    {
        super(number, dateOpen);

        this.fee = defaultFee;
    }

    public ChequingAccount(int number, LocalDate dateOpen, String first, String last)
    {
        super(number, dateOpen, first, last);

        this.fee = defaultFee;
    }

    public ChequingAccount(int number, LocalDate dateOpen, String first, String last, double fee)
    {
        super(number, dateOpen, first, last);

        setFee(fee);
    }

    //getter
    public double getFee()
    {
        return fee;
    }

    //setter
    public void setFee(double fee)
    {
        this.fee = (fee >= 0) ? fee : defaultFee;
    }

    //static gets
    public static double getDefaultFee()
    {
        return defaultFee;
    }

    //static sets
    public static void setDefaultFee(double value)
    {
        if(value > 0)
        {
            defaultFee = value;
        }
    }

    //apply monthly fee
    public double applyFee()
    {
        balance -= fee;
        return balance;
    }

    @Override
    public String toString()
    {
        return super.toString() + String.format("\n\tFee: $%.2f", fee);
    }

    @Override
    public double withdraw(double amount)
    {
        if(amount > 0 && balance >= amount)
        {
            balance -= amount;
            return amount;
        }
        return 0;
    }
}