package divePlan;
/**
 * ICS 414 Team Ricciolini Scuba Project
 * Dive class is used to store individual dive data
 * @author Gerald Abut
 */

public class Dive {
	private int 	diveNumber;  
	private int 	depth;
	private char 	currPressureGroup;
	private char 	previousPressureGroupFromTBT;  
	private char 	newPressureGroupFromTBT; 
	private char 	newPressureGroupAfterPreviousDive;  
	private double 	surfaceInterval; 
	private double 	minSurfaceInterval;  
	private int 	adjustedNoDecompressionLimitTime; 
	private int 	residualNitrogenTime;  
	private int 	actualBottomTime; 
	private int 	totalBottomTime;
	private boolean decompressionStop;
	
	/**
	 * Constructor
	 * @param diveNumber
	 * @param depth
	 * @param previousPressureGroupTBT
	 * @param newPressureGroupAfterPreviousDive
	 * @param decompressionStop
	 * @param surfaceInterval
	 * @param minSurfaceInterval
	 * @param newPressureGroupFromTBT
	 * @param adjustedNoDecompressionLimitTime
	 * @param residualNitrogen
	 * @param actualBottomTime
	 * @param totalBottomTime
	 * @throws IllegalArgumentException
	 */
	public Dive(int diveNumber, 							int depth, 								char previousPressureGroupTBT, 		
				char newPressureGroupAfterPreviousDive ,	boolean decompressionStop,  			double surfaceInterval, 			
				double minSurfaceInterval,					char newPressureGroupFromTBT,			int adjustedNoDecompressionLimitTime, 
				int residualNitrogen,   					int actualBottomTime, 					int totalBottomTime) {	
		this.diveNumber 						= diveNumber;
		this.depth 								= depth;
		this.previousPressureGroupFromTBT 		= previousPressureGroupTBT;
		this.newPressureGroupFromTBT 			= newPressureGroupFromTBT;
		this.newPressureGroupAfterPreviousDive 	= newPressureGroupAfterPreviousDive;
		this.decompressionStop 					= decompressionStop;
		this.surfaceInterval 					= surfaceInterval;
		this.adjustedNoDecompressionLimitTime 	= adjustedNoDecompressionLimitTime;
		this.minSurfaceInterval 				= minSurfaceInterval;
		this.residualNitrogenTime 				= residualNitrogen;
		this.actualBottomTime 					= actualBottomTime;
		this.totalBottomTime 					= totalBottomTime;
	}
	
	/**
	 * get the dive number
	 * @return
	 */
	public int getDiveNumber() {
		return this.diveNumber;
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
	public char getNewPressureGroupAfterPreviousDive() {
		return this.newPressureGroupAfterPreviousDive;
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
	 * AKA: maximum allowable depth after a dive
	 * @return
	 */
	public int getAdjustedNoDecompressionLimitTime(){
		return this.adjustedNoDecompressionLimitTime;	
	}
	
	/**
	 * get the surface interval after the dive
	 * @return
	 */
	public float getSurfaceInterval() {
		return (float)this.surfaceInterval;
	}
	
	/**
	 * get the minimum surface interval time
	 * @return
	 */
	public float getMinSurfaceInterval() {
		return (float)this.minSurfaceInterval;
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
