import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ���l�ѹC���D���O�A�~�Ӧ� JFrame ���O
public class GomokuGame extends JFrame {
    private JButton[][] board; // �ѽL�W�����s�}�C
    private int currentPlayer; // ��e���쪺���a�]1 �� 2�^
    private boolean gameEnded; // �C���O�_�������лx
    private String player1Symbol; // ���a 1 ���Ѥl�Ÿ�
    private String player2Symbol; // ���a 2 ���Ѥl�Ÿ�
    private int movesCount; // �����ѽL�W���`�U�Ѧ���
    private int player1UndoCount; // ���a 1 �����Ѧ���
    private int player2UndoCount; // ���a 2 �����Ѧ���
    private JButton undoButton; // ���ѫ��s
    private int lastMoveRow; // �̫�@�B���Ѥl��y��
    private int lastMoveCol; // �̫�@�B���Ѥl�C�y��
    private boolean hasMoved; // �O�_�w�g����L�U�Ѱʧ@���лx
    private boolean[] hasMovedByPlayer = new boolean[3]; // 0: ���ϥ�, 1: ���a 1, 2: ���a 2
    private boolean firstTime = true; // �K�[�лx�H�ˬd�O�_�Ĥ@���}�l�C��

    // ���l�ѹC�����غc��k
    public GomokuGame() {
        hasMoved = false; // ��l�� hasMoved �� false
        initializeBoard(); // ��l�ƴѽL
        initializeGUI(); // ��l�ƹC������
    }

