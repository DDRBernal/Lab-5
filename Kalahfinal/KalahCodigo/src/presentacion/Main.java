package presentacion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JPanel {
    private static final int ROWS = 8;
    private static final int COLS = ROWS;
    private static final int GAP = 5;
    private JButton[][] buttonGrid = new JButton[8][8];
    private JPanel board,board2;

    public Main() {
        board2 = new JPanel();
        board2.setLayout(new BorderLayout());
        board = new JPanel();
        board.setLayout(new GridLayout(8, 8, 5, 5));

        ActionListener buttonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                JButton selectedBtn = (JButton) evt.getSource();
                for (int row = 0; row < buttonGrid.length; row++) {
                    for (int col = 0; col < buttonGrid[row].length; col++) {
                        if (buttonGrid[row][col] == selectedBtn) {
                            buttonGrid[row][col].setText("SDSDS");
                            System.out.printf("Selected row and column: %d %d%n", row, col);
                        }
                    }
                }
            }
        };
        for (int row = 0; row < buttonGrid.length; row++) {
            for (int col = 0; col < buttonGrid[row].length; col++) {
                String text = String.format("Button [%d, %d]", row, col);
                buttonGrid[row][col] = new JButton(text);
                buttonGrid[row][col].addActionListener(buttonListener);
                add(buttonGrid[row][col]);
            }
        }
        board2.add (board, BorderLayout.CENTER);

    }

    private static void createAndShowGui() {
        Main mainPanel = new Main();

        JFrame frame = new JFrame("ButtonGridEg");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}