import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 五子棋遊戲主類別，繼承自 JFrame 類別
public class GomokuGame extends JFrame {
    private JButton[][] board; // 棋盤上的按鈕陣列
    private int currentPlayer; // 當前輪到的玩家（1 或 2）
    private boolean gameEnded; // 遊戲是否結束的標誌
    private String player1Symbol; // 玩家 1 的棋子符號
    private String player2Symbol; // 玩家 2 的棋子符號
    private int movesCount; // 紀錄棋盤上的總下棋次數
    private int player1UndoCount; // 玩家 1 的悔棋次數
    private int player2UndoCount; // 玩家 2 的悔棋次數
    private JButton undoButton; // 悔棋按鈕
    private int lastMoveRow; // 最後一步的棋子行座標
    private int lastMoveCol; // 最後一步的棋子列座標
    private boolean hasMoved; // 是否已經執行過下棋動作的標誌
    private boolean[] hasMovedByPlayer = new boolean[3]; // 0: 未使用, 1: 玩家 1, 2: 玩家 2
    private boolean firstTime = true; // 添加標誌以檢查是否第一次開始遊戲

    // 五子棋遊戲的建構方法
    public GomokuGame() {
        hasMoved = false; // 初始化 hasMoved 為 false
        initializeBoard(); // 初始化棋盤
        initializeGUI(); // 初始化遊戲介面
    }

    // 提示玩家是否要更改棋子符號
    private void askForSymbolChange() {
        // 只有在第一次遊戲時才顯示規則介紹
        if (firstTime) {
            // 顯示五子棋的遊戲規則和悔棋規則
            String rulesMessage = "歡迎來到五子棋！\n\n"
                    + "遊戲規則:\n"
                    + "1. 遊戲在一個 9x9 的棋盤上進行。\n"
                    + "2. 玩家輪流在空格上放置自己的棋子。\n"
                    + "3. 第一個連成五子的玩家（橫向、縱向或對角線）獲勝。\n\n"
                    + "悔棋規則:\n"
                    + "1. 每位玩家可以悔棋一次。\n"
                    + "2. 悔棋次數限制為每回合一次。\n\n"
                    + "現在，讓我們設定棋子符號。";

            // 顯示規則訊息的對話框
            JOptionPane.showMessageDialog(GomokuGame.this, rulesMessage, "五子棋規則", JOptionPane.INFORMATION_MESSAGE);

            firstTime = false; // 在第一次後將標誌設置為 false
        }
        // 提示玩家是否要更改預設的棋子符號
        int symbolChangeOption = JOptionPane.showConfirmDialog(
                GomokuGame.this,
                "是否要更改預設的棋子符號?",
                "更改符號",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // 如果玩家選擇更改符號
        if (symbolChangeOption == JOptionPane.YES_OPTION) {
            // 詢問玩家輸入玩家 1 的符號
            player1Symbol = getPlayerSymbolInput("輸入玩家 1 的棋子符號 (例如：X)：");

            // 詢問玩家輸入玩家 2 的符號
            player2Symbol = getPlayerSymbolInput("輸入玩家 2 的棋子符號 (例如：O)：");

            // 確保玩家 1 和玩家 2 的符號不相同
            while (player1Symbol.equals(player2Symbol)) {
                JOptionPane.showMessageDialog(GomokuGame.this, "玩家 1 和玩家 2 的棋子符號不能相同。請重新輸入。");
                player1Symbol = getPlayerSymbolInput("輸入玩家 1 的棋子符號 (例如：X)：");
                player2Symbol = getPlayerSymbolInput("輸入玩家 2 的棋子符號 (例如：O)：");
            }
        } else if (player1Symbol == null || player2Symbol == null) {
            // 如果玩家取消更改且符號為空，設定預設符號
            player1Symbol = "●";
            player2Symbol = "○";
        }
    }

    // 提示玩家輸入棋子符號的輔助方法
    private String getPlayerSymbolInput(String message) {
        String symbol;
        do {
            // 使用對話框輸入符號
            symbol = JOptionPane.showInputDialog(GomokuGame.this, message);

            // 檢查符號是否為空，如果是，重新輸入
            if (symbol == null || symbol.trim().isEmpty()) {
                JOptionPane.showMessageDialog(GomokuGame.this, "棋子符號不能為空。請重新輸入。");
            }
        } while (symbol == null || symbol.trim().isEmpty());

        return symbol;
    }

    // 初始化棋盤的方法
    private void initializeBoard() {
        board = new JButton[9][9]; // 初始化 9x9 的按鈕陣列
        currentPlayer = 1; // 初始化當前玩家為玩家 1
        gameEnded = false; // 初始化遊戲未結束
        movesCount = 0; // 初始化下棋次數為 0
        player1UndoCount = 1; // 初始化玩家 1 的悔棋次數為 1
        player2UndoCount = 1; // 初始化玩家 2 的悔棋次數為 1
        askForSymbolChange(); // 提示玩家是否更改棋子符號

        // 初始化按鈕陣列，並設定按鈕樣式和監聽器
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new JButton();
                board[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                board[i][j].setPreferredSize(new Dimension(60, 60));
                board[i][j].addActionListener(new ButtonClickListener(i, j));
            }
        }

        undoButton = new JButton("悔棋"); // 初始化悔棋按鈕
        undoButton.setFont(new Font("宋體", Font.PLAIN, 16));
        undoButton.addActionListener(new UndoButtonListener());
        undoButton.setPreferredSize(new Dimension(120, 60));
        // 設定悔棋按鈕的大小和居中對齊
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        undoButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        // 將悔棋按鈕添加到主視窗的底部正中間
        add(undoButton, BorderLayout.SOUTH);

    }

