package com.ShanesATM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates and stores the bank accounts available to the ATM.
 */
public class ATMAccountManager
{
    private final List<BankAccount> accounts;

    public ATMAccountManager()
    {
        accounts = new ArrayList<>();
        createInitialAccounts();
    }

    private void createInitialAccounts()
    {
        BankAccount account = new ChequingAccount(1001, LocalDate.of(2024, 1, 15), "Ava", "Martin");
        account.deposit(500.00);
        accounts.add(account);

        account = new ChequingAccount(1002, LocalDate.of(2024, 2, 20), "Ghassan", "Abulaila");
        account.deposit(750.00);
        accounts.add(account);

        account = new ChequingAccount(1003, LocalDate.of(2024, 3, 12), "Colin", "Steen");
        account.deposit(1000.00);
        accounts.add(account);

        account = new SavingsAccount(2001, LocalDate.of(2023, 9, 5), "Mark", "Morely");
        account.deposit(2500.00);
        accounts.add(account);

        account = new SavingsAccount(2002, LocalDate.of(2023, 10, 18), "April", "Dennison");
        account.deposit(3200.00);
        accounts.add(account);

        account = new SavingsAccount(2003, LocalDate.of(2023, 11, 27), "Lemon", "Dennison");
        account.deposit(1800.00);
        accounts.add(account);

        account = new SuperChequingAccount(3001, LocalDate.of(2024, 4, 8), "Sidney", "Crosby");
        account.deposit(1200.00);
        accounts.add(account);

        account = new SuperChequingAccount(3002, LocalDate.of(2024, 5, 16), "Brayden", "Kallumbah");
        account.deposit(900.00);
        accounts.add(account);

        account = new SuperChequingAccount(3003, LocalDate.of(2024, 6, 24), "Sergi", "Bobrovsky");
        account.deposit(1500.00);
        accounts.add(account);
    }

    public List<BankAccount> getAccounts()
    {
        return accounts;
    }

    /**
     * Finds an account with the supplied account number.
     *
     * @param accountNumber the number to find
     * @return the matching account, or null when it is not in the collection
     */
    public BankAccount findAccount(int accountNumber)
    {
        for(BankAccount account : accounts)
        {
            if(account.getNumber() == accountNumber)
            {
                return account;
            }
        }

        return null;
    }
    
    public boolean accountExists(int accountNumber)
    {
        return findAccount(accountNumber) != null;
    }
}
