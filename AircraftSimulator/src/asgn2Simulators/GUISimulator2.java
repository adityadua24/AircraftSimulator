package asgn2Simulators;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by eddy on 1/06/2016.
 */
public class GUISimulator2 extends JFrame implements Runnable{
    private JButton drawChart;
    private JTextField rngSeedValue;
    private JPanel mainPanel;
    private JButton runSim;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField dailyMeanValue;
    private JTextField queueSizeValue;
    private JTextField cancellationValue;
    private JTextArea textArea1;
    private JLabel rngSeed;

    public GUISimulator2() {
        rngSeedValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { //Listener for RNG seed
                super.keyPressed(e);
            }
        });
        runSim.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        dailyMeanValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
        queueSizeValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
        cancellationValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
    }

    private static void createAndShowGUI() {
        // TODO: place custom component creation code here
        JFrame frame = new JFrame("Aircraft Simulator");
        frame.setContentPane(new GUISimulator2().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void run() {
    }
}
