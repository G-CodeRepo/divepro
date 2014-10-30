/**
 * ICS 414 Team Ricciolini Scuba Project
 * Serach class is used for assorted searching algorithms
 * @author Gerald Abut
 */
public class Search {
	
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
}