    // ���ܪ��a�O�_�n���Ѥl�Ÿ�
    private void askForSymbolChange() {
        // �u���b�Ĥ@���C���ɤ~��ܳW�h����
        if (firstTime) {
            // ��ܤ��l�Ѫ��C���W�h�M���ѳW�h
            String rulesMessage = "�w��Ө줭�l�ѡI\n\n"
                    + "�C���W�h:\n"
                    + "1. �C���b�@�� 9x9 ���ѽL�W�i��C\n"
                    + "2. ���a���y�b�Ů�W��m�ۤv���Ѥl�C\n"
                    + "3. �Ĥ@�ӳs�����l�����a�]��V�B�a�V�ι﨤�u�^��ӡC\n\n"
                    + "���ѳW�h:\n"
                    + "1. �C�쪱�a�i�H���Ѥ@���C\n"
                    + "2. ���Ѧ��ƭ���C�^�X�@���C\n\n"
                    + "�{�b�A���ڭ̳]�w�Ѥl�Ÿ��C";

            // ��ܳW�h�T������ܮ�
            JOptionPane.showMessageDialog(GomokuGame.this, rulesMessage, "���l�ѳW�h", JOptionPane.INFORMATION_MESSAGE);

            firstTime = false; // �b�Ĥ@����N�лx�]�m�� false
        }
        // ���ܪ��a�O�_�n���w�]���Ѥl�Ÿ�
        int symbolChangeOption = JOptionPane.showConfirmDialog(
                GomokuGame.this,
                "�O�_�n���w�]���Ѥl�Ÿ�?",
                "���Ÿ�",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // �p�G���a��ܧ��Ÿ�
        if (symbolChangeOption == JOptionPane.YES_OPTION) {
            // �߰ݪ��a��J���a 1 ���Ÿ�
            player1Symbol = getPlayerSymbolInput("��J���a 1 ���Ѥl�Ÿ� (�Ҧp�GX)�G");

            // �߰ݪ��a��J���a 2 ���Ÿ�
            player2Symbol = getPlayerSymbolInput("��J���a 2 ���Ѥl�Ÿ� (�Ҧp�GO)�G");

            // �T�O���a 1 �M���a 2 ���Ÿ����ۦP
            while (player1Symbol.equals(player2Symbol)) {
                JOptionPane.showMessageDialog(GomokuGame.this, "���a 1 �M���a 2 ���Ѥl�Ÿ�����ۦP�C�Э��s��J�C");
                player1Symbol = getPlayerSymbolInput("��J���a 1 ���Ѥl�Ÿ� (�Ҧp�GX)�G");
                player2Symbol = getPlayerSymbolInput("��J���a 2 ���Ѥl�Ÿ� (�Ҧp�GO)�G");
            }
        } else if (player1Symbol == null || player2Symbol == null) {
            // �p�G���a�������B�Ÿ����šA�]�w�w�]�Ÿ�
            player1Symbol = "��";
            player2Symbol = "��";
        }
    }

    // ���ܪ��a��J�Ѥl�Ÿ������U��k
    private String getPlayerSymbolInput(String message) {
        String symbol;
        do {
            // �ϥι�ܮؿ�J�Ÿ�
            symbol = JOptionPane.showInputDialog(GomokuGame.this, message);

            // �ˬd�Ÿ��O�_���šA�p�G�O�A���s��J
            if (symbol == null || symbol.trim().isEmpty()) {
                JOptionPane.showMessageDialog(GomokuGame.this, "�Ѥl�Ÿ����ର�šC�Э��s��J�C");
            }
        } while (symbol == null || symbol.trim().isEmpty());

        return symbol;
    }

    // ��l�ƴѽL����k
    private void initializeBoard() {
        board = new JButton[9][9]; // ��l�� 9x9 �����s�}�C
        currentPlayer = 1; // ��l�Ʒ�e���a�����a 1
        gameEnded = false; // ��l�ƹC��������
        movesCount = 0; // ��l�ƤU�Ѧ��Ƭ� 0
        player1UndoCount = 1; // ��l�ƪ��a 1 �����Ѧ��Ƭ� 1
        player2UndoCount = 1; // ��l�ƪ��a 2 �����Ѧ��Ƭ� 1
        askForSymbolChange(); // ���ܪ��a�O�_���Ѥl�Ÿ�

        // ��l�ƫ��s�}�C�A�ó]�w���s�˦��M��ť��
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new JButton();
                board[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                board[i][j].setPreferredSize(new Dimension(60, 60));
                board[i][j].addActionListener(new ButtonClickListener(i, j));
            }
        }

        undoButton = new JButton("����"); // ��l�Ʈ��ѫ��s
        undoButton.setFont(new Font("����", Font.PLAIN, 16));
        undoButton.addActionListener(new UndoButtonListener());
        undoButton.setPreferredSize(new Dimension(120, 60));
        // �]�w���ѫ��s���j�p�M�~�����
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        undoButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        // �N���ѫ��s�K�[��D����������������
        add(undoButton, BorderLayout.SOUTH);

    }

    // ��l�ƹC������
    private void initializeGUI() {
        // �]�w���������ާ@
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �]�w�������D
        setTitle("���l�ѹC��");
        // �ϥ� BorderLayout �G���޲z��
        setLayout(new BorderLayout());

        // ��l�Ƶ����
        JMenuBar menuBar = new JMenuBar();

        // ��l�Ƶ��
        JMenu gameMenu = new JMenu("Menu");

        // ��l�ƹC�����] MenuItem
        JMenuItem resetMenuItem = new JMenuItem("���]�C��");
        resetMenuItem.addActionListener(new ResetMenuItemListener());

        // ��l�Ƥ��l�ѳW�h���� MenuItem
        JMenuItem rulesMenuItem = new JMenuItem("���l�ѳW�h����");
        rulesMenuItem.addActionListener(new RulesMenuItemListener());

        // �N MenuItem �K�[����
        gameMenu.add(resetMenuItem);
        gameMenu.add(rulesMenuItem);

        // �N���K�[������
        menuBar.add(gameMenu);

        // �]�w�����
        setJMenuBar(menuBar);

