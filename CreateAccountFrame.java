import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CreateAccountFrame extends JFrame implements ActionListener
{
    JTextField nameField, balanceField;
    JPasswordField pinField;

    JButton createButton, backButton;

    Color bg = new Color(33,37,43);

    Font titleFont = new Font("Arial", Font.BOLD, 32);
    Font labelFont = new Font("Arial", Font.BOLD, 18);
    Font fieldFont = new Font("Arial", Font.BOLD, 18);

    public CreateAccountFrame()
    {
        setTitle("YCSK BANK - CREATE ACCOUNT");

        setSize(550,500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(bg);

        setLayout(null);



        JLabel title = new JLabel("YCSK BANK");

        title.setBounds(145,20,300,40);

        title.setForeground(Color.WHITE);

        title.setFont(titleFont);

        add(title);



        JLabel subtitle = new JLabel("CREATE ACCOUNT");

        subtitle.setBounds(165,65,250,30);

        subtitle.setForeground(Color.LIGHT_GRAY);

        subtitle.setFont(new Font("Arial", Font.BOLD, 18));

        add(subtitle);




        JLabel name = new JLabel("Name");

        name.setBounds(60,130,150,30);

        name.setFont(labelFont);

        name.setForeground(Color.WHITE);

        add(name);



        nameField = new JTextField();

        nameField.setBounds(240,130,220,40);

        nameField.setFont(fieldFont);

        add(nameField);




        JLabel pin = new JLabel("PIN");

        pin.setBounds(60,190,150,30);

        pin.setFont(labelFont);

        pin.setForeground(Color.WHITE);

        add(pin);



        pinField = new JPasswordField();

        pinField.setBounds(240,190,220,40);

        pinField.setFont(fieldFont);

        add(pinField);




        JLabel balance = new JLabel("Initial Deposit");

        balance.setBounds(60,250,150,30);

        balance.setFont(labelFont);

        balance.setForeground(Color.WHITE);

        add(balance);



        balanceField = new JTextField();

        balanceField.setBounds(240,250,220,40);

        balanceField.setFont(fieldFont);

        add(balanceField);




        createButton = new JButton("CREATE ACCOUNT");

        createButton.setBounds(155,330,230,45);

        createButton.setBackground(new Color(0,120,215));

        createButton.setForeground(Color.WHITE);

        createButton.setFont(new Font("Arial", Font.BOLD, 16));

        createButton.setFocusPainted(false);

        createButton.addActionListener(this);

        add(createButton);




        backButton = new JButton("BACK TO LOGIN");

        backButton.setBounds(155,390,230,40);

        backButton.setBackground(new Color(108,117,125));

        backButton.setForeground(Color.WHITE);

        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        backButton.setFocusPainted(false);

        backButton.addActionListener(this);

        add(backButton);



        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==backButton)
        {
            dispose();

            new LoginFrame();

            return;
        }


        if(e.getSource()==createButton)
        {
            try
            {
                String name = nameField.getText();

                String pin = String.valueOf(pinField.getPassword());

                double balance = Double.parseDouble(balanceField.getText());

                if(name.isEmpty() || pin.isEmpty())
                {
                    JOptionPane.showMessageDialog(this,"Please fill all fields!");

                    return;
                }

                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO accounts(name,pin,balance) VALUES(?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

                ps.setString(1,name);

                ps.setInt(2,Integer.parseInt(pin));

                ps.setDouble(3,balance);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next())
                {
                    int accNo = rs.getInt(1);

                    JOptionPane.showMessageDialog(this,
                            "Account Created Successfully!\n\n"
                            + "Account Number : " + accNo
                            + "\n\nPlease login to continue.");

                    dispose();

                    new LoginFrame();
                }

                rs.close();

                ps.close();

                con.close();

            }

            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        }
    }



    public static void main(String[] args)
    {
        new CreateAccountFrame();
    }
}