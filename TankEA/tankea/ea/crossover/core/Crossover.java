package tankea.ea.crossover.core;

import tankea.entities.Tank;

public interface Crossover {
	/**
	 * This method is where any extra method calls within any classes that
	 * implement this interface should be called. The method will take data from
	 * parent tanks and produce a new tank using the some of the parents data.
	 * 
	 * @param t1 one of the parents that will be used for the crossover
	 * @param t2 one of the parents that will be used for the crossover
	 * @param maxNoOfTanks the number of tanks on the parents team to help set the tanks ID
	 * @return Tank[] a list of tanks produced by the crossover
	 */
	public Tank[] produceCrossover(Tank t1, Tank t2, int maxNoOFTanks);
}
