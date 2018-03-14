package tankea.ea.crossover.core;

import tankea.entities.Tank;

public class OnePointCrossover implements Crossover {

	/**
	 * This method is where any extra method calls within any classes that
	 * implement this interface should be called. The method will take data from
	 * parent tanks and produce a new tank using the some of the parents data.
	 * 
	 * @param t1 one of the parents that will be used for the crossover
	 * @param t2 one of the parents that will be used for the crossover
	 * @param maxNoOfTanksthe number of tanks on the parents team to help set the tanks ID
	 * @return Tank[] a list of tanks produced by the crossover
	 */
	@Override
	public Tank[] produceCrossover(Tank t1, Tank t2, int maxNoOFTanks) {
		Tank childTank = new Tank(t1.getTeam(), maxNoOFTanks + 1, t1.getAttackPower(), t1.getSensorRange(),
				t2.getAmmoSupply(), t2.getOriginalArmour());
		Tank[] tempList = new Tank[1];
		tempList[0] = childTank;
		return tempList;
	}
}
