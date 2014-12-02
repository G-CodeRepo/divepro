package divePlan;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;


@SuppressWarnings("unused")
public class DivePlannerGUI {

	protected Shell DivePlannerHome;
	
	//private static DivePlannerGUI windowHome;
	//public DivePlannerHelp windowHelp;
	//public DivePlannerHistory windowHistory;
	//public DivePlannerNewDive windowNewDive;
	private Button btnHome;
	private Button btnHistory;
	private Button btnHelp;
	private Button btnNewDive;
	
	private Text dateDay;
	private Text dateMonth;
	private Text dateYear;
	private Text timeHour;
	private Text timeMin;
	private Text depth;
	private Text timeUnderMin;
	private Text surfaceIntervalMin;
	
	private Text diveNum;
	
	private Label lblWelcome;
	private Label lblDisclaimer;
	private Label lblDisclaimerBody;
	private Label lblDisclaimerBody2;
	private Label lblDisclaimerBody3;
	
	private Label lblDate;
	private Label lblTime;
	private Label lblDepth;
	private Label lblTimeUnder;
	private Label lblSurfaceInterval;
	private Label lblDateDiv;
	private Label lblDateDiv2;
	private Label lblTimeDiv;
	private Label lblFt;
	private Label lblMin;
	private Label lblMin2;
	private Label lblNewDiveHeader;
	private Label lblTutorial;
	private Label TutorialBody;
	private Label lblTutorialBody2;
	private Label lblTutorialBody3;
	private Label lblTutorialBody4;
	private Label lblTutorialBody5;
	private Label lblHistoryHeader;
	
