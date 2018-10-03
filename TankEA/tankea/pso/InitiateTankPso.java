package tankea.pso;

import java.util.LinkedList;
import tankea.entities.Tank;
import tankea.world.core.Situation;
import tankea.ranking.BestSituation;
import tankea.pso.Pso;

public class InitiateTankPso {
	private Tank movingTank;
	private int x;
	private int y;

	public InitiateTankPso(LinkedList<Tank> tempGTList, LinkedList<Tank> tempLTList, Tank tempTank) {
		BestSituation betSit = new BestSituation();
		Situation gSit = betSit.getBestSituation(this.makeSitList(tempGTList));
		Situation lSit = betSit.getBestSituation(this.makeSitList(tempLTList));//this will sometimes be null when there are no local tank situations to add to the PSO
		Situation pSit = betSit.getBestSituation(tempTank.getAllSituations());
		Pso pso = new Pso();
		if(lSit!=null){
			pso.input(pSit.getSitCoordinates().getX(), lSit.getSitCoordinates().getX(), gSit.getSitCoordinates().getX(),
					tempTank.getCoord().getX());
		}
		else {
			pso.input(pSit.getSitCoordinates().getX(), gSit.getSitCoordinates().getX(),
					tempTank.getCoord().getX());
		}
		x = pso.output();
		if(lSit!=null){
			pso.input(pSit.getSitCoordinates().getY(), lSit.getSitCoordinates().getY(), gSit.getSitCoordinates().getY(),
					tempTank.getCoord().getY());
		}
		else {
			pso.input(pSit.getSitCoordinates().getY(), gSit.getSitCoordinates().getY(),
					tempTank.getCoord().getY());
		}
		y = pso.output();
		
	}

	private LinkedList<Situation> makeSitList(LinkedList<Tank> linkedList) {
		LinkedList<Situation> tempSitList = new LinkedList<>();
		for (Tank t : linkedList) {
			tempSitList.add(t.getCurrentSituation());
		}
		return tempSitList;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
