import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class GomokuClient extends JFrame {

    private JButton[][] buttons;
    private char currentPlayer;
    private char playerSymbol;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public GomokuClient(String title) {
        super(title);
        this.setLocation(550, 250);
        this.setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = this.getContentPane();
        contentPane.setBackground(Color.BLUE);

        contentPane.setLayout(new GridLayout(9, 9));

        buttons = new JButton[9][9];
        currentPlayer = 'O';

        try {
            Socket socket = new Socket("localhost", 8888);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Receive player's symbol and set it on the GUI
            playerSymbol = (char) in.readObject();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    buttons[i][j] = new JButton("");
                    buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    buttons[i][j].addActionListener(new MyActionListener(i, j));
                    contentPane.add(buttons[i][j]);
                }
            }

            // Start the game loop
            playGame();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        try {
            while (true) {
                // Receive updated game status
                char[][] board = (char[][]) in.readObject();
                currentPlayer = (char) in.readObject();

                // Update the GUI based on the received game status
                updateGUI(board);

                // Check if the player has won
                char winner = checkForWinner();
                if (winner != 0) {
                    showWinner(winner);
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateGUI(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j].setText(Character.toString(board[i][j]));
            }
        }
    }

    private char checkForWinner() {
        // Implement your winning condition logic here
        // For simplicity, you can use the existing checkForWinner logic from MyJFrame
        // Adjust it to work with a 9x9 board
        return 0;
    }

    private void showWinner(char winner) {
        String result = (winner == playerSymbol) ? "You win!" : "You lose!";
        JOptionPane.showMessageDialog(GomokuClient.this, result, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private class MyActionListener implements ActionListener {
        private int row;
        private int col;

        public MyActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent event) {
            try {
                // Send the selected move to the server
                out.writeObject(new Point(row, col));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GomokuClient("Gomoku Game");
    }
}