    // 初始化遊戲介面
    private void initializeGUI() {
        // 設定視窗關閉操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 設定視窗標題
        setTitle("五子棋遊戲");
        // 使用 BorderLayout 佈局管理器
        setLayout(new BorderLayout());

        // 初始化菜單欄
        JMenuBar menuBar = new JMenuBar();

        // 初始化菜單
        JMenu gameMenu = new JMenu("Menu");

        // 初始化遊戲重設 MenuItem
        JMenuItem resetMenuItem = new JMenuItem("重設遊戲");
        resetMenuItem.addActionListener(new ResetMenuItemListener());

        // 初始化五子棋規則說明 MenuItem
        JMenuItem rulesMenuItem = new JMenuItem("五子棋規則說明");
        rulesMenuItem.addActionListener(new RulesMenuItemListener());

        // 將 MenuItem 添加到菜單
        gameMenu.add(resetMenuItem);
        gameMenu.add(rulesMenuItem);

        // 將菜單添加到菜單欄
        menuBar.add(gameMenu);

        // 設定菜單欄
        setJMenuBar(menuBar);

        // 初始化棋盤面板，使用 GridLayout 排列 9x9 的按鈕
        JPanel boardPanel = new JPanel(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardPanel.add(board[i][j]);
            }
        }

        // 初始化悔棋按鈕
        undoButton = new JButton("悔棋");
        undoButton.setFont(new Font("宋體", Font.PLAIN, 16));
        undoButton.addActionListener(new UndoButtonListener());
        undoButton.setPreferredSize(new Dimension(100, 40));

        // 創建一個容器面板，使用 FlowLayout 將悔棋按鈕置中
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(undoButton);

