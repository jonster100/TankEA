package tankea.ranking;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import tankea.world.core.Situation;

public class BestSituation {

	public Situation getBestSituation(LinkedList<Situation> sit) {
		Situation returnSit=null;
		//try{
		if (sit.isEmpty() != true) {
			Situation topSit = sit.getLast();
			for (Situation t : sit) {
				int tempScoreTop = 0;
				int tempScoreCurrent = 0;
				tempScoreCurrent += (t.getAttackPower() > topSit.getAttackPower()) ? 1 : 0;
				tempScoreCurrent += (t.getSensorRange() > topSit.getSensorRange()) ? 1 : 0;
				tempScoreCurrent += (t.getArmour() > topSit.getArmour()) ? 1 : 0;
				tempScoreCurrent += (t.getAmmoSupply() > topSit.getAmmoSupply()) ? 1 : 0;
				tempScoreTop += (t.getAttackPower() < topSit.getAttackPower()) ? 1 : 0;
				tempScoreTop += (t.getSensorRange() < topSit.getSensorRange()) ? 1 : 0;
				tempScoreTop += (t.getArmour() < topSit.getArmour()) ? 1 : 0;
				tempScoreTop += (t.getAmmoSupply() < topSit.getAmmoSupply()) ? 1 : 0;
				if (tempScoreTop < tempScoreCurrent) {
					topSit = t;
				}
				returnSit = topSit;
			}
		}
		else {
			returnSit = new Situation();
		}
		/*}catch(NoSuchElementException e){
			returnSit = new Situation();
		}*/
		return returnSit;
	}
}
