package com.ShanesATM;

import java.time.LocalDate;

public final class SuperChequingAccount extends ChequingAccount
{
    //instance variable
    private double overDraft;

    //constructors
    public SuperChequingAccount(int number, LocalDate dateOpen)
    {
        super(number, dateOpen);
        this.overDraft = 0;
    }

    public SuperChequingAccount(int number, LocalDate dateOpen, String first, String last)
    {
        super(number, dateOpen, first, last);
        this.overDraft = 0;
    }

    public SuperChequingAccount(int number, LocalDate dateOpen, String first, String last, double fee)
    {
        super(number, dateOpen, first, last, fee);
        this.overDraft = 0;
    }

    public SuperChequingAccount(int number, LocalDate dateOpen, String first, String last, double fee, double overDraft)
    {
        super(number, dateOpen, first, last, fee);
        setOverDraft(overDraft);
    }

    //gets
    public double getOverDraft()
    {
        return overDraft;
    }

    //sets
    public void setOverDraft(double overDraft)
    {
        this.overDraft = (overDraft >= 0) ? overDraft : 0;
    }

    //override toString
    @Override
    public String toString()
    {
        return super.toString() + String.format("\n\tDraft: $%.2f", overDraft);
    }

    //override withdraw
    @Override
    public double withdraw(double amount)
    {
        if(amount > 0 && (balance + overDraft) >= amount)
        {
            balance -= amount;
            return amount;
        }
        else
        {
            return 0;
        }
    }
}