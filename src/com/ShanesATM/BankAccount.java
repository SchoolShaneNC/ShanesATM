package com.ShanesATM;
import java.time.LocalDate;

public abstract class BankAccount
{
    // Instance Variables

    private final int number;
    private String first;
    private String last;
    protected double balance;

    // Containment

    private final LocalDate dateOpen;

    // Static and Constants

    private static int count = 0;

    private static final String BANKNAME = "The Sovereign Bank of SHANE";

    // Constructors

    public BankAccount(int number, LocalDate dateOpen)
    {
        setAccount();

        this.number = (number > 0) ? number : 0;

        this.dateOpen = (!dateOpen.isAfter(LocalDate.now())) ? dateOpen : LocalDate.now();
    }

    public BankAccount(int number, LocalDate dateOpen, String first, String last)
    {
        setAccount();

        this.number = (number > 0) ? number : 0;

        this.dateOpen = (!dateOpen.isAfter(LocalDate.now())) ? dateOpen : LocalDate.now();

        setFirst(first);
        setLast(last);
    }

    // Initialization Method

    private void setAccount()
    {
        first = "Unknown";
        last = "Unknown";
        balance = 0.0;

        count++;
    }

    // Finalizer (similar to destructor)

    @Override
    protected void finalize() throws Throwable
    {
        count--;
        super.finalize();
    }

    // Getters and Setters

    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        if(first != null && !first.isEmpty())
        {
            this.first = first;
        }
    }

    public String getLast()
    {
        return last;
    }

    public void setLast(String last)
    {
        if(last != null && !last.isEmpty())
        {
            this.last = last;
        }
    }

    public int getNumber()
    {
        return number;
    }

    public String getDateOpen()
    {
        return dateOpen.toString();
    }

    public String getBalance()
    {
        return String.format("$%.2f", balance);
    }

    // Static Getters

    public static String getBankName()
    {
        return BANKNAME;
    }

    public static int getCount()
    {
        return count;
    }

    // Methods

    public double deposit(double amount)
    {
        if(amount > 0)
        {
            balance += amount;
            return balance;
        }

        return 0;
    }

    @Override
    public String toString()
    {
        return String.format( "\n\tAccount: %d\n\tName: %s %s\n\tBalance: %s\n\tOpened: %s\n",
                getNumber(), getFirst(), getLast(), getBalance(), getDateOpen());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof BankAccount)
        {
            BankAccount temp = (BankAccount)obj;
            return this.number == temp.number;
        }

        return false;
    }

    // Abstract Method

    public abstract double withdraw(double amount);
}