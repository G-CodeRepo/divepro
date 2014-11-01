/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveProfile class is used to store dive profile data
 * @author Gerald Abut
 */

public class DiveProfile {
	private int depth;
	private int bottomTime;
	private char pressureGroup;
	private char pressureGroup_SI;
	private double surfaceInterval;
	private int residualNitrogen;
	private int nextDiveDepth;
	private boolean decompressionStop;
	
	public DiveProfile(int depth, int bottomTime, char pressureGroup, 
					  char pressureGroup_SI, double surfaceInterval, int residualNitrogen, 
					  int nextDiveDepth, boolean decompressionStop) {
		this.depth = depth;
		this.bottomTime = bottomTime;
		this.pressureGroup = pressureGroup;
		this.pressureGroup_SI = pressureGroup_SI;
		this.surfaceInterval = surfaceInterval;
		this.residualNitrogen = residualNitrogen;
		this.residualNitrogen =	nextDiveDepth;
		this.decompressionStop = decompressionStop;
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
	 * get the pressure group associated with the surface interval
	 * @return
	 */
	public char getPressureGroup_SI() {
		return this.pressureGroup_SI;
	}
	
	/**
	 * get the surface interval after the dive
	 * @return
	 */
	public double getsurfaceInterval() {
		return this.surfaceInterval;
	}
	
	/**
	 * get the residual nitrogen associated with the surface interval
	 * and the second dive
	 * @return
	 */
	public int getResidualNitrogen() {
		return this.residualNitrogen;
	}
	
	/**
	 * get the next dive depth after the surface interval
	 * @return
	 */
	public int getNextDiveDepth(){
		return this.nextDiveDepth;
	}
	
	/**
	 * determine if this dive requires a decompression stop
	 * @return
	 */
	public boolean getDecompressionStop() {
		return this.decompressionStop;
	}
}
