import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HangmanGame extends JFrame {
    private String targetWord;
    private StringBuilder guessedWord;
    private StringBuilder wrongGuesses;
    private int remainingGuesses = 7;
    private JLabel wordLabel;
    private JLabel statusLabel;
    private JTextField guessField;

    public HangmanGame() {
        setTitle("Hangman Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        selectTargetWord();
        setupUI();
        updateWordLabel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selectTargetWord() {
        targetWord = Main.getWord();
        guessedWord = new StringBuilder("_".repeat(targetWord.length()));
        wrongGuesses = new StringBuilder();
    }

    private void setupUI() {
        wordLabel = new JLabel();
        add(wordLabel);

        statusLabel = new JLabel();
        add(statusLabel);

        guessField = new JTextField();
        guessField.addActionListener(e -> processGuess());
        add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> processGuess());
        add(guessButton);
    }

    private void updateWordLabel() {
        StringBuilder displayWord = new StringBuilder();
        for (int i = 0; i < guessedWord.length(); i++) {
            displayWord.append(guessedWord.charAt(i)).append(" ");
        }
        wordLabel.setText(displayWord.toString());
    }

    private void processGuess() {
        String guess = guessField.getText().toLowerCase();
        guessField.setText("");

        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Please enter a single letter.");
            return;
        }

        char guessedLetter = guess.charAt(0);

        if (targetWord.contains(guess)) {
            for (int i = 0; i < targetWord.length(); i++) {
                if (targetWord.charAt(i) == guessedLetter) {
                    guessedWord.setCharAt(i, guessedLetter);
                }
            }
            updateWordLabel();

            if (guessedWord.toString().equals(targetWord)) {
                JOptionPane.showMessageDialog(this, "Congratulations! You won!");
                resetGame();
            }
        } else {
            remainingGuesses--;
            wrongGuesses.append(guessedLetter).append(" ");
            statusLabel.setText("Incorrect guesses: " + wrongGuesses + " Remaining guesses: " + remainingGuesses);
            if (remainingGuesses == 0) {
                JOptionPane.showMessageDialog(this, "Sorry, you ran out of guesses. The word was: " + targetWord);
                resetGame();
            }
        }
    }

    private void resetGame() {
        remainingGuesses = 7;
        selectTargetWord();
        updateWordLabel();
        statusLabel.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGame::new);
    }
}
