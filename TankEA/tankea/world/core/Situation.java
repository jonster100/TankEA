package tankea.world.core;

public class Situation {
	private int attackPower;
	private int sensorRange;
	private int ammoSupply;
	private int armour;
	private Coordinates sitCoordinates;

	public Situation(int aP, int sR, int aS, int a, Coordinates coord) {
		attackPower = aP;
		sensorRange = sR;
		ammoSupply = aS;
		armour = a;
		sitCoordinates = coord;

	}
	
	public Situation(){
		attackPower = 0;
		sensorRange = 0;
		ammoSupply = 0;
		armour = 0;
		sitCoordinates = new Coordinates(0,0);
	}

	public int getAttackPower() {
		return attackPower;
	}

	public int getSensorRange() {
		return sensorRange;
	}

	public int getAmmoSupply() {
		return ammoSupply;
	}

	public int getArmour() {
		return armour;
	}
	
	public int getTotalPower(){
		return attackPower+sensorRange+ammoSupply+armour;
	}
	public Coordinates getSitCoordinates() {
		return sitCoordinates;
	}
}
