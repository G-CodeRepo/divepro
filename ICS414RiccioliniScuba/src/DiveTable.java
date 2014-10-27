/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveTable class calculates the dive plan
 * @author Gerald Abut, Kavika Tavui, Victor Silva
 */


// SAVING CONCURRENT DIVE PLANS NOT YET IMPLEMENTED (MOST LIKING IN A SEPARATE CLASS). THIS DIVE TABLE CURRENTLY ONLY WORKS FOR A SINGLE DIVE.
// THIS CLASS NEEDS TESTING

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DiveTable {
	private LinkedHashMap<Integer, int[]> times;
	// int[] depths;
	private int depth;
	private int bottomTime;
	private char pressureGroup;
	private double surfaceInterval;
	private boolean decompression;
		
	// Constructor
	public DiveTable(int diveDepth, int timeUnder, double surfaceInterval) {	
			
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
		
		// depth chart	(NEED TO ADD NITROGEN LEVELS)
		this.times = new LinkedHashMap<Integer, int[]>();
		this.times.put(DEPTHS[0], MINUTES_D35);
		this.times.put(DEPTHS[1], MINUTES_D40);
		this.times.put(DEPTHS[2], MINUTES_D50);
		this.times.put(DEPTHS[3], MINUTES_D60);
		this.times.put(DEPTHS[4], MINUTES_D70);
		this.times.put(DEPTHS[5], MINUTES_D80);
		this.times.put(DEPTHS[6], MINUTES_D90);
		this.times.put(DEPTHS[7], MINUTES_D100);
		this.times.put(DEPTHS[8], MINUTES_D110);
		this.times.put(DEPTHS[9], MINUTES_D120);
		this.times.put(DEPTHS[10], MINUTES_D130);
		this.times.put(DEPTHS[11], MINUTES_D140);
		
		
		// calculations
		int searchDepth = diveDepth;		// feet
		int searchTimeAtDepth = timeUnder;	// minutes
		this.depth = DEPTHS[this.binarySearch(DEPTHS, searchDepth)];
		int[] validTimesAtDepth = this.times.get(this.depth);
		int time_index = this.binarySearch(validTimesAtDepth, searchTimeAtDepth);
		this.bottomTime = validTimesAtDepth[time_index];
		this.pressureGroup = this.calcPressureGroup(time_index);
	
		
		// surface interval chart (NOT YET IMPLEMENTED)
		


		
		
		
		
		
		
		
		// decompression (NOT YET IMPLEMENTED)
		this.decompression = false;
		
		
		
		
		
	}
	
	/**
	 * @return
	 */
	public String getResults() {
		return "Depth (ft):\t\t" + this.getDepth() + "\n" +
			   "Time Under (min):\t" + this.getTimeUnder()+ "\n" +
			   "Pressure Group:\t\t" + this.getPressureGroup() + "\n" + 
			   "Surface Interval (min):\t" + "NOT YET IMPLEMENTED" + "\n" + 	// temp value. THIS NEEDS TO BE CALCULATED
			   "Require Decompression:\t" + "NOT YET IMPLEMENTED" + "\n";		// temp value. THIS NEEDS TO BE CALCULATED
			   
	}
	
	/**
	 * get the depth
	 * @return
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * get the time under water
	 * @return
	 */
	public int getTimeUnder() {
		return this.bottomTime;
	}
	
	/**
	 * get the pressure group associated with the depth and time under
	 * @return
	 */
	public char getPressureGroup() {
		return this.pressureGroup;
	}

	/**
	 * do a binary search on the given list
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
		
		if (right+1 > timeList.length-1) {	// compensate for end of list
			return right;		// item not found but but at the end of the list. this means that this is the limit it can go up to
		} else {
			return right + 1;	// item not found but NOT YET at the end of the list. this means that searchItem must be rounded up
		}
	}
	
	/**
	 * determine pressure group from the given index of the time
	 * @param ArrayIndex
	 * @return
	 */
	private char calcPressureGroup (int ArrayIndex) {
		char pg = ' ';
		
		switch (ArrayIndex) {
		case 0:			// note array index starts at zero
			pg = 'A';
			break;
		case 1:
			pg = 'B';
			break;
		case 2:
			pg = 'C';
			break;
		case 3:
			pg = 'D';
			break;
		case 4:
			pg = 'E';
			break;
		case 5:
			pg = 'F';
			break;
		case 6:
			pg = 'G';
			break;
		case 7:
			pg = 'H';
			break;
		case 8:
			pg = 'I';
			break;
		case 9:
			pg = 'J';
			break;
		case 10:
			pg = 'K';
			break;
		case 11:
			pg = 'L';
			break;
		case 12:
			pg = 'M';
			break;
		case 13:
			pg = 'N';
			break;
		case 14:
			pg = 'O';
			break;
		case 15:
			pg = 'P';
			break;
		case 16:
			pg = 'Q';
			break;
		case 17:
			pg = 'R';
			break;
		case 18:
			pg = 'S';
			break;
		case 19:
			pg = 'T';
			break;
		case 20:
			pg = 'U';
			break;
		case 21:
			pg = 'V';
			break;
		case 22:
			pg = 'W';
			break;
		case 23:
			pg = 'X';
			break;
		case 24:
			pg = 'Y';
			break;
		case 25:		
			pg = 'Z';
			break;
		default:
			System.err.println("ERROR: Should no be here."); // temp
			break;
		}
		return pg; 
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
		int depth = 50;
		int time = 10;
		double surface = 1.30;		// will change to a float if needed
		DiveTable dt = new DiveTable(depth, time, surface);
		System.out.println("INCOMPLETE DIVE TABLE CLASS");
		System.out.println(dt.getResults());
	}
}
