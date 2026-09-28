package com.ShanesATM;
import java.time.LocalDate;

public class ChequingAccount extends BankAccount
{
    // Instance variable

    private double fee;

    // Static variable

    private static double defaultFee = 5.0;

    // Constructors

    public ChequingAccount(int number, LocalDate dateOpen)
    {
        super(number, dateOpen);

        this.fee = defaultFee;
    }

    public ChequingAccount(int number,
                           LocalDate dateOpen,
                           String first,
                           String last)
    {
        super(number, dateOpen, first, last);

        this.fee = defaultFee;
    }

    public ChequingAccount(int number,
                           LocalDate dateOpen,
                           String first,
                           String last,
                           double fee)
    {
        super(number, dateOpen, first, last);

        setFee(fee);
    }

    // Getter

    public double getFee()
    {
        return fee;
    }

    // Setter

    public void setFee(double fee)
    {
        this.fee = (fee >= 0) ? fee : defaultFee;
    }

    // Static Getter

    public static double getDefaultFee()
    {
        return defaultFee;
    }

    // Static Setter

    public static void setDefaultFee(double value)
    {
        if(value > 0)
        {
            defaultFee = value;
        }
    }

    // Apply monthly fee

    public double applyFee()
    {
        balance -= fee;

        return balance;
    }

    @Override
    public String toString()
    {
        return super.toString() +
                String.format("\n\tFee: $%.2f", fee);
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