/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveProfile class calculates the dive plan
 * @author Gerald Abut
 */


// SAVING CONCURRENT DIVE PLANS NOT YET IMPLEMENTED (MOST LIKING IN A SEPARATE CLASS). THIS DIVE PROFILE ONLY WORKS FOR A SINGLE DIVE.
// THIS CLASS NEEDS TESTING


import java.util.LinkedHashMap;

public class DivePlanner {
	private int depthInput;
	private int bottomTimeInput;
	private double surfaceIntervalInput;
	private int nextDiveDepthInput;
	private int currDepth;
	private int currBottomTime;
	private char currPressureGroup;
	private char currPressureGroup_SI;
	private double currSurfaceInterval;
	private int currResidualNitrogen;
	private int currNextDiveDepth;
	private boolean currDecompressionStop;
	DiveTable diveTable;
	DiveProfile diveProfile;
	DiveProfileManager diveManager;	// (NOT YET IMPLEMENTED)
		
	// Constructor
	public DivePlanner(int diveDepth, int bottomTime, double timeOnSurface, int nextDiveDepth) {
		this.diveTable = new DiveTable();
		
		// save values from user input (USE FOR DEBUGGING)
		this.depthInput = diveDepth;
		this.bottomTimeInput= bottomTime;
		this.surfaceIntervalInput = timeOnSurface;
		this.nextDiveDepthInput = nextDiveDepth;
		
		// create a dive profile
		this.createDiveProfile(diveDepth, bottomTime, timeOnSurface, nextDiveDepth);
		
	}
	
	// Overloaded Constructor
	public DivePlanner() {
		this.diveTable = new DiveTable();
		
		// set default values
		this.depthInput = 0;
		this.bottomTimeInput= 0;
		this.surfaceIntervalInput = 0;
		this.nextDiveDepthInput = 0;
		this.currDepth = 0;
		this.currBottomTime = 0;
		this.currPressureGroup = ' ';
		this.currPressureGroup_SI = ' ';
		this.currSurfaceInterval = 0;
		this.currResidualNitrogen = 0;
		this.currNextDiveDepth = 0;
		this.currDecompressionStop = false;
	}
	
	
	/**
	 *  create a dive profile
	 *  calculate depth, bottomTime, pressureGroup, and pressureGroup_SI, and residualNitrogen
	 * @param diveDepth
	 * @param bottomTime
	 */
	public void createDiveProfile(int diveDepth, int bottomTime, double timeOnSurface, int nextDive) {
		this.currDepth = this.adjustDepth(diveDepth);
		this.currBottomTime = this.adjustBottomTime(this.currDepth, bottomTime);
		this.currPressureGroup = this.diveTable.getPressureGroup(this.getTimeIndex(this.currDepth, this.currBottomTime));
		this.currSurfaceInterval = this.adjustSurfaceInterval(this.currPressureGroup, timeOnSurface);
		this.currPressureGroup_SI = this.calcPressureGroup_SI(this.currPressureGroup, this.currSurfaceInterval);
		this.currNextDiveDepth = this.adjustDepth(nextDive);
		this.currResidualNitrogen = this.calcResidualNitrogen(this.currNextDiveDepth, this.currPressureGroup_SI);
		this.currDecompressionStop = this.requireDecompressionStop(this.currDepth, this.currBottomTime);
		this.diveProfile = new DiveProfile(this.currDepth, 				this.currBottomTime, 		this.currPressureGroup, 
				  						   this.currPressureGroup_SI, 	this.currSurfaceInterval, 	this.currResidualNitrogen, 
				  						   this.currNextDiveDepth, 		this.currDecompressionStop);
	}
										  	
	/**
	 * retrieve results from dive profile calculations
	 * USE ONLY FOR DEBUGGING
	 * @return
	 */
	public String getResults() {
		return "Inputed Depth (ft):\t\t\t" + this.getDepthInput() + "\n" +
			   "Depth (ft):\t\t\t\t" + this.getCurrDepth() + "\n" +
			   "Inputed Bottom Time (min):\t\t" + this.getBottomTimeInput() + "\n" +
			   "Bottom Time (min):\t\t\t" + this.getCurrBottomTime() + "\n" +
			   "Pressure Group:\t\t\t\t" + this.getCurrPressureGroup() + "\n" +
			   "Inputed Surface Interval (min):\t\t" + this.getSurfaceIntervalInput() + "\n" +
			   "Surface Interval (min):\t\t\t" + this.getCurrSurfaceInterval() + "\n" + 					
			   "Pressure Group After Surface Interval:\t" + this.getCurrPressureGroup_SI() + "\n" + 
			   "Residual Nitrogen:\t\t\t" + this.getCurrResidualNitrogen() + "\n" +	
			   "Next Dive Depth Input (ft):\t\t" + this.getNextDiveDepthInput() + "\n" +					
			   "Next Dive Depth (ft):\t\t\t" + this.getCurrNextDiveDepth() + "\n" +							
			   "Require Decompression:\t\t\t" + this.getCurrDecompressionStop() + "\n";						 
	}
	
	/**
	 * get the adjusted depth from the dive table with the given input depth
	 * @param searchDepth
	 * @return
	 */
	private int adjustDepth(int searchDepth) {
		int[] validDepths = this.diveTable.getValidDepths();	// array of valid depths
		return validDepths[DiveProfileUtility.binarySearch(validDepths, searchDepth)];
	}
		
