package com.ShanesATM;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The initial Swing window for the ATM application.
 */
public class ATMFrame extends JFrame
{
    private static final String LOGIN_CARD = "Login";
    private static final String ACCOUNT_CARD = "Account";
    private static final Color NAVY = new Color(18, 48, 79);
    private static final Color GOLD = new Color(196, 151, 53);
    private static final Color BACKGROUND = new Color(244, 246, 248);
    private static final Color OFF_WHITE = new Color(250, 248, 242);

    private final ATMAccountManager accountManager;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private JTextField accountNumberField;
    private JTextArea statusArea;
    private BankAccount currentAccount;
    private JLabel accountTypeLabel;
    private JLabel accountBalanceLabel;
    private JLabel accountHolderLabel;
    

    public ATMFrame()
    {
        super(BankAccount.getBankName());

        accountManager = new ATMAccountManager();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createMainPanel());
        setPreferredSize(new Dimension(540, 530));
        pack();
        setMinimumSize(new Dimension(480, 430));
        setLocationRelativeTo(null);
    }

    private JPanel createMainPanel()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        /*
         * The outer border is intentionally inset from the JFrame edges.
         * This creates a card-like effect instead of making the border
         * look like the border of the entire window.
         */
        JPanel atmPanel = new JPanel(new BorderLayout(0, 10));
        atmPanel.setBackground(OFF_WHITE);

        atmPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(OFF_WHITE, 2),
        		BorderFactory.createEmptyBorder(14, 24, 14, 24)));

        atmPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        cardPanel.add(createLoginPanel(), LOGIN_CARD);
        cardPanel.add(createAccountPanel(), ACCOUNT_CARD);

        atmPanel.add(cardPanel, BorderLayout.CENTER);
        atmPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        mainPanel.add(atmPanel, BorderLayout.CENTER);

        return mainPanel;
    }


    private JPanel createHeaderPanel()
    {
    	
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 2));

        headerPanel.setBackground(NAVY);

        headerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(GOLD, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        JLabel bankLabel = new JLabel("SOVEREIGN BANK OF SHANE",SwingConstants.CENTER);

        bankLabel.setForeground(Color.WHITE);
        bankLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));

        JLabel welcomeLabel = new JLabel("Secure financial sovereignty.", SwingConstants.CENTER);

        welcomeLabel.setForeground(new Color(225, 231, 237));
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));

        headerPanel.add(bankLabel);
        headerPanel.add(welcomeLabel);

        return headerPanel;
    }

    private JPanel createLoginPanel()
    {
        JPanel loginPanel = new JPanel(new GridBagLayout());

        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder( new Color(205, 211, 217)),
                        "Account Access"));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(7, 10, 7, 10);
        constraints.weighty = 0;

        // Account number label
        JLabel accountNumberLabel = new JLabel("Account number:");
        accountNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        loginPanel.add(accountNumberLabel, constraints);

        // Account number text field
        accountNumberField = new JTextField(18);
        accountNumberField.setToolTipText("Enter your account number");

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        loginPanel.add(accountNumberField, constraints);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setToolTipText("Log in with your account number");
        loginButton.addActionListener(event -> handleLogin());

        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.anchor = GridBagConstraints.EAST;

        loginPanel.add(loginButton, constraints);

        // Status information
        statusArea = new JTextArea("Enter your account number, then select Login.");

        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        statusArea.setFocusable(false);
        statusArea.setOpaque(false);
        statusArea.setForeground(new Color(72, 79, 86));
        statusArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(7, 10, 7, 10);

        loginPanel.add(statusArea, constraints);

        return loginPanel;
    }
    
    private JPanel createAccountPanel()
    {
        JPanel accountPanel = new JPanel(new GridBagLayout());
        accountPanel.setOpaque(false);
        accountPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(205, 211, 217)),
                "Account Summary"));

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 10, 5, 10);

        accountTypeLabel = new JLabel("Account Type");
        accountTypeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        accountTypeLabel.setForeground(NAVY);
        constraints.gridy = 0;
        summaryPanel.add(accountTypeLabel, constraints);

        accountBalanceLabel = new JLabel("$0.00");
        accountBalanceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        accountBalanceLabel.setForeground(NAVY);
        constraints.gridy = 1;
        summaryPanel.add(accountBalanceLabel, constraints);

        accountHolderLabel = new JLabel("Account holder");
        accountHolderLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        accountHolderLabel.setForeground(new Color(72, 79, 86));
        constraints.gridy = 2;
        summaryPanel.add(accountHolderLabel, constraints);

        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        actionPanel.setOpaque(false);
        actionPanel.setPreferredSize(new Dimension(340, 80));
        actionPanel.add(createTransactionPanel("Deposit",
                event -> handleDeposit()));
        actionPanel.add(createTransactionPanel("Withdraw",
                event -> handleWithdraw()));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(event -> handleLogout());
        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);

        GridBagConstraints accountConstraints = new GridBagConstraints();
        accountConstraints.gridx = 0;
        accountConstraints.anchor = GridBagConstraints.CENTER;

        accountConstraints.gridy = 0;
        accountConstraints.insets = new Insets(4, 10, 2, 10);
        accountPanel.add(summaryPanel, accountConstraints);

        accountConstraints.gridy = 1;
        accountConstraints.insets = new Insets(4, 10, 4, 10);
        accountPanel.add(actionPanel, accountConstraints);

        accountConstraints.gridy = 2;
        accountConstraints.insets = new Insets(2, 10, 4, 10);
        accountPanel.add(logoutPanel, accountConstraints);

        // Absorbs extra vertical space so the transaction panels do not stretch.
        JPanel verticalSpacer = new JPanel();
        verticalSpacer.setOpaque(false);
        accountConstraints.gridy = 3;
        accountConstraints.fill = GridBagConstraints.BOTH;
        accountConstraints.weighty = 1.0;
        accountPanel.add(verticalSpacer, accountConstraints);

        return accountPanel;
    }

    private JPanel createTransactionPanel(String buttonText,
                                          ActionListener listener)
    {
        JPanel transactionPanel = new JPanel(new BorderLayout(0, 8));
        transactionPanel.setBackground(NAVY);
        transactionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(GOLD, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel transactionLabel = new JLabel(buttonText.toUpperCase(),SwingConstants.CENTER);
        transactionLabel.setForeground(Color.WHITE);
        transactionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        JButton transactionButton = new JButton(buttonText);
        transactionButton.setToolTipText(buttonText + " funds in this account.");
        transactionButton.addActionListener(listener);

        transactionPanel.add(transactionLabel, BorderLayout.NORTH);
        transactionPanel.add(transactionButton, BorderLayout.CENTER);
        return transactionPanel;
    }

    private void handleDeposit()
    {
        if(!hasCurrentAccount())
        {
            return;
        }

        Double amount = promptForAmount("deposit");

        if(amount == null)
        {
            return;
        }

        currentAccount.deposit(amount);
        updateAccountScreen();
        showTransactionComplete("Deposit");
    }

    private void handleWithdraw()
    {
        if(!hasCurrentAccount())
        {
            return;
        }

        Double amount = promptForAmount("withdraw");

        if(amount == null)
        {
            return;
        }

        if(currentAccount.withdraw(amount) == 0)
        {
            JOptionPane.showMessageDialog(this,
                    "Withdrawal could not be completed."
                            + " Please check the amount and available funds.",
                    "Withdrawal Unavailable",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateAccountScreen();
        showTransactionComplete("Withdrawal");
    }

    private Double promptForAmount(String transactionType)
    {
        String enteredAmount = JOptionPane.showInputDialog(this,
                "Enter " + transactionType + " amount:",
                transactionType.substring(0, 1).toUpperCase()
                        + transactionType.substring(1),
                JOptionPane.QUESTION_MESSAGE);

        if(enteredAmount == null)
        {
            return null;
        }

        try
        {
            double amount = Double.parseDouble(enteredAmount.trim());

            if(!Double.isFinite(amount) || amount <= 0)
            {
                throw new NumberFormatException();
            }

            return amount;
        }
        catch(NumberFormatException exception)
        {
            JOptionPane.showMessageDialog(this,
                    "Enter a positive numeric amount.",
                    "Invalid Amount",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private boolean hasCurrentAccount()
    {
        if(currentAccount != null)
        {
            return true;
        }

        JOptionPane.showMessageDialog(this,
                "Please log in to an account first.",
                "No Account Selected",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private void showTransactionComplete(String transactionType)
    {
        JOptionPane.showMessageDialog(this,
                transactionType + " complete. New balance: "
                        + currentAccount.getBalance(),
                transactionType + " Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLogin()
    {
        String enteredNumber = accountNumberField.getText().trim();

        if(enteredNumber.isEmpty())
        {
            statusArea.setText("Please enter a valid account number.");
            return;
        }

        try
        {
            int accountNumber = Integer.parseInt(enteredNumber);

            if(accountNumber <= 0)
            {
                statusArea.setText("Please enter a valid account number.");
                return;
            }

            currentAccount = accountManager.findAccount(accountNumber);

            if(currentAccount != null)
            {
                statusArea.setText(String.format("Welcome, %s %s. Current balance: %s",currentAccount.getFirst(),
                        currentAccount.getLast(),
                        currentAccount.getBalance()));
                updateAccountScreen();
                cardLayout.show(cardPanel, ACCOUNT_CARD);
            }
            else
            {
                statusArea.setText("Account not found. Please try again.");
            }
        }
        catch(NumberFormatException exception)
        {
            statusArea.setText("Please enter a valid account number.");
        }
    }

    private void updateAccountScreen()
    {
        accountTypeLabel.setText(getAccountTypeName());
        accountBalanceLabel.setText(currentAccount.getBalance());
        accountHolderLabel.setText("Account holder: " + currentAccount.getFirst() + " " + currentAccount.getLast());
    }

    private String getAccountTypeName()
    {
        if(currentAccount instanceof SuperChequingAccount)
        {
            return "Super Chequing Account";
        }
        else if(currentAccount instanceof SavingsAccount)
        {
            return "Savings Account";
        }

        return "Chequing Account";
    }

    private void handleLogout()
    {
        currentAccount = null;
        accountNumberField.setText("");
        statusArea.setText("Enter your account number, then select Login.");
        cardLayout.show(cardPanel, LOGIN_CARD);
        accountNumberField.requestFocusInWindow();
    }

    private JPanel createFooterPanel()
    {
        JPanel footerPanel = new JPanel(new BorderLayout());

        footerPanel.setOpaque(false);
        footerPanel.setPreferredSize(new Dimension(0, 30));

        JLabel securityLabel = new JLabel("Your account information is protected.");

        securityLabel.setForeground(new Color(92, 100, 108));

        securityLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));

        JButton shutdownButton = new JButton("Shutdown");

        shutdownButton.addActionListener(event -> dispose());

        footerPanel.add(securityLabel, BorderLayout.WEST);
        footerPanel.add(shutdownButton, BorderLayout.EAST);

        return footerPanel;
    }
}
