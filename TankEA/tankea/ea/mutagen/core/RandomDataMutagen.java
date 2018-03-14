
package tankea.ea.mutagen.core;

import tankea.entities.Tank;
import java.util.Random;

public class RandomDataMutagen implements Mutagen {
	
	/**
	 * This method will randomly mutate one piece of data from the tank.
	 * @param t1 this tanks data is what will be mutated in the method.*/
	@Override
	public void mutateData(Tank t1) {
		Random noGen = new Random();
		int chosenData = noGen.nextInt(3);
		switch (chosenData) {
		case 0:
			int newData = (int) (t1.getAttackPower() * 0.2);
			t1.decreaseAttackPower(newData);
			break;
		case 1:
			int newData1 = (int) (t1.getSensorRange() * 0.2);
			t1.decreaseSensorRange(newData1);
			break;
		case 2:
			int newData2 = (int) (t1.getAmmoSupply() * 0.2);
			t1.decreaseAmmoSupply(newData2);
			break;
		case 3:
			int newData3 = (int) (t1.getArmour() * 0.2);
			t1.decreaseArmour(newData3);
			break;
		}
	}
}
