/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.*;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	GridBagLayout layoutManager;

	JButton runSimButton, showGraphButton; JLabel label; JTextField rngSeedTxtF, dailyMeanTxtF, queueSizeTxtF, cancellationTxtF, firstTxtF, businessTxtF, premiumTxtF, economyTxtF; JTextArea txtA;
    double rngseed, dailymean, queueSize, cancellation; int first, business, economy, premium;
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 500));
		this.setVisible(true);

        layoutManager = new GridBagLayout();
		getContentPane().setLayout(layoutManager);
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        runSimButton = new JButton("Run Simulation");
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 0;
        constraints.gridwidth = 2;
        runSimButton.addActionListener(new captureValues());
		this.add(runSimButton, constraints);

        showGraphButton = new JButton("Show Chart");
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        getContentPane().add(showGraphButton, constraints);

        label = new JLabel("Simulation");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        //constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(label, constraints);

        label = new JLabel("RNG Seed");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        label = new JLabel("Daily Mean");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        label = new JLabel("Queue Size");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        label = new JLabel("Cancellation");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);

        rngSeedTxtF = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        getContentPane().add(rngSeedTxtF, constraints);
        dailyMeanTxtF = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        getContentPane().add(dailyMeanTxtF, constraints);
        queueSizeTxtF = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        getContentPane().add(queueSizeTxtF, constraints);
        cancellationTxtF = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        getContentPane().add(cancellationTxtF, constraints);


        label = new JLabel("Fare Classes");
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        getContentPane().add(label, constraints);

        label = new JLabel("First");
        constraints.ipadx = 10;
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        label = new JLabel("Business");
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        label = new JLabel("Premium");
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        label = new JLabel("Economy");
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        getContentPane().add(label, constraints);
        firstTxtF = new JTextField();
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        getContentPane().add(firstTxtF, constraints);
        businessTxtF = new JTextField();
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        getContentPane().add(businessTxtF, constraints);
        premiumTxtF = new JTextField();
        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        getContentPane().add(premiumTxtF, constraints);
        economyTxtF = new JTextField();
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        getContentPane().add(economyTxtF, constraints);

        label = new JLabel("Output Log");
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 6;
        getContentPane().add(label, constraints);

        txtA = new JTextArea();
        constraints.gridwidth = 4;
        constraints.gridheight = 5;
        //constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.ipady = 60;
        getContentPane().add(txtA, constraints);


        this.pack();
        this.loadDefaults();
	}

    private void loadDefaults() {
        rngSeedTxtF.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN*0.33)); //TODO confirm this value and rest too(others are prob right)
        dailyMeanTxtF.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
        queueSizeTxtF.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
        cancellationTxtF.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
        firstTxtF.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
        businessTxtF.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
        premiumTxtF.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
        economyTxtF.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
    }
    private class captureValues implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Component source = (Component) e.getSource();
            if(source == runSimButton){ //TODO double check data types pls
                rngseed = Double.parseDouble(rngSeedTxtF.getText());
                dailymean = Double.parseDouble(dailyMeanTxtF.getText());
                cancellation = Double.parseDouble(cancellationTxtF.getText());
                queueSize = Double.parseDouble(queueSizeTxtF.getText());
                first = Integer.parseInt(firstTxtF.getText());
                business = Integer.parseInt(businessTxtF.getText());
                premium = Integer.parseInt(premiumTxtF.getText());
                economy = Integer.parseInt(economyTxtF.getText());

                //TODO pass obtained values to simulation constructor!
            }
        }
    }
    /**
	 * @param args
	 */
	public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
		javax.swing.SwingUtilities.invokeLater(new GUISimulator("Aircraft Simulator"));
	}


}
