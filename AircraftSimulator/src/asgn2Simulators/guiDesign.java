package asgn2Simulators;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by eddy on 1/06/2016.
 */
public class guiDesign {
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

    public guiDesign() {
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
