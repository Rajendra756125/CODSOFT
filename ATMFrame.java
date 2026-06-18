import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ATMFrame extends JFrame implements ActionListener
{
    int accountNo;
    String name;

    JLabel welcomeLabel;
    JLabel balanceLabel;

    JTextField amountField;

    JButton depositButton;
    JButton withdrawButton;
    JButton checkButton;
    JButton logoutButton;

    JTextArea logArea;

    Color bg = new Color(33,37,43);

    public ATMFrame(int accountNo, String name)
    {
        this.accountNo = accountNo;
        this.name = name;

        setTitle("YCSK BANK ATM");

        setSize(750,550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(bg);

        setLayout(null);



        welcomeLabel = new JLabel("Welcome, " + name);

        welcomeLabel.setBounds(30,20,300,35);

        welcomeLabel.setForeground(Color.WHITE);

        welcomeLabel.setFont(new Font("Arial",Font.BOLD,24));

        add(welcomeLabel);



        balanceLabel = new JLabel();

        balanceLabel.setBounds(30,70,300,30);

        balanceLabel.setForeground(Color.GREEN);

        balanceLabel.setFont(new Font("Arial",Font.BOLD,20));

        add(balanceLabel);



        JLabel amount = new JLabel("Amount");

        amount.setBounds(30,130,120,30);

        amount.setForeground(Color.WHITE);

        amount.setFont(new Font("Arial",Font.BOLD,18));

        add(amount);



        amountField = new JTextField();

        amountField.setBounds(150,130,180,40);

        amountField.setFont(new Font("Arial",Font.BOLD,18));

        add(amountField);




        depositButton = new JButton("Deposit");

        depositButton.setBounds(30,220,130,45);

        depositButton.addActionListener(this);

        add(depositButton);




        withdrawButton = new JButton("Withdraw");

        withdrawButton.setBounds(190,220,130,45);

        withdrawButton.addActionListener(this);

        add(withdrawButton);




        checkButton = new JButton("Check Balance");

        checkButton.setBounds(350,220,150,45);

        checkButton.addActionListener(this);

        add(checkButton);




        logoutButton = new JButton("Logout");

        logoutButton.setBounds(530,220,120,45);

        logoutButton.addActionListener(this);

        add(logoutButton);




        logArea = new JTextArea();

        logArea.setEditable(false);

        logArea.setFont(new Font("Monospaced",Font.BOLD,16));

        logArea.setBackground(new Color(20,20,20));

        logArea.setForeground(new Color(0,255,100));



        JScrollPane sp = new JScrollPane(logArea);

        sp.setBounds(30,300,650,180);

        add(sp);



        updateBalance();

        setVisible(true);
    }



    public void updateBalance()
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT balance FROM accounts WHERE account_no=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,accountNo);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                double balance = rs.getDouble("balance");

                balanceLabel.setText("Balance : ₹ " + balance);
            }

            rs.close();

            ps.close();

            con.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



    @Override

    public void actionPerformed(ActionEvent e)
    {
        try
        {

            if(e.getSource()==depositButton)
            {
                double amount = Double.parseDouble(amountField.getText());

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE accounts SET balance=balance+? WHERE account_no=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setDouble(1,amount);

                ps.setInt(2,accountNo);

                ps.executeUpdate();
		logArea.append("Deposited ₹" + amount + "\n");
		updateBalance();
		logArea.append(balanceLabel.getText() + "\n\n");

                ps.close();

                con.close();
            }




            else if(e.getSource()==withdrawButton)
            {
                double amount = Double.parseDouble(amountField.getText());

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE accounts SET balance=balance-? WHERE account_no=? AND balance>=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setDouble(1,amount);

                ps.setInt(2,accountNo);

                ps.setDouble(3,amount);

                int rows = ps.executeUpdate();

                if(rows>0)
                {
			updateBalance();
			logArea.append("Withdrawn ₹" + amount + "\n");
			logArea.append(balanceLabel.getText() + "\n\n");
		}

                else
                {
                    JOptionPane.showMessageDialog(this,"Insufficient Balance!");
                }

                updateBalance();

                ps.close();

                con.close();
            }

		else if(e.getSource()==checkButton)
		{
			updateBalance();
			JOptionPane.showMessageDialog(this, balanceLabel.getText());
		}




            else if(e.getSource()==logoutButton)
            {
                dispose();

                new LoginFrame();
            }


            amountField.setText("");

        }

        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,"Invalid Input!");
        }

    }



    public static void main(String[] args)
    {
        new ATMFrame(1,"Yogya");
    }

}