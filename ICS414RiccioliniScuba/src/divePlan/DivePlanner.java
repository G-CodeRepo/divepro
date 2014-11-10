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
	DiveTable 			diveTable;
	LinkedList<Dive> 	previousDives;
	DiveProfileManager 	diveManager;	
	 
	/**
	 * Constructor
	 * @param diveDepth
	 * @param bottomTime
	 * @param timeOnSurface
	 * @throws IllegalArgumentException
	 */
	public DivePlanner(int diveDepth, int bottomTime, double timeOnSurface) throws IllegalArgumentException {
		this.diveTable 		= new DiveTable();
		this.previousDives 	= new LinkedList<Dive>();
		this.diveManager 	= new DiveProfileManager();
		
		// create a dive profile
		this.addDive(diveDepth, bottomTime, timeOnSurface); 	
	}
	
	/**
	 * Overloaded Constructor
	 */
	public DivePlanner() {
		this.diveTable 		= new DiveTable();
		this.previousDives 	= new LinkedList<Dive>();
		this.diveManager 	= new DiveProfileManager();
	}
		
	/**
	 * add a dive
	 * @param newDiveDepth
	 * @param bottomTime
	 * @param surfaceInterval
	 * @throws IllegalArgumentException
	 */
	private void addDive(int newDiveDepth, int bottomTime, double surfaceInterval) { 
		
		// TO-DO-LIST
		// CHECK FOR MAX AND MIN SURFACE INTERVAL TIME
		int 	diveNumber 							= 0;
		int 	nextDiveDepth 						= 0;
		boolean decompressionStop 					= false;
		char 	newPressureGroupFromTBT 			= ' '; 
		char 	newPressureGroupAfterPreviousDive 	= ' ';
		char 	previousPressureGroupFromTBT 		= ' ';
		double 	surfaceIntervalAfterDive 			= 0;
		double 	minSurfaceInterval					= 0;
		int 	adjustedNoDecompressionLimitTime 	= 0;
		int 	residualNitrogenTime 				= 0;
		int 	actualBottomTime 					= 0;
		int 	adjustedActualBottomTime 			= 0;
		int 	totalBottomTime 					= 0;
		
		diveNumber 								= this.previousDives.size() + 1;	// increment dive number
		nextDiveDepth 							= this.adjustDepth(newDiveDepth);
		actualBottomTime 						= bottomTime;	  			
		adjustedActualBottomTime 				= this.adjustActualBottomTime(nextDiveDepth, bottomTime); // adjust depth to be able to search the table
		
		// check if next dive depth is a valid dive depth. next dive depth should be less than or equal to previous dive
		if ((this.previousDives.size() > 0) && (newDiveDepth > this.previousDives.getLast().getDepth())) {
			throw new IllegalArgumentException("ERROR: Given Dive Depth Exceeds The Maximum Allowed Dive Depth After A Previous Dive.\n" +
											   "Error Location:\t\t\t\t" + "DivePlanner -> addDive()\n" +
											   "Given Dive Depth:\t\t\t" + newDiveDepth + "\n" +
											   "Max Allowable Depth Of Next Dive:\t" + this.previousDives.getLast().getDepth() + "\n");
		}
		
		if (this.previousDives.size() > 0) {
			// MULTIPLE DIVES 
			previousPressureGroupFromTBT 		= this.previousDives.getLast().getNewPressureGroupFromTBT(); 
			surfaceIntervalAfterDive 			= this.adjustSurfaceInterval(previousPressureGroupFromTBT, surfaceInterval);
			newPressureGroupAfterPreviousDive 	= this.calcPressureGroup(previousPressureGroupFromTBT, surfaceIntervalAfterDive);			
			residualNitrogenTime 				= this.calcResidualNitrogenTime(nextDiveDepth, newPressureGroupAfterPreviousDive);
			totalBottomTime 					= this.calcTotalBottomTime(residualNitrogenTime, actualBottomTime);
			newPressureGroupFromTBT 			= this.diveTable.getValidPressureGroup(DivePlannerUtility.getTimeIndex(nextDiveDepth, totalBottomTime));
		} else {
			// SINGLE DIVE			
			previousPressureGroupFromTBT 		= this.diveTable.getValidPressureGroup(DivePlannerUtility.getTimeIndex(nextDiveDepth, adjustedActualBottomTime));
			newPressureGroupAfterPreviousDive 	= previousPressureGroupFromTBT; 	// SAME AS TBT FOR THE FIRST DIVE ONLY
			residualNitrogenTime 				= 0; 								// THERE IS NO RESIDUAL NITROGEN FOR THE FIRST DIVE ONLY
			surfaceIntervalAfterDive 			= 0;								// THERE IS NO SURFACE INTERVAL FOR THE FIRST DIVE ONLY
			totalBottomTime 					= actualBottomTime; 				// SAME AS ACTUAL BOTTOM TIME FOR THE FIRST DIVE ONLY
			newPressureGroupFromTBT 			= previousPressureGroupFromTBT;		// SAME AS TBT FOR THE FIRST DIVE ONLY
		}
		
		decompressionStop = this.requireDecompressionStop(nextDiveDepth, adjustedActualBottomTime);
		adjustedNoDecompressionLimitTime = this.calcAdjustedNoDecompressionLimitTime(nextDiveDepth, newPressureGroupAfterPreviousDive);
		minSurfaceInterval = this.calcMinSurfaceIntervalTime(previousPressureGroupFromTBT, nextDiveDepth, adjustedActualBottomTime);
		
		// check if the surface interval is at least the minimum surface interval allowed after a dive
		if ((this.previousDives.size() > 0) && (surfaceInterval < minSurfaceInterval)) {
			throw new IllegalArgumentException("ERROR: Given Surface Interval Time Is Below The Minimum Surface Interval Time After A Previous Dive.\n" +
											   "This Could Also Be Caused By An Invalid Bottom Time.\n" +
											   "Error Location:\t\t\t\t\t\t" + "DivePlanner -> addDive()\n" +
											   "Given Surface Interval Time:\t\t\t\t" + surfaceInterval + "\n" +
											   "Minimum Allowable Surface Interval Of Next Dive:\t" + (float)minSurfaceInterval + "\n");
		}
		
		// check if the next bottom time exceeds the maximum allowed bottom time after a dive
		if ((this.previousDives.size() > 0) && (bottomTime > adjustedNoDecompressionLimitTime)) {
			throw new IllegalArgumentException("ERROR: Given Bottom Time Exceed The Maximum Allowed Bottom Time After A Previous Dive.\n" +
											   "This Could Also Be Caused By An Invalid Surface Interval Time.\n" +
											   "Error Location:\t\t\t\t\t" + "DivePlanner -> addDive()\n" +
											   "Given Bottom Time:\t\t\t\t" + bottomTime + "\n" +
											   "Max Allowable Bottom Time Of Next Dive:\t\t" + adjustedNoDecompressionLimitTime + "\n");
		}
		

		
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
	 * @throws IllegalStateException
	 */
	public void printAllDives() {
		if (this.previousDives.isEmpty()) {
			throw new IllegalStateException("ERROR: No Dives Were Not Created.\n" +
											"Error Location:\t" + "DivePlanner -> printAllDives\n");
		}
		for (int i = 0; i < this.previousDives.size(); i++) {
			System.out.println("*******************************************************************************\n" + 
							   "Dive #:\t\t\t\t\t\t\t\t\t" 												+ this.previousDives.get(i).getDiveNumber() + "\n\n" +	
							   "Previous Dive's PressureGroup From (TBT):\t\t\t\t" 						+ this.previousDives.get(i).getPreviousPressureGroupFromTBT() + "\n" +
							   "Surface Interval (hr):\t\t\t\t\t\t\t" 									+ this.previousDives.get(i).getSurfaceInterval() + "\n" +
							   "New Pressure Group After Previous Dive:\t\t\t\t\t" 						+ this.previousDives.get(i).getNewPressureGroupAfterPreviousDive() + "\n" +
							   "Next Dive Depth (ft):\t\t\t\t\t\t\t" 									+ this.previousDives.get(i).getDepth() + "\n\n" +
							   "Residual Nitrogen Time (RNT) (min):\t\t\t\t\t" 							+ this.previousDives.get(i).getResidualNitrogenTime() + "\n" + 
							   "Actual Bottom Time (ABT) (min):\t\t\t\t\t\t" 							+ this.previousDives.get(i).getActualBottomTime() + "\n" + 
							   "Total Bottom Time (TBT) (min):\t\t\t\t\t\t" 							+ this.previousDives.get(i).getTotalBottomTime() + "\n\n" +
							   "New Dive's Pressure Group from (TBT):\t\t\t\t\t" 						+ this.previousDives.get(i).getNewPressureGroupFromTBT() + "\n\n" +  
							   "Require Decompression Stop:\t\t\t\t\t\t" 								+ this.previousDives.get(i).getDecompressionStop() + "\n" +
							   "Adjusted No Decompression Limit (ANDL) (min) (ABT MUST NOT EXCEED):\t" 	+ this.previousDives.get(i).getAdjustedNoDecompressionLimitTime() + "\n" +
							   "Minimum Surface Interval Time (min):\t\t\t\t\t" 						+ this.previousDives.get(i).getMinSurfaceInterval() + "\n");
		}
	}
	
	/**
	 * get the adjusted depth from the dive table with the given input depth
	 * @param searchDepth
	 * @return
	 */
	private int adjustDepth(int searchDepth) {
		int[] 	validDepths = this.diveTable.getValidDepths();	// array of valid depths
		int 	depthIndex 	= DivePlannerUtility.binarySearch(validDepths, searchDepth);
		if ((depthIndex < 0) || (depthIndex >= validDepths.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t\t\t" + "DivePlanner -> adjustDepth()\n" +
												"Calculated Index Of Depth:\t" + depthIndex + "\n" +
												"Maximum Index of Valid Depths:\t" + (validDepths.length-1) + "\n");
		}
		return validDepths[depthIndex];
	}
		
	/**
	 * 
	 * @param desiredDepth
	 * @param searchTimeAtDepth
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private int adjustActualBottomTime(int desiredDepth, int searchTimeAtDepth) {
		LinkedHashMap<Integer, int[]> 	validBottomTimes 						= this.diveTable.getValidBottomTimes();
		int[] 							validTimesAtDepth 						= validBottomTimes.get(desiredDepth);
		int 							time_index 								= DivePlannerUtility.binarySearch(validTimesAtDepth, searchTimeAtDepth);
		if ((time_index < 0) || (time_index >= validTimesAtDepth.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t\t\t\t\t" + "DivePlanner -> adjustActualBottomTime()\n" +
												"Calculated Index From Bottom Times:\t\t" + time_index + "\n" +
												"Maximum Index Of Bottom Times At Depth " + desiredDepth + ":\t" + (validTimesAtDepth.length-1) + "\n");
		}
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
	 * @throws IndexOutOfBoundsException
	 */
	private double adjustSurfaceInterval(char pressureGroup, double surfaceInterval) {
		LinkedHashMap<Character, double[]> 	validSurfaceIntervalTimes 			= this.diveTable.getValidSurfaceIntervalTimes();
		double[] 							pressureGroupSurfaceIntervalTimes 	= validSurfaceIntervalTimes.get(pressureGroup);	
		int 								surfaceIntervalIndex				= DivePlannerUtility.binarySearchReverseDouble(pressureGroupSurfaceIntervalTimes, surfaceInterval);
		if ((surfaceIntervalIndex < 0) || (surfaceIntervalIndex >= pressureGroupSurfaceIntervalTimes.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t" + "DivePlanner -> adjustSurfaceInterval()\n" +
												"Calculated Index From Pressure Group At Surface Interval:\t" + surfaceIntervalIndex + "\n" +
												"Maximum Index Of SurFaceInterval Times At Pressure Group " + pressureGroup + ":\t" + (pressureGroupSurfaceIntervalTimes.length-1) + "\n");
		}
		return pressureGroupSurfaceIntervalTimes[surfaceIntervalIndex];
	}
		
	/**
	 * get the pressure group for the given dive or pressure group after surface interval
	 * @param pressureGroup
	 * @param surfaceInterval
	 * @return
	 */
	private char calcPressureGroup(char pressureGroup, double surfaceInterval) {
		LinkedHashMap<Character, double[]> 	validSurfaceIntervalTimes 				= this.diveTable.getValidSurfaceIntervalTimes();
		double[] 							surfaceIntervalTimesAtPressureGroup 	= validSurfaceIntervalTimes.get(pressureGroup);
		int 								pressureGroupIndex					= DivePlannerUtility.binarySearchReverseDouble(surfaceIntervalTimesAtPressureGroup, surfaceInterval);		
		if ((pressureGroupIndex < 0) || (pressureGroupIndex >= surfaceIntervalTimesAtPressureGroup.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t\t\t\t\t\t\t" + "DivePlanner -> calcPressureGroup()\n" +
												"Calculated Index From Pressure Group At Surface Interval:\t" + pressureGroupIndex + "\n" +
												"Maximum Index Of SurFaceInterval Times At Pressure Group " + pressureGroup + ":\t" + (surfaceIntervalTimesAtPressureGroup.length-1) + "\n");
		}
		return this.diveTable.getValidPressureGroup(pressureGroupIndex);
	}
	
	/**
	 * calculate the residual nitrogen time  (RNT) after the surface interval
	 * @param nextDiveDepth
	 * @param newPressureGroupAfterPreviousDive
	 * @return
	 */
	private int calcResidualNitrogenTime(int nextDiveDepth, char newPressureGroupAfterPreviousDive) {
		LinkedHashMap<Integer, int[]> 	validResidualNitrogen			= this.diveTable.getValidResidualNitrogenTimes();
		int[] 							residualNitrogenTimeAtDepth 	= validResidualNitrogen.get(this.adjustDepth(nextDiveDepth));
		int 							pressureGroupIndex				= this.diveTable.getIndexOfPressureGroup(newPressureGroupAfterPreviousDive);
		if ((pressureGroupIndex < 0) || (pressureGroupIndex >= residualNitrogenTimeAtDepth.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t\t\t\t\t" + "DivePlanner -> calcResidualNitrogenTime()\n" +
												"Calculated Index Of Nitrogen Time At Depth:\t" + pressureGroupIndex + "\n" +
												"Maximum Index Of Nitrogen Time At Depth " + nextDiveDepth + ":\t" + (residualNitrogenTimeAtDepth.length-1) + "\n");
		}
		return residualNitrogenTimeAtDepth[pressureGroupIndex];	
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
		LinkedHashMap<Integer, int[]> 	getAdjustedNoDecompressionLimitTimes 		= this.diveTable.getValidAdjustedNoDecompressionLimitTimes();
		int[] 							adjustedNoDecompressionLimitTimesAtDepth 	= getAdjustedNoDecompressionLimitTimes.get(this.adjustDepth(nextDiveDepth));
		int								pressureGroupIndex							= this.diveTable.getIndexOfPressureGroup(newPressureGroupAfterPreviousDive);
		if ((pressureGroupIndex < 0) || (pressureGroupIndex >= adjustedNoDecompressionLimitTimesAtDepth.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t\t\t\t\t\t\t\t" + "DivePlanner -> calcAdjustedNoDecompressionLimitTime()\n" +
												"Calculated Index Of Adjusted No Decompression Limit Time At Depth:\t" + pressureGroupIndex + "\n" +
												"Maximum Index Of Adjusted No Decompression Limit Time At Depth " + nextDiveDepth + ":\t" + (adjustedNoDecompressionLimitTimesAtDepth.length-1) + "\n");
		}
		return adjustedNoDecompressionLimitTimesAtDepth[pressureGroupIndex];
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
		LinkedHashMap<Integer, int[]> 		validBottomTimes 			= this.diveTable.getValidAdjustedNoDecompressionLimitTimes(); 	// reused method to calculate pressure Group
		LinkedHashMap<Character, double[]> 	validSurfaceIntervalTimes 	= this.diveTable.getValidSurfaceIntervalTimes();
		double[] 							surfaceIntervalTimeAtDepth 	= validSurfaceIntervalTimes.get(previousPressureGroupFromTBT);
		int[] 								bottomTimesAtDepth 			= validBottomTimes.get(nextDiveDepth);	
		int 								bottomTimeIndex 			= DivePlannerUtility.binarySearchReverseInt(bottomTimesAtDepth, adjustedActualBottomTime);
		char 								pressureGroup 				= this.diveTable.getValidPressureGroup(bottomTimeIndex);
		if ((bottomTimeIndex < 0) || (bottomTimeIndex >= bottomTimesAtDepth.length)) {
			throw new IndexOutOfBoundsException("ERROR: Index Out Of Bounds\n" +
												"Error Location:\t\t\t\t\t\t\t" + "DivePlanner -> calcMinSurfaceIntervalTime()\n" +
												"Calculated Index Of Minimum Surface Interval Time At Depth:\t" + bottomTimeIndex + "\n" +
												"Maximum Index Of Bottom Time At Depth " + nextDiveDepth + ":\t\t\t" + (bottomTimesAtDepth.length-1) + "\n");
		}
		if (previousPressureGroupFromTBT <= pressureGroup) {
			return 0;	// the first surfaceInterval time is the minimum surface interval (all beginning surface intervals have value zero)
		} else {
			return (surfaceIntervalTimeAtDepth[bottomTimeIndex+1] + (double)0.01);
		}
	}
	
	/**
	 * get the dive number of this dive
	 * @param dive
	 * @return
	 */
	public int getDiveNumber(Dive dive) {
		return dive.getDiveNumber();
	}
			
	/**
	 * get the depth of the dive
	 * @param dive
	 * @return
	 */
	public int getDepth(Dive dive) {
		return dive.getDepth();
	}
	
	/**
	 * get the actual bottom time of the dive
	 * @param dive
	 * @return
	 */
	public int getActualBottomTime(Dive dive) {
		return dive.getActualBottomTime();
	}
	
	/**
	 * get the pressure group associated with the depth and bottom time
	 * @param dive
	 * @return
	 */
	public char getPressureGroup(Dive dive) {
		return dive.getPressureGroup();
	}
	
	/**
	 * get the pressure group from the previous dive (TBT)
	 * @param dive
	 * @return
	 */
	public char getPreviousPressureGroupFromTBT(Dive dive) {
		return dive.getPreviousPressureGroupFromTBT();
	}
	
	/**
	 * get the new pressure group after the last dive and surface interval
	 * @param dive
	 * @return
	 */
	public char getNewPressureGroupAfterPreviousDive(Dive dive) {
		return dive.getNewPressureGroupAfterPreviousDive();
	}

	/**
	 * get the new pressure group from the total bottom time (TBT)
	 * @param dive
	 * @return
	 */
	public char getNewPressureGroupFromTBT(Dive dive) {
		return dive.getNewPressureGroupFromTBT();
	}
	
	/**
	 * get the boolean for the decompression stop.
	 * if true, requires decompression
	 * if false, no decompression necessary
	 * @param dive
	 * @return
	 */
	public boolean getDecompressionStop(Dive dive) {
		return dive.getDecompressionStop();
	}
	
	/**
	 * get the surface interval after the dive
	 * @param dive
	 * @return
	 */
	public float getSurfaceInterval(Dive dive) {
		return dive.getSurfaceInterval();
	}
	
	/**
	 * get the minimum surface interval for the dive
	 * @param dive
	 * @return
	 */
	public double getMinSurfaceInterval(Dive dive) {
		return dive.getMinSurfaceInterval();
	}
				
	/**
	 * get the current residual nitrogen time (RNT)
	 * @param dive
	 * @return
	 */
	public int getResidualNitrogenTime(Dive dive) {
		return dive.getResidualNitrogenTime();
	}
	
	/**
	 * get the adjusted no decompression limit (ANDL) times 
	 * AKA: maximum allowable bottom time after a dive
	 * @param dive
	 * @return
	 */
	public int getAdjustedNoDecompressionLimitTimes(Dive dive) {
		return dive.getAdjustedNoDecompressionLimitTime();
	}
	
	/**
	 * get the total bottom time (TBT)
	 * @param dive
	 * @return
	 */
	public int getTotalBottomTime(Dive dive) {
		return dive.getTotalBottomTime();
	}
	
	
	/**
	 * Remove a dive from the given dive number
	 * Warning: removing a dive, removes the dives after the deleted dive.
	 * This was done because the dives ahead of the delete dives will not have
	 * the correct calculations for the dive planner
	 * @param diveNumber
	 * @throws IndexOutOfBoundsException
	 */
	public void removeDive(int diveNumber) {
		int actualDiveNumber = 0;
		
		if (diveNumber == 0) {
			// do nothing
		} else {
			actualDiveNumber = diveNumber - 1; // array starts at zero
		}
	
		if ((actualDiveNumber >= this.previousDives.size()) || (actualDiveNumber < 0)) {
			throw new IndexOutOfBoundsException("ERROR: The Dive Number Does not Exist.\n" +
												"Error Location:\t" + "DivePlanner -> removeDive()\n");
		} else {
			for (int i = this.previousDives.size()-1; i >= actualDiveNumber; i--) {
				this.previousDives.remove(i);
			}
		}
	}
	
	/**
	 * returns all the dives that were entered in a linkedlist
	 * @return
	 * @throws IllegalStateException
	 */
	public LinkedList<Dive> getDivePlan() {
		if (this.previousDives.isEmpty()) {
			throw new IllegalStateException("ERROR: No Dive Was Created.\n" +
										    "Error Location:\t" + "DivePlanner -> getDivePlan()\n");
		}
		return this.previousDives;
	}
	
	/**
	 * save the current dive plan (ALL the dives that were made)
	 * @throws IllegalStateException
	 */
	public void saveDivePlan() {		
		// NOT YET IMPLEMENTED
		if (this.previousDives.isEmpty()) {
			throw new IllegalStateException("ERROR: There Are No Dives To Save. Please Create A Dive.\n" + 
											"Error Location:\t" + "DivePlanner -> saveDivePlan()\n");
		} else {
			// SAVE USING THE "DiveProfileManager" CLASS HERE...
			LinkedList<Dive> divesToSave = getDivePlan();
			
			
			
			
			
			
			
			
			
			
		}
	}
	
	// DIVER ONLY (ONLY USE FOR MINOR TESTING. J-UNIT TESTING IS REQUIRED)
	public static void main(String[] args) {
		int 	depth 			= 60;
		int 	bottomTime 		= 39;						// ABT
		double 	surfaceInterval = 0;						// THERE IS NO SURFACE INTERVAL FOR FIRST DIVE
		try {
			// dive 1
			DivePlanner dt = new DivePlanner(depth, bottomTime, surfaceInterval);
			
			// dive 2
			depth 					= 50;	
			bottomTime				= 10;					// ABT		
			surfaceInterval 		= 0.10;		
			dt.addDive(depth, bottomTime, surfaceInterval);		
		
			// dive 3
			depth 					= 40;		
			bottomTime 				= 80;					// ABT
			surfaceInterval 		= 1.00;		
			dt.addDive(depth, bottomTime, surfaceInterval);	
			
			// dive 4
			depth 					= 30;		
			bottomTime 				= 60;					// ABT
			surfaceInterval 		= 1.00;		
			dt.addDive(depth, bottomTime, surfaceInterval);	
			
			System.out.println("<<DIVEPLANNER: DEBUG>>");
			dt.printAllDives();
		
			// remove a dive
			System.out.println("\n*******************************************************************************\n" + 
							   "\n\t\t\t   <<REMOVING A DIVE>>\n" + 
							   "REMOVING A DIVE ALSO REMOVES THE DIVES THAT ARE AHEAD OF THE REMOVED DIVE\n");
			dt.removeDive(3);
			dt.printAllDives();

			//LinkedList<Dive> dives = dt.getDivePlan();
			//Dive d = dives.get(0);
			//dt.getActualBottomTime(d);
			//dt.getAdjustedNoDecompressionLimitTimes(d);
			dt.saveDivePlan();
			
			
			
		} catch (IllegalStateException x) {
			// THESE ERROR CHECKS ARE ONLY USED FOR DEBUGGING
			// DO NOT PUT MESSAGE IN GUI
			// GUI WILL USE COLORS INSTEAD
			System.err.println(x.getMessage());
		} catch (IndexOutOfBoundsException y) {
			System.err.println(y.getMessage());
		} catch (IllegalArgumentException z) {
			System.err.println(z.getMessage());
		}
	}
}
