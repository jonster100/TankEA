package tankea.world.core;

import java.util.Random;
import java.util.LinkedList;
import tankea.entities.Tank;
import tankea.ea.core.EvolutionAlgorithm;
import tankea.pso.InitiateTankPso;

public class WorldEngine implements Runnable{
	private Position[][] world;
	private LinkedList<Tank> tankList;
	private int noWorldRounds;
	static private int arrayX;
	static private int arrayY;
	private int currentRound;
	private boolean tankTrainingSituations;
	private boolean threadEngineRunning;
	
	/**
	 * WorldEngine Constructor
	 * @param tempRounds the initial number of rounds for the engine to run
	 * @param x the initial horizontal size of the 2d array
	 * @param y the initial vertical size of the 2d array*/
	public WorldEngine(int x,int y) {
		tankList = new LinkedList<Tank>();
		this.tankSetup();
		arrayX=x;
		arrayY=y;
		world = new Position[arrayX][arrayY];
		this.worldSetup();
		this.setupTanksInWorld();
		noWorldRounds = 0;
		currentRound=0;
		threadEngineRunning=false;
		tankTrainingSituations=true;
	}

	/**This method will setup tank objects in the tanks list*/
	private void tankSetup() {
		for (int i = 0; i < 10; i++) {
			tankList.add(new Tank('R', i));
			tankList.add(new Tank('B', i));
		}
	}

	/**This method will add position object into the world 2d array*/
	private void worldSetup() {
		for (int x = 0; x < arrayX; x++) {
			for (int y = 0; y < arrayY; y++) {
				Position tempPos = new Position(x, y);
				world[x][y] = tempPos;
			}
		}
	}
	
	/**this method will add the specified tank into the specified position while also checking position conditions
	 * @param t the specified tank to be added
	 * @param x the coordinate to be used to get position
	 * @param y the coordinate to be used to get position*/
	private void addTankToPosition(Tank t, int x, int y,boolean setupOrNot){
		Position tempPos = world[x][y];
		if(tempPos.getTank()==null||tempPos.getTank().isDead()==true){
			tempPos.addTank(t,setupOrNot);
			t.setTankCoord(tempPos.getCoord());
		}
	}
	
	/**this method will add the specified tank into the global 2d array
	 * @param t the specified tank to be added to the world*/
	private void addTankToWorld(Tank t,boolean setupOrNot){
		boolean loopRun = true;
		while(loopRun){
			Random tempNoGen = new Random();
			int tempX = tempNoGen.nextInt(arrayX);
			int tempY = tempNoGen.nextInt(arrayY);
			this.addTankToPosition(t, tempX, tempY,setupOrNot);
			if(world[tempX][tempY].getTank().equals(t)){
				loopRun=false;
			}
		}
		t.addSituation();
	}

	/**this method will add the created tank into random positions in the world array*/
	private void setupTanksInWorld() {
		for (Tank t : tankList) {
			this.addTankToWorld(t,false);
		}
	}

	/**this method will get a specified position
	 * @return Position returns the specified object*/
	private Position getPos(int x,int y){
		return world[x][y];
	}

	/**this method will print the coorindates of the tanks to be used for testing*/
	public void printTankPositions() {
		System.out.println("\n");
		for (Tank t : tankList) {
			Coordinates tempPos = t.getCoord();
			System.out.println(t.getTeam() + ":" + t.getId() + ":" + tempPos.getX() + "-" + tempPos.getY()
					+ ":isDead=" + t.isDead());
		}
	}
	
	private int tankNavigation(int dC,int cC){
		return (dC<cC)?-1:(dC>cC)?+1:0;
	}
	
	/**this method will move the tanks using a Particle Swarm Optimisation or randomly generated position*/
	private void moveTanks() {
		for (Tank t : tankList) {
			if (t.isDead() == false) {
				InitiateTankPso pso=new InitiateTankPso(this.getTeamList(t.getTeam()),this.getLocalAllyTanks(t),t);	
				int x = pso.getX();
				int y = pso.getY();
				try {
					int nX=t.getCoord().getX()+this.tankNavigation(x, t.getCoord().getX());
					int nY=t.getCoord().getY()+this.tankNavigation(y, t.getCoord().getY());
					this.getPos(t.getCoord().getX(),t.getCoord().getY()).setPositionNull();
					this.addTankToPosition(t, (tankTrainingSituations)?t.moveRandomXCoordinate():nX, (tankTrainingSituations)?t.moveRandomYCoordinate():nY,true);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("ArrayIndexOutOfBounds " + t.getTeam() + ":" + t.getId() + ":" + x + "-" + y);
				}
				t.addSituation();
			}
		}
	}
	
