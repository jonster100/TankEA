package tankea.ea.mutagen.core;
import tankea.entities.Tank;
public interface Mutagen {
	
	/**
	 * This method will randomly mutate one piece of data from the tank.
	 * @param t1 this tanks data is what will be mutated in the method.*/
	public void mutateData(Tank t1);
}
