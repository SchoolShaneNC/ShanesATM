package com.ShanesATM;

import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
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
import javax.swing.SwingUtilities;

//initial window for the atm using swing
public class ATMFrame extends JFrame
{
	//creating seperate cards to switch into the single jframe instead of multiple jframes
    private static final String SPLASH_CARD = "Splash";
    private static final String LOGIN_CARD = "Login";
    private static final String ACCOUNT_CARD = "Account";
    private static final long SPLASH_DURATION_MS = 5000;
    private static final Color NAVY = new Color(18, 48, 79);
    private static final Color GOLD = new Color(196, 151, 53);
    private static final Color BACKGROUND = new Color(244, 246, 248);
    private static final Color OFF_WHITE = new Color(250, 248, 242);

    //declaring components and classes / current bank account to use 
    private final ATMAccountManager accountManager;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final SplashPanel splashPanel;
    private JTextField accountNumberField;
    private JTextArea statusArea;
    private BankAccount currentAccount;
    private JLabel accountTypeLabel;
    private JLabel accountBalanceLabel;
    private JLabel accountHolderLabel;
    

    public ATMFrame()
    {
    	//building the foundation of the frame
        super(BankAccount.getBankName());

        accountManager = new ATMAccountManager();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        splashPanel = new SplashPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createMainPanel());
        setPreferredSize(new Dimension(540, 530));
        pack();
        setMinimumSize(new Dimension(480, 430));
        setLocationRelativeTo(null);
        cardLayout.show(cardPanel, SPLASH_CARD);
        startSplashAnimation();
    }

    private JPanel createMainPanel()
    {
    	// Create the main container for the ATM
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

       //main atm panel creation
        JPanel atmPanel = new JPanel(new BorderLayout(0, 10));
        atmPanel.setBackground(OFF_WHITE);
        
        //creating 2 seperate borders
        atmPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(OFF_WHITE, 2),
        		BorderFactory.createEmptyBorder(14, 24, 14, 24)));
        
        //creating header and making it be in the top section
        atmPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        //creating 3 seperate cards for the middle center section of panel 
        cardPanel.add(splashPanel, SPLASH_CARD);
        cardPanel.add(createLoginPanel(), LOGIN_CARD);
        cardPanel.add(createAccountPanel(), ACCOUNT_CARD);

        //placing the cards into the main atm center panel
        //forcing footer to bottom too
        atmPanel.add(cardPanel, BorderLayout.CENTER);
        atmPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        mainPanel.add(atmPanel, BorderLayout.CENTER);

        return mainPanel;
    }


    private JPanel createHeaderPanel()
    {
    	//this builds the header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        
        //setting colours and borders
        headerPanel.setBackground(NAVY);

        headerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(GOLD, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        
        //creating the labels and adding them to the panel
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
    	//creating the login panel
        JPanel loginPanel = new JPanel(new GridBagLayout());

        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder( new Color(205, 211, 217)),
                        "Account Access"));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(7, 10, 7, 10);
        constraints.weighty = 0;

        //creating account number label
        JLabel accountNumberLabel = new JLabel("Account number:");
        accountNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        loginPanel.add(accountNumberLabel, constraints);

        //creating Account number text field
        accountNumberField = new JTextField(18);
        accountNumberField.setToolTipText("Enter your account number");

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        loginPanel.add(accountNumberField, constraints);

        //creating login button
        JButton loginButton = new JButton("Login");
        loginButton.setToolTipText("Log in with your account number");
        loginButton.addActionListener(event -> handleLogin());

        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.anchor = GridBagConstraints.EAST;

        loginPanel.add(loginButton, constraints);

        //status information
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
    	//creating account panel 
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
        actionPanel.add(createTransactionPanel("Deposit", event -> handleDeposit()));
        actionPanel.add(createTransactionPanel("Withdraw", event -> handleWithdraw()));

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

        //this absorbs extra vertical space so the transaction panels dont stretch.
        JPanel verticalSpacer = new JPanel();
        verticalSpacer.setOpaque(false);
        accountConstraints.gridy = 3;
        accountConstraints.fill = GridBagConstraints.BOTH;
        accountConstraints.weighty = 1.0;
        accountPanel.add(verticalSpacer, accountConstraints);

        return accountPanel;
    }

    private JPanel createTransactionPanel(String buttonText, ActionListener listener)
    {
    	//creating transaction panels (the buttons)
        JPanel transactionPanel = new JPanel(new BorderLayout(0, 8));
        transactionPanel.setBackground(NAVY);
        transactionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(GOLD, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel transactionLabel = new JLabel(buttonText.toUpperCase(),SwingConstants.CENTER);
        transactionLabel.setForeground(Color.WHITE);
        transactionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        //create the button and connects it to the listener.
        JButton transactionButton = new JButton(buttonText);
        transactionButton.setToolTipText(buttonText + " funds in this account.");
        transactionButton.addActionListener(listener);

        transactionPanel.add(transactionLabel, BorderLayout.NORTH);
        transactionPanel.add(transactionButton, BorderLayout.CENTER);
        return transactionPanel;
    }

    private JPanel createFooterPanel()
    {
    	//creates the footer
    	//holds the shutdown button and basic little texts
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

    private void handleDeposit()
    {
    	//method for deposit
    	//checks validation for account
        if(!hasCurrentAccount())
        {
            return;
        }
        
        //validates amount too then uses current account deposit class method 
        Double amount = promptForAmount("deposit");

        if(amount == null)
        {
            return;
        }
        //updates after to show changes
        currentAccount.deposit(amount);
        updateAccountScreen();
        showTransactionComplete("Deposit");
    }

    private void handleWithdraw()
    {
    	//handles withdraw the same way as deposit
        if(!hasCurrentAccount())
        {
            return;
        }

        Double amount = promptForAmount("withdraw");

        if(amount == null)
        {
            return;
        }
        //gives validation message to user if withdraw coudnt go through updates as well
        if(currentAccount.withdraw(amount) == 0)
        {
            JOptionPane.showMessageDialog(this, "Withdrawal could not be completed. Please check available funds.",
                    "Withdrawal Unavailable",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateAccountScreen();
        showTransactionComplete("Withdrawal");
    }

    private Double promptForAmount(String transactionType)
    {
    	//gives user pop up to enter amount for deposit/withdraw
        String enteredAmount = JOptionPane.showInputDialog(this,"Enter " + transactionType + " amount:",
                transactionType.substring(0, 1).toUpperCase() + transactionType.substring(1),
                JOptionPane.QUESTION_MESSAGE);
        
        //basic validation on entered amount
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
            JOptionPane.showMessageDialog(this,"Enter a positive numeric amount.", "Invalid Amount",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private boolean hasCurrentAccount()
    {
    	//account check validation just incase 
        if(currentAccount != null)
        {
            return true;
        }

        JOptionPane.showMessageDialog(this, "Please log in to an account first.", "No Account Selected",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private void showTransactionComplete(String transactionType)
    {
    	//another popup once transaction completes shows balance as well
        JOptionPane.showMessageDialog(this, transactionType + " complete. New balance: " + currentAccount.getBalance(),
                transactionType + " Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLogin()
    {
    	//login method takes input with validation
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
            //uses the findaccount method to search for inputed matching account
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
    	//updates account screen post login to show name and account type and balance
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

    
    
    private void startSplashAnimation()
    {
        //thread runs five second startup animation
        Thread splashThread = new Thread(() ->
        {
            long startTime = System.currentTimeMillis();
            int progress;

            do
            {
                long elapsedTime = System.currentTimeMillis() - startTime;
                progress = (int)Math.min(100, elapsedTime * 100 / SPLASH_DURATION_MS);
                int currentProgress = progress;

                //swing components get updated on the event dispatch thread
                SwingUtilities.invokeLater(() -> splashPanel.setProgress(currentProgress));
                
                //safe try catches
                try
                {
                    Thread.sleep(40);
                }
                catch(InterruptedException exception)
                {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            while(progress < 100);

            //final animation update transitions from the splashscreen to login
            SwingUtilities.invokeLater(() ->
            {
                splashPanel.setProgress(100);
                cardLayout.show(cardPanel, LOGIN_CARD);
                accountNumberField.requestFocusInWindow();
            });
        }, "ATM Splash Animation");

        splashThread.setDaemon(true);
        splashThread.start();
    }

   
     //draws the vault door startup animation for the splashscreen
    private static class SplashPanel extends JPanel
    {
    	//field for the current progress time
        private int progress;

        SplashPanel()
        {
            setOpaque(false);
            setPreferredSize(new Dimension(400, 300));
        }

        //update animation and refreash drawing
        void setProgress(int progress)
        {
            this.progress = Math.max(0, Math.min(100, progress));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics)
        {
            super.paintComponent(graphics);
            
            //creates the actual graphic
            Graphics2D graphics2D = (Graphics2D)graphics.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            //calculates the door
            int width = getWidth();
            int height = getHeight();
            int doorDiameter = Math.min(190,
                    Math.max(120, Math.min(width - 100, height - 125)));
            int doorX = (width - doorDiameter) / 2;
            int doorY = Math.max(18, (height - doorDiameter - 90) / 2);
            int centerX = doorX + doorDiameter / 2;
            int centerY = doorY + doorDiameter / 2;

            drawVaultRing(graphics2D, doorX, doorY, doorDiameter);
            drawVaultDoor(graphics2D, doorX, doorY, doorDiameter,
                    centerX, centerY);
            drawProgressBar(graphics2D, doorY + doorDiameter + 28, width);
            
            //gets ride of the graphic since we dont need it after
            graphics2D.dispose();
        }

        //outer rings of the vault
        private void drawVaultRing(Graphics2D graphics2D, int doorX,
                                   int doorY, int doorDiameter)
        {
            graphics2D.setColor(GOLD);
            graphics2D.fillOval(doorX, doorY, doorDiameter, doorDiameter);
            graphics2D.setColor(new Color(59, 67, 74));
            graphics2D.fillOval(doorX + 5, doorY + 5,
                    doorDiameter - 10, doorDiameter - 10);
            graphics2D.setColor(new Color(28, 35, 42));
            graphics2D.fillOval(doorX + 14, doorY + 14,
                    doorDiameter - 28, doorDiameter - 28);
        }

        //the actual door and animation
        private void drawVaultDoor(Graphics2D graphics2D, int doorX,int doorY, int doorDiameter,int centerX, int centerY)
        {
        	//calculates door opening on how long the progress bar is
            double opening = progress / 100.0;
            double doorWidth = 1.0 - opening * 0.68;
            int hingeX = doorX + 14;
            
            //transforms door
            Graphics2D doorGraphics = (Graphics2D)graphics2D.create();
            doorGraphics.translate(hingeX, 0);
            doorGraphics.scale(doorWidth, 1.0);
            doorGraphics.translate(-hingeX, 0);
            
            //main door
            doorGraphics.setColor(new Color(75, 93, 110));
            doorGraphics.fillOval(doorX + 14, doorY + 14,
                    doorDiameter - 28, doorDiameter - 28);
            doorGraphics.setColor(new Color(171, 181, 190));
            doorGraphics.setStroke(new BasicStroke(4));
            
            //creates the spokes this one is 6 can be however many
            for(int spoke = 0; spoke < 6; spoke++)
            {
                double angle = spoke * Math.PI / 3 + opening * Math.PI / 4;
                int spokeX = centerX + (int)(Math.cos(angle) * doorDiameter * 0.22);
                int spokeY = centerY + (int)(Math.sin(angle) * doorDiameter * 0.22);
                doorGraphics.drawLine(centerX, centerY, spokeX, spokeY);
            }

            doorGraphics.setColor(GOLD);
            doorGraphics.fillOval(centerX - 17, centerY - 17, 34, 34);
            doorGraphics.setColor(NAVY);
            doorGraphics.fillOval(centerX - 8, centerY - 8, 16, 16);
            doorGraphics.dispose();
        }

        //draws the progress bar
        private void drawProgressBar(Graphics2D graphics2D, int barY, int width)
        {
        	//makes the size and position
            int barWidth = Math.min(320, width - 70);
            int barHeight = 16;
            int barX = (width - barWidth) / 2;
            int filledWidth = barWidth * progress / 100;

            graphics2D.setColor(NAVY);
            graphics2D.fillRoundRect(barX, barY, barWidth, barHeight, 12, 12);
            graphics2D.setColor(GOLD);
            graphics2D.fillRoundRect(barX, barY, filledWidth, barHeight, 12, 12);
            graphics2D.setColor(new Color(59, 67, 74));
            graphics2D.drawRoundRect(barX, barY, barWidth, barHeight, 12, 12);

            //this is for the initializing underneath the bar
            graphics2D.setColor(NAVY);
            graphics2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
            String message = "INITIALIZING...";
            int messageWidth = graphics2D.getFontMetrics().stringWidth(message);
            graphics2D.drawString(message, (width - messageWidth) / 2,
                    barY + 40);
        }
    }

}
