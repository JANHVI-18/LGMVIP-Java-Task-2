import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGame implements ActionListener {

    private JFrame frame;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JLabel textLabel;
    private JButton[] buttons = new JButton[9];
    private JButton resetButton;
    private boolean player1Turn;

    public TicTacToeGame() {
        // Frame setup
        frame = new JFrame("Tic-Tac-Toe");
        frame.setSize(500, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Title panel setup
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 500, 100);

        textLabel = new JLabel();
        textLabel.setBackground(new Color(25, 25, 25));
        textLabel.setForeground(new Color(255, 255, 255));
        textLabel.setFont(new Font("Ink Free", Font.BOLD, 75));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        titlePanel.add(textLabel);

        // Button panel setup
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        // Initialize buttons
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(240, 240, 240));
            buttons[i].addActionListener(this);
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            // Hover effect
            buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    JButton button = (JButton) evt.getSource();
                    if (button.getText().equals("")) {
                        button.setBackground(new Color(200, 200, 200));
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    JButton button = (JButton) evt.getSource();
                    if (button.getText().equals("")) {
                        button.setBackground(new Color(240, 240, 240));
                    }
                }
            });

            buttonPanel.add(buttons[i]);
        }

        // Reset button setup
        resetButton = new JButton("Play Again");
        resetButton.setFont(new Font("Ink Free", Font.BOLD, 30));
        resetButton.setFocusable(false);
        resetButton.setBackground(new Color(70, 130, 180));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> resetGame());
        resetButton.setVisible(false);

        // Add components to frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(resetButton, BorderLayout.SOUTH);

        frame.setVisible(true);

        firstTurn();
    }

    public void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Math.random() < 0.5) {
            player1Turn = true;
            textLabel.setText("X's Turn");
        } else {
            player1Turn = false;
            textLabel.setText("O's Turn");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            if (e.getSource() == button) {
                if (button.getText().equals("")) {
                    if (player1Turn) {
                        button.setForeground(new Color(255, 0, 0));
                        button.setText("X");
                        textLabel.setText("O's Turn");
                    } else {
                        button.setForeground(new Color(0, 0, 255));
                        button.setText("O");
                        textLabel.setText("X's Turn");
                    }
                    player1Turn = !player1Turn;
                    checkWin();
                }
            }
        }
    }

    public void checkWin() {
        // Check X win conditions
        if (
            (checkCombo(0, 1, 2, "X")) ||
            (checkCombo(3, 4, 5, "X")) ||
            (checkCombo(6, 7, 8, "X")) ||
            (checkCombo(0, 3, 6, "X")) ||
            (checkCombo(1, 4, 7, "X")) ||
            (checkCombo(2, 5, 8, "X")) ||
            (checkCombo(0, 4, 8, "X")) ||
            (checkCombo(2, 4, 6, "X"))
        ) {
            xWins();
        }
        // Check O win conditions
        else if (
            (checkCombo(0, 1, 2, "O")) ||
            (checkCombo(3, 4, 5, "O")) ||
            (checkCombo(6, 7, 8, "O")) ||
            (checkCombo(0, 3, 6, "O")) ||
            (checkCombo(1, 4, 7, "O")) ||
            (checkCombo(2, 5, 8, "O")) ||
            (checkCombo(0, 4, 8, "O")) ||
            (checkCombo(2, 4, 6, "O"))
        ) {
            oWins();
        }
        // Check draw condition
        else if (isBoardFull()) {
            draw();
        }
    }

    public boolean checkCombo(int a, int b, int c, String player) {
        return (buttons[a].getText().equals(player) &&
                buttons[b].getText().equals(player) &&
                buttons[c].getText().equals(player));
    }

    public boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    public void xWins() {
        highlightWin("X");
    }

    public void oWins() {
        highlightWin("O");
    }

    public void highlightWin(String player) {
        textLabel.setText(player + " Wins!");
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        resetButton.setVisible(true);
    }

    public void draw() {
        textLabel.setText("It's a Draw!");
        resetButton.setVisible(true);
    }

    public void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(new Color(240, 240, 240));
            button.setEnabled(true);
        }
        textLabel.setText("Tic-Tac-Toe");
        resetButton.setVisible(false);
        firstTurn();
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }


}
