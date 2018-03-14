package tankea.ea.selection.core;

import tankea.entities.Tank;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.PriorityQueue;

public class TournamentSelection implements Selection {
	private Tank[] tankList;

	public class tankComparator implements Comparator<Tank> {
		/**
		 * @param x,y
		 *            Identifiers for the 'Tank' objects to be passed through
		 *            the method
		 * @return int Returns the output of a comparison between two 'Tank'
		 *         objects e.g 1,0,-1
		 **/
		@Override
		public int compare(Tank t1, Tank t2) {
			return (t1.getRoundTankDiedIn() < t2.getRoundTankDiedIn()) ? 1
					: (t1.getRoundTankDiedIn() > t2.getRoundTankDiedIn()) ? -1 : 0;
		}
	}

	/**
	 * TournamentSelection construction
	 * @param noTanks the amount of tanks that will be added to the array*/
	public TournamentSelection(int noTanks) {
		tankList = new Tank[noTanks];
	}

	/**
	 * This method will setup all the tanks in the selection algorithm for parent tanks to be chosen.
	 * @param tempTankList the population of tanks that the parents wil be chosen from*/
	@Override
	public void setupTanksInList(LinkedList<Tank> tempTankList) {
		int i = 0;
		for (Tank t : tempTankList) {
			tankList[i] = t;
			i++;
		}
	}

	/**
	 * This method will select parent tanks from the population.
	 * @return Tank[] returning an array of parent tanks*/
	@Override
	public Tank[] selectParentTanks() {
		int tankPop = tankList.length;
		System.out.println("\n" + tankPop);
		Random noGen = new Random();
		Tank[] tempTankList = new Tank[2];
		while (tempTankList[0] == null || tempTankList[1] == null) {
			PriorityQueue<Tank> tempTankOrderQueue;
			tempTankOrderQueue = new PriorityQueue<Tank>(tankPop, new tankComparator());
			for (int i = 0; i < (tankPop / 2); i++) {
				tempTankOrderQueue.offer(tankList[noGen.nextInt(tankPop)]);
			}
			if (tempTankList[0] == null) {
				tempTankList[0] = tempTankOrderQueue.poll();
			} else if (tempTankList[1] == null) {
				Tank tempTank = tempTankOrderQueue.poll();
				if (tempTank.equals(tempTankList[0])) {
				} else {
					tempTankList[1] = tempTank;
				}
			}
		}
		return tempTankList;
	}
}
