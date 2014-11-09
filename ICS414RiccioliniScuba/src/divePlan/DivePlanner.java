package divePlan;
/**
 * ICS 414 Team Ricciolini Scuba Project
 * DivePlanner class calculates the dive plan
 * @author Gerald Abut
 */

// SAVING CONCURRENT DIVE PLANS NOT YET IMPLEMENTED (MOST LIKING IN A SEPARATE CLASS). 
// DIVEPLANNER NOW WORKS WITH MULTIPLE DIVES
// THIS CLASS NEEDS TESTING

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DivePlanner {
	DiveTable diveTable;
	LinkedList<Dive> previousDives;
	DiveProfileManager diveManager;	
	
	// Constructor
	public DivePlanner(int diveDepth, int bottomTime, double timeOnSurface) {
		this.diveTable = new DiveTable();
		this.previousDives = new LinkedList<Dive>();
		this.diveManager = new DiveProfileManager();
		
		// create a dive profile
		this.addDive(diveDepth, bottomTime, timeOnSurface); 	
	}
	
	// Overloaded Constructor
	public DivePlanner() {
		this.diveTable = new DiveTable();
		this.previousDives = new LinkedList<Dive>();
		this.diveManager = new DiveProfileManager();
	}
		
	/**
	 * add a dive
	 * @param newDiveDepth
	 * @param bottomTime
	 * @param surfaceInterval
	 */
	private void addDive(int newDiveDepth, int bottomTime, double surfaceInterval) { 
		
		// TO-DO-LIST
		// CHECK IF THE NEXT DIVE DEPTH IS VALID (NEXT DIVE DEPTH MUST NOT EXCEED PREVIOUS DIVE)
		// CHECK TO SEE IF THE GIVEN diveDepth IS VALID FOR THE NEXT DIVE
		// CHECK IF THE GIVEN bottomTime IS VALID FOR THE NEXT DIVE
		// NEEDS TO CREATE GETTERS FOR SOME INSTANCE VARIABLES (OR SIMPLY REMOVE INSTANCE VARIABLES)
		int diveNumber = 0;
		int nextDiveDepth = 0;
		boolean decompressionStop = false;
		char newPressureGroupFromTBT = ' '; 
		char newPressureGroupAfterPreviousDive = ' ';
		char previousPressureGroupFromTBT = ' ';
		double surfaceIntervalAfterDive = 0;
		double minSurfaceInterval= 0;
		int adjustedNoDecompressionLimitTime = 0;
		int residualNitrogenTime = 0;
		int actualBottomTime = 0;
		int adjustedActualBottomTime = 0;
		int totalBottomTime = 0;
		
		diveNumber = this.previousDives.size() + 1;	// increment dive number
		nextDiveDepth = this.adjustDepth(newDiveDepth);
		actualBottomTime = bottomTime;	  			
		adjustedActualBottomTime = this.adjustActualBottomTime(nextDiveDepth, bottomTime); // adjust depth to be able to search the table
		
		if (this.previousDives.size() > 0) {
			// MULTIPLE DIVES 
			previousPressureGroupFromTBT = this.previousDives.getLast().getNewPressureGroupFromTBT(); //getNewPressureGroupAfterLastDive();;
			surfaceIntervalAfterDive = this.adjustSurfaceInterval(previousPressureGroupFromTBT, surfaceInterval);
			newPressureGroupAfterPreviousDive = this.calcPressureGroup(previousPressureGroupFromTBT, surfaceIntervalAfterDive);			
			residualNitrogenTime = this.calcResidualNitrogenTime(nextDiveDepth, newPressureGroupAfterPreviousDive);
			totalBottomTime = this.calcTotalBottomTime(residualNitrogenTime, actualBottomTime);
			newPressureGroupFromTBT = this.diveTable.getPressureGroup(DivePlannerUtility.getTimeIndex(nextDiveDepth, totalBottomTime));
		} else {
			// SINGLE DIVE			
			previousPressureGroupFromTBT = this.diveTable.getPressureGroup(DivePlannerUtility.getTimeIndex(nextDiveDepth, adjustedActualBottomTime));
			newPressureGroupAfterPreviousDive = previousPressureGroupFromTBT; 	// SAME AS TBT FOR THE FIRST DIVE ONLY
			residualNitrogenTime = 0; 											// THERE IS NO RESIDUAL NITROGEN FOR THE FIRST DIVE ONLY
			surfaceIntervalAfterDive = 0;										// THERE IS NO SURFACE INTERVAL FOR THE FIRST DIVE ONLY
			totalBottomTime = actualBottomTime; 								// SAME AS ACTUAL BOTTOM TIME FOR THE FIRST DIVE ONLY
			newPressureGroupFromTBT = previousPressureGroupFromTBT;
		}
		
		decompressionStop = this.requireDecompressionStop(nextDiveDepth, adjustedActualBottomTime);
		adjustedNoDecompressionLimitTime = this.calcAdjustedNoDecompressionLimitTime(nextDiveDepth, newPressureGroupAfterPreviousDive);
		minSurfaceInterval = this.calcMinSurfaceIntervalTime(previousPressureGroupFromTBT, nextDiveDepth, adjustedActualBottomTime);
		Dive dive = new Dive(diveNumber, 							nextDiveDepth, 					previousPressureGroupFromTBT, 		
							 newPressureGroupAfterPreviousDive,		decompressionStop,				surfaceIntervalAfterDive, 					
							 minSurfaceInterval,					newPressureGroupFromTBT,		adjustedNoDecompressionLimitTime,
							 residualNitrogenTime, 					actualBottomTime, 				totalBottomTime);
		
		// add to recent dives
		this.previousDives.add(dive);
	}
	
	/**
	 * prints out all the dives that were entered
	 * USE ONLY FOR DEBUGGING
	 */
	public void printAllDive() {
		for (int i = 0; i < this.previousDives.size(); i++) {
			System.out.println("*******************************************************************************\n" + 
							   "Dive #:\t\t\t\t\t\t\t\t\t" + this.previousDives.get(i).getDiveNumber() + "\n\n" +	
							   "Previous Dive's PressureGroup From (TBT):\t\t\t\t" + this.previousDives.get(i).getPreviousPressureGroupFromTBT() + "\n" +
							   "Surface Interval (hr):\t\t\t\t\t\t\t" + this.previousDives.get(i).getSurfaceInterval() + "\n" +
							   "New Pressure Group After Previous Dive:\t\t\t\t\t" + this.previousDives.get(i).getNewPressureGroupAfterLastDive() + "\n" +
							   "Next Dive Depth (ft):\t\t\t\t\t\t\t" + this.previousDives.get(i).getDepth() + "\n\n" +
							   "Residual Nitrogen Time (RNT) (min):\t\t\t\t\t" + this.previousDives.get(i).getResidualNitrogenTime() + "\n" + 
							   "Actual Bottom Time (ABT) (min):\t\t\t\t\t\t" + this.previousDives.get(i).getActualBottomTime() + "\n" + 
							   "Total Bottom Time (TBT) (min):\t\t\t\t\t\t" + this.previousDives.get(i).getTotalBottomTime() + "\n\n" +
							   "New Dive's Pressure Group from (TBT):\t\t\t\t\t" + this.previousDives.get(i).getNewPressureGroupFromTBT() + "\n\n" +  
							   "Require Decompression Stop:\t\t\t\t\t\t" + this.previousDives.get(i).getDecompressionStop() + "\n" +
							   "Adjusted No Decompression Limit (ANDL) (min) (ABT MUST NOT EXCEED):\t" + this.previousDives.get(i).getAdjustedNoDecompressionLimitTime() + "\n" +
							   "Minimum Surface Interval Time (min):\t\t\t\t\t" + this.previousDives.get(i).getMinSurfaceInterval() + "\n\n");
		}
	}
	
	/**
	 * get the adjusted depth from the dive table with the given input depth
	 * @param searchDepth
	 * @return
	 */
	private int adjustDepth(int searchDepth) {
		int[] validDepths = this.diveTable.getValidDepths();	// array of valid depths
		return validDepths[DivePlannerUtility.binarySearch(validDepths, searchDepth)];
	}
		
	/**
	 * 
	 * @param desiredDepth
	 * @param searchTimeAtDepth
	 * @return
	 */
	private int adjustActualBottomTime(int desiredDepth, int searchTimeAtDepth) {
		LinkedHashMap<Integer, int[]> validDepthTimes = this.diveTable.getDepthTimes();
		int[] validTimesAtDepth = validDepthTimes.get(desiredDepth);
		int time_index = DivePlannerUtility.binarySearch(validTimesAtDepth, searchTimeAtDepth);		
		return validTimesAtDepth[time_index];
	}
	
	/**
	 * checks to see if a decompression stop is required
	 * @param depth
	 * @param bottomTime
	 * @return
	 */
	private boolean requireDecompressionStop(int depth, int bottomTime) {
		return this.diveTable.requireDecompressionStop(depth, bottomTime);
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
		return pressureGroupSurfaceIntervalTimes[DivePlannerUtility.binarySearchReverseDouble(pressureGroupSurfaceIntervalTimes, surfaceInterval)];
	}
		
	/**
	 * get the pressure group for the given dive or pressure group after surface interval
	 * @param pressureGroup
	 * @param surfaceInterval
	 * @return
	 */
	private char calcPressureGroup(char pressureGroup, double surfaceInterval) {
		LinkedHashMap<Character, double[]> validSurfaceIntervalTimes = this.diveTable.getSurfaceIntervalTimes();	
		return this.diveTable.getPressureGroup(DivePlannerUtility.binarySearchReverseDouble(validSurfaceIntervalTimes.get(pressureGroup), surfaceInterval));
	}
	
	/**
	 * calculate the residual nitrogen time  (RNT) after the surface interval
	 * @param nextDiveDepth
	 * @param newPressureGroupAfterPreviousDive
	 * @return
	 */
	private int calcResidualNitrogenTime(int nextDiveDepth, char newPressureGroupAfterPreviousDive) {
		LinkedHashMap<Integer, int[]> validResidualNitrogen = this.diveTable.getResidualNitrogenTimes();
		int[] residualNitrogenTimeAtDepth = validResidualNitrogen.get(this.adjustDepth(nextDiveDepth));
		return residualNitrogenTimeAtDepth[this.diveTable.getIndexOfPressureGroup(newPressureGroupAfterPreviousDive)];	
	}
	
	/**
	 * calculate the adjusted no decompression limit (ANDL) time
	 * this limit should not exceed actual bottom time (ABT)
	 * AKA: MAXIMUM ALLOWED DEPTH from a previous dive
	 * @param nextDiveDepth
	 * @param newPressureGroupAfterPreviousDive
	 * @return
	 */
	private int calcAdjustedNoDecompressionLimitTime(int nextDiveDepth, char newPressureGroupAfterPreviousDive) {
		LinkedHashMap<Integer, int[]> getAdjustedNoDecompressionLimitTimes = this.diveTable.getAdjustedNoDecompressionLimitTimes();
		int[] adjustedNoDecompressionLimitTimesAtDepth = getAdjustedNoDecompressionLimitTimes.get(this.adjustDepth(nextDiveDepth));
		return adjustedNoDecompressionLimitTimesAtDepth[this.diveTable.getIndexOfPressureGroup(newPressureGroupAfterPreviousDive)];
	}
	
	/**
	 * calculate the total bottom time (TBT) from the given residual nitrogen time (RNT) and 
	 * the actual bottom time (ABT)
	 * @param residualNitrogenTime
	 * @param actualBottomTime
	 * @return
	 */
	private int calcTotalBottomTime(int residualNitrogenTime, int actualBottomTime) {
		return (residualNitrogenTime + actualBottomTime);
	}
	
	/**
	 * calculate the minimum surface interval between two dives
	 * @param nextDiveDepth
	 * @param adjustedActualBottomTime
	 * @return
	 */
	private double calcMinSurfaceIntervalTime(char previousPressureGroupFromTBT, int nextDiveDepth, int adjustedActualBottomTime) {
		LinkedHashMap<Integer, int[]> validBottomTimes = this.diveTable.getAdjustedNoDecompressionLimitTimes(); 	// reused method to calculate pressure Group
		LinkedHashMap<Character, double[]> validSurfaceIntervalTimes = this.diveTable.getSurfaceIntervalTimes();
		double[] surfaceIntervalTimeAtDepth = validSurfaceIntervalTimes.get(previousPressureGroupFromTBT);
		int[] bottomTimesAtDepth = validBottomTimes.get(nextDiveDepth);	
		int timeIndex = DivePlannerUtility.binarySearchReverseInt(bottomTimesAtDepth, adjustedActualBottomTime);
		char pressureGroup = this.diveTable.getPressureGroup(timeIndex);
		if (previousPressureGroupFromTBT <= pressureGroup) {
			return 0;	// the first surfaceInterval time is the minimum surface interval (all beginning surface intervals have value zero)
		} else {
			return (surfaceIntervalTimeAtDepth[timeIndex+1] + (double)0.01);
		}
	}
	
	/**
	 * get the dive number of this dive
	 * @return
	 */
	public int getDiveNumber(Dive d) {
		return d.getDiveNumber();
	}
			
	/**
	 * get the depth of the dive
	 * @return
	 */
	public int getDepth(Dive d) {
		return d.getDepth();
	}
	
	/**
	 * get the actual bottom time of the dive
	 * @return
	 */
	public int getActualBottomTime(Dive d) {
		return d.getActualBottomTime();
	}
	
	/**
	 * get the pressure group associated with the depth and bottom time
	 * @return
	 */
	public char getPressureGroupAtDepth(Dive d) {
		return d.getPressureGroup();
	}
	
	/**
	 * get the boolean for the decompression stop.
	 * if true, requires decompression
	 * if false, no decompression necessary
	 * @return
	 */
	public boolean getDecompressionStop(Dive d) {
		return d.getDecompressionStop();
	}
	
	/**
	 * get the surface interval after the dive
	 * @return
	 */
	public double getSurfaceInterval(Dive d) {
		return d.getSurfaceInterval();
	}
	
	/**
	 * get the minimum surface interval for the dive
	 * @return
	 */
	public double getMinSurfaceInterval(Dive d) {
		return d.getMinSurfaceInterval();
	}
				
	/**
	 * get the current residual nitrogen time (RNT)
	 * @return
	 */
	public int getResidualNitrogenTime(Dive d) {
		return d.getResidualNitrogenTime();
	}
	
	/**
	 * get the adjusted no decompression limit (ANDL) times 
	 * @return
	 */
	public int getAdjustedNoDecompressionLimitTimes(Dive d) {
		return d.getAdjustedNoDecompressionLimitTime();
	}
	
	/**
	 * get the current total bottom time (TBT)
	 * @return
	 */
	public int getCurrTotalBottomTime(Dive d) {
		return d.getTotalBottomTime();
	}
	
	/**
	 * returns all the previous dives in a linkedlist
	 * @return
	 */
	public LinkedList<Dive> getAllDives() {
		return this.previousDives;
	}
	
	/**
	 * save the current dive plan (ALL the dives that were made)
	 * uses the Date class to get the current time
	 */
	public void saveDivePlan() {		
		// NOT YET IMPLEMENTED
		
		
		
		
		
		
	}
	
	// DIVER ONLY (ONLY USE FOR MINOR TESTING. J-UNIT TESTING IS REQUIRED)
	public static void main(String[] args) {
		int depth = 60;
		int bottomTime = 39;				// ABT
		double surfaceInterval = 0;			// THERE IS NO SURFACE INTERVAL FOR FIRST DIVE
		
		// WARNING: DIVES CURRENTLY DOES NOT HAVE A ANY ERROR CHECKS
		// dive 1
		DivePlanner dt = new DivePlanner(depth, bottomTime, surfaceInterval);
		
		// dive 2
		depth = 40;	
		bottomTime = 30;					// ABT		
		surfaceInterval = 1.00;		
		dt.addDive(depth, bottomTime, surfaceInterval);		
	
		// dive 3
		depth = 50;		
		bottomTime = 30;					// ABT
		surfaceInterval = 1.00;		
		dt.addDive(depth, bottomTime, surfaceInterval);	
		
		// dive 4
		depth = 49;		
		bottomTime = 50;					// ABT
		surfaceInterval = 2.00;		
		dt.addDive(depth, bottomTime, surfaceInterval);	
		
		System.out.println("<<DIVEPLANNER: DEBUG>>");
		dt.printAllDive();
		
		// CALL saveDivePlan HERE...
		
		
		
		
		
		
	}
}
