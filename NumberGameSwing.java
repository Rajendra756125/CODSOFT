import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberGameSwing extends JFrame implements ActionListener
{
	private JTextField guessField;
	private JTextArea gameArea;
	private JLabel attemptsLabel;
	private JLabel statsLabel;
	private JButton submitButton;
	private JButton playAgainButton;

	private int randomNumber;
	private int guesses;
	private final int MAX_ATTEMPTS = 7;
	private int rounds = 0;
	private int wins = 0;
	private int totalGuesses = 0;

	public NumberGameSwing()
	{
		setTitle("Number Guessing Game");
		setSize(750, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Color bg = new Color(33, 37, 43);
		getContentPane().setBackground(bg);
		setLayout(new BorderLayout(15,15));

	//---------------- Title ----------------
		JLabel title = new JLabel("NUMBER GUESSING GAME", JLabel.CENTER);

		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setForeground(Color.WHITE);
		add(title, BorderLayout.NORTH);

        //---------------- Main Panel ----------------

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10,10));
		mainPanel.setBackground(bg);

        //---------------- Input Panel ----------------

		JPanel inputPanel = new JPanel();

		inputPanel.setBackground(bg);
		JLabel guessLabel = new JLabel("Enter Guess:");

		guessLabel.setForeground(Color.WHITE);
		guessLabel.setFont(new Font("Arial", Font.BOLD, 16));

		guessField = new JTextField(10);
		guessField.setFont(new Font("Arial", Font.PLAIN, 18));
		submitButton = new JButton("Submit");

		playAgainButton = new JButton("Play Again");
		playAgainButton.setEnabled(false);
		submitButton.addActionListener(this);
		playAgainButton.addActionListener(this);

		inputPanel.add(guessLabel);
		inputPanel.add(guessField);
		inputPanel.add(submitButton);
		inputPanel.add(playAgainButton);

        //---------------- Game Area ----------------

		gameArea = new JTextArea();
		gameArea.setEditable(false);
		gameArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		gameArea.setBackground(new Color(20,20,20));
		gameArea.setForeground(new Color(0,255,100));

		JScrollPane scrollPane = new JScrollPane(gameArea);

        //---------------- Stats ----------------

		JPanel statsPanel = new JPanel();
		statsPanel.setBackground(bg);
		statsPanel.setLayout(new GridLayout(2,1));

		attemptsLabel = new JLabel("Attempts Left: 7", JLabel.CENTER);
		statsLabel = new JLabel("Rounds: 0 | Wins: 0 | Avg: 0", JLabel.CENTER);
		attemptsLabel.setForeground(Color.WHITE);
		statsLabel.setForeground(Color.WHITE);
		statsPanel.add(attemptsLabel);
		statsPanel.add(statsLabel);

        //---------------- Add to Main ----------------

		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(statsPanel, BorderLayout.SOUTH);

		add(mainPanel, BorderLayout.CENTER);

		startNewGame();
		setVisible(true);
	}

	public void startNewGame()
	{
		randomNumber = (int)(Math.random()*100)+1;
		guesses = 0;

		guessField.setText("");
		gameArea.setText("");

		gameArea.append("================================\n");

		gameArea.append("       NEW GAME STARTED\n");
		gameArea.append("================================\n");
		gameArea.append("Guess a number between 1 and 100\n\n");

		attemptsLabel.setText("Attempts Left: " + MAX_ATTEMPTS);

		submitButton.setEnabled(true);
		playAgainButton.setEnabled(false);
		guessField.setEditable(true);
		guessField.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==submitButton)
		{
			try
			{
				int guess = Integer.parseInt(guessField.getText());

				if(guess<1 || guess>100)
				{
					JOptionPane.showMessageDialog(this, "Enter number between 1 and 100");
					return;
				}

				guesses++;
				if(guess<randomNumber)
					gameArea.append(guess + " -> Too Low!\n");

				else if(guess>randomNumber)
					gameArea.append(guess + " -> Too High!\n");

				else
				{
					gameArea.append("\nCorrect Guess!\n");
					gameArea.append("You guessed in " + guesses + " attempts.\n");
						wins++;
					rounds++;
					totalGuesses += guesses;
					updateStats();

					submitButton.setEnabled(false);
					playAgainButton.setEnabled(true);
					guessField.setEditable(false);
					return;
				}

				int remaining = MAX_ATTEMPTS - guesses;

				attemptsLabel.setText("Attempts Left: " + remaining);
				if(remaining==0)
				{
					gameArea.append("\nGAME OVER!\n");
					gameArea.append("Correct Number was : " + randomNumber + "\n");
					rounds++;
					totalGuesses += guesses;
					updateStats();

					submitButton.setEnabled(false);
					playAgainButton.setEnabled(true);
					guessField.setEditable(false);
				}

				guessField.setText("");
			}

			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(this, "Please enter a valid integer!");
			}
		}

		if(e.getSource()==playAgainButton)
			startNewGame();
	}

	public void updateStats()
	{
		double avg = (double) totalGuesses / rounds;

		statsLabel.setText("Rounds: " + rounds + " | Wins: " + wins + " | Avg Guesses: " + String.format("%.2f",avg));
	}

	public static void main(String[] args)
	{
		new NumberGameSwing();
	}
}