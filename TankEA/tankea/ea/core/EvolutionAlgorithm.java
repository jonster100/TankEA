package tankea.ea.core;

import tankea.entities.Tank;
import tankea.ea.crossover.core.*;
import tankea.ea.selection.core.*;
import tankea.ea.mutagen.core.*;
import java.util.LinkedList;

public class EvolutionAlgorithm {
	private Tank[] tankParentList;
	private Tank[] tankChildList;

	/**
	 * EvolutionAlgorithm Constructor
	 */
	public EvolutionAlgorithm(LinkedList<Tank> tempList) {
		this.selectionSetup(tempList);
		this.initiateCrossover(tempList.size());
		this.initiateMutagen();
	}

	/**
	 * This method will select the parents that will be used in the crossover.
	 * 
	 * @param tempList the list of tanks that the parents will be randomly picked from
	 */
	private void selectionSetup(LinkedList<Tank> tempList) {
		Selection selection = new TournamentSelection(tempList.size());
		selection.setupTanksInList(tempList);
		tankParentList = selection.selectParentTanks();
	}

	/**
	 * This method will take the parents and use there data to produce the
	 * children.
	 * 
	 * @param npTankOnTeam the total number of tanks on a team so that a new id can be
	 * made through the crossover
	 */
	private void initiateCrossover(int noTankOnTeam) {
		Crossover crossover = new OnePointCrossover();
		tankChildList = crossover.produceCrossover(tankParentList[0], tankParentList[1], noTankOnTeam);
	}

	/**
	 * This method will initiate a mutagen on a random piece of child data.
	 */
	private void initiateMutagen() {
		Mutagen mutagen = new RandomDataMutagen();
		for (int i = 0; i < tankChildList.length; i++) {
			mutagen.mutateData(tankChildList[i]);
		}
	}

	/**
	 * This method is used to get the children from the EvolutionAlgorithm.
	 * 
	 * @param Tank[] returns however many children were produced by the algorithm.
	 */
	public Tank[] getNewGeneration() {
		return tankChildList;
	}

	/**
	 * This method prints the data from the parents and the children of the
	 * algorithm.
	 */
	public void printEAStatus() {
		System.out.println("\n" + tankParentList[0].getTankData());
		System.out.println("\n" + tankParentList[1].getTankData());
		System.out.println("\n" + tankChildList[0].getTankData());
	}
}