	private boolean homeDisplay;
	private boolean newDiveDisplay;
	private boolean historyDisplay;
	private boolean helpDisplay;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DivePlannerGUI windowHome = new DivePlannerGUI();
			windowHome.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		DivePlannerHome.open();
		DivePlannerHome.layout();
		while (!DivePlannerHome.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		DivePlannerHome = new Shell();
		DivePlannerHome.setSize(580, 480);
		DivePlannerHome.setText("Dive Planner");
		
		homeDisplay = true;
		newDiveDisplay = false;
		historyDisplay = false;
		helpDisplay = false;
		
		lblWelcome = new Label(DivePlannerHome, SWT.NONE);
		lblWelcome.setFont(SWTResourceManager.getFont("Vani", 10, SWT.NORMAL));
		lblWelcome.setBounds(10, 68, 426, 20);
		lblWelcome.setText("Welcome to version 1.1 of our dive planner!");
		
		lblDisclaimer = new Label(DivePlannerHome, SWT.NONE);
		lblDisclaimer.setFont(SWTResourceManager.getFont("Segoe UI Light", 11, SWT.BOLD));
		lblDisclaimer.setBounds(10, 113, 116, 40);
		lblDisclaimer.setText("Disclaimer:");
		
		lblDisclaimerBody = new Label(DivePlannerHome, SWT.NONE);
		lblDisclaimerBody.setBounds(10, 149, 505, 20);
		lblDisclaimerBody.setText("This piece of software is simply a prototype and not for commercial use.");
		
		lblDisclaimerBody2 = new Label(DivePlannerHome, SWT.NONE);
		lblDisclaimerBody2.setBounds(10, 187, 527, 20);
		lblDisclaimerBody2.setText("It is designed by three college students in the University of Hawaii at Manoa");
		
		lblDisclaimerBody3 = new Label(DivePlannerHome, SWT.NONE);
		lblDisclaimerBody3.setBounds(10, 213, 389, 20);
		lblDisclaimerBody3.setText("as an assignment project for the ICS414 course.");
		
		btnNewDive = new Button(DivePlannerHome, SWT.NONE);
		btnNewDive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent s) {
				try {
					changingDisplay();
					homeDisplay = false;
					newDiveDisplay = true;
					historyDisplay = false;
					helpDisplay = false;
					btnNewDive.setEnabled(false);
					btnNewDive.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
					
					lblDate = new Label(DivePlannerHome, SWT.NONE);
					lblDate.setFont(SWTResourceManager.getFont("Segoe UI Light", 9, SWT.BOLD));
					lblDate.setBounds(10, 82, 70, 20);
					lblDate.setText("Date:");
					
					lblTime = new Label(DivePlannerHome, SWT.NONE);
					lblTime.setFont(SWTResourceManager.getFont("Segoe UI Light", 9, SWT.BOLD));
					lblTime.setText("Time:");
					lblTime.setBounds(10, 129, 70, 20);
					
					lblDepth = new Label(DivePlannerHome, SWT.NONE);
					lblDepth.setFont(SWTResourceManager.getFont("Segoe UI Light", 9, SWT.BOLD));
					lblDepth.setBounds(10, 180, 70, 20);
					lblDepth.setText("Depth:");
					
					lblTimeUnder = new Label(DivePlannerHome, SWT.NONE);
					lblTimeUnder.setFont(SWTResourceManager.getFont("Segoe UI Light", 9, SWT.BOLD));
					lblTimeUnder.setBounds(10, 223, 85, 20);
					lblTimeUnder.setText("Time Under:");
					
					lblSurfaceInterval = new Label(DivePlannerHome, SWT.NONE);
					lblSurfaceInterval.setFont(SWTResourceManager.getFont("Segoe UI Light", 9, SWT.BOLD));
					lblSurfaceInterval.setBounds(10, 280, 114, 20);
					lblSurfaceInterval.setText("Surface Interval:");
					
					dateDay = new Text(DivePlannerHome, SWT.BORDER);
					dateDay.setBounds(171, 79, 35, 26);
					
					lblDateDiv = new Label(DivePlannerHome, SWT.NONE);
					lblDateDiv.setBounds(212, 82, 15, 26);
					lblDateDiv.setText("/");
					
					dateMonth = new Text(DivePlannerHome, SWT.BORDER);
					dateMonth.setBounds(227, 79, 35, 26);
					
					lblDateDiv2 = new Label(DivePlannerHome, SWT.NONE);
					lblDateDiv2.setBounds(266, 82, 15, 20);
					lblDateDiv2.setText("/");
					
					dateYear = new Text(DivePlannerHome, SWT.BORDER);
					dateYear.setBounds(275, 79, 50, 26);
					
					timeHour = new Text(DivePlannerHome, SWT.BORDER);
					timeHour.setBounds(171, 126, 43, 26);
					
					lblTimeDiv = new Label(DivePlannerHome, SWT.NONE);
					lblTimeDiv.setBounds(222, 129, 15, 20);
					lblTimeDiv.setText(":");
					
					timeMin = new Text(DivePlannerHome, SWT.BORDER);
					timeMin.setBounds(238, 126, 43, 26);
					
					depth = new Text(DivePlannerHome, SWT.BORDER);
					depth.setBounds(171, 177, 78, 26);
					
					lblFt = new Label(DivePlannerHome, SWT.NONE);
					lblFt.setBounds(255, 180, 70, 20);
					lblFt.setText("Ft");
					
					timeUnderMin = new Text(DivePlannerHome, SWT.BORDER);
					timeUnderMin.setBounds(171, 220, 78, 26);
					
					surfaceIntervalMin = new Text(DivePlannerHome, SWT.BORDER);
					surfaceIntervalMin.setBounds(171, 280, 78, 26);
					
					lblMin = new Label(DivePlannerHome, SWT.NONE);
					lblMin.setBounds(255, 223, 70, 20);
					lblMin.setText("min");
					
					lblMin2 = new Label(DivePlannerHome, SWT.NONE);
					lblMin2.setBounds(255, 286, 70, 20);
					lblMin2.setText("min");
					
					lblNewDiveHeader = new Label(DivePlannerHome, SWT.NONE);
					lblNewDiveHeader.setFont(SWTResourceManager.getFont("Segoe Print", 11, SWT.NORMAL));
					lblNewDiveHeader.setBounds(10, 21, 410, 41);
					lblNewDiveHeader.setText("Please complete the following fields:");
					//DivePlannerHome.close();
					//windowNewDive = new DivePlannerNewDive();
					//windowNewDive.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnNewDive.setBounds(10, 353, 116, 73);
		btnNewDive.setText("New Dive");
		
		btnHelp = new Button(DivePlannerHome, SWT.NONE);
		btnHelp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent s) {
				try {
					changingDisplay();
					homeDisplay = false;
					newDiveDisplay = false;
					historyDisplay = false;
					helpDisplay = true;
					btnHelp.setEnabled(false);
					btnHelp.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
					
					lblTutorial = new Label(DivePlannerHome, SWT.NONE);
					lblTutorial.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
					lblTutorial.setBounds(10, 10, 119, 57);
					lblTutorial.setText("Tutorial:");
					
					TutorialBody = new Label(DivePlannerHome, SWT.NONE);
					TutorialBody.setBounds(10, 79, 426, 20);
					TutorialBody.setText("Hello and welcome to the tutorial!");
					
					lblTutorialBody2 = new Label(DivePlannerHome, SWT.NONE);
					lblTutorialBody2.setBounds(20, 121, 532, 20);
					lblTutorialBody2.setText("1. To add a dive, click on the new dive button and enter in the required info.");
					
					lblTutorialBody3 = new Label(DivePlannerHome, SWT.NONE);
					lblTutorialBody3.setBounds(20, 159, 499, 20);
					lblTutorialBody3.setText("2. To get data from previously recorded dives, click on the History button ");
					
					lblTutorialBody4 = new Label(DivePlannerHome, SWT.NONE);
					lblTutorialBody4.setBounds(30, 185, 509, 20);
					lblTutorialBody4.setText("and enter the dive number desired. For example, type in \"1\" for dive 1, ");
					
					lblTutorialBody5 = new Label(DivePlannerHome, SWT.NONE);
					lblTutorialBody5.setBounds(29, 212, 199, 20);
					lblTutorialBody5.setText("\"2\" for dive 2, and so on.");
					
					//DivePlannerHome.close();
					//windowHelp = new DivePlannerHelp();
					//windowHelp.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnHelp.setBounds(147, 353, 116, 73);
		btnHelp.setText("Help");
		
		btnHistory = new Button(DivePlannerHome, SWT.NONE);
		btnHistory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent s) {
				try {
					changingDisplay();
					homeDisplay = false;
					newDiveDisplay = false;
					historyDisplay = true;
					helpDisplay = false;
					btnHistory.setEnabled(false);
					btnHistory.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
					
					lblHistoryHeader = new Label(DivePlannerHome, SWT.NONE);
					lblHistoryHeader.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
					lblHistoryHeader.setFont(SWTResourceManager.getFont("Segoe Print", 10, SWT.NORMAL));
					lblHistoryHeader.setText("Please specify a dive by giving a number (1, 2, 3, etc.):");
					lblHistoryHeader.setBounds(10, 10, 525, 44);
					
					diveNum = new Text(DivePlannerHome, SWT.BORDER);
					diveNum.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
					diveNum.setBounds(10, 60, 78, 26);
					//DivePlannerHome.close();
					//windowHistory = new DivePlannerHistory();
					//windowHistory.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnHistory.setBounds(289, 353, 110, 73);
		btnHistory.setText("History");
		
		btnHome = new Button(DivePlannerHome, SWT.NONE);
		btnHome.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changingDisplay();
				homeDisplay = true;
				newDiveDisplay = false;
				historyDisplay = false;
				helpDisplay = false;
				btnHome.setEnabled(false);
				btnHome.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
				
				lblWelcome = new Label(DivePlannerHome, SWT.NONE);
				lblWelcome.setFont(SWTResourceManager.getFont("Vani", 10, SWT.NORMAL));
				lblWelcome.setBounds(10, 68, 426, 20);
				lblWelcome.setText("Welcome to version 1.1 of our dive planner!");
				
				lblDisclaimer = new Label(DivePlannerHome, SWT.NONE);
				lblDisclaimer.setFont(SWTResourceManager.getFont("Segoe UI Light", 11, SWT.BOLD));
				lblDisclaimer.setBounds(10, 113, 116, 40);
				lblDisclaimer.setText("Disclaimer:");
				
				lblDisclaimerBody = new Label(DivePlannerHome, SWT.NONE);
				lblDisclaimerBody.setBounds(10, 149, 505, 20);
				lblDisclaimerBody.setText("This piece of software is simply a prototype and not for commercial use.");
				
				lblDisclaimerBody2 = new Label(DivePlannerHome, SWT.NONE);
				lblDisclaimerBody2.setBounds(10, 187, 527, 20);
				lblDisclaimerBody2.setText("It is designed by three college students in the University of Hawaii at Manoa");
				
				lblDisclaimerBody3 = new Label(DivePlannerHome, SWT.NONE);
				lblDisclaimerBody3.setBounds(10, 213, 389, 20);
				lblDisclaimerBody3.setText("as an assignment project for the ICS414 course.");
			}
		});
		
		btnHome.setBounds(439, 353, 116, 73);
		btnHome.setText("Home");
		btnHome.setEnabled(false);
		btnHome.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));

	}
	public void changingDisplay(){
		
		if(homeDisplay){
			lblWelcome.dispose();
			lblDisclaimer.dispose();
			lblDisclaimerBody.dispose();
			lblDisclaimerBody2.dispose();
			lblDisclaimerBody3.dispose();
		}
		
		if(historyDisplay){
			lblHistoryHeader.dispose();
			diveNum.dispose();
		}
		
		if(newDiveDisplay){
			dateDay.dispose();
			dateMonth.dispose();
			dateYear.dispose();
			timeHour.dispose();
			timeMin.dispose();
			depth.dispose();
			timeUnderMin.dispose();
			surfaceIntervalMin.dispose();
			lblDate.dispose();
			lblTime.dispose();
			lblDepth.dispose();
			lblTimeUnder.dispose();
			lblSurfaceInterval.dispose();
			lblDateDiv.dispose();
			lblDateDiv2.dispose();
			lblTimeDiv.dispose();
			lblFt.dispose();
			lblMin.dispose();
			lblMin2.dispose();
			lblNewDiveHeader.dispose();
		}
			
		if(helpDisplay){
			lblTutorial.dispose();
			TutorialBody.dispose();
			lblTutorialBody2.dispose();
			lblTutorialBody3.dispose();
			lblTutorialBody4.dispose();
			lblTutorialBody5.dispose();
		}
		
		btnHome.setEnabled(true);
		btnHome.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnNewDive.setEnabled(true);
		btnNewDive.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnHistory.setEnabled(true);
		btnHistory.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnHelp.setEnabled(true);
		btnHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		
	}
}
