/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveTable class is used to create an instance of a dive table
 * @author Gerald Abut
 */

import java.util.LinkedHashMap;


public class DiveTable {
	private LinkedHashMap<Integer, int[]> depthTimes;
	private LinkedHashMap<Character, double[]> surfaceIntervalTimes;
	private LinkedHashMap<Integer, int[]> residualNitrogen;
	private int[] validDepths;
	private char[] pressureGroups;

	public DiveTable() {
		// valid depths
		final int[] DEPTHS = {35, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140};
		final char[] ALPHA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V','W', 'X', 'Y', 'Z'};
		this.validDepths = DEPTHS;
		this.pressureGroups = ALPHA;
		
		// times for each depth
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
		// depth table
		this.depthTimes = new LinkedHashMap<Integer, int[]>();
		this.depthTimes.put(this.validDepths[0], MINUTES_D35);	
		this.depthTimes.put(this.validDepths[1], MINUTES_D40);
		this.depthTimes.put(this.validDepths[2], MINUTES_D50);
		this.depthTimes.put(this.validDepths[3], MINUTES_D60);
		this.depthTimes.put(this.validDepths[4], MINUTES_D70);
		this.depthTimes.put(this.validDepths[5], MINUTES_D80);
		this.depthTimes.put(this.validDepths[6], MINUTES_D90);
		this.depthTimes.put(this.validDepths[7], MINUTES_D100);
		this.depthTimes.put(this.validDepths[8], MINUTES_D110);
		this.depthTimes.put(this.validDepths[9], MINUTES_D120);
		this.depthTimes.put(this.validDepths[10], MINUTES_D130);
		this.depthTimes.put(this.validDepths[11], MINUTES_D140);
		
		// residual nitrogen for each depth
		final int[] NITROGEN_D35 	= {195, 186, 180, 176, 173, 169, 165, 161, 157, 153, 148, 143, 138, 132, 126, 120, 113, 105, 97, 88, 78, 66, 53, 37, 17};
		final int[] NITROGEN_D40 	= {131, 124, 118, 115, 113, 109, 106, 103, 100, 96, 92, 89, 85, 80, 76, 71, 66, 61, 55, 49, 43, 36, 29, 20, 11};
		final int[] NITROGEN_D50 	= {73, 67, 63, 61, 59, 56, 54, 52, 49, 47, 44, 42, 39, 36, 33, 30, 27, 23, 20, 17, 13, 9, 5};
		final int[] NITROGEN_D60 	= {49, 44, 41, 39, 38, 36, 34, 32, 30, 28, 26, 24, 22, 20, 18, 16, 13, 11, 8, 6, 3, 1};
		final int[] NITROGEN_D70 	= {35, 31, 28, 27, 25, 24, 22, 21, 19, 18, 16, 14, 13, 11, 9, 7, 6, 4, 2};
		final int[] NITROGEN_D80 	= {26, 22, 20, 19, 17, 16, 15, 13, 12, 11, 9, 8, 7, 5, 4, 2};
		final int[] NITROGEN_D90 	= {21, 18, 16, 15, 14, 13, 12, 10, 9, 8, 7, 6, 4, 3, 2};
		final int[] NITROGEN_D100 	= {17, 14, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
		final int[] NITROGEN_D110	= {13, 10, 9, 8, 7, 6, 5, 4, 3, 2, 2};						// the 2 twos are correct
		final int[] NITROGEN_D120	= {10, 8, 7, 6, 5, 4, 3, 2};
		final int[] NITROGEN_D130	= {7, 5, 4, 3};												// there is no depth 140 for residual nitrogen
		
		// residual nitrogen table
		this.residualNitrogen = new LinkedHashMap<Integer, int[]>();
		this.residualNitrogen.put(this.validDepths[0], NITROGEN_D35);
		this.residualNitrogen.put(this.validDepths[1], NITROGEN_D40);
		this.residualNitrogen.put(this.validDepths[2], NITROGEN_D50);
		this.residualNitrogen.put(this.validDepths[3], NITROGEN_D60);
		this.residualNitrogen.put(this.validDepths[4], NITROGEN_D70);
		this.residualNitrogen.put(this.validDepths[5], NITROGEN_D80);
		this.residualNitrogen.put(this.validDepths[6], NITROGEN_D90);
		this.residualNitrogen.put(this.validDepths[7], NITROGEN_D100);
		this.residualNitrogen.put(this.validDepths[8], NITROGEN_D110);
		this.residualNitrogen.put(this.validDepths[9], NITROGEN_D120);
		this.residualNitrogen.put(this.validDepths[10], NITROGEN_D130);							// there is no depth 140 for residual nitrogen

		// surface interval table	
		// the list of surface intervals goes backwards from highest times to lowest times
		final double[] MINUTES_SA 	= {3.00};
		final double[] MINUTES_SB 	= {3.48, 0.47};
		final double[] MINUTES_SC 	= {4.10, 1.09, 0.21};
		final double[] MINUTES_SD 	= {4.19, 1.18, 0.30, 0.08};
		final double[] MINUTES_SE 	= {4.28, 1.27, 0.38, 0.16, 0.07};
		final double[] MINUTES_SF 	= {4.35, 1.34, 0.46, 0.24, 0.15, 0.07};
		final double[] MINUTES_SG 	= {4.42, 1.41, 0.53, 0.31, 0.22, 0.13, 0.06};
		final double[] MINUTES_SH 	= {4.48, 1.47, 0.59, 0.37, 0.28, 0.20, 0.12, 0.05};
		final double[] MINUTES_SI 	= {4.54, 1.53, 1.05, 0.43, 0.34, 0.26, 0.18, 0.11, 0.05};
		final double[] MINUTES_SJ 	= {5.00, 1.59, 1.11, 0.49, 0.40, 0.31, 0.24, 0.17, 0.11, 0.05};
		final double[] MINUTES_SK 	= {5.05, 2.04, 1.16, 0.54, 0.45, 0.37, 0.29, 0.22, 0.16, 0.10, 0.04};
		final double[] MINUTES_SL	= {5.10, 2.09, 1.21, 0.59, 0.50, 0.42, 0.34, 0.27, 0.21, 0.15, 0.09, 0.04};
		final double[] MINUTES_SM 	= {5.15, 2.14, 1.25, 1.04, 0.55, 0.46, 0.39, 0.32, 0.25, 0.19, 0.14, 0.09, 0.04};
		final double[] MINUTES_SN 	= {5.19, 2.18, 1.30, 1.08, 0.59, 0.51, 0.43, 0.36, 0.30, 0.24, 0.18, 0.13, 0.08, 0.03};
		final double[] MINUTES_SO 	= {5.24, 2.23, 1.34, 1.12, 1.03, 0.55, 0.47, 0.41, 0.34, 0.28, 0.23, 0.17, 0.12, 0.08, 0.03};
		final double[] MINUTES_SP 	= {5.28, 2.27, 1,38, 1.16, 1.07, 0.59, 0.51, 0.45, 0.38, 0.32, 0.27, 0.21, 0.16, 0.12, 0.07, 0.03};
		final double[] MINUTES_SQ 	= {5.31, 2.30, 1.42, 1.20, 1.11, 1.03, 0.55, 0.48, 0.42, 0.36, 0.30, 0.25, 0.20, 0.16, 0.11, 0.07, 0.03};
		final double[] MINUTES_SR 	= {5.35, 2.34, 1.46, 1.24, 1.15, 1.07, 0.59, 0.52, 0.46, 0.40, 0.34, 0.29, 0.24, 0.19, 0.15, 0.11, 0.07, 0.03};
		final double[] MINUTES_SS 	= {5.39, 2.38, 1.49, 1.27, 1.18, 1.10, 1.03, 0.56, 0.49, 0.43, 0.38, 0.32, 0.27, 0.23, 0.18, 0.14, 0.10, 0.06, 0.03};
		final double[] MINUTES_ST 	= {5.42, 2.41, 1.53, 1.31, 1.22, 1.13, 1.06, 0.59, 0.53, 0.47, 0.41, 0.36, 0.31, 0.26, 0.22, 0.17, 0.13, 0.10, 0.06, 0.02};
		final double[] MINUTES_SU 	= {5.45, 2.44, 1.56, 1.34, 1.25, 1.17, 1.09, 1.02, 0.56, 0.50, 0.44, 0.39, 0.34, 0.29, 0.25, 0.21, 0.17, 0.13, 0.09, 0.04, 0.02};
		final double[] MINUTES_SV 	= {5.48, 2.47, 1.59, 1.37, 1.28, 1.20, 1.12, 1.05, 0.59, 0.53, 0.47, 0.42, 0.37, 0.33, 0.28, 0.24, 0.20, 0.16, 0.12, 0.09, 0.05, 0.02};
		final double[] MINUTES_SW 	= {5.51, 2.50, 2.02, 1.40, 1.31, 1.23, 1.15, 1.08, 1.02, 0.56, 0.50, 0.45, 0.40, 0.36, 0.31, 0.27, 0.23, 0.19, 0.15, 0.12, 0.08, 0.05, 0.02};
		final double[] MINUTES_SX 	= {5.54, 2.53, 2.05, 1.43, 1.34, 1.26, 1.18, 1.11, 1.05, 0.59, 0.53, 0.48, 0.43, 0.39, 0.34, 0.30, 0.26, 0.22, 0.18, 0.15, 0.11, 0.08, 0.05, 0.02};
		final double[] MINUTES_SY 	= {5.57, 2.56, 2.08, 1.46, 1.37, 1.29, 1.21, 1.14, 1.08, 1.02, 0.56, 0.51, 0.46, 0.41, 0.37, 0.33, 0.29, 0.25, 0.21, 0.18, 0.14, 0.11, 0.08, 0.05, 0.02};
		final double[] MINUTES_SZ 	= {6.00, 2.59, 2.11, 1.49, 1.40, 1.31, 1.24, 1.17, 1.11, 1.05, 0.59, 0.54, 0.49, 0.44, 0.40, 0.35, 0.31, 0.28, 0.24, 0.20, 0.17, 0.14, 0.11, 0.08, 0.05, 0.02};

		
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
	}
	
	/**
	 * get the hashmap of depth times
	 * @return
	 */
	public LinkedHashMap<Integer, int[]> getDepthTimes() {
		return this.depthTimes;
	}
	
	/**
	 * get the hashmap of surface interval times
	 * @return
	 */
	public LinkedHashMap<Character, double[]> getSurfaceIntervalTimes() {
		return this.surfaceIntervalTimes;
	}
	
	/**
	 * get the hashmap of residual nitrogen
	 * @return
	 */
	public LinkedHashMap<Integer, int[]> getResidualNitrogen() {
		return this.residualNitrogen;
	}
	
	/**
	 * get the hashmap of valid depths
	 * @return
	 */
	public int[] getValidDepths() {
		return this.validDepths;
	}
	
	/**
	 * determine pressure group from the given index of the time
	 * @param arrayIndex
	 * @return
	 */
	public char getPressureGroup (int arrayIndex) {

		// TEMP ERROR CHECK. CALLING METHOD NEEDS TO CATCH ERROR
		if ((arrayIndex < 0) || arrayIndex > this.pressureGroups.length-1) {	
			throw new IllegalArgumentException("ERROR: Array index needs to be between 0 and " + (this.pressureGroups.length-1));	
		} else {
			return this.pressureGroups[arrayIndex];	// return 
		}
	}
	
	/**
	 * returns a list of valid pressure groups
	 * @return
	 */
	public char[] getValidPressureGroups() {
		return this.pressureGroups;
	}
	
	/**
	 * 
	 * @param pressureGroup
	 * @return
	 */
	public int getIndexOfPressureGroup(char pressureGroup) {
		int index = 0;
		switch (pressureGroup) { // note array index starts at zero
		case 'A':	index = 0;		break;
		case 'B': 	index = 1;		break;
		case 'C':	index = 2;		break;
		case 'D':	index = 3;		break;
		case 'E':	index = 4;		break;
		case 'F':	index = 5;		break;
		case 'G':	index = 6;		break;
		case 'H':	index = 7;		break;
		case 'I':	index = 8;		break;
		case 'J':	index = 9;		break;
		case 'K':	index = 10;		break;
		case 'L':	index = 11;		break;
		case 'M':	index = 12;		break;
		case 'N':	index = 13;		break;
		case 'O':	index = 14;		break;
		case 'P':	index = 15;		break;
		case 'Q':	index = 16;		break;
		case 'R':	index = 17;		break;
		case 'S':	index = 18;		break;
		case 'T':	index = 19;		break;
		case 'U':	index = 20;		break;
		case 'V':	index = 21;		break;
		case 'W':	index = 22;		break;
		case 'X':	index = 23;		break;
		case 'Y':	index = 24;		break;
		case 'Z':	index = 26;		break;
		default:	
				System.err.println("ERROR: Should no be here.");		// temp error
				break;
		}
		return index;
	}
	
	/**
	 * requireDecompressionStop determines if the given depth (in feet) and time (in minutes) requires a decompression stop.
	 * this assumes that the caller already adjusted the depth and time.
	 * @param depth
	 * @param time
	 * @return
	 */
	public boolean requireDecompressionStop(int depth, int time) {
		if (((depth ==  35) && (time >= 152)) ||
			((depth ==  40) && (time >= 111)) ||
			((depth ==  50) && (time >=  67)) ||
			((depth ==  60) && (time >=  49)) ||
			((depth ==  70) && (time >=  35)) ||
			((depth ==  80) && (time >=  26)) ||
			((depth ==  90) && (time >=  22)) ||
			((depth >= 100))) {
			return true;
		} else {
			return false;
		}
	}
}
