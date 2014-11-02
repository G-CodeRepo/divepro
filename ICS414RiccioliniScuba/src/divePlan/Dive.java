package divePlan;
/**
 * ICS 414 Team Ricciolini Scuba Project
 * Dive class is used to store individual dive data
 * @author Gerald Abut
 */

public class Dive {
	private int depth;
	private int bottomTime;
	private char pressureGroup;
	private char pressureGroup_SI;
	private double surfaceInterval;
	private int nextDiveDepth;
	private int residualNitrogenTime;
	private int actualBottomTime;
	private int totalBottomTime;
	private boolean decompressionStop;
	
	public Dive(int depth, 					int bottomTime, 		char pressureGroup, 	
				boolean decompressionStop,  double surfaceInterval, char pressureGroup_SI,
				int nextDiveDepth,			int residualNitrogen, 	int actualBottomTime, 	
				int totalBottomTime) {
		this.depth = depth;
		this.bottomTime = bottomTime;
		this.pressureGroup = pressureGroup;
		this.decompressionStop = decompressionStop;
		this.surfaceInterval = surfaceInterval;
		this.pressureGroup_SI = pressureGroup_SI;
		this.nextDiveDepth = nextDiveDepth;
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
	 * get the bottom time of the dive
	 * @return
	 */
	public int getBottomTime() {
		return this.bottomTime;
	}
	
	/**
	 * get the pressure group
	 * @return
	 */
	public char getPressureGroup() {
		return this.pressureGroup;
	}
	
	/**
	 * determine if this dive requires a decompression stop
	 * @return
	 */
	public boolean getDecompressionStop() {
		return this.decompressionStop;
	}
	
	/**
	 * get the pressure group associated with the surface interval
	 * @return
	 */
	public char getPressureGroup_SI() {
		return this.pressureGroup_SI;
	}
	
	/**
	 * get the next dive depth after the surface interval
	 * @return
	 */
	public int getNextDiveDepth(){
		return this.nextDiveDepth;
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
	public int getTotalBottomtime() {
		return this.totalBottomTime;
	}
}
