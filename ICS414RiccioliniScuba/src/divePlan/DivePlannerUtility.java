package divePlan;
import java.util.LinkedHashMap;

/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveProfileUtility class is used for assorted searching and conversion algorithms
 * @author Gerald Abut
 */
public class DivePlannerUtility {
	
	// TEMP BINARY SEARCHES (WILL IMPLEMENT GENERICS SOON)
	
	/**
	 * do a binary search on the given list of type int
	 * @param timeList
	 * @param searchItem
	 * @return
	 */
	public static int binarySearch(int[] timeList, int searchItem) {		
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
	 * this is a type double version of binarySearch (ONLY USE FOR SURFACE INTERVALS SINCE IT USES DOUBLES AND REVERSE ARRAYS)
	 * @param timeList
	 * @param searchItem
	 * @return
	 */
	public static int binarySearch(double[] timeList, double searchItem) {		
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
	 * get the time index
	 * @param searchTimeAtDepth
	 * @param desiredDepth
	 * @return
	 */
	public static int getTimeIndex(int desiredDepth, int searchTimeAtDepth) {
		DiveTable diveTable = new DiveTable();
		LinkedHashMap<Integer, int[]> validDepthTimes = diveTable.getDepthTimes();
		return DivePlannerUtility.binarySearch(validDepthTimes.get(desiredDepth), searchTimeAtDepth);
	}
	
	/**
	 * convert meters to feet
	 * @return
	 */
	public int unitConverter(int meter) {
		// NOT YET IMPLEMENTED
		
		
		
		
		
		
		
		return 0;	// temp
	}
}
