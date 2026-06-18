import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeCalcSwing extends JFrame implements ActionListener
{
    private JPanel inputPanel;

    private JButton addButton;
    private JButton removeButton;
    private JButton calculateButton;

    private ArrayList<JTextField> subjectFields;
    private ArrayList<JTextField> marksFields;

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;
    private JLabel percentageLabel;
    private JLabel resultLabel;

    private Color bg = new Color(33,37,43);

    public StudentGradeCalcSwing()
    {
        subjectFields = new ArrayList<>();

        marksFields = new ArrayList<>();

        setTitle("Student Grade Calculator");

        setSize(900,700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        getContentPane().setBackground(bg);

        setLayout(new BorderLayout(10,10));


        //---------------- TITLE ----------------

        JLabel title =
                new JLabel(
                        "STUDENT GRADE CALCULATOR",
                        JLabel.CENTER);

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30));

        add(title,BorderLayout.NORTH);



        //---------------- MAIN PANEL ----------------

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(10,10));

        mainPanel.setBackground(bg);



        //---------------- BUTTON PANEL ----------------

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(bg);

        addButton = new JButton("Add Subject");

        removeButton = new JButton("Remove Last");

        calculateButton = new JButton("Calculate");

        addButton.addActionListener(this);

        removeButton.addActionListener(this);

        calculateButton.addActionListener(this);

        buttonPanel.add(addButton);

        buttonPanel.add(removeButton);

        buttonPanel.add(calculateButton);



        //---------------- INPUT PANEL ----------------

        inputPanel = new JPanel();

        inputPanel.setBackground(bg);

        inputPanel.setLayout(
                new BoxLayout(
                        inputPanel,
                        BoxLayout.Y_AXIS));


        JScrollPane inputScroll =
                new JScrollPane(inputPanel);

        inputScroll.setPreferredSize(
                new Dimension(850,250));



        //---------------- TABLE ----------------

        String[] cols =
        {
                "Subject",
                "Marks",
                "Grade"
        };

        model =
                new DefaultTableModel(
                        cols,
                        0);

        table =
                new JTable(model);

        table.setRowHeight(28);

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16));

        JScrollPane tableScroll =
                new JScrollPane(table);



        //---------------- RESULT PANEL ----------------

        JPanel resultPanel =
                new JPanel(
                        new GridLayout(3,1));

        resultPanel.setBackground(bg);


        totalLabel =
                new JLabel(
                        "Total : ",
                        JLabel.CENTER);

        percentageLabel =
                new JLabel(
                        "Percentage : ",
                        JLabel.CENTER);

        resultLabel =
                new JLabel(
                        "Result : ",
                        JLabel.CENTER);


        totalLabel.setForeground(Color.WHITE);

        percentageLabel.setForeground(Color.WHITE);

        resultLabel.setForeground(Color.WHITE);


        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));

        percentageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));

        resultLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24));


        resultPanel.add(totalLabel);

        resultPanel.add(percentageLabel);

        resultPanel.add(resultLabel);



        //---------------- ASSEMBLE ----------------

        JPanel north =
                new JPanel(
                        new BorderLayout());

        north.setBackground(bg);

        north.add(
                buttonPanel,
                BorderLayout.NORTH);

        north.add(
                inputScroll,
                BorderLayout.CENTER);


        JPanel south =
                new JPanel(
                        new BorderLayout());

        south.setBackground(bg);

        south.add(
                tableScroll,
                BorderLayout.CENTER);

        south.add(
                resultPanel,
                BorderLayout.SOUTH);


        mainPanel.add(
                north,
                BorderLayout.NORTH);

        mainPanel.add(
                south,
                BorderLayout.CENTER);

        add(mainPanel);


        setVisible(true);
    }



    public char grade(int n)
    {
        if(n>=90)
            return 'A';

        else if(n>=75)
            return 'B';

        else if(n>=60)
            return 'C';

        else if(n>=45)
            return 'D';

        else if(n>=33)
            return 'E';

        return 'F';
    }



    public void addSubject()
    {

        JPanel row = new JPanel();

        row.setBackground(bg);


        JTextField subject =
                new JTextField(20);

        JTextField marks =
                new JTextField(8);


        subject.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));

        marks.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));


        subject.setPreferredSize(
                new Dimension(
                        250,
                        40));

        marks.setPreferredSize(
                new Dimension(
                        100,
                        40));


        JLabel subjectLabel =
                new JLabel("Subject");

        JLabel marksLabel =
                new JLabel("Marks");

        subjectLabel.setForeground(Color.WHITE);

        marksLabel.setForeground(Color.WHITE);


        row.add(subjectLabel);

        row.add(subject);

        row.add(Box.createHorizontalStrut(20));

        row.add(marksLabel);

        row.add(marks);


        subjectFields.add(subject);

        marksFields.add(marks);


        inputPanel.add(row);

        inputPanel.revalidate();

        inputPanel.repaint();
    }



    public void removeLast()
    {

        if(subjectFields.size()==0)
            return;


        int last =
                subjectFields.size()-1;


        inputPanel.remove(last);


        subjectFields.remove(last);

        marksFields.remove(last);


        inputPanel.revalidate();

        inputPanel.repaint();
    }



    public void calculate()
    {

        try
        {

            model.setRowCount(0);

            int total = 0;


            for(int i=0;
                i<subjectFields.size();
                i++)
            {

                String sub =
                        subjectFields
                                .get(i)
                                .getText();

                int marks =
                        Integer.parseInt(

                                marksFields
                                        .get(i)
                                        .getText());


                if(marks<0 || marks>100)
                {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100");

                    return;
                }


                char g =
                        grade(marks);


                total += marks;


                model.addRow(

                        new Object[]
                        {
                                sub,
                                marks,
                                g
                        });

            }


            double percentage =
                    (double) total
                            /
                            subjectFields.size();


            totalLabel.setText(

                    "Total : "

                            + total

                            + " / "

                            + (subjectFields.size()*100));


            percentageLabel.setText(

                    String.format(

                            "Percentage : %.2f%%",

                            percentage));


            if(percentage>=33)
            {

                resultLabel.setText(
                        "Result : PASS");

                resultLabel.setForeground(
                        Color.GREEN);
            }

            else
            {

                resultLabel.setText(
                        "Result : FAIL");

                resultLabel.setForeground(
                        Color.RED);
            }

        }

        catch(Exception ex)
        {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid data!");

        }

    }



    @Override

    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==addButton)
        {

            addSubject();

        }

        else if(e.getSource()==removeButton)
        {

            removeLast();

        }

        else if(e.getSource()==calculateButton)
        {

            calculate();

        }

    }



    public static void main(String[] args)
    {

        new StudentGradeCalcSwing();

    }

}