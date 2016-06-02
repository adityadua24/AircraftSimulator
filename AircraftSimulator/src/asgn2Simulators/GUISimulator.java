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
import java.io.*;
import javax.swing.*;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	GridBagLayout layoutManager;
    Log l;

	JButton runSimButton, showGraphButton; JLabel label; JTextField rngSeedTxtF, dailyMeanTxtF, queueSizeTxtF, cancellationTxtF, firstTxtF, businessTxtF, premiumTxtF, economyTxtF; JTextArea txtA;
    private volatile double queueSize, cancellation, first, business, economy, premium;
    private volatile int rngseed, dailymean;
    String[] simulatorArgs;
    Simulator sim;
    String forTxtA;
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
        simulatorArgs = new String[16];
        forTxtA = "";
	}
    private void createAndShowGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 500));
        this.setVisible(true);

        layoutManager = new GridBagLayout();
        getContentPane().setLayout(layoutManager);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 100;
        constraints.weighty = 100;

        runSimButton = new JButton("Run Simulation");
        runSimButton.addActionListener(new captureValues());
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        addToPanel(runSimButton, constraints, 0,0,2,1);

        showGraphButton = new JButton("Show Chart");
        addToPanel(showGraphButton, constraints, 2, 0, 2, 1);

        label = new JLabel("Simulation");
        addToPanel(label, constraints, 0, 1, 1, 1);

        label = new JLabel("RNG Seed");
        addToPanel(label, constraints, 0, 2, 1, 1);

        label = new JLabel("Daily Mean");
        addToPanel(label, constraints, 0, 3, 1, 1);

        label = new JLabel("Queue Size");
        addToPanel(label, constraints, 0, 4, 1, 1);

        label = new JLabel("Cancellation");
        addToPanel(label, constraints, 0, 5, 1, 1);

        rngSeedTxtF = new JTextField();
        addToPanel(rngSeedTxtF, constraints, 1, 2, 1, 1);

        dailyMeanTxtF = new JTextField();
        addToPanel(dailyMeanTxtF, constraints, 1, 3, 1, 1);

        queueSizeTxtF = new JTextField();
        addToPanel(queueSizeTxtF, constraints, 1, 4, 1, 1);

        cancellationTxtF = new JTextField();
        addToPanel(cancellationTxtF, constraints, 1, 5, 1, 1);


        label = new JLabel("Fare Classes");
        addToPanel(label, constraints, 2, 1, 2, 1);

        label = new JLabel("First");
        addToPanel(label, constraints, 2, 2, 1, 1);

        label = new JLabel("Business");
        addToPanel(label, constraints, 2, 3, 1, 1);

        label = new JLabel("Premium");
        addToPanel(label, constraints, 2, 4, 1, 1);

        label = new JLabel("Economy");
        addToPanel(label, constraints, 2, 5, 1, 1);

        firstTxtF = new JTextField();
        addToPanel(firstTxtF, constraints, 3, 2, 1, 1);

        businessTxtF = new JTextField();
        addToPanel(businessTxtF, constraints, 3, 3, 1, 1);

        premiumTxtF = new JTextField();
        addToPanel(premiumTxtF, constraints, 3, 4, 1, 1);

        economyTxtF = new JTextField();
        addToPanel(economyTxtF, constraints, 3, 5, 1, 1);

        label = new JLabel("Output Log");
        addToPanel(label, constraints, 0, 6, 1, 1);

        txtA = new JTextArea();
        constraints.ipady = 60;
        txtA.setEditable(false);
        txtA.setLineWrap(true);
        txtA.setFont(new Font("Arial",Font.BOLD,24));
        txtA.setBorder(BorderFactory.createEtchedBorder());
        addToPanel(txtA, constraints, 0, 7, 4, 5);


        this.pack();
        this.loadDefaults();
    }
    private void addToPanel(Component c, GridBagConstraints constraints,int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        getContentPane().add(c, constraints);
    }
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createAndShowGUI();
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
                rngseed = Integer.parseInt(rngSeedTxtF.getText());
                dailymean = Integer.parseInt(dailyMeanTxtF.getText());
                cancellation = Double.parseDouble(cancellationTxtF.getText());
                queueSize = Double.parseDouble(queueSizeTxtF.getText());
                first = Double.parseDouble(firstTxtF.getText());
                business = Double.parseDouble(businessTxtF.getText());
                premium = Double.parseDouble(premiumTxtF.getText());
                economy = Double.parseDouble(economyTxtF.getText());
                txtA.setText("Works");

                buildStringArgs();
    /*
                try {
                    runSimulation();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (SimulationException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //TODO pass obtained values to simulation constructor!
                try {
                    getLog();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                txtA.setText(forTxtA);
*/
            }
        }
    }
    private void runSimulation() throws InterruptedException, SimulationException, IOException {

        Simulator s = SimulationRunner.createSimulatorUsingArgs(simulatorArgs);
        l = new Log();
        SimulationRunner sr = new SimulationRunner(s, l);
        try {
            sr.runSimulation();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    private void getLog() throws IOException {
        String file_path = "";
        FileReader fr = new FileReader(file_path);
        BufferedReader bf = new BufferedReader(fr);
        String line = "";

        while ((line = bf.readLine()) != null) {
            forTxtA += line;
        }

        // Clean up
        fr.close();
        bf.close();

    }
    private void buildStringArgs() {
        simulatorArgs[0] = String.valueOf(this.rngseed); // seed value
        simulatorArgs[1] = String.valueOf(this.queueSize);
        simulatorArgs[2] = String.valueOf(this.dailymean);
        simulatorArgs[3] = String.valueOf(0.33*this.dailymean); //SD booking value
        simulatorArgs[4] = String.valueOf(this.first);
        simulatorArgs[5] = String.valueOf(this.business);
        simulatorArgs[6] = String.valueOf(this.premium);
        simulatorArgs[7] = String.valueOf(this.economyTxtF);
        simulatorArgs[8] = String.valueOf(this.cancellation);
    }
    /**
	 * @param args
	 */
	public static void main(String[] args) throws  InterruptedException{
        JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.SwingUtilities.invokeLater(new GUISimulator("Aircraft Simulator"));
    }
}
