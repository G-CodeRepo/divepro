/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveTable class calculates the dive plan
 * @author Gerald Abut, Kavika Tavui, Victor Silva
 */


// SAVING CONCURRENT DIVE PLANS NOT YET IMPLEMENTED (MOST LIKING IN A SEPARATE CLASS). THIS DIVE TABLE CURRENTLY ONLY WORKS FOR A SINGLE DIVE.
// THIS CLASS NEEDS TESTING


import java.util.LinkedHashMap;

public class DiveTable {
	private LinkedHashMap<Integer, int[]> depthTimes;
	private LinkedHashMap<Character, double[]> surfaceIntervalTimes;
	private int depthInput;
	private int depth;
	private int bottomTimeInput;
	private int bottomTime;
	private char pressureGroup;
	private char pressureGroup_SI;
	private double surfaceIntervalInput;
	private double surfaceInterval;
	private boolean decompressionStop;
	private int residualNitrogen;
		
	// Constructor
	public DiveTable(int diveDepth, int timeUnder, double surfaceIntervalInput) {	
		// original inputed values
		this.depthInput = diveDepth;		// feet
		this.surfaceIntervalInput = surfaceIntervalInput;
		this.bottomTimeInput = timeUnder;

		// valid depths
		final int[] DEPTHS = {35, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140};
		
		// time chart
		final int[] MINUTES_D35 	= {10, 19, 25, 29, 32, 36, 40, 44, 48, 52, 57, 62, 67, 73, 79, 85, 92, 100, 108, 117, 127, 139, 152, 168, 188, 205};
		final int[] MINUTES_D40 	= {9, 16, 22, 25, 27, 31, 34, 37, 40, 44, 48, 51, 55, 60, 64, 69, 74, 79, 85, 91, 97, 104, 11, 120, 129, 140};
		final int[] MINUTES_D50 	= {7, 13, 17, 19, 21, 24, 26, 28, 31, 33, 36, 39, 41, 44, 47, 50, 53, 57, 60, 63, 67, 71, 75, 80};
		final int[] MINUTES_D60 	= {6, 11, 14, 16, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 42, 44, 47, 49, 52, 54, 55};
		final int[] MINUTES_D70 	= {5, 9, 12, 13, 15, 16, 18, 19, 21, 22, 24, 26, 27, 29, 31, 33, 35, 36, 38, 40};
		final int[] MINUTES_D80 	= {4, 8, 10, 11, 13, 14, 15, 17, 18, 19, 21, 22, 23, 25, 26, 28, 29, 30};
		final int[] MINUTES_D90 	= {4, 7, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 21, 22, 23, 24, 25};
		final int[] MINUTES_D100 	= {3, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		final int[] MINUTES_D110 	= {3, 6, 7, 8, 9, 10, 11, 12, 13, 13, 14, 15, 16};			// the second 13 represents the "arrow" value on the dive table
		final int[] MINUTES_D120	= {3, 5, 6, 7, 8, 9, 10, 11, 11, 12, 13};					// the second 11 represents the "arrow" value on the dive table
		final int[] MINUTES_D130	= {3, 5, 6, 7, 7, 8, 9, 10};								// the second  7 represents the "arrow" value on the dive table
		final int[] MINUTES_D140	= {4, 4, 5, 6, 7, 8};										// the first   4 represents the "arrow" value on the dive table
																								// THESE ARE JUST TEMP FOR THE => SYMBOL (OR NOT)
		// depth chart	(NEED TO ADD NITROGEN LEVELS)
		this.depthTimes = new LinkedHashMap<Integer, int[]>();
		this.depthTimes.put(DEPTHS[0], MINUTES_D35);
		this.depthTimes.put(DEPTHS[1], MINUTES_D40);
		this.depthTimes.put(DEPTHS[2], MINUTES_D50);
		this.depthTimes.put(DEPTHS[3], MINUTES_D60);
		this.depthTimes.put(DEPTHS[4], MINUTES_D70);
		this.depthTimes.put(DEPTHS[5], MINUTES_D80);
		this.depthTimes.put(DEPTHS[6], MINUTES_D90);
		this.depthTimes.put(DEPTHS[7], MINUTES_D100);
		this.depthTimes.put(DEPTHS[8], MINUTES_D110);
		this.depthTimes.put(DEPTHS[9], MINUTES_D120);
		this.depthTimes.put(DEPTHS[10], MINUTES_D130);
		this.depthTimes.put(DEPTHS[11], MINUTES_D140);
		
		
		// depth to pressure group calculations (SHOULD BE IN ITS OWN METHOD)
		int searchDepth = diveDepth;		// feet
		int searchTimeAtDepth = timeUnder;	// minutes
		this.depth = DEPTHS[this.binarySearch(DEPTHS, searchDepth)];
		int[] validTimesAtDepth = this.depthTimes.get(this.depth);
		int time_index = this.binarySearch(validTimesAtDepth, searchTimeAtDepth);
		this.bottomTime = validTimesAtDepth[time_index];
		this.pressureGroup = this.getPressureGroupChar(time_index);
	
		
		// surface interval chart	
		// the list of surface intervals goes backwards from highest times to lowest times
		final double[] MINUTES_SA = {3.00};
		final double[] MINUTES_SB = {3.48, 0.47};
		final double[] MINUTES_SC = {4.10, 1.09, 0.21};
		final double[] MINUTES_SD = {4.19, 1.18, 0.30, 0.08};
		final double[] MINUTES_SE = {4.28, 1.27, 0.38, 0.16, 0.07};
		final double[] MINUTES_SF = {4.35, 1.34, 0.46, 0.24, 0.15, 0.07};
		final double[] MINUTES_SG = {4.42, 1.41, 0.53, 0.31, 0.22, 0.13, 0.06};
		final double[] MINUTES_SH = {4.48, 1.47, 0.59, 0.37, 0.28, 0.20, 0.12, 0.05};
		final double[] MINUTES_SI = {4.54, 1.53, 1.05, 0.43, 0.34, 0.26, 0.18, 0.11, 0.05};
		final double[] MINUTES_SJ = {5.00, 1.59, 1.11, 0.49, 0.40, 0.31, 0.24, 0.17, 0.11, 0.05};
		final double[] MINUTES_SK = {5.05, 2.04, 1.16, 0.54, 0.45, 0.37, 0.29, 0.22, 0.16, 0.10, 0.04};
		final double[] MINUTES_SL = {5.10, 2.09, 1.21, 0.59, 0.50, 0.42, 0.34, 0.27, 0.21, 0.15, 0.09, 0.04};
		final double[] MINUTES_SM = {5.15, 2.14, 1.25, 1.04, 0.55, 0.46, 0.39, 0.32, 0.25, 0.19, 0.14, 0.09, 0.04};
		final double[] MINUTES_SN = {5.19, 2.18, 1.30, 1.08, 0.59, 0.51, 0.43, 0.36, 0.30, 0.24, 0.18, 0.13, 0.08, 0.03};
		final double[] MINUTES_SO = {5.24, 2.23, 1.34, 1.12, 1.03, 0.55, 0.47, 0.41, 0.34, 0.28, 0.23, 0.17, 0.12, 0.08, 0.03};
		final double[] MINUTES_SP = {5.28, 2.27, 1,38, 1.16, 1.07, 0.59, 0.51, 0.45, 0.38, 0.32, 0.27, 0.21, 0.16, 0.12, 0.07, 0.03};
		final double[] MINUTES_SQ = {5.31, 2.30, 1.42, 1.20, 1.11, 1.03, 0.55, 0.48, 0.42, 0.36, 0.30, 0.25, 0.20, 0.16, 0.11, 0.07, 0.03};
		final double[] MINUTES_SR = {5.35, 2.34, 1.46, 1.24, 1.15, 1.07, 0.59, 0.52, 0.46, 0.40, 0.34, 0.29, 0.24, 0.19, 0.15, 0.11, 0.07, 0.03};
		final double[] MINUTES_SS = {5.39, 2.38, 1.49, 1.27, 1.18, 1.10, 1.03, 0.56, 0.49, 0.43, 0.38, 0.32, 0.27, 0.23, 0.18, 0.14, 0.10, 0.06, 0.03};
		final double[] MINUTES_ST = {5.42, 2.41, 1.53, 1.31, 1.22, 1.13, 1.06, 0.59, 0.53, 0.47, 0.41, 0.36, 0.31, 0.26, 0.22, 0.17, 0.13, 0.10, 0.06, 0.02};
		final double[] MINUTES_SU = {5.45, 2.44, 1.56, 1.34, 1.25, 1.17, 1.09, 1.02, 0.56, 0.50, 0.44, 0.39, 0.34, 0.29, 0.25, 0.21, 0.17, 0.13, 0.09, 0.04, 0.02};
		final double[] MINUTES_SV = {5.48, 2.47, 1.59, 1.37, 1.28, 1.20, 1.12, 1.05, 0.59, 0.53, 0.47, 0.42, 0.37, 0.33, 0.28, 0.24, 0.20, 0.16, 0.12, 0.09, 0.05, 0.02};
		final double[] MINUTES_SW = {5.51, 2.50, 2.02, 1.40, 1.31, 1.23, 1.15, 1.08, 1.02, 0.56, 0.50, 0.45, 0.40, 0.36, 0.31, 0.27, 0.23, 0.19, 0.15, 0.12, 0.08, 0.05, 0.02};
		final double[] MINUTES_SX = {5.54, 2.53, 2.05, 1.43, 1.34, 1.26, 1.18, 1.11, 1.05, 0.59, 0.53, 0.48, 0.43, 0.39, 0.34, 0.30, 0.26, 0.22, 0.18, 0.15, 0.11, 0.08, 0.05, 0.02};
		final double[] MINUTES_SY = {5.57, 2.56, 2.08, 1.46, 1.37, 1.29, 1.21, 1.14, 1.08, 1.02, 0.56, 0.51, 0.46, 0.41, 0.37, 0.33, 0.29, 0.25, 0.21, 0.18, 0.14, 0.11, 0.08, 0.05, 0.02};
		final double[] MINUTES_SZ = {6.00, 2.59, 2.11, 1.49, 1.40, 1.31, 1.24, 1.17, 1.11, 1.05, 0.59, 0.54, 0.49, 0.44, 0.40, 0.35, 0.31, 0.28, 0.24, 0.20, 0.17, 0.14, 0.11, 0.08, 0.05, 0.02};

		
		this.surfaceIntervalTimes = new LinkedHashMap<Character, double[]>();
		this.surfaceIntervalTimes.put('A', MINUTES_SA);
		this.surfaceIntervalTimes.put('B', MINUTES_SB);
		this.surfaceIntervalTimes.put('C', MINUTES_SC);
		this.surfaceIntervalTimes.put('D', MINUTES_SD);
		this.surfaceIntervalTimes.put('E', MINUTES_SE);
		this.surfaceIntervalTimes.put('F', MINUTES_SF);
		this.surfaceIntervalTimes.put('G', MINUTES_SG);
		this.surfaceIntervalTimes.put('H', MINUTES_SH);
		this.surfaceIntervalTimes.put('I', MINUTES_SI);
		this.surfaceIntervalTimes.put('J', MINUTES_SJ);
		this.surfaceIntervalTimes.put('K', MINUTES_SK);
		this.surfaceIntervalTimes.put('L', MINUTES_SL);
		this.surfaceIntervalTimes.put('M', MINUTES_SM);
		this.surfaceIntervalTimes.put('N', MINUTES_SN);
		this.surfaceIntervalTimes.put('O', MINUTES_SO);
		this.surfaceIntervalTimes.put('P', MINUTES_SP);
		this.surfaceIntervalTimes.put('Q', MINUTES_SQ);
		this.surfaceIntervalTimes.put('R', MINUTES_SR);
		this.surfaceIntervalTimes.put('S', MINUTES_SS);
		this.surfaceIntervalTimes.put('T', MINUTES_ST);
		this.surfaceIntervalTimes.put('U', MINUTES_SU);
		this.surfaceIntervalTimes.put('V', MINUTES_SV);
		this.surfaceIntervalTimes.put('W', MINUTES_SW);
		this.surfaceIntervalTimes.put('X', MINUTES_SX);
		this.surfaceIntervalTimes.put('Y', MINUTES_SY);
		this.surfaceIntervalTimes.put('Z', MINUTES_SZ);

		// pressure group to surface interval calculations (SHOULD BE IN IT'S OWN METHOD)
		double[] pressureGroupSurfaceIntervalTimes = this.surfaceIntervalTimes.get(this.getPressureGroup());		
		this.surfaceInterval = this.binarySearch(pressureGroupSurfaceIntervalTimes, surfaceIntervalInput);
		this.surfaceInterval = pressureGroupSurfaceIntervalTimes[this.binarySearch(pressureGroupSurfaceIntervalTimes, surfaceIntervalInput)];
		
		
		// surface interval to next pressure group calculations (SHOULD BE IN IT'S OWN METHOD)
		this.pressureGroup_SI = this.getPressureGroupChar(this.binarySearch(pressureGroupSurfaceIntervalTimes, surfaceIntervalInput));
		

		
		
		
		// decompression (NOT YET IMPLEMENTED)
		this.decompressionStop = false;
		
		
		
		
		
	}
	
	/**
	 * retrieve results from dive table calculations
	 * @return
	 */
	public String getResults() {
		return "Inputed Depth (ft):\t\t\t" + this.getDepthInput() + "\n" +
			   "Depth (ft):\t\t\t\t" + this.getDepth() + "\n" +
			   "Inputed Buttom Time (min):\t\t" + this.getBottomTimeInput() + "\n" +
			   "Buttom Time (min):\t\t\t" + this.getBottomTime()+ "\n" +
			   "Pressure Group:\t\t\t\t" + this.getPressureGroup() + "\n" +
			   "Inputed Surface Interval (min):\t\t" + this.surfaceIntervalInput + "\n" +
			   "Surface Interval (min):\t\t\t" + this.getSurfaceInterval() + "\n" + 					
			   "Pressure Group After Surface Interval:\t" + this.getPressureGroup_SI() + "\n" + 
			   "Residual Nitrogen:\t\t\t" + "NOT YET IMPLEMENTED" + "\n" +							// temp value. THIS NEEDS TO BE CALCULATED
			   "Require Decompression:\t\t\t" + "NOT YET IMPLEMENTED" + "\n";						// temp value. THIS NEEDS TO BE CALCULATED  
	}
	
	/**
	 * get the depth
	 * @return
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * get the depth that was inputed by the user
	 * @return
	 */
	public int getDepthInput() {
		return this.depthInput;
	}
	
	/**
	 * get the bottom time (time under water)
	 * @return
	 */
	public int getBottomTime() {
		return this.bottomTime;
	}
	
	/**
	 * get the bottom time inputed by the user
	 * @return
	 */
	public int getBottomTimeInput() {
		return this.bottomTimeInput;
	}
	 
	
	/**
	 * get the surface interval
	 * @return
	 */
	public double getSurfaceInterval() {
		return this.surfaceInterval;
	}
	
	/**
	 * get the surface interval inputed by the user
	 * @return
	 */
	public double getSurfaceIntervalInput() {
		return this.surfaceIntervalInput;
	}

	
	/**
	 * get the pressure group associated with the depth and time under
	 * @return
	 */
	public char getPressureGroup() {
		return this.pressureGroup;
	}
	
	/**
	 * get the pressure group after the surface interval
	 * @return
	 */
	public char getPressureGroup_SI() {
		return this.pressureGroup_SI;
	}

	
	/**
	 * do a binary search on the given list of type int
	 * @param timeList
	 * @param searchItem
	 * @return
	 */
	private int binarySearch(int[] timeList, int searchItem) {		
		int left = 0;
		int right = timeList.length-1;
		int mid = 0;
		while (left <= right) {
			mid = (left + right)/2;					// find mid of array
			
			if (searchItem > timeList[mid]) { 		// item is larger than mid. move search to the right of array
				left = mid + 1;
			} else if (searchItem < timeList[mid]){	// item is less than mid. move search to the left of array
				right =  mid - 1;
			} else {								// searchItem found
				return mid;
			}
		}
		
		if (right+1 > timeList.length-1) {			// compensate for end of list
			return right;							// item not found but but at the end of the list. this means that this is the limit it can go up to
		} else {
			return right + 1;						// item not found but NOT YET at the end of the list. this means that searchItem must be rounded up
		}
	}
	
	/**
	 * this is a type double version of binarySearch (ONLY USE FOR SURFACE INTERVALS)
	 * @param timeList
	 * @param searchItem
	 * @return
	 */
	private int binarySearch(double[] timeList, double searchItem) {		
		int left = 0;
		int right = timeList.length-1;
		int mid = 0;
		while (left <= right) {
			mid = (left + right)/2;										// find mid of array
			
			if (Double.compare(searchItem, timeList[mid]) < 0) { 		// item is smaller than mid. move search to the right of array
				left = mid + 1;
			} else if (Double.compare(searchItem,timeList[mid]) > 0){	// item is greater than mid. move search to the left of array
				right =  mid - 1;
			} else {													// searchItem found
				return mid;
			}
		}
		
		if ((left-1 > timeList.length-1) || (left == 0)) {				// compensate for end of list
			return left;												// item not found but but at the end of the list. this means that this is the limit it can go up to
		} else {
			return left - 1;											// item not found but NOT YET at the end of the list. this means that searchItem must be rounded up
		}
	}
	
	/**
	 * determine pressure group from the given index of the time
	 * @param arrayIndex
	 * @return
	 */
	private char getPressureGroupChar (int arrayIndex) {
		final char[] ALPHA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V','W', 'X', 'Y', 'Z'};

		// TEMP ERROR CHECK. CALLING METHOD NEEDS TO CATCH ERROR
		if ((arrayIndex < 0) || arrayIndex > ALPHA.length-1) {	
			throw new IllegalArgumentException("ERROR: Array index needs to be between 0 and " + (ALPHA.length-1));	
		} else {
			return ALPHA[arrayIndex];	// return 
		}
	}
	
	/**
	 * checks to see if a decompression stop is required
	 * @param depth
	 * @param pressureGroup
	 * @return
	 */
	public boolean requireDecompressionStop(int depth, char pressureGroup) {
		// NOT YET IMPLEMENTED
		return false;	// temp
	}
	
	/**
	 * convert feet to meter
	 * @return
	 */
	public int unitConverter() {
		// NOT YET IMPLEMENTED
		return 0;	// temp
	}
	
	// dive table
	public static void main(String[] args) {
		int depth = 49;
		int time = 42;
		double surface = 0.41;		// will change to a float if needed
		DiveTable dt = new DiveTable(depth, time, surface);
		System.out.println("<<INCOMPLETE DIVE TABLE CLASS>>");
		System.out.println(dt.getResults());
	}
}
