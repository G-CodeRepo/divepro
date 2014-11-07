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
	private int currDiveDepth;
	private boolean currDecompressionStop;
	private char currPressureGroupAtDepth;
	private char currNewPressureGroupFromTBT; 
	private char currNewPressureGroupAfterPreviousDive;
	private char currPreviousPressureGroupFromTBT;
	private double currSurfaceInterval;
	private int adjustedNoDecompressionLimitTime;
	private int currResidualNitrogenTime;
	private int currActualBottomTime;
	private int currAdjustedActualBottomTime;
	private int currTotalBottomTime;
	DiveTable diveTable;
	Dive dive;
	LinkedList<Dive> previousDives;
	DiveProfileManager diveManager;	// (NOT YET IMPLEMENTED)
	
	// Constructor
	public DivePlanner(int diveDepth, int bottomTime, double timeOnSurface) {
		this.diveTable = new DiveTable();
		this.previousDives = new LinkedList<Dive>();
		
		// create a dive profile
		this.addDive(diveDepth, bottomTime, timeOnSurface); 	
	}
	
	// Overloaded Constructor
	public DivePlanner() {
		this.diveTable = new DiveTable();
		this.previousDives = new LinkedList<Dive>();
		
		// set default values
		this.currDiveDepth = 0;
		this.currPressureGroupAtDepth = ' ';
		this.currNewPressureGroupFromTBT = ' ';
		this.currNewPressureGroupAfterPreviousDive = ' ';
		this.currPreviousPressureGroupFromTBT = ' ';
		this.currDecompressionStop = false;
		this.currSurfaceInterval = 0;
		this.adjustedNoDecompressionLimitTime = 0;
		this.currResidualNitrogenTime = 0;
		this.currActualBottomTime = 0;
		this.currAdjustedActualBottomTime = 0;
		this.currTotalBottomTime = 0;
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
		// NEEDS TO CREATE GETTERS FOR SOME INSTANCE VARIABLES
						
		this.currDiveDepth = this.adjustDepth(newDiveDepth);
		this.currActualBottomTime = bottomTime;	  // ABT IS NOT SUPPOSE TO BE ADJUSTED
		this.currAdjustedActualBottomTime = this.adjustActualBottomTime(this.currDiveDepth, bottomTime); // adjust depth to work to look it up in the table
		
		if (this.previousDives.size() > 0) {
			// MULTIPLE DIVES 
			this.currPreviousPressureGroupFromTBT = this.previousDives.getLast().getNewPressureGroupFromTBT(); //getNewPressureGroupAfterLastDive();;
			this.currSurfaceInterval = this.adjustSurfaceInterval(this.currPreviousPressureGroupFromTBT, surfaceInterval);
			this.currNewPressureGroupAfterPreviousDive = this.calcPressureGroup_SI(this.currPreviousPressureGroupFromTBT, this.currSurfaceInterval);			
			this.currResidualNitrogenTime = this.calcResidualNitrogenTime(this.currDiveDepth, this.currNewPressureGroupAfterPreviousDive);
			this.currTotalBottomTime = this.calcTotalBottomTime(this.currResidualNitrogenTime, this.currActualBottomTime);
			this.currNewPressureGroupFromTBT = this.diveTable.getPressureGroup(DivePlannerUtility.getTimeIndex(this.currDiveDepth,this.currTotalBottomTime));
		} else {
			// SINGLE DIVE			
			this.currPreviousPressureGroupFromTBT = this.diveTable.getPressureGroup(DivePlannerUtility.getTimeIndex(this.currDiveDepth, this.currAdjustedActualBottomTime));
			this.currNewPressureGroupAfterPreviousDive = this.currPreviousPressureGroupFromTBT; 	// SAME AS TBT FOR THE FIRST DIVE ONLY
			this.currResidualNitrogenTime = 0; 			// THERE IS NO RESIDUAL NITROGEN FOR THE FIRST DIVE ONLY
			this.currActualBottomTime = bottomTime;		// ABT IS NOT SUPPOSE TO BE ADJUSTED
			this.currSurfaceInterval = 0;
			this.currTotalBottomTime = this.currActualBottomTime; 	// SAME AS ACTUAL BOTTOM TIME FOR THE FIRST DIVE ONLY
			this.currNewPressureGroupFromTBT = this.currPreviousPressureGroupFromTBT;
		}

		this.currDecompressionStop = this.requireDecompressionStop(this.currDiveDepth, this.currAdjustedActualBottomTime);
		this.adjustedNoDecompressionLimitTime = this.calcAdjustedNoDecompressionLimitTime(this.currDiveDepth, this.currNewPressureGroupAfterPreviousDive);

		this.dive = new Dive(this.currDiveDepth, 					this.currPreviousPressureGroupFromTBT, 		this.currNewPressureGroupAfterPreviousDive,			
							 this.currDecompressionStop,			this.currSurfaceInterval, 					this.currNewPressureGroupFromTBT,	
							 this.adjustedNoDecompressionLimitTime,		this.currResidualNitrogenTime, 				
							 this.currActualBottomTime, 			this.currTotalBottomTime);
		
		// add to recent dives
		this.previousDives.add(this.dive);

	}
	
									
	/**
	 * prints out all the dives that were entered
	 * USE ONLY FOR DEBUGGING
	 */
	public void printAllDive() {
		for (int i = 0; i < this.previousDives.size(); i++) {
			System.out.println("*******************************************************************************\n" + 
							   "Dive #:\t\t\t\t\t\t\t\t\t" + (i+1) + "\n" +							   
							   "Previous Dive's PressureGroup From (TBT):\t\t\t\t" + this.previousDives.get(i).getPreviousPressureGroupFromTBT() + "\n" +
							   "Surface Interval (hr):\t\t\t\t\t\t\t" + this.previousDives.get(i).getsurfaceInterval() + "\n" +
							   "New Pressure Group After Previous Dive:\t\t\t\t\t" + this.previousDives.get(i).getNewPressureGroupAfterLastDive() + "\n" +
							   "Next Dive Depth (ft):\t\t\t\t\t\t\t" + this.previousDives.get(i).getDepth() + "\n\n" +
							   "Require Decompression Stop:\t\t\t\t\t\t" + this.previousDives.get(i).getDecompressionStop() + "\n" +
							   "Adjusted No Decompression Limit (ANDL) (min) (ABT MUST NOT EXCEED):\t" + this.previousDives.get(i).getAdjustedNoDecompressionLimitTime() + "\n\n" +
							   "Residual Nitrogen Time (RNT) (min):\t\t\t\t\t" + this.previousDives.get(i).getResidualNitrogenTime() + "\n" + 
							   "Actual Bottom Time (ABT) (min):\t\t\t\t\t\t" + this.previousDives.get(i).getActualBottomTime() + "\n" + 
							   "Total Bottom Time (TBT) (min):\t\t\t\t\t\t" + this.previousDives.get(i).getTotalBottomTime() + "\n\n" +
							   "New Dive's Pressure Group from (TBT):\t\t\t\t\t" + this.previousDives.get(i).getNewPressureGroupFromTBT());
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
		return pressureGroupSurfaceIntervalTimes[DivePlannerUtility.binarySearch(pressureGroupSurfaceIntervalTimes, surfaceInterval)];
	}
		
	/**
	 * get the pressure group after the surface interval
	 * @param pressureGroup
	 * @param surfaceInterval
	 * @return
	 */
	private char calcPressureGroup_SI(char pressureGroup, double surfaceInterval) {
		LinkedHashMap<Character, double[]> validSurfaceIntervalTimes = this.diveTable.getSurfaceIntervalTimes();	
		return this.diveTable.getPressureGroup(DivePlannerUtility.binarySearch(validSurfaceIntervalTimes.get(pressureGroup), surfaceInterval));
	}
	
	/**
	 * calculate the residual nitrogen time  (RNT) after the surface interval
	 * @param depthOfNextDive
	 * @param pressureGroup_SI
	 * @return
	 */
	private int calcResidualNitrogenTime(int depthOfNextDive, char pressureGroup_SI) {
		LinkedHashMap<Integer, int[]> validResidualNitrogen = this.diveTable.getResidualNitrogenTimes();
		int[] residualNitrogenTimeAtDepth = validResidualNitrogen.get(this.adjustDepth(depthOfNextDive));
		return residualNitrogenTimeAtDepth[this.diveTable.getIndexOfPressureGroup(pressureGroup_SI)];	
	}
	
	
	/**
	 * calculate the adjusted no decompression limit (ANDL) time
	 * this limit should not exceed actual bottom time (ABT)
	 * @param depthOfNextDive
	 * @param pressureGroup_SI
	 * @return
	 */
	private int calcAdjustedNoDecompressionLimitTime(int depthOfNextDive, char pressureGroup_SI) {
		LinkedHashMap<Integer, int[]> getAdjustedNoDecompressionLimitTimes = this.diveTable.getAdjustedNoDecompressionLimitTimes();
		int[] adjustedNoDecompressionLimitTimesAtDepth = getAdjustedNoDecompressionLimitTimes.get(this.adjustDepth(depthOfNextDive));;
		return adjustedNoDecompressionLimitTimesAtDepth[this.diveTable.getIndexOfPressureGroup(pressureGroup_SI)];
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
	 * get the current depth of the dive
	 * @return
	 */
	public int getCurrDepth() {
		return this.currDiveDepth;
	}
	
	/**
	 * get the current actual bottom time of the dive
	 * @return
	 */
	public int getCurrActualBottomTime() {
		return this.currActualBottomTime;
	}
	
	/**
	 * get the current pressure group associated with the depth and bottom time
	 * @return
	 */
	public char getCurrPressureGroupAtDepth() {
		return this.currPressureGroupAtDepth;
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
	 * get the current surface interval after the dive
	 * @return
	 */
	public double getCurrSurfaceInterval() {
		return this.currSurfaceInterval;
	}
				
	/**
	 * get the current residual nitrogen time (RNT)
	 * @return
	 */
	public int getCurrResidualNitrogenTime() {
		return this.currResidualNitrogenTime;
	}
	
	/**
	 * get the adjusted no decompression limit (ANDL) times 
	 * @return
	 */
	public int getAdjustedNoDecompressionLimitTimes() {
		// NOT YET IMPLEMENTED
		
		
		
		
		
		
		
		return this.currActualBottomTime;
	}
	
	/**
	 * get the current total bottom time (TBT)
	 * @return
	 */
	public int getCurrTotalBottomTime() {
		return this.currTotalBottomTime;
	}	
	
	// DIVER ONLY (ONLY USE FOR MINOR TESTING. J-UNIT TESTING IS REQUIRED)
	public static void main(String[] args) {
		int depth = 60;
		int bottomTime = 39;				// ABT
		double surfaceInterval = 1.00;		// will change to a float if needed
		
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
	}
}