        // 將棋盤面板和悔棋按鈕容器面板添加到主視窗
        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // 調整視窗大小
        setLocationRelativeTo(null); // 將視窗置中顯示
        setVisible(true); // 顯示視窗
    }

    // 新增 ResetMenuItemListener 類別來處理 Reset 選單項目的事件
    private class ResetMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame(); // 重設遊戲
        }
    }

    // 新增 RulesMenuItemListener 類別來處理五子棋規則說明選單項目的事件
    private class RulesMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showRules(); // 顯示五子棋規則
        }
    }

    // 新增 showRules 方法用於顯示五子棋規則
    private void showRules() {
        // 顯示五子棋的遊戲規則和悔棋規則
        String rulesMessage = "歡迎來到五子棋！\n\n"
                + "遊戲規則:\n"
                + "1. 遊戲在一個 9x9 的棋盤上進行。\n"
                + "2. 玩家輪流在空格上放置自己的棋子。\n"
                + "3. 第一個連成五子的玩家（橫向、縱向或對角線）獲勝。\n\n"
                + "悔棋規則:\n"
                + "1. 每位玩家可以悔棋一次。\n"
                + "2. 悔棋次數限制為每回合一次。\n\n"
                + "現在，讓我們設定棋子符號。";

        // 顯示規則訊息的對話框
        JOptionPane.showMessageDialog(GomokuGame.this, rulesMessage, "五子棋規則", JOptionPane.INFORMATION_MESSAGE);
    }

    // 按鈕點擊事件的內部類別
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        // 構造方法
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // 按鈕點擊事件處理
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果遊戲未結束且該位置為空
            if (!gameEnded && board[row][col].getText().equals("")) {
                String currentPlayerSymbol = getCurrentPlayerSymbol(); // 取得當前玩家的棋子符號
                board[row][col].setText(currentPlayerSymbol); // 在按鈕上設定玩家的棋子符號

                // 更新最後一步的座標
                lastMoveRow = row;
                lastMoveCol = col;
                hasMoved = true; // 將 hasMoved 設定為 true
                hasMovedByPlayer[currentPlayer] = false; // 重置當前玩家的移動標誌

                // 檢查是否有玩家獲勝
                if (checkWinner(row, col)) {
                    JOptionPane.showMessageDialog(GomokuGame.this, "玩家 " + currentPlayer + " 獲勝！");
                    askForReplay(); // 詢問是否重新遊玩
                } else {
                    movesCount++; // 更新下棋次數
                    currentPlayer = 3 - currentPlayer; // 切換到另一位玩家（1 換成 2，2 換成 1）
                }
            }
        }
    }

    // 檢查是否有玩家獲勝的方法
    private boolean checkWinner(int row, int col) {
        String currentPlayerSymbol = (currentPlayer == 1) ? player1Symbol : player2Symbol; // 取得當前玩家的棋子符號

        // 檢查水平方向
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (board[row][i].getText().equals(currentPlayerSymbol)) {
                count++;
                if (count == 5) {
                    gameEnded = true; // 遊戲結束
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // 檢查垂直方向
        count = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i][col].getText().equals(currentPlayerSymbol)) {
                count++;
                if (count == 5) {
                    gameEnded = true; // 遊戲結束
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // 檢查對角線（從左上到右下）
        count = 0;
        for (int i = -4; i <= 4; i++) {
            int r = row + i;
            int c = col + i;
            if (r >= 0 && r < 9 && c >= 0 && c < 9) {
                if (board[r][c].getText().equals(currentPlayerSymbol)) {
                    count++;
                    if (count == 5) {
                        gameEnded = true; // 遊戲結束
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        // 檢查對角線（從左下到右上）
        count = 0;
        for (int i = -4; i <= 4; i++) {
            int r = row - i;
            int c = col + i;
            if (r >= 0 && r < 9 && c >= 0 && c < 9) {
                if (board[r][c].getText().equals(currentPlayerSymbol)) {
                    count++;
                    if (count == 5) {
                        gameEnded = true; // 遊戲結束
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        return false;
    }

    // 悔棋按鈕點擊事件的內部類別
    private class UndoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            undoLastMove();
        }
    }

    // 悔棋邏輯
    private void undoLastMove() {
        int currentPlayerUndoCount = (currentPlayer == 1) ? player1UndoCount : player2UndoCount;

        // 檢查是否有悔棋次數、是否有移動、是否已經悔過棋
        if (currentPlayerUndoCount > 0 && hasMoved && !hasMovedByPlayer[currentPlayer]) {
            // 確認是否真的要悔棋
            int undoOption = JOptionPane.showConfirmDialog(
                    GomokuGame.this,
                    "確定要悔棋嗎？",
                    "確認悔棋",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (undoOption == JOptionPane.YES_OPTION) {
                // 使用存儲的座標來悔掉最後一步
                board[lastMoveRow][lastMoveCol].setText("");
                movesCount--;

                hasMoved = false; // 重置移動標誌
                currentPlayerUndoCount--; // 減少玩家悔棋次數
                hasMovedByPlayer[currentPlayer] = true; // 該玩家已經使用過悔棋
                currentPlayer = 3 - currentPlayer; // 切換回執行悔棋的玩家

                // 如果已經切換到另一位玩家，將其對應的 playerUndoCount 變回初始值
                if (currentPlayer == 1) {
                    player1UndoCount = 1;
                } else {
                    player2UndoCount = 1;
                }
            }
        } else {
            // 顯示悔棋不合法的訊息
            if (!hasMoved) {
                JOptionPane.showMessageDialog(GomokuGame.this, "您必須在悔棋前下一步棋。");
            } else if (hasMovedByPlayer[currentPlayer]) {
                JOptionPane.showMessageDialog(GomokuGame.this, "您已經在這回合中使用過悔棋了。");
            } else {
                JOptionPane.showMessageDialog(GomokuGame.this, "您已經用完本回合的悔棋機會。");
            }
        }
    }

    // 取得目前玩家的棋子符號
    private String getCurrentPlayerSymbol() {
        return (currentPlayer == 1) ? player1Symbol : player2Symbol;
    }

    // 詢問是否重新遊玩
    private void askForReplay() {
        int replayOption = JOptionPane.showConfirmDialog(
                GomokuGame.this,
                "是否重新遊玩？",
                "重新遊玩",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (replayOption == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // 重置遊戲
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

        askForSymbolChange(); // 重新詢問是否更換符號
    }

    public static void main(String[] args) {
        // 啟動 GomokuGame
        SwingUtilities.invokeLater(() -> new GomokuGame());
    }
}
