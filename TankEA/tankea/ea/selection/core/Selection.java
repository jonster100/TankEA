package tankea.ea.selection.core;

import tankea.entities.Tank;
import java.util.LinkedList;

public interface Selection {

	/**
	 * This method will setup all the tanks in the selection algorithm for parent tanks to be chosen.
	 * @param tempTankList the population of tanks that the parents wil be chosen from*/
	void setupTanksInList(LinkedList<Tank> tempTankList);

	/**
	 * This method will select parent tanks from the population.
	 * @return Tank[] returning an array of parent tanks*/
	public Tank[] selectParentTanks();

}