        // ��l�ƴѽL���O�A�ϥ� GridLayout �ƦC 9x9 �����s
        JPanel boardPanel = new JPanel(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardPanel.add(board[i][j]);
            }
        }

        // ��l�Ʈ��ѫ��s
        undoButton = new JButton("����");
        undoButton.setFont(new Font("����", Font.PLAIN, 16));
        undoButton.addActionListener(new UndoButtonListener());
        undoButton.setPreferredSize(new Dimension(100, 40));

        // �Ыؤ@�Ӯe�����O�A�ϥ� FlowLayout �N���ѫ��s�m��
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(undoButton);

        // �N�ѽL���O�M���ѫ��s�e�����O�K�[��D����
        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // �վ�����j�p
        setLocationRelativeTo(null); // �N�����m�����
        setVisible(true); // ��ܵ���
    }

    // �s�W ResetMenuItemListener ���O�ӳB�z Reset ��涵�ت��ƥ�
    private class ResetMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame(); // ���]�C��
        }
    }

    // �s�W RulesMenuItemListener ���O�ӳB�z���l�ѳW�h������涵�ت��ƥ�
    private class RulesMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showRules(); // ��ܤ��l�ѳW�h
        }
    }

    // �s�W showRules ��k�Ω���ܤ��l�ѳW�h
    private void showRules() {
        // ��ܤ��l�Ѫ��C���W�h�M���ѳW�h
        String rulesMessage = "�w��Ө줭�l�ѡI\n\n"
                + "�C���W�h:\n"
                + "1. �C���b�@�� 9x9 ���ѽL�W�i��C\n"
                + "2. ���a���y�b�Ů�W��m�ۤv���Ѥl�C\n"
                + "3. �Ĥ@�ӳs�����l�����a�]��V�B�a�V�ι﨤�u�^��ӡC\n\n"
                + "���ѳW�h:\n"
                + "1. �C�쪱�a�i�H���Ѥ@���C\n"
                + "2. ���Ѧ��ƭ���C�^�X�@���C\n\n"
                + "�{�b�A���ڭ̳]�w�Ѥl�Ÿ��C";

        // ��ܳW�h�T������ܮ�
        JOptionPane.showMessageDialog(GomokuGame.this, rulesMessage, "���l�ѳW�h", JOptionPane.INFORMATION_MESSAGE);
    }

    // ���s�I���ƥ󪺤������O
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        // �c�y��k
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // ���s�I���ƥ�B�z
        @Override
        public void actionPerformed(ActionEvent e) {
            // �p�G�C���������B�Ӧ�m����
            if (!gameEnded && board[row][col].getText().equals("")) {
                String currentPlayerSymbol = getCurrentPlayerSymbol(); // ���o��e���a���Ѥl�Ÿ�
                board[row][col].setText(currentPlayerSymbol); // �b���s�W�]�w���a���Ѥl�Ÿ�

                // ��s�̫�@�B���y��
                lastMoveRow = row;
                lastMoveCol = col;
                hasMoved = true; // �N hasMoved �]�w�� true
                hasMovedByPlayer[currentPlayer] = false; // ���m��e���a�����ʼлx

                // �ˬd�O�_�����a���
                if (checkWinner(row, col)) {
                    JOptionPane.showMessageDialog(GomokuGame.this, "���a " + currentPlayer + " ��ӡI");
                    askForReplay(); // �߰ݬO�_���s�C��
                } else {
                    movesCount++; // ��s�U�Ѧ���
                    currentPlayer = 3 - currentPlayer; // ������t�@�쪱�a�]1 ���� 2�A2 ���� 1�^
                }
            }
        }
    }

    // �ˬd�O�_�����a��Ӫ���k
    private boolean checkWinner(int row, int col) {
        String currentPlayerSymbol = (currentPlayer == 1) ? player1Symbol : player2Symbol; // ���o��e���a���Ѥl�Ÿ�

        // �ˬd������V
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (board[row][i].getText().equals(currentPlayerSymbol)) {
                count++;
                if (count == 5) {
                    gameEnded = true; // �C������
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // �ˬd������V
        count = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i][col].getText().equals(currentPlayerSymbol)) {
                count++;
                if (count == 5) {
                    gameEnded = true; // �C������
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // �ˬd�﨤�u�]�q���W��k�U�^
        count = 0;
        for (int i = -4; i <= 4; i++) {
            int r = row + i;
            int c = col + i;
            if (r >= 0 && r < 9 && c >= 0 && c < 9) {
                if (board[r][c].getText().equals(currentPlayerSymbol)) {
                    count++;
                    if (count == 5) {
                        gameEnded = true; // �C������
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        // �ˬd�﨤�u�]�q���U��k�W�^
        count = 0;
        for (int i = -4; i <= 4; i++) {
            int r = row - i;
            int c = col + i;
            if (r >= 0 && r < 9 && c >= 0 && c < 9) {
                if (board[r][c].getText().equals(currentPlayerSymbol)) {
                    count++;
                    if (count == 5) {
                        gameEnded = true; // �C������
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        return false;
    }

    // ���ѫ��s�I���ƥ󪺤������O
    private class UndoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            undoLastMove();
        }
    }

    // �����޿�
    private void undoLastMove() {
        int currentPlayerUndoCount = (currentPlayer == 1) ? player1UndoCount : player2UndoCount;

        // �ˬd�O�_�����Ѧ��ơB�O�_�����ʡB�O�_�w�g���L��
        if (currentPlayerUndoCount > 0 && hasMoved && !hasMovedByPlayer[currentPlayer]) {
            // �T�{�O�_�u���n����
            int undoOption = JOptionPane.showConfirmDialog(
                    GomokuGame.this,
                    "�T�w�n���ѶܡH",
                    "�T�{����",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (undoOption == JOptionPane.YES_OPTION) {
                // �ϥΦs�x���y�ШӮ����̫�@�B
                board[lastMoveRow][lastMoveCol].setText("");
                movesCount--;

                hasMoved = false; // ���m���ʼлx
                currentPlayerUndoCount--; // ��֪��a���Ѧ���
                hasMovedByPlayer[currentPlayer] = true; // �Ӫ��a�w�g�ϥιL����
                currentPlayer = 3 - currentPlayer; // �����^���殬�Ѫ����a

                // �p�G�w�g������t�@�쪱�a�A�N������� playerUndoCount �ܦ^��l��
                if (currentPlayer == 1) {
                    player1UndoCount = 1;
                } else {
                    player2UndoCount = 1;
                }
            }
        } else {
            // ��ܮ��Ѥ��X�k���T��
            if (!hasMoved) {
                JOptionPane.showMessageDialog(GomokuGame.this, "�z�����b���ѫe�U�@�B�ѡC");
            } else if (hasMovedByPlayer[currentPlayer]) {
                JOptionPane.showMessageDialog(GomokuGame.this, "�z�w�g�b�o�^�X���ϥιL���ѤF�C");
            } else {
                JOptionPane.showMessageDialog(GomokuGame.this, "�z�w�g�Χ����^�X�����Ѿ��|�C");
            }
        }
    }

    // ���o�ثe���a���Ѥl�Ÿ�
    private String getCurrentPlayerSymbol() {
        return (currentPlayer == 1) ? player1Symbol : player2Symbol;
    }

    // �߰ݬO�_���s�C��
    private void askForReplay() {
        int replayOption = JOptionPane.showConfirmDialog(
                GomokuGame.this,
                "�O�_���s�C���H",
                "���s�C��",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (replayOption == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // ���m�C��
    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setText("");
            }
        }
        currentPlayer = 1;
        gameEnded = false;
        movesCount = 0;
        player1UndoCount = 1;
        player2UndoCount = 1;
        hasMovedByPlayer[1] = false;
        hasMovedByPlayer[2] = false;

        askForSymbolChange(); // ���s�߰ݬO�_�󴫲Ÿ�
    }

    public static void main(String[] args) {
        // �Ұ� GomokuGame
        SwingUtilities.invokeLater(() -> new GomokuGame());
    }
}
