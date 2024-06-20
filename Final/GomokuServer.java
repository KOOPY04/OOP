import java.io.*;
import java.net.*;
import java.awt.Point; // Add this import statement

public class GomokuServer {
    private static final int PORT = 8888;
    private Socket player1;
    private Socket player2;
    private char[][] board;
    private char currentPlayer;

    public GomokuServer() {
        board = new char[9][9];
        currentPlayer = 'O';

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running. Waiting for players...");

            player1 = serverSocket.accept();
            System.out.println("Player 1 connected.");

            player2 = serverSocket.accept();
            System.out.println("Player 2 connected.");

            playGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        try {
            ObjectOutputStream out1 = new ObjectOutputStream(player1.getOutputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(player2.getOutputStream());
            ObjectInputStream in1 = new ObjectInputStream(player1.getInputStream());
            ObjectInputStream in2 = new ObjectInputStream(player2.getInputStream());

            while (true) {
                sendGameStatus(out1);
                sendGameStatus(out2);

                Point move = (Point) (currentPlayer == 'O' ? in1.readObject() : in2.readObject());

                if (isValidMove(move)) {
                    makeMove(move);
                    if (checkForWinner()) {
                        sendGameStatus(out1);
                        sendGameStatus(out2);
                        sendWinner(out1);
                        sendWinner(out2);
                        break;
                    }
                    currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
                }
            }

            player1.close();
            player2.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendGameStatus(ObjectOutputStream out) throws IOException {
        out.writeObject(board);
        out.writeObject(currentPlayer);
    }

    private void sendWinner(ObjectOutputStream out) throws IOException {
        char winner = (currentPlayer == 'O') ? 'X' : 'O';
        out.writeObject(winner);
    }

    private boolean isValidMove(Point move) {
        // Check if the move is within the board and the cell is empty
        return move.x >= 0 && move.x < 9 && move.y >= 0 && move.y < 9 && board[move.x][move.y] == 0;
    }

    private void makeMove(Point move) {
        board[move.x][move.y] = currentPlayer;
    }

    private boolean checkForWinner() {
        // Implement your winning condition logic here
        // For simplicity, you can use the existing checkForWinner logic from MyJFrame
        // Adjust it to work with a 9x9 board
        return false;
    }

    public static void main(String[] args) {
        new GomokuServer();
    }
}
