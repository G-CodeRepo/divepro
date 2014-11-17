package jUnitTesting;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import divePlan.Dive;
import divePlan.DivePlanner;

public class DivePlannerJUnit {
	
	@Test
	/**
	 * Constructor 1 Test
	 */
	public void constructor_1_Test() {
		DivePlanner dp = new DivePlanner();
		Assert.assertNotSame(null, dp);					// an instance should not be null
		Assert.assertNotSame(new DivePlanner(), dp);	// each new instance should not be the same object
		Assert.assertSame(dp, dp);						// same instance should be the same
	}
	
	@Test
	/**
	 * Constructor 2 Test
	 */
	public void constructor_2_Test() {
		DivePlanner dp = new DivePlanner(50, 35, 1.00);
		Assert.assertNotSame(null, dp);								// an instance should not be null
		Assert.assertNotSame(new DivePlanner(50, 35, 1.00), dp);	// each new instance should not be the same object
		Assert.assertSame(dp, dp);									// same instance should be the same
	}
	
	@Test
	/**
	 * test the adjusted depth of a new dive
	 */
	public void depthTest() {
		DivePlanner			dp 				= null;
		LinkedList<Dive>	dives			= null;
		int 				adjustedDepth	= 0;
		
		// DEPTH 35 TEST
		adjustedDepth		= 35;
		dp					= new DivePlanner(5, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(10, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(15, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(20, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(25, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(30, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(35, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(36, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(37, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(38, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(39, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(40, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(41, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));

		// DEPTH 40 TEST
		adjustedDepth		= 40;
		dp					= new DivePlanner(36, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(37, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(38, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));

		dp					= new DivePlanner(39, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(40, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(41, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(42, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 50 TEST
		adjustedDepth		= 50;
		dp					= new DivePlanner(40, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(41, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(42, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(43, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));

		dp					= new DivePlanner(44, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(45, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(46, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(47, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(48, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(49, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(50, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(51, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(52, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 60 TEST
		adjustedDepth		= 60;
		dp					= new DivePlanner(50, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(51, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(52, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(53, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(54, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(55, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(56, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(57, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(58, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(59, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(60, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(61, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(62, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 70 TEST
		adjustedDepth		= 70;
		dp					= new DivePlanner(60, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(61, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(62, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(63, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(64, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(65, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(66, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(67, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(68, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(69, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(70, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(71, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(72, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 80 TEST
		adjustedDepth		= 80;
		dp					= new DivePlanner(70, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(71, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(72, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(73, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(74, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(75, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(76, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(77, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(78, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(79, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(80, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(81, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(82, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 90 TEST
		adjustedDepth		= 90;
		dp					= new DivePlanner(80, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(81, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(82, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(83, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(84, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(85, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(86, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(87, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(88, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(89, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(90, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(91, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(92, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 100 TEST
		adjustedDepth		= 100;
		dp					= new DivePlanner(90, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(91, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(92, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(93, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(94, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(95, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(96, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(97, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(98, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(99, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(100, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(101, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(102, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		// DEPTH 110
		adjustedDepth		= 110;
		dp					= new DivePlanner(100, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(101, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(102, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(103, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(104, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(105, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(106, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(107, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(108, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(109, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(110, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(111, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(112, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		
		// DEPTH 120
		adjustedDepth		= 120;
		dp					= new DivePlanner(110, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(111, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(112, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(113, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(114, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(115, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(116, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(117, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(118, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(119, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(120, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		try {
			dp				= new DivePlanner(121, 10, 1.00);// SPECIAL CASE WHERE USING THE INCORRECT BOTTOM TIME WILL NOT HAVE CORRECT INDEX FOR ANDL TIME
			dives			= dp.getDivePlan();
			Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (IndexOutOfBoundsException e) {
			// catch error but do nothing
		}
		
		try {
		dp					= new DivePlanner(121, 7, 1.00);// CORRECT ANDL BUT EXCEED DEPTH			
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (IndexOutOfBoundsException e) {
			// catch error but do nothing
		}
		
		// DEPTH 130 TEST
		adjustedDepth		= 130;
		dp					= new DivePlanner(120, 10, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(121, 7, 1.00);	// CHASES BELOW CANNOT EXCEED A BOTTOM TIME OF 7 MINUTES BECAUSE OF THE DEPTH
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(122, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(123, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(124, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(125, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(126, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(127, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(128, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(129, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		dp					= new DivePlanner(130, 7, 1.00);
		dives				= dp.getDivePlan();
		Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		try {
			dp				= new DivePlanner(131, 7, 1.00);// SPECIAL CASE WHERE USING THE INCORRECT DEPTH WILL NOT HAVE CORRECT INDEX FOR ANDL TIME
			dives			= dp.getDivePlan();
			Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		} 
		
		try {
			dp					= new DivePlanner(132, 7, 1.00);// SPECIAL CASE WHERE USING THE INCORRECT DEPTH WILL NOT HAVE CORRECT INDEX FOR ANDL TIME			
			dives				= dp.getDivePlan();
			Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		// DEPTH 140 TEST
		adjustedDepth		= 140;
		dp					= new DivePlanner(130, 7, 1.00);// ALL TEST CHASES BELOW CANNOT EXCEED A BOTTOM TIME OF 7 MINUTES BECAUSE OF THE DEPTH
		dives				= dp.getDivePlan();				// ALL TEST CASES BELOW DO NOT HAVE AN ANDL TIME
		Assert.assertNotEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		
		try {
			dp				= new DivePlanner(131, 7, 1.00);
			dives			= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		} 
		
		try {
			dp					= new DivePlanner(132, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp				= new DivePlanner(133, 7, 1.00);
			dives			= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		} 
		
		try {
			dp					= new DivePlanner(134, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp					= new DivePlanner(135, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp					= new DivePlanner(136, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp					= new DivePlanner(137, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp					= new DivePlanner(138, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp					= new DivePlanner(139, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}
		
		try {
			dp					= new DivePlanner(140, 7, 1.00);			
			dives				= dp.getDivePlan();
			Assert.assertEquals(adjustedDepth, dp.getDepth(dives.get(0)));
		} catch (NullPointerException e) {
			// catch error but do nothing
		}	
	}
	
	@Test
	public void bottomTimeTest() {
		// NOT YET IMPLEMENTED
		
		
		
		
	}
	
	@Test
	public void surfaceIntervalTest() {
		// NOT YET IMPLEMENTED
		
		
		
	}
}
