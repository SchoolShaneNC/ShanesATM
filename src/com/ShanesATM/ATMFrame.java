package com.ShanesATM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The initial Swing window for the ATM application.
 */
public class ATMFrame extends JFrame
{
    private static final Color NAVY = new Color(18, 48, 79);
    private static final Color GOLD = new Color(196, 151, 53);
    private static final Color BACKGROUND = new Color(244, 246, 248);

    private JTextField accountNumberField;
    private JTextArea statusArea;

    public ATMFrame()
    {
        super(BankAccount.getBankName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createMainPanel());
        setPreferredSize(new Dimension(540, 390));
        pack();
        setMinimumSize(new Dimension(480, 350));
        setLocationRelativeTo(null);
    }

    private JPanel createMainPanel()
    {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 18));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createLoginPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createHeaderPanel()
    {
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setBackground(NAVY);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD, 2),
                BorderFactory.createEmptyBorder(15, 12, 15, 12)));

        JLabel bankLabel = new JLabel("SOVEREIGN BANK OF SHANE",
                SwingConstants.CENTER);
        bankLabel.setForeground(Color.WHITE);
        bankLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));

        JLabel welcomeLabel = new JLabel(
                "Welcome. Secure banking starts here.", SwingConstants.CENTER);
        welcomeLabel.setForeground(new Color(225, 231, 237));
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        headerPanel.add(bankLabel);
        headerPanel.add(welcomeLabel);
        return headerPanel;
    }

    private JPanel createLoginPanel()
    {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(205, 211, 217)),
                "Account Access"));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(7, 10, 7, 10);
        constraints.anchor = GridBagConstraints.WEST;

        JLabel accountNumberLabel = new JLabel("Account number:");
        accountNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        loginPanel.add(accountNumberLabel, constraints);

        accountNumberField = new JTextField(18);
        accountNumberField.setToolTipText("Enter your account number");
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        loginPanel.add(accountNumberField, constraints);

        JButton loginButton = new JButton("Continue");
        loginButton.setToolTipText("Account login will be added in a later step");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.anchor = GridBagConstraints.EAST;
        loginPanel.add(loginButton, constraints);

        statusArea = new JTextArea("Enter your account number, then select Continue.");
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
        constraints.anchor = GridBagConstraints.WEST;
        loginPanel.add(statusArea, constraints);

        return loginPanel;
    }

    private JPanel createFooterPanel()
    {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);

        JLabel securityLabel = new JLabel("Your account information is protected.");
        securityLabel.setForeground(new Color(92, 100, 108));
        securityLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));

        JButton shutdownButton = new JButton("Shutdown");
        shutdownButton.addActionListener(event -> dispose());

        footerPanel.add(securityLabel, BorderLayout.WEST);
        footerPanel.add(shutdownButton, BorderLayout.EAST);
        return footerPanel;
    }
}

