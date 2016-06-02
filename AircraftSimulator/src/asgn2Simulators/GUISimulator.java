/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import asgn2Aircraft.Bookings;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	private GridBagLayout layoutManager;

	private JButton runSimButton,
            showTextButton,
            showGraph1Button,
            showGraph2Button;

	private JLabel label;

	private JTextField rngSeedTxtF,
			dailyMeanTxtF,
			queueSizeTxtF,
			cancellationTxtF,
			firstTxtF,
			businessTxtF,
			premiumTxtF,
			economyTxtF;

    private JTextArea simTextOutput;

    private volatile double dailyMean, cancellation, first, business, economy, premium;
    private volatile int rngSeed, queueSize;

	private String[] simulatorArgs;

    // Chart 1 which displayed the passenger variables
    private ChartPanel chart1Panel;
    private JFreeChart chart1;

    // Chart 2 which displayed the refused/queued variables
    private ChartPanel chart2Panel;
    private JFreeChart chart2;

    // Series collections which group all the series into 1 variable
    private XYSeriesCollection chart1DataSet;
    private XYSeriesCollection chart2DataSet;

    // Series variables which keep track of the values in real time
    private XYSeries firstSeries;
    private XYSeries businessSeries;
    private XYSeries premiumSeries;
    private XYSeries economySeries;
    private XYSeries totalSeries;
    private XYSeries emptySeries;

    private XYSeries queueSeries;
    private XYSeries refusedSeries;

    private JScrollPane scrollText;
    private JPanel textPanel;

	/**
	 * @param arg0 arguments for the Simulation
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
        simulatorArgs = new String[16];
	}
    private void createAndShowGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 900));
        this.setVisible(true);

        this.setResizable(false);

        // Layout setup
        layoutManager = new GridBagLayout();
        getContentPane().setLayout(layoutManager);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 100;
        constraints.weighty = 100;

        // GUI creation
        setUpButtons(constraints);

        setUpTextFields(constraints);
        setUpLabels(constraints);

        setUpOutputLog(constraints);

        setUpChartVariables(constraints);

        showTextPressed();

        this.pack();
        this.loadDefaults();
    }

//region GUI creation

    private void setUpButtons(GridBagConstraints constraints) {
        runSimButton = new JButton("Run Simulation");
        runSimButton.addActionListener(e ->  {
            runSimulationPressed();
        });
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        addToPanel(runSimButton, constraints, 0,0,1,1);

        showTextButton = new JButton("Simulation Results Text");
        showTextButton.addActionListener(e ->  {
            showTextPressed();
        });
        addToPanel(showTextButton, constraints, 1, 0, 1, 1);

        showGraph1Button = new JButton("Simulation Results Graph 1");
        showGraph1Button.addActionListener(e ->  {
            showGraph1Pressed();
        });
        addToPanel(showGraph1Button, constraints, 2, 0, 1, 1);

        showGraph2Button = new JButton("Simulation Results Graph 2");
        showGraph2Button.addActionListener(e ->  {
            showGraph2Pressed();
        });
        addToPanel(showGraph2Button, constraints, 3, 0, 1, 1);
    }

    private void setUpTextFields(GridBagConstraints constraints) {
        rngSeedTxtF = new JTextField();
        addToPanel(rngSeedTxtF, constraints, 1, 2, 1, 1);

        dailyMeanTxtF = new JTextField();
        addToPanel(dailyMeanTxtF, constraints, 1, 3, 1, 1);

        queueSizeTxtF = new JTextField();
        addToPanel(queueSizeTxtF, constraints, 1, 4, 1, 1);

        cancellationTxtF = new JTextField();
        addToPanel(cancellationTxtF, constraints, 1, 5, 1, 1);

        firstTxtF = new JTextField();
        addToPanel(firstTxtF, constraints, 3, 2, 1, 1);

        businessTxtF = new JTextField();
        addToPanel(businessTxtF, constraints, 3, 3, 1, 1);

        premiumTxtF = new JTextField();
        addToPanel(premiumTxtF, constraints, 3, 4, 1, 1);

        economyTxtF = new JTextField();
        addToPanel(economyTxtF, constraints, 3, 5, 1, 1);
    }

    private void setUpLabels(GridBagConstraints constraints) {
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
    }

    private void setUpOutputLog(GridBagConstraints constraints) {
        label = new JLabel("Output Log");
        addToPanel(label, constraints, 0, 6, 1, 1);

        textPanel = new JPanel(new BorderLayout());
        simTextOutput = new JTextArea();
        scrollText = new JScrollPane(textPanel);
        textPanel.add(simTextOutput, BorderLayout.CENTER);
        constraints.ipady = 500;
        simTextOutput.setEditable(false);
        simTextOutput.setLineWrap(true);
        simTextOutput.setFont(new Font("Arial",Font.BOLD,14));
        simTextOutput.setBorder(BorderFactory.createEtchedBorder());
        simTextOutput.setVisible(true);
        addToPanel(scrollText, constraints, 0, 7, 4, 5);
    }

    private void setUpChartVariables(GridBagConstraints constraints) {
        // Both these charts run passively in the background.
        // Chart 1 set up
        chart1DataSet = new XYSeriesCollection();

        firstSeries = new XYSeries("First");
        businessSeries = new XYSeries("Business");
        premiumSeries = new XYSeries("Premium");
        economySeries = new XYSeries("Economy");
        totalSeries = new XYSeries("Total");
        emptySeries = new XYSeries("Empty");

        chart1DataSet.addSeries(firstSeries);
        chart1DataSet.addSeries(businessSeries);
        chart1DataSet.addSeries(premiumSeries);
        chart1DataSet.addSeries(economySeries);
        chart1DataSet.addSeries(totalSeries);
        chart1DataSet.addSeries(emptySeries);

        chart1 = ChartFactory.createXYLineChart("Simulation Results Passenger Info", "Passengers", "Day", chart1DataSet);
        chart1Panel = new ChartPanel(chart1);
        chart1Panel.setVisible(false);

        addToPanel(chart1Panel, constraints, 0, 7, 4, 5);

        // Chart 2 set up
        chart2DataSet = new XYSeriesCollection();

        queueSeries = new XYSeries("Queued");
        refusedSeries = new XYSeries("Refused");

        chart2DataSet.addSeries(queueSeries);
        chart2DataSet.addSeries(refusedSeries);

        chart2 = ChartFactory.createXYLineChart("Simulation Results Queued/Refused", "Passengers", "Day", chart2DataSet);
        chart2Panel = new ChartPanel(chart2);
        chart2Panel.setVisible(false);

        addToPanel(chart2Panel, constraints, 0, 7, 4, 5);
    }

    private void addToPanel(Component c, GridBagConstraints constraints,int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        getContentPane().add(c, constraints);
    }

    private void loadDefaults() {
        rngSeedTxtF.setText(String.valueOf((int)(Constants.DEFAULT_DAILY_BOOKING_MEAN*0.33))); //TODO confirm this value and rest too(others are prob right)
        dailyMeanTxtF.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
        queueSizeTxtF.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
        cancellationTxtF.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
        firstTxtF.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
        businessTxtF.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
        premiumTxtF.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
        economyTxtF.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
    }

    //endregion

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createAndShowGUI();
	}



    //region GUI event functions

    private void showTextPressed(){
        showTextButton.setEnabled(false);
        showGraph1Button.setEnabled(true);
        showGraph2Button.setEnabled(true);

        setTextAreaVisible(true);
        chart1Panel.setVisible(false);
        chart2Panel.setVisible(false);
    }

    private void showGraph1Pressed(){
        showTextButton.setEnabled(true);
        showGraph1Button.setEnabled(false);
        showGraph2Button.setEnabled(true);

        setTextAreaVisible(false);
        chart1Panel.setVisible(true);
        chart2Panel.setVisible(false);
    }

    private void showGraph2Pressed(){
        showTextButton.setEnabled(true);
        showGraph1Button.setEnabled(true);
        showGraph2Button.setEnabled(false);

        setTextAreaVisible(false);
        chart1Panel.setVisible(false);
        chart2Panel.setVisible(true);
    }

    private void runSimulationPressed() {
        runSimButton.setEnabled(false);
        rngSeed = Integer.parseInt(rngSeedTxtF.getText());
        queueSize = Integer.parseInt(queueSizeTxtF.getText());
        dailyMean = Double.parseDouble(dailyMeanTxtF.getText());
        cancellation = Double.parseDouble(cancellationTxtF.getText());
        first = Double.parseDouble(firstTxtF.getText());
        business = Double.parseDouble(businessTxtF.getText());
        premium = Double.parseDouble(premiumTxtF.getText());
        economy = Double.parseDouble(economyTxtF.getText());

        buildStringArgs();

        // Reset all the outputs
        firstSeries.clear();
        businessSeries.clear();
        premiumSeries.clear();
        economySeries.clear();
        totalSeries.clear();
        emptySeries.clear();

        queueSeries.clear();
        refusedSeries.clear();

        simTextOutput.setText("");

        // Creates a worker thread that will run the simulation in the background
        SwingWorker workerThread = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                runSimulation();
                return null;
            }

            @Override
            protected void done() {
                runSimButton.setEnabled(true);
            }
        };

        // Runs the simulation on a background thread
        workerThread.execute();
    }

    //endregion

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

    private void setTextAreaVisible(boolean state) {
        simTextOutput.setVisible(state);
        scrollText.setVisible(state);
    }

    //region Log/output functions

    public void initialEntry(Simulator sim) throws IOException, SimulationException {
        simTextOutput.append(getLogTime() + ": Start of Simulation\n");
        simTextOutput.append(sim.toString() + "\n");
        String capacities = sim.getFlights(Constants.FIRST_FLIGHT).initialState();
        simTextOutput.append(capacities);
    }

    public void LogEntry(int time,Simulator sim) throws IOException, SimulationException {
        boolean flying = (time >= Constants.FIRST_FLIGHT);
        simTextOutput.append(sim.getSummary(time, flying));
    }

    public void finalise(Simulator sim) throws IOException {
        String time = getLogTime();
        simTextOutput.append("\n" + time + ": End of Simulation\n");
        simTextOutput.append(sim.finalState());
    }

    private String getLogTime() {
        String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return timeLog;
    }

    public void chartFlightDetails(Simulator sim, int time) throws SimulationException {
        // Keeps track of all the values in real time.
        queueSeries.add(time, sim.numInQueue());
        refusedSeries.add(time, sim.numRefused());

        // If the first flight hasn't left yet, there are no values to graph
        if (time < Constants.FIRST_FLIGHT)
            return ;

        Bookings currentBookings = sim.getFlights(time).getCurrentCounts();

        firstSeries.add(time, currentBookings.getNumFirst());
        businessSeries.add(time, currentBookings.getNumBusiness());
        premiumSeries.add(time, currentBookings.getNumPremium());
        economySeries.add(time, currentBookings.getNumEconomy());
        totalSeries.add(time, currentBookings.getTotal());
        emptySeries.add(time, currentBookings.getAvailable());
    }

    //endregion

    /**
	 * @param args
	 */
	public static void main(String[] args) throws  InterruptedException{
        JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.SwingUtilities.invokeLater(new GUISimulator("Aircraft Simulator"));
    }
}
