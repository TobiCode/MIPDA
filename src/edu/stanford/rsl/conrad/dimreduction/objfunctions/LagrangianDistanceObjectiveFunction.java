package edu.stanford.rsl.conrad.dimreduction.objfunctions;


import edu.stanford.rsl.conrad.dimreduction.DimensionalityReduction;
import edu.stanford.rsl.conrad.dimreduction.utils.HelperClass;
import edu.stanford.rsl.conrad.dimreduction.utils.PointCloudViewableOptimizableFunction;
import edu.stanford.rsl.jpop.GradientOptimizableFunction;

/*
 * Copyright (C) 2013-14  Susanne Westphal, Andreas Maier
 * CONRAD is developed as an Open Source project under the GNU General Public License (GPL).
 */
public class LagrangianDistanceObjectiveFunction extends
		PointCloudViewableOptimizableFunction implements
		GradientOptimizableFunction {

	private double[][] distanceMap;
	private DimensionalityReduction dimRed;

	
	/**
	 * Constructor of the LagrangianDistanceObjectiveFunction
	 */
	public LagrangianDistanceObjectiveFunction() {
		//nothing to see
	}

	/**
	 * sets the distance matrix
	 * 
	 * @param distances
	 */
	public void setDistances(double[][] distances) {
		distanceMap = distances;
	}

	@Override
	public void setNumberOfProcessingBlocks(int number) {

	}

	@Override
	public int getNumberOfProcessingBlocks() {
		return 1;
	}

	/**
	 * Lagrangian Distance Objective Function : L(\vec{x})=\sum_p \sum_q
	 * ||\vec{x}_p - \vec{x}_q ||_2 \cdot ( ||\vec{x}_p - \vec{x}_q ||_2 -
	 * d_{pq})
	 */
	public double evaluate(double[] x, int block) {

		if (!DimensionalityReduction.runConvexityTest) {
			updatePoints(x, dimRed);
		}
		double value = 0;
		for (int p = 0; p < distanceMap.length; p++) {
			for (int q = 0; q < distanceMap[0].length; q++) {
				double distance = HelperClass.distance2D(x, p, q);				
				value += (distance * -distanceMap[p][q]) + distance * distance;
			}
		}
		return value;
	}

	@Override
	public double[] gradient(double[] x, int block) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * function to set the DimensionalityReduction
	 */
	public void setOptimization(DimensionalityReduction dimRed){
		this.dimRed = dimRed;
	}

}
