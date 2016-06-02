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
import java.awt.geom.Arc2D;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	private GridBagLayout layoutManager;

	private JButton runSimButton,
			showGraphButton;
	private JLabel label;
	private JTextField rngSeedTxtF,
			dailyMeanTxtF,
			queueSizeTxtF,
			cancellationTxtF,
			firstTxtF,
			businessTxtF,
			premiumTxtF,
			economyTxtF;
	JTextArea txtA;
    private volatile double dailyMean, cancellation, first, business, economy, premium;
    private volatile int rngSeed, queueSize;
	private String[] simulatorArgs;
	private String forTxtA;

    JScrollPane scroll;


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
        scroll = new JScrollPane(txtA);
        constraints.ipady = 100;
        txtA.setEditable(false);
        txtA.setLineWrap(true);
        txtA.setFont(new Font("Arial",Font.BOLD,14));
        txtA.setBorder(BorderFactory.createEtchedBorder());
        addToPanel(scroll, constraints, 0, 7, 4, 5);


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
        rngSeedTxtF.setText(String.valueOf((int)(Constants.DEFAULT_DAILY_BOOKING_MEAN*0.33))); //TODO confirm this value and rest too(others are prob right)
        dailyMeanTxtF.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
        queueSizeTxtF.setText(String.valueOf((int)Constants.DEFAULT_MAX_QUEUE_SIZE));
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
                showGraphButton.setEnabled(false);
                revalidate();

                rngSeed = Integer.parseInt(rngSeedTxtF.getText());
                queueSize = Integer.parseInt(queueSizeTxtF.getText());
                dailyMean = Double.parseDouble(dailyMeanTxtF.getText());
                cancellation = Double.parseDouble(cancellationTxtF.getText());
                first = Double.parseDouble(firstTxtF.getText());
                business = Double.parseDouble(businessTxtF.getText());
                premium = Double.parseDouble(premiumTxtF.getText());
                economy = Double.parseDouble(economyTxtF.getText());

                buildStringArgs();
                try {
                    runSimulation();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (SimulationException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                runSimButton.setEnabled(true);
                showGraphButton.setEnabled(true);

                txtA.setText(forTxtA);
            }
        }
    }
    private void runSimulation() throws InterruptedException, SimulationException, IOException {
        Simulator s = SimulationRunner.createSimulatorUsingArgs(simulatorArgs);
        Log simLog = new Log();
        SimulationRunner sr = new SimulationRunner(s, simLog);
        try {
            sr.runSimulation(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    private void buildStringArgs() {
        simulatorArgs[0] = String.valueOf(this.rngSeed); // seed value
        simulatorArgs[1] = String.valueOf(this.queueSize);
        simulatorArgs[2] = String.valueOf(this.dailyMean);
        simulatorArgs[3] = String.valueOf(0.33*this.dailyMean); //SD booking value
        simulatorArgs[4] = String.valueOf(this.first);
        simulatorArgs[5] = String.valueOf(this.business);
        simulatorArgs[6] = String.valueOf(this.premium);
        simulatorArgs[7] = String.valueOf(this.economy);
        simulatorArgs[8] = String.valueOf(this.cancellation);
    }

    public void initialEntry(Simulator sim) throws IOException, SimulationException {
        forTxtA += getLogTime() + ": Start of Simulation\n";
        forTxtA += sim.toString() + "\n";
        String capacities = sim.getFlights(Constants.FIRST_FLIGHT).initialState();
        forTxtA += capacities;
    }

    public void LogEntry(int time,Simulator sim) throws IOException, SimulationException {
        boolean flying = (time >= Constants.FIRST_FLIGHT);
        forTxtA += sim.getSummary(time, flying);
    }

    public void finalise(Simulator sim) throws IOException {
        String time = getLogTime();
        forTxtA += "\n" + time + ": End of Simulation\n";
        forTxtA += sim.finalState();
    }

    private String getLogTime() {
        String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return timeLog;
    }
    /**
	 * @param args
	 */
	public static void main(String[] args) throws  InterruptedException{
        JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.SwingUtilities.invokeLater(new GUISimulator("Aircraft Simulator"));
    }
}
