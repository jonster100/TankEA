package tankea.ranking;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import tankea.entities.Tank;
import tankea.world.core.Situation;

public class BestSituation {

	public Situation getBestSituation(LinkedList<Situation> sit) {
		Situation returnSit = null;
		for (Situation s : sit) {
			if (returnSit != null) {
				returnSit = (s.getTotalPower() > returnSit.getTotalPower()) ? s : returnSit;
			} else {
				returnSit = s;
			}
		}
		return returnSit;
	}
}
