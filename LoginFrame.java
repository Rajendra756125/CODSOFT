import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener
{
    JTextField accField;
    JPasswordField pinField;

    JButton loginButton;
    JButton createButton;

    Color bg = new Color(33,37,43);

    Font labelFont = new Font("Arial", Font.BOLD, 18);
    Font fieldFont = new Font("Arial", Font.BOLD, 18);

    public LoginFrame()
    {
        setTitle("YCSK BANK LOGIN");

        setSize(500,400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(bg);

        setLayout(null);


        JLabel title = new JLabel("YCSK BANK");

        title.setBounds(150,20,250,40);

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Arial", Font.BOLD, 30));

        add(title);



        JLabel acc = new JLabel("Account No");

        acc.setBounds(50,100,150,30);

        acc.setForeground(Color.WHITE);

        acc.setFont(labelFont);

        add(acc);



        accField = new JTextField();

        accField.setBounds(220,100,200,40);

        accField.setFont(fieldFont);

        add(accField);



        JLabel pin = new JLabel("PIN");

        pin.setBounds(50,170,150,30);

        pin.setForeground(Color.WHITE);

        pin.setFont(labelFont);

        add(pin);



        pinField = new JPasswordField();

        pinField.setBounds(220,170,200,40);

        pinField.setFont(fieldFont);

        add(pinField);



        loginButton = new JButton("LOGIN");

        loginButton.setBounds(70,270,150,45);

        loginButton.setBackground(new Color(0,120,215));

        loginButton.setForeground(Color.WHITE);

        loginButton.setFont(new Font("Arial", Font.BOLD, 16));

        loginButton.addActionListener(this);

        add(loginButton);



        createButton = new JButton("CREATE ACCOUNT");

        createButton.setBounds(250,270,180,45);

        createButton.setBackground(new Color(0,153,76));

        createButton.setForeground(Color.WHITE);

        createButton.setFont(new Font("Arial", Font.BOLD, 16));

        createButton.addActionListener(this);

        add(createButton);



        setVisible(true);
    }



    @Override

    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==loginButton)
        {
            try
            {
                int accNo = Integer.parseInt(accField.getText());

                int pin = Integer.parseInt(
                        String.valueOf(pinField.getPassword()));

                Connection con = DBConnection.getConnection();

                String sql = "SELECT * FROM accounts WHERE account_no=? AND pin=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1,accNo);

                ps.setInt(2,pin);

                ResultSet rs = ps.executeQuery();


                if(rs.next())
                {
                    String name = rs.getString("name");

                    JOptionPane.showMessageDialog(this, "Welcome " + name + "!");

                    dispose();

                    new ATMFrame(accNo, name);
                }

                else
                {
                    JOptionPane.showMessageDialog(
                            this,

                            "Invalid Account Number or PIN!"
                    );
                }

                rs.close();

                ps.close();

                con.close();

            }

            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(
                        this,

                        ex.getMessage()
                );
            }

        }



        if(e.getSource()==createButton)
        {
            new CreateAccountFrame();

            dispose();
        }

    }



    public static void main(String[] args)
    {
        new LoginFrame();
    }

}