	/**
	 * 
	 * @param desiredDepth
	 * @param searchTimeAtDepth
	 * @return
	 */
	private int adjustBottomTime(int desiredDepth, int searchTimeAtDepth) {
		LinkedHashMap<Integer, int[]> validDepthTimes = this.diveTable.getDepthTimes();
		int[] validTimesAtDepth = validDepthTimes.get(desiredDepth);
		int time_index = DiveProfileUtility.binarySearch(validTimesAtDepth, searchTimeAtDepth);		
		return validTimesAtDepth[time_index];
	}
	
	/**
	 * get the surface interval
	 * @param pressureGroup
	 * @param surfaceInterval
	 * @return
	 */
	private double adjustSurfaceInterval(char pressureGroup, double surfaceInterval) {
		LinkedHashMap<Character, double[]> validSurfaceIntervalTimes = this.diveTable.getSurfaceIntervalTimes();
		double[] pressureGroupSurfaceIntervalTimes = validSurfaceIntervalTimes.get(pressureGroup);		
		return pressureGroupSurfaceIntervalTimes[DiveProfileUtility.binarySearch(pressureGroupSurfaceIntervalTimes, surfaceInterval)];
	}
	
	/**
	 * get the time index
	 * @param searchTimeAtDepth
	 * @param desiredDepth
	 * @return
	 */
	private int getTimeIndex(int desiredDepth, int searchTimeAtDepth) {
		LinkedHashMap<Integer, int[]> validDepthTimes = this.diveTable.getDepthTimes();
		return DiveProfileUtility.binarySearch(validDepthTimes.get(desiredDepth), searchTimeAtDepth);
	}
	
	/**
	 * get the pressure group after the surface interval
	 * @param pressureGroup
	 * @param surfaceInterval
	 * @return
	 */
	private char calcPressureGroup_SI(char pressureGroup, double surfaceInterval) {
		LinkedHashMap<Character, double[]> validSurfaceIntervalTimes = this.diveTable.getSurfaceIntervalTimes();	
		return this.diveTable.getPressureGroup(DiveProfileUtility.binarySearch(validSurfaceIntervalTimes.get(pressureGroup), surfaceIntervalInput));
	}
	
	/**
	 * calculate the residual nitrogen after a first dive or the nth dive
	 * @param depthOfNextDive
	 * @param pressureGroup_SI
	 * @return
	 */
	private int calcResidualNitrogen(int depthOfNextDive, char pressureGroup_SI) {
		LinkedHashMap<Integer, int[]> validResidualNitrogen = this.diveTable.getResidualNitrogen();
		int[] residualNitrogenAtDepth = validResidualNitrogen.get(this.adjustDepth(depthOfNextDive));
		return residualNitrogenAtDepth[this.diveTable.getIndexOfPressureGroup(pressureGroup_SI)];	
	}
	
	/**
	 * checks to see if a decompression stop is required
	 * @param depth
	 * @param currPressureGroup
	 * @return
	 */
	private boolean requireDecompressionStop(int depth, int bottomTime) {
		return this.diveTable.requireDecompressionStop(depth, bottomTime);
	}
	
	/**
	 * get the current depth of the dive
	 * @return
	 */
	public int getCurrDepth() {
		return this.currDepth;
	}
	
	/**
	 * get the current bottom time of the dive
	 * @return
	 */
	public int getCurrBottomTime() {
		return this.currBottomTime;
	}
	
	/**
	 * get the current pressure group associated with the depth and bottom time
	 * @return
	 */
	public char getCurrPressureGroup() {
		return this.currPressureGroup;
	}
	
	/**
	 * get the current surface interval after the dive
	 * @return
	 */
	public double getCurrSurfaceInterval() {
		return this.currSurfaceInterval;
	}
	
	/**
	 * get the current pressure group after the surface interval
	 * @return
	 */
	public char getCurrPressureGroup_SI() {
		return this.currPressureGroup_SI;
	}
	
	/**
	 * get the current boolean for the decompression stop.
	 * if true, requires decompression
	 * if false, no decompression necessary
	 * @return
	 */
	public boolean getCurrDecompressionStop() {
		return this.currDecompressionStop;
	}
	
	/**
	 * return the current depth of the next dive after the surface interval
	 * @return
	 */
	public int getCurrNextDiveDepth() {
		return this.currNextDiveDepth;
	}
	
	/**
	 * get the current residual nitrogen
	 * @return
	 */
	public int getCurrResidualNitrogen() {
		return this.currResidualNitrogen;
	}
	
	/**
	 * get the depth that was inputed by the user
	 * @return
	 */
	public int getDepthInput() {
		return this.depthInput;
	}
	
	/**
	 * get the bottom time inputed by the user
	 * @return
	 */
	public int getBottomTimeInput() {
		return this.bottomTimeInput;
	}
	 	
	/**
	 * get the surface interval inputed by the user
	 * @return
	 */
	public double getSurfaceIntervalInput() {
		return this.surfaceIntervalInput;
	}
	
	/**
	 * get the next dive depth input by the user
	 * @return
	 */
	public int getNextDiveDepthInput() {
		return this.nextDiveDepthInput;
	}
	
	// DIVER ONLY (ONLY USE FOR MINOR TESTING. J-UNIT TESTING IS REQUIRED)
	public static void main(String[] args) {
		int depth = 59;
		int bottomTime = 30;
		double surfaceInterval = 1.00;		// will change to a float if needed
		int nextDiveDepth = 48;
		DivePlanner dt = new DivePlanner(depth, bottomTime, surfaceInterval, nextDiveDepth);
		System.out.println("<<INCOMPLETE DIVE PROFILE CLASS>>");
		System.out.println(dt.getResults());
	}
}
