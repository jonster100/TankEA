package tankea.entities;

import tankea.world.core.Coordinates;
import tankea.world.core.Situation;
import java.util.Observable;
import java.util.Random;
import java.util.LinkedList;

public class Tank extends Observable{
	private char team;
	private int id;
	private int attackPower;
	private int sensorRange;
	private int ammoSupply;
	private int currentArmour;
	private int originalArmour;
	private Coordinates tankPosition;
	private int roundTankDiedIn;
	private boolean dead;
	private Coordinates destination;
	private LinkedList<Situation> tankSituations;
	
	/**this constructor is to be used for the pre-setup of the tanks, when the engine is created.
	 * @param tempTeam the initial team the tank is specified to be on
	 * @param tempId the initial id for the tank*/
	public Tank(char tempTeam, int tempId) {
		Random tempNoGenerator = new Random();
		team = tempTeam;
		id = tempId;
		attackPower = tempNoGenerator.nextInt(60);
		sensorRange = tempNoGenerator.nextInt(10);
		ammoSupply = tempNoGenerator.nextInt(10);
		currentArmour = tempNoGenerator.nextInt(100);
		originalArmour = currentArmour;
		dead = false;
		tankSituations = new LinkedList<Situation>();
	}

	/**this constructor is to be used when a new tank is produced through the Evolutionary algorithm.
	 * @param tempTeam the initial team the tank is specified to be on
	 * @param tempId the initial id for the tank
	 * @param tempAP the initial attackPower
	 * @param tempSR the initial sensorRange
	 * @param tempAS the initial ammoSupply
	 * @param tempA the initial currentArmour and originalArmour*/
	public Tank(char tempTeam, int tempId, int tempAP, int tempSR, int tempAS, int tempA) {
		team = tempTeam;
		id = tempId;
		attackPower = tempAP;
		sensorRange = tempSR;
		ammoSupply = tempAS;
		currentArmour = tempA;
		originalArmour = currentArmour;
		dead = false;
		tankSituations=new LinkedList<Situation>();
	}

	/**
	 * @return int returns the tanks originalArmour*/
	public int getOriginalArmour() {
		return originalArmour;
	}
	/**
	 * @return int returns the round the tank died in*/
	public int getRoundTankDiedIn() {
		return roundTankDiedIn;
	}
	/**
	 * @param roundTankDiedIn sets the specified round for the tank to of died in*/
	public void setRoundTankDiedIn(int roundTankDiedIn) {
		this.roundTankDiedIn = roundTankDiedIn;
	}
	/**
	 * @return boolean returns whether the tank is dead yet*/
	public boolean isDead() {
		return dead;
	}
	/**
	 * @param dead sets the tank to dead or not dead*/
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	/**
	 * @param decreaseByNo decreases the attack power of the tank by the specified amount*/
	public void decreaseAttackPower(int decreaseByNo) {
		attackPower -= decreaseByNo;
	}
	/**
	 * @param decreaseByNo decreases the sensor range of the tank by the specified amount*/
	public void decreaseSensorRange(int decreaseByNo) {
		sensorRange -= decreaseByNo;
	}
	/**
	 * @param decreaseByNo decreases the ammo supply by the specified amount*/
	public void decreaseAmmoSupply(int decreaseByNo) {
		ammoSupply -= decreaseByNo;
	}
	/**
	 * @param decreaseByNo decreases the armour by the specified amount*/
	public void decreaseArmour(int decreaseByNo) {
		currentArmour -= decreaseByNo;
	}
	/**
	 * @param tempPos sets the tanks tankPosition by the specified Coordinates*/
	public void setTankCoord(Coordinates tempPos) {
		tankPosition = tempPos;
	}
	/**
	 * @param char returns the team the tank is on*/
	public char getTeam() {
		return team;
	}
	/**
	 * @param int reutrns the tanks id*/
	public int getId() {
		return id;
	}
	/**
	 * @param int returns the tank attackPower*/
	public int getAttackPower() {
		return attackPower;
	}
	/**
	 * @param int returns the tanks sensor range*/
	public int getSensorRange() {
		return sensorRange;
	}
	/**
	 * @param int returns the ammo supply for the tank*/
	public int getAmmoSupply() {
		return ammoSupply;
	}
	/**
	 * @param int returns the current armour for the tank*/
	public int getArmour() {
		return currentArmour;
	}
	/**this method will generate a random number between the specified numbers.
	 * @return int returns the randomly generated number*/
	public int moveRandomXCoordinate() {
		boolean loopCheck = false;
		int toBeReturned = 20;
		while (loopCheck != true) {
			Random tempNoGen = new Random();
			toBeReturned = tankPosition.getX() + (tempNoGen.nextInt(3) - 1);
			if ((toBeReturned >= 0) && (toBeReturned <= 49)) {
				loopCheck = true;
			}
		}
		return toBeReturned;
	}
	/**this method will generate a random number between the specified numbers.
	 * @return int returns the randomly generated number*/
	public int moveRandomYCoordinate() {
		boolean loopCheck = false;
		int toBeReturned = 20;
		while (loopCheck != true) {
			Random tempNoGen = new Random();
			toBeReturned = tankPosition.getY() + (tempNoGen.nextInt(3)-1);
			if ((toBeReturned >= 0) && (toBeReturned <= 49)) {
				loopCheck = true;
			}
		}
		return toBeReturned;
	}
	/**
	 * @return Coordintes returns the tanks position*/
	public Coordinates getCoord() {
		return tankPosition;
	}

	/**
	 * @return String returns all the information for the tank*/
	public String getTankData() {
		return "Team: " + team + "\nID: " + id + "\nAttack Power: " + attackPower + "\nSensor Range: " + sensorRange
				+ "\nAmmo Supply: " + ammoSupply + "\nArmour: " + originalArmour + "\nRound Tank Died in: "
				+ roundTankDiedIn;
	}
	
	public LinkedList<Situation> getAllSituations(){
		return tankSituations;
	}
	
	public Situation getCurrentSituation(){
		return tankSituations.peekLast();
	}
	public void addSituation(){
		Situation tempSit = new Situation(attackPower,sensorRange,ammoSupply,currentArmour,tankPosition);
		tankSituations.addLast(tempSit);
	}
	public void setDestination(Coordinates tempCoord){
		destination=tempCoord;
	}
	public Coordinates getDestination(){
		return destination;
	}
	
	public int getTankTotalPower(){
		return attackPower+sensorRange+ammoSupply+currentArmour;
	}
}