	private LinkedList<Tank> getLocalAllyTanks(Tank t){
		Coordinates tempPosition = t.getCoord();
		LinkedList<Tank> tempTankList=new LinkedList<>();
		for (int x = 0; x < t.getSensorRange(); x++) {
			for (int y = 0; y < t.getSensorRange(); y++) {
				try {
					boolean positionCheck = world[tempPosition.getX() - x][tempPosition.getY() - y]
							.checkTankAtPosition();
					Tank positionGetTank = world[tempPosition.getX() - x][tempPosition.getY() - y]
							.getTank();
					if ((positionCheck == true) && (positionGetTank.getTeam() != t.getTeam())) {
						tempTankList.add(positionGetTank);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					// position does not exist
				}
			}
		}
		return tempTankList;
		
	}
	/**this method will get the sensor range of the tank and go through every position within that range to 
	 * detect a tank. The mthod will then initiate an attack on that tank.
	 * @param currentRound passed through to add to a tank object if it dies*/
	private void tankSensorAction(int currentRound) {
		for (Tank t : tankList) {
			if (t.isDead() == false) {
				Coordinates tempPosition = t.getCoord();
				for (int x = 0; x < t.getSensorRange(); x++) {
					for (int y = 0; y < t.getSensorRange(); y++) {
						try {
							boolean positionCheck = world[tempPosition.getX() - x][tempPosition.getY() - y]
									.checkTankAtPosition();
							Tank positionGetTank = world[tempPosition.getX() - x][tempPosition.getY() - y]
									.getTank();
							if ((positionCheck == true) && (positionGetTank.isDead() == false)
									&& (positionGetTank.getTeam() != t.getTeam())) {
								this.attackTankAction(t, positionGetTank, currentRound);
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							// position does not exist
						}
					}
				}
			}
		}
	}

	/**
	 * this method will print data retaining to the tanks that were in battle
	 * @param t1 tank that initated the attack
	 * @param t2 the defending tank
	 * @param originalTankArmour the iriginal armour of the defending tank to show how much the attack was worth*/
	private void printBattleResult(Tank t1, Tank t2, int originalTankArmour) {
		System.out.println("Attacking Tank: " + t1.getAttackPower() + " - Defending Tank Armour: " + t2.getArmour()
				+ ":" + originalTankArmour);
	}

	/**this method will perform an attack action between an attacking and defending tank
	 * @param at this is the attacking tank
	 * @param dt this is the defending tank
	 * @param currentRound the round the attack is happening in*/
	private void attackTankAction(Tank at, Tank dt, int currentRound) {
		if (at.getAmmoSupply() >= 1) {
			int tempArmour = dt.getArmour();
			dt.decreaseArmour(at.getAttackPower());
			if (dt.getArmour() < 1) {
				dt.setDead(true);
				dt.setRoundTankDiedIn(currentRound);
				world[dt.getCoord().getX()][dt.getCoord().getY()].setTankDiedAtPosition();
			}
			at.decreaseAmmoSupply(1);
			//this.printBattleResult(at, dt, tempArmour);
		}
	}

	/**this method will return all the tanks from the specified team
	 * @param c the team to be returned
	 * @return LinkedList<Tank> the list of tanks returned by the specified team*/
	private LinkedList<Tank> getTeamList(char c) {
		LinkedList<Tank> tempList;
		tempList = new LinkedList<Tank>();
		for (Tank t : tankList) {
			if (t.getTeam() == c) {
				tempList.add(t);
			}
		}
		return tempList;
	}

	/**this method will initiate the Evolutionary Algorithm, which will take two tanks and produce another tank
	 * using the two chosen tanks data.
	 * @param t the team the algorithm will use to produce another tank */
	private void initiateEA(char t) {
		LinkedList<Tank> tempList = this.getTeamList(t);
		EvolutionAlgorithm eAlgorithm = new EvolutionAlgorithm(tempList);
		eAlgorithm.printEAStatus();
		Tank[] newGeneration = eAlgorithm.getNewGeneration();
		for (int i = 0; i < newGeneration.length; i++) {
			this.addTankToWorld(newGeneration[i],false);
		}
	}
	
	public void setTraining(boolean b){
		tankTrainingSituations = b;
	}
	
	public void setNoWorldRounds(int i){
		noWorldRounds=i;
	}
	/**this method get the tanks world
	 * @return Position[][] returns the 2d array of position objects*/
	public Position[][] getWorld(){
		return world;
	}
	
	/**this method will a set number of rounds of the world actions*/
	public void runSetNoRounds(int i){
		for(int x=0;x<i;x++){
			currentRound++;
			this.moveTanks();
			this.tankSensorAction(currentRound);
		}
	}

	/**this method will run a specified number of round of world actions.*/
	public void worldRun() {
		this.runSetNoRounds(noWorldRounds);
		this.initiateEA('B');
		this.initiateEA('R');
	}
	
	public boolean getIfThreadRunning(){return threadEngineRunning;}
	public void setSituationTraining(boolean b){tankTrainingSituations=b;}
	
	/**this method will run when the thread is initiated*/
	@Override
	public void run() {
		threadEngineRunning=true;
		this.worldRun();
		threadEngineRunning=false;
	}

}
