package divePlan;
/**
 * ICS 414 Team Ricciolini Scuba Project
 * Dive class is used to store individual dive data
 * @author Gerald Abut
 */

public class Dive {
	private int depth;
	private char currPressureGroup;
	private char previousPressureGroupFromTBT;
	private char newPressureGroupFromTBT;
	private char newPressureGroupAfterLastDive;
	private double surfaceInterval;
	private int getAdjustedNoDecompressionLimitTime;
	private int residualNitrogenTime;
	private int actualBottomTime;
	private int totalBottomTime;
	private boolean decompressionStop;
	
	public Dive(int depth, 											
				char previousPressureGroupTBT, 				char newPressureGroupAfterLastDive ,	boolean decompressionStop,  				
				double surfaceInterval, 					char newPressureGroupFromTBT,			 
			    int getAdjustedNoDecompressionLimitTime,	int residualNitrogen,   				int actualBottomTime, 	
			    int totalBottomTime) {		
		this.depth = depth;
		this.previousPressureGroupFromTBT = previousPressureGroupTBT;
		this.newPressureGroupFromTBT = newPressureGroupFromTBT;
		this.newPressureGroupAfterLastDive = newPressureGroupAfterLastDive;
		this.decompressionStop = decompressionStop;
		this.surfaceInterval = surfaceInterval;
		this.getAdjustedNoDecompressionLimitTime = getAdjustedNoDecompressionLimitTime;
		this.residualNitrogenTime = residualNitrogen;
		this.actualBottomTime = actualBottomTime;
		this.totalBottomTime = totalBottomTime;
	}
	
	/**
	 * get the depth
	 * @return
	 */
	public int getDepth() {
		return this.depth;
	}
		
	/**
	 * get the pressure group
	 * @return
	 */
	public char getPressureGroup() {
		return this.currPressureGroup;
	}
	

	
	/**
	 * get the next pressure group based on the TBT
	 * @return
	 */
	public char getPreviousPressureGroupFromTBT() {
		return this.previousPressureGroupFromTBT;
	}
	
	public char getNewPressureGroupFromTBT() {
		return this.newPressureGroupFromTBT;
	}
	
	/**
	 * 
	 * @return
	 */
	public char getNewPressureGroupAfterLastDive() {
		return this.newPressureGroupAfterLastDive;
	}
	
	/**
	 * determine if this dive requires a decompression stop
	 * @return
	 */
	public boolean getDecompressionStop() {
		return this.decompressionStop;
	}
	

	
	/**
	 * get the adjusted no decompression limit (ANDL) time
	 * @return
	 */
	public int getAdjustedNoDecompressionLimitTime(){
		return this.getAdjustedNoDecompressionLimitTime;	
	}
	
	/**
	 * get the surface interval after the dive
	 * @return
	 */
	public double getsurfaceInterval() {
		return this.surfaceInterval;
	}
		
	/**
	 * get the residual nitrogen time (RNT) associated with the surface interval
	 * and the second dive
	 * @return
	 */
	public int getResidualNitrogenTime() {
		return this.residualNitrogenTime;
	}
	
	/**
	 * get the actual bottom time (ABT) associated with the surface interval
	 * and the second dive
	 * @return
	 */
	public int getActualBottomTime() {
		return this.actualBottomTime;
	}
	
	/**
	 * get the total bottom time (TBT) based when calculating 
	 * residual nitrogen time (RNT) + actual bottom time (ABT)
	 * @return
	 */
	public int getTotalBottomTime() {
		return this.totalBottomTime;
	}
}
